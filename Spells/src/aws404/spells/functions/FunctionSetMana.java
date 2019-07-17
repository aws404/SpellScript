package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.DataType;

public class FunctionSetMana extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Player player;
		if (target.getType().equals(EntityType.PLAYER)) {
    		player = (Player) target;
		} else {
			return;
		}
		Integer amount = (Integer) plugin.convertType(args[0], DataType.INTEGER);
		
		setMana(player, amount);
	}

	@Override
	public String name() {
		return "setMana";
	}
	
	public boolean setMana(Player player, Integer amount) {
    		if (amount <= 0) amount = 0;
    		if (amount >= 20) amount = 20;
    		if(plugin.manaLevels.containsKey(player)) {
        		plugin.manaLevels.replace(player, amount);
        		return true;
        	} else {
        		plugin.manaLevels.put(player, amount);
        		return true;
        	}
	}

}
