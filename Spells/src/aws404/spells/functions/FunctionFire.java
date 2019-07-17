package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.DataType;

public class FunctionFire extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Integer time = (int) plugin.convertType(args[0], DataType.INTEGER);
		
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
