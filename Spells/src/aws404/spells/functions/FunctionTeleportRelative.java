package aws404.spells.functions;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import aws404.spells.DataType;

public class FunctionTeleportRelative extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
    	double x = (double) plugin.convertType(args[0], DataType.DOUBLE);
    	double y = (double) plugin.convertType(args[1], DataType.DOUBLE);
    	double z = (double) plugin.convertType(args[2], DataType.DOUBLE);
		
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
