package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import aws404.spells.DataType;

public class FunctionAddRelativeMotion extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Double x = (double) plugin.convertType(args[0], DataType.DOUBLE);
		Double y = (double) plugin.convertType(args[1], DataType.DOUBLE);
		
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
