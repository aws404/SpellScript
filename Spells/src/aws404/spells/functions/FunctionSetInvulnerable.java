package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.DataType;

public class FunctionSetInvulnerable extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Boolean value = (boolean) plugin.convertType(args[0], DataType.BOOLEAN);
		
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
