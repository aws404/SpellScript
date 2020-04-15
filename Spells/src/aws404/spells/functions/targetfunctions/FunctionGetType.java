package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionGetType extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		String typeName = args[0].toString();
		EntityType type = EntityType.valueOf(typeName);
		
		if (type == null) {
			plugin.sendError(typeName + " is an Unkown entity type.");
			return SpellScriptReturnValue.ERROR;
		}
			
		
		if (target.getType() == type) return SpellScriptReturnValue.TRUE;
		else return SpellScriptReturnValue.FALSE;
	}

	@Override
	public String name() {
		return "getType";
	}
	

}
