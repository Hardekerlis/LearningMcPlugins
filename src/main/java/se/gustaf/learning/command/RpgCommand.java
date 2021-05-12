package se.gustaf.learning.command;

import org.mineacademy.fo.command.SimpleCommand;
import se.gustaf.learning.rpg.ClassRegister;

public class RpgCommand extends SimpleCommand {
	public RpgCommand() {
		super("rpg");
		
		setMinArguments(1);
		setUsage("<new>");
	}
	
	@Override protected void onCommand() {
		final String param = args[0];
		
		if ("new".equals(param)) {
			checkArgs(2, "You must also set the class name");
			final String className = args[1];
			
			ClassRegister.getInstance().createClass(className);
			tell("&6Class " + className + " has been created");
		}
	}
}
