package aws404.spells.selectors;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class SpellScriptCasterSelector extends SpellScriptSelector<LivingEntity> {

	@Override
	public String identifier() {
		return "caster";
	}

	@SuppressWarnings("unchecked")
	@Override
	public SpellScriptReturnValue runFunction(SpellScriptFunction<LivingEntity> function, SpellScriptSpell spell, LivingEntity target, LivingEntity caster, SpellScriptArgument[] args, Integer radius) {
		if(radius == 0) {
			//Targeting Single Entity
			return function.runFunction(caster, args);
		} else {

			List<Entity> nearby = caster.getNearbyEntities(radius, radius, radius);
			nearby.removeIf((e) -> (!e.getType().isAlive() || e.equals(caster) || e.equals(target)));
			List<LivingEntity> livingnearby = (List<LivingEntity>) ((Object) nearby);
			
			for (LivingEntity currentTarget : livingnearby) {
				if (plugin.debug) caster.sendMessage("Target: " + currentTarget.toString());;
				
    			SpellScriptReturnValue value = function.runFunction(currentTarget, args);
				
    			
				//Do tests for return value
				switch(value) {
					case ERROR:
					case FINSIH:
					case STOP:
						return value;
					default:
						break;
				}
			}
			return SpellScriptReturnValue.CONTINUE;
		}
	}

}
