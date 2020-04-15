package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionExecute extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		//By utilising the execute command, permissions are ignored
		
		String cmd = args[0].toString();
		
		//scoreboard tags are used as @e no longer can select using UUID
		String tag ="entityExecution"+target.getUniqueId(); //make a unique scoreboard tag to apply
		
		target.addScoreboardTag(tag); //apply the tag
		
		Boolean success = plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "execute as @e[tag="+tag+"] run " + cmd); //execute the command as the entity with the tag
		
		target.removeScoreboardTag(tag); //remove the tag
		
		if (success)
			return SpellScriptReturnValue.CONTINUE;
		else
			return SpellScriptReturnValue.ERROR;
	}

	@Override
	public String name() {
		return "execute";
	}

}
