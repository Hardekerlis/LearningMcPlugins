package se.gustaf.learning.conversation;

import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimpleConversation;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.remain.CompSound;

public class ExpPrompt extends SimplePrompt {
	public ExpPrompt() {
		super(false);
	}
	
	@Override
	protected String getPrompt(final ConversationContext conversationContext) {
		return "&6Write the amount of xp levels you want to receive:";
	}
	
	@Override
	protected boolean isInputValid(final ConversationContext context, final String input) {
		if (!Valid.isInteger(input)) {
			return false;
		}
		
		final int level = Integer.parseInt(input);
		
		return level > 0 && level < 9009;
	}
	
	@Override
	protected String getFailedValidationText(final ConversationContext context, final String invalidInput) {
		return "&cOnly specify a number between 1 and 9009.";
	}
	
	@Nullable
	@Override
	protected Prompt acceptValidatedInput(@NotNull final ConversationContext context, @NotNull final String input) {
		final int level = Integer.parseInt(input);
		final Player player = getPlayer(context);
		
		player.setLevel(level);
		CompSound.LEVEL_UP.play(player);
		
		tell(context, "&6You now have " + level + " levels");
		
		return Prompt.END_OF_CONVERSATION;
	}
	
	@Override
	public void onConversationEnd(final SimpleConversation conversation, final ConversationAbandonedEvent event) {
		// Do stuff when convo ends
	}
}
