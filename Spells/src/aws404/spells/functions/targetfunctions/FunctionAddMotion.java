package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionAddMotion extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		double x = args[0].toDouble();
		double y = args[1].toDouble();
		double z = args[2].toDouble();
		
		Vector vec = new Vector(x, y, z);
		
		target.setVelocity(vec);
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "addMotion";
	}
	

}
