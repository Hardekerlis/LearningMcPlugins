package se.gustaf.learning.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import se.gustaf.learning.tool.KittyArrow;

public class LearningPluginUtil {
	
	public static boolean checkKittyArrow(final Player player, final Cancellable event) {
		final ItemStack arrow = PlayerUtil.getFirstItem(player, KittyArrow.getInstance().getItem());
		
		if (arrow == null) {
			Common.tell(player, "&cYou lack the Kitty Arrow required to shoot from this tool!");
			
			event.setCancelled(true);
			return false;
		}
		
		if (player.getGameMode() == GameMode.SURVIVAL) {
			PlayerUtil.takeOnePiece(player, arrow);
		}
		
		return true;
	}
}
