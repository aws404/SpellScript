package aws404.spells.functions;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;

public class FunctionTeleportRelative extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptArgument[] args) {
    	double x = args[0].getDouble();
    	double y = args[1].getDouble();
    	double z = args[2].getDouble();
		
    	return teleportRelative(target, x, y, z);
	}

	@Override
	public String name() {
		return "teleportRelative";
	}
	
	public boolean teleportRelative(LivingEntity target, double x, double y, double z) {
    	Location newLocation = target.getLocation();
    	newLocation.add(x, y, z);
		  
    	target.teleport(newLocation);
    	return true;
	}

}
