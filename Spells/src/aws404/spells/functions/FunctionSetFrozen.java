package aws404.spells.functions;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import aws404.spells.SpellScriptVariable;

public class FunctionSetFrozen extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Boolean value = args[0].getBoolean();
		
		return setFrozen(target, value);
	}

	@Override
	public String name() {
		return "setFrozen";
	}
	
	public boolean setFrozen(LivingEntity target, boolean value) {
		if (target.getType() == EntityType.PLAYER) {
			//Use playerMoveEvent
			Player player = (Player) target;
			if (value) {
				plugin.frozenPlayers.add(player);
				return true;
			} else {
				if (plugin.frozenPlayers.contains(player)) {
					plugin.frozenPlayers.remove(player);
					return true;
				} else {
					return false;
				}
				
			}
		} else {
			//Set Mob Attributes
			AttributeInstance walk = target.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
			AttributeInstance fly = target.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
			if (value) {
				walk.setBaseValue(0);
				fly.setBaseValue(0);
				return true;
			} else {
				walk.setBaseValue(fly.getDefaultValue());
				fly.setBaseValue(fly.getDefaultValue());
				return true;
			}
		}
	}

}
