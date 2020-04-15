package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;
import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionSetTag extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		String tagName = args[0].toString();
		
		target.addScoreboardTag(tagName);
		
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "setTag";
	}
	

}
