package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import aws404.spells.SpellScriptVariable;

public class FunctionAddMotion extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
		double x = args[0].getDouble();
		double y = args[1].getDouble();
		double z = args[2].getDouble();
				
		return addMotion(target, x, y, z);
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
