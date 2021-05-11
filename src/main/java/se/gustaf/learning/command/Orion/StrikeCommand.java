package se.gustaf.learning.command.Orion;

import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class StrikeCommand extends SimpleSubCommand {
	protected StrikeCommand(final SimpleCommandGroup parent) {
		super(parent, "strike|s");
		
		setMinArguments(1);
		setUsage("<target>");
		setDescription("Hide the target from you.");
	}
	
	@Override
	protected void onCommand() {
		final Player target = findPlayer(args[0]);
		
		target.getWorld().strikeLightning(target.getLocation());
		tell("&cStroke lightning at " + target.getName());
	}
}
