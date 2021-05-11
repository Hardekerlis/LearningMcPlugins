package se.gustaf.learning.command.Orion;

import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;
import se.gustaf.learning.LearningPlugin;

public class HideCommand extends SimpleSubCommand {
	protected HideCommand(final SimpleCommandGroup parent) {
		super(parent, "hide|h");
		
		setMinArguments(1);
		setUsage("<target>");
		setDescription("Strike lightning at the target");

//		setPermission(null) - Everyone can use it
	}
	
	@Override
	protected void onCommand() {
		checkConsole();
		
		final Player target = findPlayer(args[0]);
		
		checkBoolean(target.getName().equalsIgnoreCase(getPlayer().getName()), "You cannot hide from yourself");
		
		if (target.canSee(getPlayer())) {
			target.hidePlayer(LearningPlugin.getInstance(), getPlayer());
			tell("&aPlayer " + target.getName() + " can no longer see you");
		} else {
			target.showPlayer(LearningPlugin.getInstance(), getPlayer());
			tell("&6Player " + target.getName() + " can now see you");
		}
	}
}
