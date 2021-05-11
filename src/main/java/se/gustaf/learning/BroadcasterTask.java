package se.gustaf.learning;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.RandomUtil;
import org.mineacademy.fo.remain.Remain;

import java.util.Arrays;
import java.util.List;

public class BroadcasterTask extends BukkitRunnable {
	
	private final List<String> messages = Arrays.asList(
			"Checekout our website https://google.com",
			"You can support our server and purchase ranks at https://google.com/donate",
			"Use the /orion command to run some fancy features of our custom plugin LearningPlugins"
	);
	
	@Override
	public void run() {
		final String prefix = "&8[&4Tip&8] &7";
		final String message = RandomUtil.nextItem(messages);
		for (final Player player : Remain.getOnlinePlayers()) {
			Common.tell(player, prefix + message);
		}
	}
}
