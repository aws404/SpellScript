package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;

public class FunctionSetHealth extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Integer amount = args[0].getInt();
		
		return setHealth(target, amount);
	}

	@Override
	public String name() {
		return "setHealth";
	}
	
	public boolean setHealth(LivingEntity target, Integer amount) {
		target.setHealth(amount);
		return true;
	}

}
