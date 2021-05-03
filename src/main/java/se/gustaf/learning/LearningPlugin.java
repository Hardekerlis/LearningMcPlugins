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

public class LearningPlugin extends SimplePlugin {
	
	@Override
	protected void onPluginStart() {
		
		// Dont!
		getLogger().info("All works!");
		System.out.println("All works from sout!");
		
		// Do!
		Common.log("Hello from Foundation!");
		
		registerCommand(new FireworkCommand());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		Common.tellLater(2, player, "&eHello &9from &cFoundation");
		
//		player.getInventory().addItem(new ItemStack(Material.DIRT, 64));
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		
		if(event.getDamager() instanceof Player && victim instanceof Cow) {
			victim.getWorld().createExplosion(victim.getLocation(), 5);
		}
	}
}
