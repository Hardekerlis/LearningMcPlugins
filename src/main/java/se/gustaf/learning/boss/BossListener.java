package se.gustaf.learning.boss;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.model.SimpleEnchant;
import org.mineacademy.fo.remain.CompColor;
import org.mineacademy.fo.remain.CompItemFlag;
import org.mineacademy.fo.remain.CompMaterial;
import se.gustaf.learning.boss.skill.BossSkill;

import java.util.List;

public class BossListener implements Listener {

//	@EventHandler
//	public void onCreatureSpawn(final CreatureSpawnEvent event) {
//
//		final LivingEntity entity = event.getEntity();
//
//		if (entity.getType() != EntityType.SKELETON) {
//			return;
//		}
//
//		Remain.setCustomName(entity, Common.colorize("&4Skeleton Warrior"));
//
////		Common.log("Skeleton had: " + entity.getHealth() + "HP");
//
//		entity.setHealth(5);
//
//		final EntityEquipment equipment = entity.getEquipment();
//
//		// Better
////		equipment.setArmorContents();
//
//		equipment.setHelmetDropChance(1.0F /* 100% */);
//		equipment.setHelmet(buildArmor(
//				CompMaterial.LEATHER_HELMET,
//				"&cWarrior Helmet",
//				"",
//				"The legendary warrior",
//				"armor with enchants"
//				).build().makeSurvival()
//		);
//
//		equipment.setChestplateDropChance(1.0F /* 100% */);
//		equipment.setChestplate(buildArmor(
//				CompMaterial.LEATHER_CHESTPLATE,
//				"&cWarrior Chestplate",
//				"",
//				"The legendary warrior",
//				"armor with enchants"
//				).build().makeSurvival()
//		);
//
//		equipment.setLeggingsDropChance(1.0F /* 100% */);
//		equipment.setLeggings(buildArmor(
//				CompMaterial.LEATHER_LEGGINGS,
//				"&cWarrior Leggings",
//				"",
//				"The legendary warrior",
//				"armor with enchants"
//				).build().makeSurvival()
//		);
//
//		equipment.setBootsDropChance(1.0F /* 100% */);
//		equipment.setBoots(buildArmor(
//				CompMaterial.LEATHER_BOOTS,
//				"&cWarrior Boots",
//				"",
//				"The legendary warrior",
//				"armor with enchants"
//				).build().makeSurvival()
//		);
//
//		entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
//
//		entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
//
//
//		final Spider spider = entity.getWorld().spawn(entity.getLocation(), Spider.class);
//
//		spider.setPassenger(entity);
////		spider.getPassengers().add(entity); // --> For latest MC versions
//
//		spider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(1);
//	}
	
	private ItemCreator.ItemCreatorBuilder buildArmor(final CompMaterial material, final String title, final String... lore) {
		return ItemCreator.of(material, title, lore)
				.enchant(new SimpleEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1))
				.color(CompColor.RED)
				.flag(CompItemFlag.HIDE_ATTRIBUTES);
	}
	
	@EventHandler
	public void onCombust(final EntityCombustEvent event) {
		final Boss boss = findBoss(event.getEntity());
		
		if (boss != null) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(final EntityDeathEvent event) {
		final Boss boss = findBoss(event.getEntity());
		
		if (boss != null) {
			final List<ItemStack> drops = event.getDrops();
			final LivingEntity entity = event.getEntity();
			final BossEquipment equipment = boss.getEquipment();
			Common.log(String.valueOf(equipment));
			
			drops.clear();
			
			// TODO The skeletons doesnt drop anything. equipment.getHelmet returns AIR.
			drops.add(buildUndamagedItem(equipment.getHelmet()));
			drops.add(buildUndamagedItem(equipment.getChestplate()));
			drops.add(buildUndamagedItem(equipment.getLeggings()));
			drops.add(buildUndamagedItem(equipment.getBoots()));
//		drops.addAll(Arrays.asList(equipment.getArmorContents()));// --> None of the values can be null, helmet, chestplate, leggings, boots
//		Common.log(String.valueOf(drops));
			
			if (boss.getDroppedExp() != null) {
				event.setDroppedExp(boss.getDroppedExp());
			}
			
			final Entity vehicle = entity.getVehicle();
			
			if (vehicle != null) {
				vehicle.remove();
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(final EntityDamageByEntityEvent event) {
		final Entity damager = event.getDamager();
		final Entity victim = event.getEntity();
		
		if (!(damager instanceof LivingEntity) || !(victim instanceof LivingEntity)) {
			return;
		}
		
		Boss boss = findBoss(damager);
		
		if (boss != null) {
			final SpawnedBoss spawnedBoss = new SpawnedBoss(boss, (LivingEntity) damager);
			
			for (final BossSkill skill : boss.getSkills()) {
				skill.onBossAttack(spawnedBoss, event);
			}
		}
		
		boss = findBoss(victim);
		
		if (boss != null) {
			final SpawnedBoss spawnedBoss = new SpawnedBoss(boss, (LivingEntity) damager);
			
			for (final BossSkill skill : boss.getSkills()) {
				skill.onBossAttack(spawnedBoss, event);
			}
		}
	}
	
	private ItemStack buildUndamagedItem(final ItemCreator.ItemCreatorBuilder item) {
		return item.damage(0).build().makeSurvival();
	}
	
	private Boss findBoss(final Entity entity) {
		return BossRegister.getInstance().findBoss(entity);
	}
}
