package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionEffect extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		PotionEffectType type = PotionEffectType.getByName(args[0].toString().toUpperCase());
		if (type == null) {
			plugin.sendError(args[0].toString() + " is not a valid potion effect!");
			return SpellScriptReturnValue.ERROR;
		}
		
		int durration = args[1].toInt()*2/100;
		int level = args[2].toInt();
		boolean hideParticles = args[3].toBoolean();

		PotionEffect potion = new PotionEffect(type, durration, level, hideParticles);
		
		if (target.addPotionEffect(potion))
			return SpellScriptReturnValue.CONTINUE;
		else
			return SpellScriptReturnValue.ERROR;
	}

	@Override
	public String name() {
		return "effect";
	}


}
