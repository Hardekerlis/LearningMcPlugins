package se.gustaf.learning.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KittyCannon extends Tool {
	
	@Getter
	private final static Tool instance = new KittyCannon();
	
	@Override
	public ItemStack getItem() {
		return ItemCreator.of(
				CompMaterial.STICK,
				"&dKitty Cannon",
				"",
				"Right click air to launch",
				"some flying kitties...",
				"PS: they'll explode on impact!"
		)
				.glow(true)
				.build().make();
	}
	
	@Override
	protected void onBlockClick(final PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR) {
			return;
		}
		
		final Player player = event.getPlayer();
		final ItemStack arrow = PlayerUtil.getFirstItem(player, KittyArrow.getInstance().getItem());
		
		if (arrow == null) {
			Common.tell(player, "&cYou lack the Kitty Arrow required to shoot from this tool!");
			
			event.setCancelled(true);
			return;
		}
		
		if (player.getGameMode() == GameMode.SURVIVAL) {
			PlayerUtil.takeOnePiece(player, arrow);
		}
		
		final Cat cat = player.getWorld().spawn(player.getEyeLocation(), Cat.class);

//		cat.setVelocity();
	}
	
	@Override
	protected boolean ignoreCancelled() {
		return false;
	}
}
