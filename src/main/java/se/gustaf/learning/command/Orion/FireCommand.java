package se.gustaf.learning.command.Orion;

import org.bukkit.entity.Player;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;

public class FireCommand extends SimpleSubCommand {
	protected FireCommand(final SimpleCommandGroup parent) {
		super(parent, "fire|f");
		
		setMinArguments(1);
		setUsage("<target>");
		setDescription("Set the target on fire!");
		
		setPermission("orion.fire");
	}
	
	@Override
	protected void onCommand() {
		final Player target = findPlayer(args[0]);
		
		checkBoolean(PlayerUtil.hasPerm(target, "dont.put.me.on.fire"), "You cannot put " + target.getName() + " on fire!");
		
		target.setFireTicks(20 * 4);
		tell("&cSet " + target.getName() + " on fire for four seconds.");
	}
}
