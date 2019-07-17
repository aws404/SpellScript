package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import aws404.spells.DataType;

public class FunctionEffect extends Function {

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		PotionEffectType type = PotionEffectType.getByName(((String) plugin.convertType(args[0], DataType.STRING)).toUpperCase());
		if (type == null) {
			plugin.sendError((String) plugin.convertType(args[0], DataType.STRING) + " is not a valid potion effect!");
			return;
		}
		int durration = (int) plugin.convertType(args[1], DataType.INTEGER);
		int level = (int) plugin.convertType(args[2], DataType.INTEGER);
		boolean hideParticles = (boolean) plugin.convertType(args[3], DataType.BOOLEAN);
		
		effect(target, type, durration, level, hideParticles);
	}

	@Override
	public String name() {
		return "effect";
	}
	
	public boolean effect(LivingEntity target, PotionEffectType type, int durration, int level, boolean hideParticles) {
		durration = durration*2/100;
		PotionEffect potion = new PotionEffect(type, durration, level, hideParticles);
		
		target.addPotionEffect(potion);
		return true;
	}

}
