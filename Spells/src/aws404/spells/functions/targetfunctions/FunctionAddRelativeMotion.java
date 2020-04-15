package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionAddRelativeMotion extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Double x = args[0].toDouble();
		Double y = args[1].toDouble();
		
		Vector dir = target.getLocation().getDirection();
		Vector yvect = new Vector(0, y, 0);
		  
		dir.normalize();
		dir.add(yvect);
		dir.multiply(x); ;
		
		target.setVelocity(dir);
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "addRelativeMotion";
	}
	

}
