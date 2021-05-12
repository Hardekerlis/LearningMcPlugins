package se.gustaf.learning.tool;

import lombok.Getter;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Rocket;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompParticle;

public class HellfireRocket extends Rocket {
	
	@Getter
	private static final Tool instance = new HellfireRocket();
	
	private HellfireRocket() {
		super(DragonFireball.class, 2, 10);
	}
	
	@Override public ItemStack getItem() {
		return ItemCreator.of(
				CompMaterial.FIRE_CHARGE,
				"&cHellfire",
				"",
				"Deliver some cookies"
		)
				.build().make();
	}
	
	@Override protected void onLaunch(final Projectile projectile, final Player shooter) {
		Common.tell(shooter, "&8[&aRocket&8] &7Hey grandma, here are some cookies for ya!");
	}
	
	@Override protected void onExplode(final Projectile projectile, final Player shooter) {
		Common.tell(shooter, "&8[&aRocket&8] &7Cookies delivered, grandma not responding yet...");
	}
	
	@Override protected void onFlyTick(final Projectile projectile, final Player shooter) {
		CompParticle.FIREWORKS_SPARK.spawn(projectile.getLocation(), 1D);
	}
	
	//	@Override protected boolean canExplode(final Projectile projectile, final Player shooter) {
//		return super.canExplode(projectile, shooter);
//	}
//
//	@Override protected boolean canLaunch(final Player shooter, final Location location) {
//		return super.canLaunch(shooter, location);
//	}
}
