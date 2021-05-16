package se.gustaf.learning.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.MathUtil;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.MenuQuantitable;
import org.mineacademy.fo.menu.MenuTools;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonConversation;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.model.MenuQuantity;
import org.mineacademy.fo.remain.CompColor;
import org.mineacademy.fo.remain.CompMaterial;
import se.gustaf.learning.PlayerCache;
import se.gustaf.learning.conversation.ExpPrompt;
import se.gustaf.learning.rpg.ClassRegister;
import se.gustaf.learning.rpg.PlayerClass;
import se.gustaf.learning.settings.Settings;
import se.gustaf.learning.tool.DiamondChangingTool;
import se.gustaf.learning.tool.HellfireRocket;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PreferencesPanelMenu extends Menu {
	// Time --> Day / Night
	// Monster Spawn Egg (paged menu)
	// Set chat color
	
	private final Button timeButton;
	private final Button mobEggButton;
	private final Button chatColorButton;
	private final Button levelButton;
	private final Button toolsButton;
	private final Button classesButton;
	
	private final Button expButton;
	
	
	public PreferencesPanelMenu() {
		
		setTitle(Settings.Menu.MENU_PREFERENCES_TITLE);
		setSize(9 * 6);
		
		setSlotNumbersVisible();
		
		timeButton = new Button() {
			@Override
			public void onClickedInMenu(final Player player, final Menu menu, final ClickType clickType) {
				final Boolean isDay = isDay();
				
				player.getWorld().setFullTime(isDay ? 13000 : 1000);
				restartMenu("&2Set the time to " + (isDay ? "Night" : "Day"));
			}
			
			@Override
			public ItemStack getItem() {
				final boolean isDay = isDay();
				return ItemCreator.of(isDay ? CompMaterial.SUNFLOWER : CompMaterial.RED_BED,
						"Change time",
						"",
						"Currently: " + (isDay ? "Day" : "Night"),
						"Click to switch between",
						"the day and night")
						.build()
						.make();
			}
			
			private boolean isDay() {
				return getViewer().getWorld().getFullTime() < 12500;
			}
		};
		
		mobEggButton = new ButtonMenu(
				new EggSelectionMenu(),
				CompMaterial.ENDERMAN_SPAWN_EGG,
				"Monster egg menu",
				"",
				"Click to open monster",
				"egg selection menu"
		);
		
		chatColorButton = new ButtonMenu(
				new ChatColorSelectionMenu(),
				CompMaterial.INK_SAC,
				"Chat color menu",
				"",
				"Click to open chat",
				"color selection menu"
		);
		
		levelButton = new ButtonMenu(
				new LevelMenu(),
				CompMaterial.ELYTRA,
				"Level menu",
				"",
				"Click to open level",
				"menu to change your level"
		);
		
		toolsButton = new ButtonMenu(
				new ToolsMenu(),
				CompMaterial.BLAZE_ROD,
				"Tools menu",
				"",
				"Click to open tools",
				"menu to get admin tools"
		);
		
		classesButton = new ButtonMenu(
				new ClassSelectionMenu(),
				CompMaterial.IRON_CHESTPLATE,
				"Classes menu",
				"",
				"Click to open classes",
				"menu to select your RPG class"
		);
		
		expButton = new ButtonConversation(
				new ExpPrompt(),
				CompMaterial.EXPERIENCE_BOTTLE,
				"Experience prompt",
				"",
				"Click to open a prompt",
				"giving you experience levels."
		);
	}
	
	@Override
	public ItemStack getItemAt(final int slot) {
		if (slot == 9 * 1 + 1) {
			return timeButton.getItem();
		}
		
		if (slot == 9 * 1 + 3) {
			return mobEggButton.getItem();
		}
		
		if (slot == 9 * 1 + 5) {
			return chatColorButton.getItem();
		}
		
		if (slot == 9 * 1 + 7) {
			return levelButton.getItem();
		}
		
		if (slot == 9 * 3 + 1) {
			return toolsButton.getItem();
		}
		
		if (slot == 9 * 3 + 3) {
			return classesButton.getItem();
		}
		
		if (slot == 9 * 3 + 5) {
			return expButton.getItem();
		}
		
		
		return null;
	}
	
	@Override
	protected String[] getInfo() {
		return new String[]{
				"This menu contains simple features",
				"For players or administrators to",
				"enhance their gameplay experience"
		};
	}
	
	private final class EggSelectionMenu extends MenuPagged<EntityType> {
		
		protected EggSelectionMenu() {
			
			
			super(
					PreferencesPanelMenu.this,
					Arrays.asList(EntityType.values())
							.stream()
							.filter((entityType) -> entityType.isSpawnable() && entityType.isAlive() && (entityType == EntityType.SHEEP || CompMaterial.makeMonsterEgg(entityType) != CompMaterial.SHEEP_SPAWN_EGG))
							.collect(Collectors.toList())
			);
			
			setTitle(Settings.Menu.MENU_EGG_SELECTION_TITLE);
		}
		
		@Override
		protected ItemStack convertToItemStack(final EntityType entityType) {
			return ItemCreator.of(CompMaterial.makeMonsterEgg(entityType), "Spawn " + ItemUtil.bountifyCapitalized(entityType)).build().make();
		}
		
		@Override
		protected void onPageClick(final Player player, final EntityType entityType, final ClickType clickType) {
			player.getInventory().addItem(ItemCreator.of(CompMaterial.makeMonsterEgg(entityType)).build().make());
			animateTitle("Egg added into your inventory");
		}
		
		@Override
		protected String[] getInfo() {
			return new String[]{
					"Click an egg to get it",
					"into your inventory."
			};
		}
	}
	
	private final class ChatColorSelectionMenu extends MenuPagged<ChatColor> {
		
		protected ChatColorSelectionMenu() {
			super(
					PreferencesPanelMenu.this,
					Arrays.asList(ChatColor.values())
							.stream()
							.filter(chatColor -> chatColor.isColor())
							.collect(Collectors.toList())
			);
			
			setTitle(Settings.Menu.MENU_CHAT_COLOR_TITLE);
		}
		
		@Override
		protected ItemStack convertToItemStack(final ChatColor color) {
			return ItemCreator.ofWool(CompColor.fromChatColor(color)).name("Select " + ItemUtil.bountifyCapitalized(color.name()))
					.build().make();
		}
		
		@Override
		protected void onPageClick(final Player player, final ChatColor color, final ClickType clickType) {
			final PlayerCache cache = PlayerCache.getCache(player);
			
			cache.setColor(color);
			animateTitle("Changed chat color to " + ItemUtil.bountifyCapitalized(color.name()));
		}
		
		@Override
		protected String[] getInfo() {
			return new String[]{
					"Click a color to use it",
					"in your chat messages."
			};
		}
	}
	
	private final class LevelMenu extends Menu implements MenuQuantitable {
		
		
		@Setter @Getter
		private MenuQuantity quantity = MenuQuantity.ONE;
		private final Button quantityButton;
		private final Button levelButton;
		
		
		private LevelMenu() {
			super(PreferencesPanelMenu.this);
			
			setTitle(Settings.Menu.MENU_LEVEL_TITLE);
			
			quantityButton = getEditQuantityButton(this);
			
			levelButton = new Button() {
				@Override public void onClickedInMenu(final Player player, final Menu menu, final ClickType clickType) {
					final PlayerCache cache = PlayerCache.getCache(getViewer());
					final int nextLevel = MathUtil.range(cache.getLevel() + getNextQuantity(clickType), 0, 64);
					
					cache.setLevel(nextLevel);
					restartMenu("Changed level to " + nextLevel);
				}
				
				@Override public ItemStack getItem() {
					final PlayerCache cache = PlayerCache.getCache(getViewer());
					
					return ItemCreator.of(
							CompMaterial.ELYTRA,
							"Change your level",
							"",
							"Current: " + cache.getLevel(),
							"",
							"&8(&7Mouse click&8)",
							"< -{q} +{q} >".replace("{q}", quantity.getAmount() + "")
					)
							.amount(cache.getLevel() == 0 ? 1 : cache.getLevel())
							.build().make();
				}
			};
		}
		
		@Override
		public ItemStack getItemAt(final int slot) {
			if (slot == getCenterSlot()) {
				return levelButton.getItem();
			}
			
			if (slot == getSize() - 5) {
				return quantityButton.getItem();
			}
			
			return null;
		}
		
		@Override
		protected String[] getInfo() {
			return new String[]{
					"Click the elytra to",
					"level up/down yourself."
			};
		}
	}
	
	private final class ToolsMenu extends MenuTools {
		private ToolsMenu() {
			super(PreferencesPanelMenu.this);
			setSize(9 * 3);
			setTitle(Settings.Menu.MENU_TOOLS_TITLE);
		}
		
		@Override protected Object[] compileTools() {
			return new Object[]{
					HellfireRocket.getInstance(), DiamondChangingTool.getInstance()
			};
		}
		
		@Override protected String[] getInfo() {
			return new String[]{
					"Select your tools",
					"for some fun!"
			};
		}
	}
	
	private final class ClassSelectionMenu extends MenuPagged<PlayerClass> {
		public ClassSelectionMenu() {
			super(PreferencesPanelMenu.this, ClassRegister.getInstance().getLoadedClasses());
			setSize(9 * 2);
		}
		
		@Override protected ItemStack convertToItemStack(final PlayerClass playerClass) {
			final PlayerCache cache = PlayerCache.getCache(getViewer());
			
			
			return ItemCreator.of(
					playerClass.getIcon(),
					playerClass.getName(),
					"",
					"Click to select",
					"this class."
			)
					.glow(cache.getPlayerClass() != null && cache.getPlayerClass().getName().equals(playerClass.getName()))
//					.glow(cache.getPlayerClass() != null && cache.getPlayerClass().getName())
					.build().make();
		}
		
		@Override
		protected void onPageClick(final Player player, final PlayerClass playerClass, final ClickType clickType) {
			
			if (!PlayerUtil.hasPerm(player, "my.class.perm." + playerClass.getName())) {
				animateTitle("You can't select this class!");
				
				return;
			}
			
			final PlayerCache cache = PlayerCache.getCache(player);
			cache.setPlayerClass(playerClass);
			
			playerClass.applyFor(player);
			
			restartMenu("Selected " + playerClass.getName() + " class!");
		}
		
		@Override protected String[] getInfo() {
			return new String[]{
					"Click the classes to",
					"select them"
			};
		}
	}
}
