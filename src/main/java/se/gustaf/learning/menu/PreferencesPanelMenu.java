package se.gustaf.learning.menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompColor;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PreferencesPanelMenu extends Menu {
	// Time --> Day / Night
	// Monster Spawn Egg (paged menu)
	// Set chat color
	
	private final Button timeButton;
	private final Button mobEggButton;
	
	//	private final Button chatColorButton;
//
	public PreferencesPanelMenu() {
		
		setTitle("&4User preferences");
		setSize(9 * 4);
		
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
	}
	
	@Override
	public ItemStack getItemAt(final int slot) {
		if (slot == 9 * 1 + 2) {
			return timeButton.getItem();
		}
		
		if (slot == 9 * 1 + 6) {
			return mobEggButton.getItem();
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
			
			setTitle("Select a mob egg");
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
			
			setTitle("Select a chat color");
		}
		
		@Override
		protected ItemStack convertToItemStack(final ChatColor color) {
			return ItemCreator.ofWool(CompColor.fromChatColor(color)).name("Spawn " + ItemUtil.bountifyCapitalized(color)).
					build().make();
		}
		
		@Override
		protected void onPageClick(final Player player, final ChatColor color, final ClickType clickType) {
			animateTitle("Changed chat color to " + ItemUtil.bountifyCapitalized(color));
		}
		
		@Override
		protected String[] getInfo() {
			return new String[]{
					"Click a color to use it",
					"in your chat messages."
			};
		}
	}
}
