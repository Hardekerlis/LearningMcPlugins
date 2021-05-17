package se.gustaf.learning.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.EntityUtil;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompParticle;
import org.mineacademy.fo.remain.CompSound;
import se.gustaf.learning.util.LearningPluginUtil;

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
		
		if (!LearningPluginUtil.checkKittyArrow(player, event)) {
			return;
		}
		
		final Cat cat = player.getWorld().spawn(player.getEyeLocation(), Cat.class);
		
		cat.setVelocity(player.getEyeLocation().getDirection().multiply(2.0D));
		
		CompSound.SUCCESSFUL_HIT.play(player);
		
		EntityUtil.trackFlying(cat, () -> {
			CompParticle.BLOCK_CRACK.spawnWithData(cat.getLocation(), CompMaterial.TNT);
		});
		
		EntityUtil.trackFalling(cat, () -> {
			cat.remove();
			cat.getWorld().createExplosion(cat.getLocation(), 4F);
			
			CompSound.ANVIL_LAND.play(player.getLocation());
		});
	}
	
	@Override
	protected boolean ignoreCancelled() {
		return false;
	}
}
