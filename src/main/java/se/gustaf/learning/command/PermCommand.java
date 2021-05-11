package se.gustaf.learning.command;

import org.bukkit.permissions.PermissionAttachment;
import org.mineacademy.fo.command.SimpleCommand;
import se.gustaf.learning.LearningPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PermCommand extends SimpleCommand {
	private final static String DEMO_PERMISSION = "my.simple.permission";
	
	// TODO highest coding standards say to only have command execution code in the command class,
	// So you are recommended to move this into a seperate PermissionManager class of your plugin
	private final Map<UUID, PermissionAttachment> permissions = new HashMap<>();
	
	public PermCommand() {
		super("perm");
		setPermission(null);
//		Common.log(String.valueOf(hasPerm(DEMO_PERMISSION)));
		
		setMinArguments(1);
		setUsage("<add|remove>");
	}
	
	@Override
	protected void onCommand() {
		final String param = args[0].toLowerCase();
		
		if ("add".equals(param)) {
			checkBoolean(!permissions.containsKey(getPlayer().getUniqueId()), "Run /{label} remove to remove the demo permission first.");
			
			
			tell("Do you have the demo permission before? " + hasPerm(DEMO_PERMISSION));
			final PermissionAttachment perm = getPlayer().addAttachment(LearningPlugin.getInstance(), DEMO_PERMISSION, true);
			tell("Do you have the demo permission after? " + hasPerm(DEMO_PERMISSION));
			
			permissions.put(getPlayer().getUniqueId(), perm);
		} else if ("remove".equals(param)) {
			final PermissionAttachment perm = permissions.get(getPlayer().getUniqueId());
			checkNotNull(perm, "Run /{label} add first to give yourself the demo permission");
			permissions.remove(getPlayer().getUniqueId());
			
			tell("Do you have the demo permission before? " + hasPerm(DEMO_PERMISSION));
			getPlayer().removeAttachment(perm);
			tell("Do you have the demo permission after? " + hasPerm(DEMO_PERMISSION));
			
			
		} else {
			returnInvalidArgs();
		}
	}
}
