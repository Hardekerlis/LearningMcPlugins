package se.gustaf.learning;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import se.gustaf.learning.rpg.PlayerClass;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PlayerCache {
	private static Map<UUID, PlayerCache> cacheMap = new HashMap<>();
	
	
	private PlayerClass playerClass;
	private ChatColor color;
	private int level = 1;
	
	public static PlayerCache getCache(final Player player) {
		PlayerCache cache = cacheMap.get(player.getUniqueId());
		
		if (cache == null) {
			cache = new PlayerCache();
			
			cacheMap.put(player.getUniqueId(), cache);
		}
		
		return cache;
	}
}
