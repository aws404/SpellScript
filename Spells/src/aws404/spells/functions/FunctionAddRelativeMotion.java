package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import aws404.spells.SpellScriptVariable;

public class FunctionAddRelativeMotion extends SpellScriptFunction{

	@Override
	public void runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Double x = args[0].getDouble();
		Double y = args[1].getDouble();
		
		addRelativeMotion(target, x, y);
	}

	@Override
	public String name() {
		return "addRelativeMotion";
	}
	
	public boolean addRelativeMotion(LivingEntity target, double x, double y) {
		Vector dir = target.getLocation().getDirection();
		Vector yvect = new Vector(0, y, 0);
		  
		dir.normalize();
		dir.add(yvect);
		dir.multiply(x); ;
		
		target.setVelocity(dir);
		return true;
	}

}