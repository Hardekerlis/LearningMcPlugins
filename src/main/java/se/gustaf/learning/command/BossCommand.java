package se.gustaf.learning.command;

import org.mineacademy.fo.TabUtil;
import org.mineacademy.fo.command.SimpleCommand;
import se.gustaf.learning.boss.Boss;
import se.gustaf.learning.boss.BossRegister;

import java.util.ArrayList;
import java.util.List;

public class BossCommand extends SimpleCommand {
	public BossCommand() {
		super("boss");
		
		setMinArguments(1);
		setUsage("<bossName>");
	}
	
	@Override protected void onCommand() {
		checkConsole();
		
		final BossRegister bossRegister = BossRegister.getInstance();
		
		final String bossName = String.join(" ", args);
		final Boss boss = bossRegister.findBoss(bossName);
		
		checkNotNull(boss,
				"Boss named " + bossName + " does not exist. Available: " + String.join(", ", bossRegister.getBossesNames())
		);
		
		boss.spawn(getPlayer().getLocation());
	}
	
	@Override protected List<String> tabComplete() {
		
		if (args.length > 0) {
			final String bossName = String.join(" ", args);
			
			return TabUtil.complete(bossName, BossRegister.getInstance().getBossesNames());
		}
		
		return new ArrayList<>();
	}
}
