package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;

public class FunctionStop extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptArgument[] args) {
		plugin.sendError("Spell Stoped");
		
		return false;
	}

	@Override
	public String name() {
		return "stop";
	}
	


}
