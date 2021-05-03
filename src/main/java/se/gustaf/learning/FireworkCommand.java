package se.gustaf.learning;

import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

public class FireworkCommand extends SimpleCommand {
	
	protected FireworkCommand() {
		super("firework");
	}
	
	@Override
	protected void onCommand() {
		// Available fields:
		// sender
		// args
		
		checkConsole();
	
		final Player player = getPlayer();
		
		player.getWorld().spawn(player.getLocation(), Firework.class);
		tell("&cFIREWORK!!!");
	}
}
