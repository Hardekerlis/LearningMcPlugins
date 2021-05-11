package se.gustaf.learning.command;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntityCommand extends SimpleCommand {
	
	public SpawnEntityCommand() {
		super("spawnentity|se"); // /spawnentity or /se will run this command
		
		setMinArguments(1);
		setUsage("<type> [x] [y] [z]");
		setDescription("Spawns an entity at your or given location");
	}
	
	@Override
	protected void onCommand() {
		// spawnentity <type> [x] [y] [z]

//		if (args.length == 0) {
//			returnTell("Usage: /{label} <type> or <type> [x] [y] [z]");
//		}
//		checkArgs(1, "Usage: /{label} <type> or <type> [x] [y] [z]");
		
		checkConsole();
		
		final EntityType entityType = findEnum(EntityType.class, args[0], "&cEntity named {enum} is invalid.");
		checkBoolean(entityType.isAlive() && entityType.isSpawnable(), "&cEntity " + entityType + " is not spawnable");
		
		final Location location;
		
		if (args.length == 4) {
			final int x = findNumber(1, "&cPlease specify the x coordinate as a number.");
			final int y = findNumber(2, "&cPlease specify the y coordinate as a number.");
			final int z = findNumber(3, "&cPlease specify the z coordinate as a number.");
			
			location = new Location(getPlayer().getWorld(), x, y, z);
		} else {
			location = getPlayer().getLocation();
		}
		
		location.getWorld().spawnEntity(location, entityType);
		tell("&aSpawned " + entityType + " at " + Common.shortLocation(location));
	}
	
	@Override
	protected List<String> tabComplete() {
		// <type> [x] [y] [z]
		
		if (isPlayer()) {
			switch (args.length) {
				case 1:
					return completeLastWord(EntityType.values());
				case 2:
					return completeLastWord(getPlayer().getLocation().getBlockX());
				case 3:
					return completeLastWord(getPlayer().getLocation().getBlockY());
				case 4:
					return completeLastWord(getPlayer().getLocation().getBlockZ());
			}
		}
		
		return new ArrayList<>();
	}
}
