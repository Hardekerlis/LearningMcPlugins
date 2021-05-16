package se.gustaf.learning.command;

import org.mineacademy.fo.command.SimpleCommand;
import se.gustaf.learning.conversation.OnboardingConversation;

public class BoardingCommand extends SimpleCommand {
	public BoardingCommand() {
		super("survey|boarding");
	}
	
	@Override
	protected void onCommand() {
		checkConsole();

//		final File file = FileUtil.getFile("survey/" + getPlayer().getUniqueId());
		
		new OnboardingConversation().start(getPlayer());
	}
}
