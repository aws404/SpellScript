package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionSetHunger extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		if (!(target instanceof Player))
			return SpellScriptReturnValue.CONTINUE;
		
		Player player = (Player) target;
       	
		Integer amount = args[0].toInt();
		
		player.setFoodLevel(amount);
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "setHunger";
	}
	
}
