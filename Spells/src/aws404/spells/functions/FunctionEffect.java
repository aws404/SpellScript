package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import aws404.spells.SpellScriptVariable;

public class FunctionEffect extends SpellScriptFunction {

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
		PotionEffectType type = PotionEffectType.getByName(args[0].getString().toUpperCase());
		if (type == null) {
			plugin.sendError(args[0].getString() + " is not a valid potion effect!");
			return false;
		}
		int durration = args[1].getInt();
		int level = args[2].getInt();
		boolean hideParticles = args[3].getBoolean();
		
		return effect(target, type, durration, level, hideParticles);
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
