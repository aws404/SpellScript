package aws404.spells.functions;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptVariable;

public class FunctionParticle extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
		double x = target.getLocation().getX() + args[0].getDouble();
    	double y = target.getLocation().getY() + args[1].getDouble();
    	double z = target.getLocation().getZ() + args[2].getDouble();
    	double xoff = args[3].getDouble();
    	double yoff = args[4].getDouble();
    	double zoff = args[5].getDouble();
    	Particle particle = Particle.valueOf(args[6].getString().toUpperCase());
    	double speed = args[7].getDouble();
    	int count = args[8].getInt(); 
		
    	return particle(target, particle, x, y, z, xoff, yoff, zoff, speed, count);
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
