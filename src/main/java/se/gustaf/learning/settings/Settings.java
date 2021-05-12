package se.gustaf.learning.settings;

import org.mineacademy.fo.model.SimpleTime;
import org.mineacademy.fo.settings.SimpleSettings;

public class Settings extends SimpleSettings {
	
	@Override protected int getConfigVersion() {
		return 1;
	}
	
	public static class Menu {
		
		public static String MENU_PREFERENCES_TITLE;
		public static String MENU_EGG_SELECTION_TITLE;
		public static String MENU_CHAT_COLOR_TITLE;
		public static String MENU_TOOLS_TITLE;
		public static String MENU_LEVEL_TITLE;
		
		private static void init() {
			pathPrefix("Menu");
			
			MENU_PREFERENCES_TITLE = getString("Preferences_Title");
			MENU_EGG_SELECTION_TITLE = getString("Egg_Selection_Title");
			MENU_CHAT_COLOR_TITLE = getString("Chat_Color_Selection_Title");
			MENU_TOOLS_TITLE = getString("Tools_Menu");
			MENU_LEVEL_TITLE = getString("Level_Menu");
			
			pathPrefix(null);
		}
	}
	
	public static class Join {
		public static Boolean GIVE_EMERALD;
		
		private static void init() {
			pathPrefix("Join");
			
			GIVE_EMERALD = getBoolean("Give_Emerald");
			
			pathPrefix(null);
		}
	}
	
	public static class Entity_Hit {
		public static Boolean EXPLODE_COWS;
		public static Double EXPLOSION_POWER;
		
		private static void init() {
			pathPrefix("Entity_Hit");
			
			EXPLODE_COWS = getBoolean("Explode_Cows");
			EXPLOSION_POWER = getDouble("Power");
			
			pathPrefix(null);
		}
	}
	
	public static SimpleTime BROADCASTER_DELAY;
	
	private static void init() {
		pathPrefix(null);
		
		BROADCASTER_DELAY = getTime("Broadcaster_Delay");
	}
}
