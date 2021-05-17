package se.gustaf.learning.event;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.remain.CompParticle;
import se.gustaf.learning.LearningPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProjectileListener implements Listener {
	
	private final Set<UUID> shotEggs = new HashSet<>();
	
	@EventHandler
	public void onProjectileLaunch(final ProjectileLaunchEvent event) {
		final Projectile shot = event.getEntity();
		
		if (shot.getShooter() instanceof Player) {
			if (shot instanceof Egg) {
				shotEggs.add(shot.getUniqueId());
			} else if (shot instanceof Arrow) {
				new BukkitRunnable() {
					@Override
					public void run() {
						
						if (!shot.isValid() || shot.isOnGround()) {
							cancel();
							
							return;
						}
						
						CompParticle.FIREWORKS_SPARK.spawn(shot.getLocation());
					}
				}.runTaskTimer(LearningPlugin.getInstance(), 0, 1);
			}
			
			
		}
	}
	
	@EventHandler
	public void onProjectileHit(final ProjectileHitEvent event) {
		final Projectile shot = event.getEntity();
		
		if (shot instanceof Egg && shotEggs.contains(shot.getUniqueId())) {
			shot.getWorld().spawn(shot.getLocation(), Creeper.class);
		}
	}
	
	@EventHandler
	public void creatureSpawn(final CreatureSpawnEvent event) {
		if (event.getEntityType() == EntityType.CHICKEN) {
			event.setCancelled(true);
		}
	}
// ProjectileLaunchEvent.getHandlerList().unregister((Plugin) LearningPlugin.getInstance());
// ProjectileHitEvent.getHandlerList().unregister((Plugin) LearningPlugin.getInstance());

}


//-Xmx2G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M