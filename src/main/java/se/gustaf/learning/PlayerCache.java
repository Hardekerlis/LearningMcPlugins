package se.gustaf.learning;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mineacademy.fo.settings.YamlSectionConfig;
import se.gustaf.learning.rpg.ClassRegister;
import se.gustaf.learning.rpg.PlayerClass;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PlayerCache extends YamlSectionConfig {
	private static Map<UUID, PlayerCache> cacheMap = new HashMap<>();
	
	
	private PlayerClass playerClass;
	private ChatColor color;
	private int level = 1;
	
	protected PlayerCache(final String uuid) {
		super(uuid);
		
		loadConfiguration(null, "data.db");
	}
	
	@Override
	protected void onLoadFinish() {
		
		if (isSet("Class")) {
			final String className = getString("Class");
			
			playerClass = ClassRegister.getInstance().getClass(className);
		}
		
		if (isSet("Color")) {
			color = get("Color", ChatColor.class);
		}
		
		
	}
	
	public void setPlayerClass(final PlayerClass playerClass) {
		this.playerClass = playerClass;
		
		save("Class", playerClass.getName());
	}
	
	public void setColor(final ChatColor color) {
		this.color = color;
		
		save("Color", color.name());
	}
	
	public void setLevel(final int level) {
		this.level = level;
		
		save("Level", level);
	}
	
	public static PlayerCache getCache(final Player player) {
		PlayerCache cache = cacheMap.get(player.getUniqueId());
		
		if (cache == null) {
			cache = new PlayerCache(player.getUniqueId().toString());
			
			cacheMap.put(player.getUniqueId(), cache);
		}
		
		return cache;
	}
}
