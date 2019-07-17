package aws404.spells.functions;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import aws404.spells.DataType;

public class FunctionAdjustHealth extends Function {

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Integer amount = (int) plugin.convertType(args[0], DataType.INTEGER);
		
		adjustHealth(target, amount);
	}

	@Override
	public String name() {
		return "adjustHealth";
	}
	
	public boolean adjustHealth(LivingEntity target, Integer amount) {
		if (amount < 0) {
			  target.damage(amount*-1);
			  return true;
		  } else {
			  double newHealth = target.getHealth() + amount;
			  if (newHealth > target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()) {
				  target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
				  return true;
			  } else {
				  target.setHealth(newHealth);
				  return true;
			  }
		  }
	}

}
