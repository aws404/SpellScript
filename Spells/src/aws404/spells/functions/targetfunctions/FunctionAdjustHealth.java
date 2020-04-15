package aws404.spells.functions.targetfunctions;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionAdjustHealth extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Integer amount = args[0].toInt();
		
		if (amount < 0) {
			  target.damage(amount*-1);
		} else {
			double newHealth = target.getHealth() + amount;
			
			if (newHealth > target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) 
				newHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
			
			target.setHealth(newHealth);
		  }
		
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "adjustHealth";
	}

}
