package aws404.spells.functions.targetfunctions;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionParticle extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		double x = target.getLocation().getX() + args[0].toDouble();
    	double y = target.getLocation().getY() + args[1].toDouble();
    	double z = target.getLocation().getZ() + args[2].toDouble();
    	double xoff = args[3].toDouble();
    	double yoff = args[4].toDouble();
    	double zoff = args[5].toDouble();
    	Particle particle = Particle.valueOf(args[6].toString().toUpperCase());
    	double speed = args[7].toDouble();
    	int count = args[8].toInt(); 
		
    	target.getWorld().spawnParticle(particle, x, y, z, count, xoff, yoff, zoff, speed, null, true);
	
    	return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "particle";
	}
	
}
