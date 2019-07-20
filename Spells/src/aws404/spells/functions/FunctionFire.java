package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;

public class FunctionFire extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Integer time = args[0].getInt();
		
		return fire(target, time);
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
