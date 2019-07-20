package aws404.spells.functions;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptVariable;

public class FunctionAdjustHealth extends SpellScriptFunction {

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Integer amount = args[0].getInt();
		
		return adjustHealth(target, amount);
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
			  if (newHealth > target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
				  target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				  return true;
			  } else {
				  target.setHealth(newHealth);
				  return true;
			  }
		  }
	}

}
