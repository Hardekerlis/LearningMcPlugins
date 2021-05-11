package se.gustaf.learning.command.Orion;

import org.mineacademy.fo.command.SimpleCommandGroup;

public class OrionCommandGroup extends SimpleCommandGroup {
	
	@Override
	protected void registerSubcommands() {
		registerSubcommand(new StrikeCommand(this));
		registerSubcommand(new HideCommand(this));
		registerSubcommand(new FireCommand(this));
	}
	
	@Override
	protected String getCredits() {
		return "Visit domain.com for more information.";
	}
}
