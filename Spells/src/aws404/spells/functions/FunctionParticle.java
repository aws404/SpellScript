package aws404.spells.functions;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import aws404.spells.DataType;

public class FunctionParticle extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		double x = target.getLocation().getX() + (Double) plugin.convertType(args[0], DataType.DOUBLE);
    	double y = target.getLocation().getY() + (Double) plugin.convertType(args[1], DataType.DOUBLE);
    	double z = target.getLocation().getZ() + (Double) plugin.convertType(args[2], DataType.DOUBLE);
    	double xoff = (Double) plugin.convertType(args[3], DataType.DOUBLE);
    	double yoff = (Double) plugin.convertType(args[4], DataType.DOUBLE);
    	double zoff = (Double) plugin.convertType(args[5], DataType.DOUBLE);
    	Particle particle = Particle.valueOf(((String) plugin.convertType(args[6], DataType.STRING)).toUpperCase());
    	double speed = (double) plugin.convertType(args[7], DataType.DOUBLE);
    	int count = (int) plugin.convertType(args[8], DataType.INTEGER);  
		
    	particle(target, particle, x, y, z, xoff, yoff, zoff, speed, count);
	}

	@Override
	public String name() {
		return "particle";
	}
	
	public boolean particle(LivingEntity target, Particle particle, double x, double y, double z, double xoff, double yoff, double zoff, double speed, int count) {
		target.getWorld().spawnParticle(particle, x, y, z, count, xoff, yoff, zoff, speed, null, true);
    	return true;
	}
	

}
