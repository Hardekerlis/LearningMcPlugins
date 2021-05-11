package se.gustaf.learning.command;

import org.mineacademy.fo.command.SimpleCommand;
import se.gustaf.learning.menu.PreferencesPanelMenu;

public class PreferencesCommand extends SimpleCommand {
	public PreferencesCommand() {
		super("preferences|pref");
		
	}
	
	@Override
	protected void onCommand() {
		checkConsole();
		new PreferencesPanelMenu().displayTo(getPlayer());
//		final InventoryDrawer drawer = InventoryDrawer.of(9 * 3, "&1User preferences");
//		drawer.setItem(0, ItemCreator.of(CompMaterial.DIAMOND,
//				"&bShiny diamond",
//				"First lore line",
//				"Second lore line")
//				.glow(true)
//				.build().make());
//		drawer.setItem(9 * 1 + 4, new ItemStack(Material.GOLD_INGOT));
//
//		drawer.pushItem(ItemCreator.of(CompMaterial.DIAMOND_AXE).flag(CompItemFlag.HIDE_ATTRIBUTES).build().make());
//
//		drawer.display(getPlayer());
	}
}
