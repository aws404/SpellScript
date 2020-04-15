package aws404.spells.functions.targetfunctions;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionSetFrozen extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Boolean value = args[0].toBoolean();
		
		if (target.getType() == EntityType.PLAYER) {
			//Use playerMoveEvent
			Player player = (Player) target;
			if (value) plugin.frozenPlayers.add(player);
			else plugin.frozenPlayers.remove(player);
		} else {
			//Set Mob Attributes
			AttributeInstance walk = target.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
			if (value) walk.setBaseValue(0);
			else walk.setBaseValue(walk.getDefaultValue());
		}
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "setFrozen";
	}
}
