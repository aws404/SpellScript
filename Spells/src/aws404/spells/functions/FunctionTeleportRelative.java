package aws404.spells.functions;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptVariable;

public class FunctionTeleportRelative extends SpellScriptFunction{

	@Override
	public void runFunction(LivingEntity target, SpellScriptVariable[] args) {
    	double x = args[0].getDouble();
    	double y = args[1].getDouble();
    	double z = args[2].getDouble();
		
    	teleportRelative(target, x, y, z);
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