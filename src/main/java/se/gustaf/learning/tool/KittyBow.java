package se.gustaf.learning.tool;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.mineacademy.fo.BlockUtil;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.RandomUtil;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompMetadata;
import se.gustaf.learning.util.LearningPluginUtil;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KittyBow extends Tool implements Listener {
	
	@Getter
	private final static Tool instance = new KittyBow();
	
	private final Map<Location, Vector> explodedLocations = new HashMap<>();
	
	
	@Override public ItemStack getItem() {
		return ItemCreator.of(
				CompMaterial.BOW,
				"&dKitty Bow",
				"",
				"Right click air to launch",
				"explosive  arrows...",
				"PS: they'll damage blocks!"
		)
				.glow(true)
				.build().make();
	}
	
	@EventHandler
	public void onProjectileLaunch(final ProjectileLaunchEvent event) {
		
		if (!(event.getEntity().getShooter() instanceof Player)) {
			return;
		}
		
		final Projectile projectile = event.getEntity();
		final Player player = (Player) event.getEntity().getShooter();
		
		
		if (!ItemUtil.isSimilar(player.getItemInHand(), getItem())) {
			return;
		}
		
		if (!LearningPluginUtil.checkKittyArrow(player, event)) {
			event.setCancelled(true);
			
			
			return;
		}
		
		CompMetadata.setMetadata(projectile, "KittyArrow");
	}
	
	
	@EventHandler
	public void onProjectileHit(final ProjectileHitEvent event) {
		if (!CompMetadata.hasMetadata(event.getEntity(), "KittyArrow")) {
			return;
		}
		
		final Projectile projectile = event.getEntity();
		
		projectile.remove();
		
		explodedLocations.put(projectile.getLocation().getBlock().getLocation(), projectile.getVelocity());
		
		projectile.getWorld().createExplosion(projectile.getLocation(), 20F);
	}
	
	@EventHandler
	public void onBlockExplode(final BlockExplodeEvent event) {
		final Vector vector = explodedLocations.remove(event.getBlock().getLocation());
		
		if (vector != null) {
			for (final Block block : event.blockList()) {
				if (RandomUtil.chanceD(0.25)) {
					BlockUtil.shootBlock(block, vector, 1.00);
				} else {
					block.setType(CompMaterial.AIR.getMaterial());
				}
				
				
				event.setYield(0F);
			}
		}
	}
	
	@Override protected void onBlockClick(final PlayerInteractEvent event) {
	
	}
	
	@Override protected boolean ignoreCancelled() {
		return false;
	}
}
