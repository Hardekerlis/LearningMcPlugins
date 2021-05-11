package se.gustaf.learning.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.mineacademy.fo.Common;

public class PlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChatFirst(final AsyncPlayerChatEvent event) {
//		Common.log("Lowest priority " + event.getPlayer().getName() + ": " + event.getMessage());
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
//		Common.log("Normal priority (Cancelling) " + event.getPlayer().getName() + ": " + event.getMessage());

//		event.setCancelled(true);
		// TODO Must be wrapped into a sync runnable to make this command run from the main thread
//		if (event.getMessage().equalsIgnoreCase("hello")) {
//			Bukkit.dispatchCommand(event.getPlayer(), "firework");
//		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerChatLate(final AsyncPlayerChatEvent event) {
//		Common.log("High priority (Cancelled = " + event.isCancelled() + ") " + event.getPlayer().getName() + ": " + event.getMessage());

//		event.setCancelled(false);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChatLast(final AsyncPlayerChatEvent event) {
//		Common.log("Monitor priority (Cancelled = " + event.isCancelled() + ") " + event.getPlayer().getName() + ":	" + event.getMessage());
	}
	
	// --------------------------------------------------------
	
	@EventHandler
	public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
		Common.log("Message " + event.getMessage());
// 		TODO Not a good way to stop /plugins, give players negative plugin permission instead
//		if (event.getMessage().equalsIgnoreCase("/plugins")) {
//			Common.tell(event.getPlayer(), "You cannot view plugins, well you can but differently..");
//
//			event.setCancelled(true);
//		}
	}
}
