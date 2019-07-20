package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;

public class FunctionSetInvulnerable extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Boolean value = args[0].getBoolean();
		
		return setInvulnerable(target, value);
	}

	@Override
	public String name() {
		return "setInvulnerable";
	}
	
	public boolean setInvulnerable(LivingEntity target, Boolean value) {
		target.setInvulnerable(value);
		return true;
	}

}
