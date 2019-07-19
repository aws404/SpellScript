package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptVariable;

public class FunctionSetHealth extends SpellScriptFunction{

	@Override
	public void runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Integer amount = args[0].getInt();
		
		setHealth(target, amount);
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
