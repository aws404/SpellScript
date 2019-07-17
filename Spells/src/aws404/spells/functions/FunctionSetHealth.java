package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.DataType;

public class FunctionSetHealth extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Integer amount = (int) plugin.convertType(args[0], DataType.INTEGER);
		
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
