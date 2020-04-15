package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;
import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionGetTag extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		String tagName = args[0].toString();
		
		Boolean value = target.getScoreboardTags().contains(tagName);
		
		if (value) return SpellScriptReturnValue.TRUE;
		else return SpellScriptReturnValue.FALSE;
	}

	@Override
	public String name() {
		return "getTag";
	}
	

}
