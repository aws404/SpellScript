package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptVariable;

public class FunctionFire extends SpellScriptFunction{

	@Override
	public void runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Integer time = args[0].getInt();
		
		fire(target, time);
	}

	@Override
	public String name() {
		return "fire";
	}
	
	public boolean fire(LivingEntity target, Integer time) {
		target.setFireTicks(time*2/100);
		return true;
	}

}
