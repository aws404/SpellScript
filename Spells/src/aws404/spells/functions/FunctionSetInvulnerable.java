package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptVariable;

public class FunctionSetInvulnerable extends SpellScriptFunction{

	@Override
	public void runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Boolean value = args[0].getBoolean();
		
		setInvulnerable(target, value);
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
