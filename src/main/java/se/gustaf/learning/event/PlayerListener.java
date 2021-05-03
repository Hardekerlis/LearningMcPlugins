package se.gustaf.learning.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.mineacademy.fo.Common;

public class PlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChatFirst(final AsyncPlayerChatEvent event) {
		Common.log("Lowest priority " + event.getPlayer().getName() + ": " + event.getMessage());
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		Common.log("Normal priority " + event.getPlayer().getName() + ": " + event.getMessage());
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChatLate(final AsyncPlayerChatEvent event) {
		Common.log("High priority " + event.getPlayer().getName() + ": " + event.getMessage());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChatLast(final AsyncPlayerChatEvent event) {
		Common.log("Monitor priority " + event.getPlayer().getName() + ": " + event.getMessage());
	}
}
