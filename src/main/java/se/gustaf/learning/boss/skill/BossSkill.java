package se.gustaf.learning.boss.skill;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import se.gustaf.learning.boss.SpawnedBoss;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BossSkill {
	
	private final String name;
	
	public void onBossAttack(final SpawnedBoss spawnedBoss, final EntityDamageByEntityEvent event) {
	
	}
	
	public void onBossDamaged(final SpawnedBoss spawnedBoss, final EntityDamageByEntityEvent event) {
	
	}
	
	
	@Override public boolean equals(final Object obj) {
		return obj instanceof BossSkill && ((BossSkill) obj).getName().equals(this.name);
	}
}
