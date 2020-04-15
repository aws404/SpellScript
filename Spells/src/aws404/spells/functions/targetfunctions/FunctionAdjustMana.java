package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionAdjustMana extends SpellScriptFunction<LivingEntity>{

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {	 
    	if (!(target instanceof Player))
       		return SpellScriptReturnValue.CONTINUE;
       	 
       	Player player = (Player) target;

		Integer amount = args[0].toInt();
		
		
    	Integer mana = manaHandler.manaLevels.getOrDefault(player, 0) + amount;
		if (mana <= 0) mana = 0;
		if (mana >= 20) mana = 20;
		
    	manaHandler.manaLevels.put(player, mana);
    	
    	return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "adjustMana";
	}


}
