package se.gustaf.learning;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.settings.YamlStaticConfig;
import se.gustaf.learning.command.*;
import se.gustaf.learning.command.Orion.OrionCommandGroup;
import se.gustaf.learning.event.PlayerListener;
import se.gustaf.learning.event.ProjectileListener;
import se.gustaf.learning.rpg.ClassRegister;
import se.gustaf.learning.settings.Settings;

import java.util.Arrays;
import java.util.List;

public class LearningPlugin extends SimplePlugin {
	
	private BroadcasterTask broadcasterTask;
	
	@Override
	protected void onPluginStart() {
		
		// Dont!
		getLogger().info("All works!");
		System.out.println("All works from sout!");
		// Do!
		Common.log("Hello from Foundation!");
		
		// Tasks
		broadcasterTask = new BroadcasterTask();
		broadcasterTask.runTaskTimer(this, 0, Settings.BROADCASTER_DELAY.getTimeTicks());
		
		ClassRegister.getInstance().loadClasses();
		
		// Commands
		registerCommand(new FireworkCommand());
		registerCommand(new SpawnEntityCommand());
		registerCommand(new PermCommand());
		registerCommand(new TaskCommand());
		registerCommand(new PreferencesCommand());
		registerCommand(new RpgCommand());
		registerCommand(new BoardingCommand());
		
		// Command groups
		registerCommands("orion|or", new OrionCommandGroup());
		
		registerEvents(new PlayerListener());
		registerEvents(new ProjectileListener());
	}
	
	@Override
	protected void onPluginStop() {
		cleanBeforeReload();
	}
	
	@Override
	protected void onPluginReload() {
		cleanBeforeReload();
	}
	
	private void cleanBeforeReload() {
		if (broadcasterTask != null) {
			try {
				broadcasterTask.cancel();
			} catch (final IllegalStateException ex) {
			}
			
			broadcasterTask = null;
		}
	}
	
	@Override public List<Class<? extends YamlStaticConfig>> getSettings() {
		return Arrays.asList(Settings.class);
	}
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		Common.tellLater(2, player, "&eHello &9from &cFoundation");
		
		if (Settings.Join.GIVE_EMERALD) {
			player.getInventory().addItem(new ItemStack(Material.EMERALD, 5));
		}
	}
	
	@EventHandler
	public void onEntityDamage(final EntityDamageByEntityEvent event) {
		final Entity victim = event.getEntity();
		
		if ((event.getDamager() instanceof Player && victim instanceof Cow) && Settings.Entity_Hit.EXPLODE_COWS) {
			victim.getWorld().createExplosion(victim.getLocation(), Settings.Entity_Hit.EXPLOSION_POWER.floatValue());
		}
	}
}
