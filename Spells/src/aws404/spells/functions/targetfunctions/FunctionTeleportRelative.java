package aws404.spells.functions.targetfunctions;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionTeleportRelative extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
    	double x = args[0].toDouble();
    	double y = args[1].toDouble();
    	double z = args[2].toDouble();
		
    	Location newLocation = target.getLocation();
    	newLocation.add(x, y, z);
		  
    	if (target.teleport(newLocation))
    		return SpellScriptReturnValue.CONTINUE;
    	else
    		return SpellScriptReturnValue.ERROR;
    	
	}

	@Override
	public String name() {
		return "teleportRelative";
	}
	

}
