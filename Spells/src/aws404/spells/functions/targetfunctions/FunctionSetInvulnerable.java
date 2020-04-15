package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionSetInvulnerable extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Boolean value = args[0].toBoolean();
		
		target.setInvulnerable(value);
		
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "setInvulnerable";
	}
	

}
