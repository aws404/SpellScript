package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptVariable;

public class FunctionAdjustMana extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
		 Player player;
		 
       	 if (target.getType().equals(EntityType.PLAYER)) player = (Player) target;
       	 else return false;

		Integer amount = args[0].getInt();
		return adjustMana(player, amount);
	}

	@Override
	public String name() {
		return "adjustMana";
	}
	
	public boolean adjustMana(Player player, Integer amount) {
    	Integer mana = manaHandler.manaLevels.get(player)+amount;
		if (mana <= 0) mana = 0;
		if (mana >= 20) mana = 20;
		if(manaHandler.manaLevels.containsKey(player)) {
			manaHandler.manaLevels.replace(player, mana);
    		return true;
    	} else {
    		manaHandler.manaLevels.put(player, mana);
    		return true;
    	}
	}

}
