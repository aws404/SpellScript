package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.DataType;

public class FunctionAdjustMana extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		 Player player;
		 
       	 if (target.getType().equals(EntityType.PLAYER)) player = (Player) target;
       	 else return;

		Integer amount = (Integer) plugin.convertType(args[0], DataType.INTEGER);
		adjustMana(player, amount);
	}

	@Override
	public String name() {
		return "adjustMana";
	}
	
	public boolean adjustMana(Player player, Integer amount) {
    	Integer mana = plugin.manaLevels.get(player)+amount;
		if (mana <= 0) mana = 0;
		if (mana >= 20) mana = 20;
		if(plugin.manaLevels.containsKey(player)) {
    		plugin.manaLevels.replace(player, mana);
    		return true;
    	} else {
    		plugin.manaLevels.put(player, mana);
    		return true;
    	}
	}

}
