package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.DataType;

public class FunctionSetHunger extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Player player;
		if (target.getType() == EntityType.PLAYER) player = (Player) target;
		else return;
		Integer amount = (int) plugin.convertType(args[0], DataType.INTEGER);
		
		setHunger(player, amount);
	}

	@Override
	public String name() {
		return "setHunger";
	}
	
	public boolean setHunger(Player player, Integer amount) {
		player.setFoodLevel(amount);
		return true;
	}

}
