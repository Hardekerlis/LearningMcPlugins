package se.gustaf.learning.command;

import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommand;

public class FireworkCommand extends SimpleCommand {
	
	public FireworkCommand() {
		super("firework");
	}
	
	@Override
	protected void onCommand() {
		// Available fields:
		// sender -> the CommandSender, which can be Player or ConsoleCommandSender, use instanceof to find out or use checkConsole();
		// args
		
		hasPerm("my.super.duper.permission");
		
		checkConsole();
		
		final Player player = getPlayer();
		
		player.getWorld().spawn(player.getLocation(), Firework.class);
		tell("&cFIREWORK!!!");
	}
}
