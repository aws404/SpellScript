package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptVariable;

public class FunctionSetHunger extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Player player;
		if (target.getType() == EntityType.PLAYER) player = (Player) target;
		else return false;
		Integer amount = args[0].getInt();
		
		return setHunger(player, amount);
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
