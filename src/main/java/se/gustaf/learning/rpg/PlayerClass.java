package se.gustaf.learning.rpg;

import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

@Getter
public class PlayerClass extends YamlConfig {
	
	private double damageModifier;
	private double hitCooldown;
	private CompMaterial icon;
	
	public PlayerClass(final String className) {
		setHeader(new String[]{
				"Welcome to the main class settings file!",
				"Line 2"}
		);
		
		loadConfiguration("rpg-class.yml", "classes/" + className + (className.endsWith(".yml") ? "" : ".yml"));
	}
	
	@Override protected void onLoadFinish() {
		damageModifier = getDouble("Damage_Modifier");
		hitCooldown = getDouble("Hit_Cooldown");
		icon = getMaterial("Icon");
	}
	
	public void applyFor(final Player player) {
		player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damageModifier);
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(hitCooldown);
	}
	
	@Override public String getName() {
		return getFileName().replace(".yml", "");
	}
}
