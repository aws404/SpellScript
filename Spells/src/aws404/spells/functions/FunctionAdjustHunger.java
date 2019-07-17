package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.DataType;

public class FunctionAdjustHunger extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Player player;
		if (target.getType() == EntityType.PLAYER) player = (Player) target;
		else return;
		Integer amount = (int) plugin.convertType(args[0], DataType.INTEGER);
		
		adjustHunger(player, amount);		
	}

	@Override
	public String name() {
		return "adjustHunger";
	}

	public boolean adjustHunger(Player player, Integer amount) {
		Integer newHealth = player.getFoodLevel() + amount;
		
		if (newHealth < 0) {
			player.setFoodLevel(0);
			return true;
		} else if (newHealth > 20) {
			player.setFoodLevel(20);
			return true;
		} else {
			player.setFoodLevel(newHealth);
			return true;
		}
		
	}
}
