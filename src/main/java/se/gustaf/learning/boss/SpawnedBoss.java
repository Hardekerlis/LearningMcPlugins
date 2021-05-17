package se.gustaf.learning.boss;

import lombok.Data;
import org.bukkit.entity.LivingEntity;

@Data
public final class SpawnedBoss {
	private final Boss boss;
	private final LivingEntity entity;
}
