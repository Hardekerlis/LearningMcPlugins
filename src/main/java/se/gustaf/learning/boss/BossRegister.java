package se.gustaf.learning.boss;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.collection.StrictList;
import org.mineacademy.fo.remain.CompMetadata;

import java.util.Collections;
import java.util.List;

public class BossRegister {
	
	@Getter
	private static final BossRegister instance = new BossRegister();
	
	private final StrictList<Boss> bosses = new StrictList<>();
	
	private BossRegister() {
		add(new BossWarrior());
	}
	
	public void add(final Boss boss) {
		bosses.add(boss);
	}
	
	public void remove(final String name) {
		final Boss boss = findBoss(name);
		Valid.checkNotNull(boss, "Cannot remove non-existing boss " + name);
		
		bosses.remove(boss);
	}
	
	public Boss findBoss(final Entity entity) {
		if (!(entity instanceof LivingEntity)) {
			return null;
		}
		
		final String bossName = CompMetadata.getMetadata(entity, Boss.BOSS_TAG);
//		Common.log(bossName);
		
		return bossName != null ? findBoss(bossName) : null;
	}
	
	public Boss findBoss(/*@NotNull*/ final String name) {
		Valid.checkNotNull(name);
		
		for (final Boss boss : bosses) {
			if (boss.getName().equals(name)) {
				return boss;
			}
		}
		
		return null;
	}
	
	public List<Boss> getBosses() {
		return Collections.unmodifiableList(bosses.getSource());
	}
	
	public List<String> getBossesNames() {
		return Common.convert(bosses, boss -> boss.getName());
	}
}
