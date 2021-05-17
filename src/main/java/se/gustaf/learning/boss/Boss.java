package se.gustaf.learning.boss;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.collection.StrictList;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompAttribute;
import org.mineacademy.fo.remain.CompMetadata;
import org.mineacademy.fo.remain.Remain;
import se.gustaf.learning.boss.skill.BossSkill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class Boss {
	
	public static final String BOSS_TAG = "LearningBoss";
	
	private final String name;
	private final EntityType type;
	
	private String customName;
	private Double health;
	private BossEquipment equipment;
	private Integer droppedExp;
	private List<BossAttribute> attributes = new ArrayList<>();
	private List<BossPotionEffect> potionEffects = new ArrayList<>();
	private EntityType passenger;
	private StrictList<BossSkill> skills = new StrictList<>();
	
	public final void spawn(final Location location) {
		Valid.checkBoolean(type.isSpawnable() && type.isAlive(), "The boss must be alive and spawnable!");
		
		final LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
		
		Remain.setCustomName(entity, name);
		
		if (health != null) {
			entity.setHealth(health);
		}
		
		if (equipment != null) {
			final EntityEquipment equipment = entity.getEquipment();
			
			equipment.setHelmet(this.equipment.getHelmet().build().makeSurvival());
			equipment.setChestplate(this.equipment.getChestplate().build().makeSurvival());
			equipment.setLeggings(this.equipment.getLeggings().build().makeSurvival());
			equipment.setBoots(this.equipment.getBoots().build().makeSurvival());
		}
		
		if (attributes != null) {
			for (final BossAttribute attribute : attributes) {
				attribute.getAttribute().set(entity, attribute.getValue());
			}
		}
		
		if (potionEffects != null) {
			for (final BossPotionEffect potionEffect : potionEffects) {
				entity.addPotionEffect(new PotionEffect(potionEffect.getPotion(), Integer.MAX_VALUE, potionEffect.getAmplifier()));
			}
		}
		
		if (passenger != null) {
			final LivingEntity spawnendPassenger = (LivingEntity) location.getWorld().spawnEntity(location, passenger);
			
			spawnendPassenger.setPassenger(entity);
		}
		
		CompMetadata.setMetadata(entity, BOSS_TAG, name);
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getCustomName() {
		return Common.colorize(name);
	}
	
	public final Double getHealth() {
		return health;
	}
	
	protected final void setEquipment(final ItemCreator.ItemCreatorBuilder helmet,
																		final ItemCreator.ItemCreatorBuilder chestplate,
																		final ItemCreator.ItemCreatorBuilder leggings,
																		final ItemCreator.ItemCreatorBuilder boots
	) {
		this.equipment = new BossEquipment(helmet, chestplate, leggings, boots);
	}
	
	public final Integer getDroppedExp() {
		return droppedExp;
	}
	
	public final BossEquipment getEquipment() {
		return equipment;
	}
	
	protected final void addAttribute(final CompAttribute attribute, final double value) {
		attributes.add(new BossAttribute(attribute, value));
	}
	
	protected final void addPotionEffect(final PotionEffectType potion, final int amplifier) {
		potionEffects.add(new BossPotionEffect(potion, amplifier));
	}
	
	protected final EntityType getPassenger() {
		return passenger;
	}
	
	public final void addSkille(final BossSkill skill) {
		skills.add(skill);
		
		// save() --> to save to file
	}
	
	public final List<BossSkill> getSkills() {
		return Collections.unmodifiableList(skills.getSource());
	}
	
	@Override
	public final boolean equals(final Object obj) {
		return obj instanceof Boss && ((Boss) obj).getName().equals(getName());
	}
}
