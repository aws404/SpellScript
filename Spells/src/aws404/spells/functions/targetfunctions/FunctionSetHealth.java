package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionSetHealth extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Integer amount = args[0].toInt();
		
		target.setHealth(amount);
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "setHealth";
	}
}
