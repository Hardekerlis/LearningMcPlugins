package se.gustaf.learning.command;

import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.CompSound;
import se.gustaf.learning.LearningPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TaskCommand extends SimpleCommand {
	private final Map<UUID, BukkitTask> runningTasks = new HashMap<>();
	
	public TaskCommand() {
		super("task");
		
		setMinArguments(1);
		setUsage("<sync|async|start|stop>");
	}
	
	@Override
	protected void onCommand() {
		checkConsole();
		
		final String param = args[0].toLowerCase();
		
		if ("sync".equals(param)) {
			// --> Play sound and spawn a pet
			
			// Traditional way of running tasks later
			new BukkitRunnable() {
				
				@Override
				public void run() {
					tell("Spawning a cat (1/2)");
					
					CompSound.LEVEL_UP.play(getPlayer());
					getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), EntityType.CAT);
					
				}
			}.runTaskLater(LearningPlugin.getInstance(), 3 * 20);
			
			// Recommended way of running tasks later
			Common.runLater(6 * 20, () -> {
				tell("Spawning a sheep (2/2)");
				
				CompSound.SHEEP_SHEAR.play(getPlayer());
				getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), EntityType.SHEEP);
			});
			
		} else if ("async".equals(param)) {
			
			// This will fail since we cannot add entites asynchronously
			// connecting to the internet, using libaries other than spigot etc. or when you know what you are doing :)
			Common.runLaterAsync(20, () -> {
				tell("Spawning a zombie async");
				
				CompSound.ZOMBIE_HURT.play(getPlayer());
				getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), EntityType.ZOMBIE);
			});
			
		} else if ("start".equals(param)) {
			checkBoolean(!runningTasks.containsKey(getPlayer().getUniqueId()), "A task is already running!");
			
			runningTasks.put(getPlayer().getUniqueId(), Common.runTimer(20, () -> {
				tell("&6Sending you a timer message...");
			}));
		} else if ("stop".equals(param)) {
			checkBoolean(runningTasks.containsKey(getPlayer().getUniqueId()), "No task is running!");
			
			final BukkitTask task = runningTasks.remove(getPlayer().getUniqueId());
			task.cancel();
			
			tell("&6The task is now cancelled!");
			
		} else {
			returnInvalidArgs();
		}
	}
}
