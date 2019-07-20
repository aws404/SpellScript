package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptArgument;

public class FunctionSetMana extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Player player;
		if (target.getType().equals(EntityType.PLAYER)) {
    		player = (Player) target;
		} else {
			return false;
		}
		Integer amount = args[0].getInt();
		
		return setMana(player, amount);
	}

	@Override
	public String name() {
		return "setMana";
	}
	
	public boolean setMana(Player player, Integer amount) {
    		if (amount <= 0) amount = 0;
    		if (amount >= 20) amount = 20;
    		if(manaHandler.manaLevels.containsKey(player)) {
    			manaHandler.manaLevels.replace(player, amount);
        		return true;
        	} else {
        		manaHandler.manaLevels.put(player, amount);
        		return true;
        	}
	}

}
