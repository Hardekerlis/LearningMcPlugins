package se.gustaf.learning.conversation;

import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.FileUtil;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.conversation.SimpleConversation;
import org.mineacademy.fo.conversation.SimplePrefix;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnboardingConversation extends SimpleConversation {
	@Override
	protected Prompt getFirstPrompt() {
		return new NamePrompt();
	}
	
	@Override
	protected ConversationPrefix getPrefix() {
		return new SimplePrefix("&6[Application]&7 ");
	}

//	@Override
//	protected ConversationCanceller getCanceller() {
//		return new SimpleCanceller("one", "two", "three");
//	}
	
	
	@Override
	protected void onConversationEnd(final ConversationAbandonedEvent event) {
//		event.gracefulExit(); --> will be on true if the last prompt is null, or false if we cancelled it with getCanceller
		final Conversable player = event.getContext().getForWhom();
		final String prefix = getPrefix().getPrefix(event.getContext());
		
		if (!event.gracefulExit()) {
			tell(player, prefix + "Your survey application has been cancelled.");
		}
	}
	
	enum Boarding {
		NAME,
		JOIN_REASON,
		PLAY_PREFERENCE,
		EXPECTATION
	}
	
	class NamePrompt extends SimplePrompt {
		@Override
		protected String getPrompt(final ConversationContext conversationContext) {
			return "What is your name?";
		}
		
		@Nullable
		@Override
		protected Prompt acceptValidatedInput(@NotNull final ConversationContext context, @NotNull final String input) {
			context.setSessionData(Boarding.NAME, input);
			
			return new JoiningReasonPrompt();
		}
	}
	
	class JoiningReasonPrompt extends SimplePrompt {
		@Override
		protected String getPrompt(final ConversationContext context) {
			return "What brings you to this server, " + context.getSessionData(Boarding.NAME) + "?";
		}
		
		@Nullable
		@Override
		protected Prompt acceptValidatedInput(@NotNull final ConversationContext context, @NotNull final String input) {
			context.setSessionData(Boarding.JOIN_REASON, input);
			
			return new GamePreferencePrompt();
		}
	}
	
	class GamePreferencePrompt extends SimplePrompt {
		@Override
		protected String getPrompt(final ConversationContext context) {
			return "What servers do you like playing on?";
		}
		
		@Nullable
		@Override
		protected Prompt acceptValidatedInput(@NotNull final ConversationContext context, @NotNull final String input) {
			context.setSessionData(Boarding.PLAY_PREFERENCE, input);
			
			return new ExpectationsPrompt();
		}
	}
	
	class ExpectationsPrompt extends SimplePrompt {
		@Override
		protected String getPrompt(final ConversationContext context) {
			return "What are you looking forward to see here, " + context.getSessionData(Boarding.NAME) + "?";
		}
		
		@Nullable
		@Override
		protected Prompt acceptValidatedInput(@NotNull final ConversationContext context, @NotNull final String input) {
			context.setSessionData(Boarding.EXPECTATION, input);
			
			final Player player = getPlayer(context);
			final List<String> lines = new ArrayList<>();
			
			lines.add(Common.chatLine());
			lines.add("Date: " + TimeUtil.getFormattedDateShort());
			
			for (final Map.Entry<Object, Object> entry : context.getAllSessionData().entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					final String asked = entry.getKey().toString();
					
					if (!asked.startsWith("Asked_")) {
						lines.add(ItemUtil.bountifyCapitalized(asked) + ":" + entry.getValue().toString());
					}
					
				}
			}
			
			FileUtil.write("survey/" + getPlayer(context).getUniqueId() + ".txt", lines);
			
			tell(context, getPrefix().getPrefix(context) + "Thank you for finishing the survey, here is your reward!");
			player.getInventory().addItem(ItemCreator.of(
					CompMaterial.NETHER_STAR,
					"&cCrate key",
					"",
					"Secret key to unlock",
					"bonuses in the game."
			)
					.build().make());
			
			return Prompt.END_OF_CONVERSATION;
		}
	}
}
