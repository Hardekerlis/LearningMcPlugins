package se.gustaf.learning.boss.skill;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.RandomUtil;
import se.gustaf.learning.boss.SpawnedBoss;

public class BlockAttackBossSkill extends BossSkill {
	
	public BlockAttackBossSkill() {
		super("Block Attack");
	}
	
	@Override public void onBossAttack(final SpawnedBoss spawnedBoss, final EntityDamageByEntityEvent event) {
		if (RandomUtil.chanceD(0.50)) {
			event.setCancelled(true);
			
			if (event.getEntity() instanceof Player) {
				Common.tell(event.getEntity(), "Boss attack has been blocked");
			}
		}
	}
}
