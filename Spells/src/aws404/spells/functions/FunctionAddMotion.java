package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import aws404.spells.DataType;

public class FunctionAddMotion extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		double x = (double) plugin.convertType(args[0], DataType.DOUBLE);
		double y = (double) plugin.convertType(args[1], DataType.DOUBLE);
		double z = (double) plugin.convertType(args[2], DataType.DOUBLE);
				
		addMotion(target, x, y, z);
	}

	@Override
	public String name() {
		return "addMotion";
	}
	
	public boolean addMotion(LivingEntity target, double x, double y, double z) {
		Vector vec = new Vector(x, y, z);
		
		target.setVelocity(vec);
		return true;
	}

}
