package aws404.spells.functions.targetfunctions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionPlaySound extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
       	Double xoff = args[0].toDouble();
       	Double yoff = args[1].toDouble();
       	Double zoff = args[2].toDouble();
       	Sound sound = Sound.valueOf(args[3].toString().toUpperCase());
		if (sound == null) {
			plugin.sendError(args[3].toDouble() + " is not a valid sound name!");
			return SpellScriptReturnValue.ERROR;
		}
		Float volume = args[4].toFloat();
		Float pitch = args[5].toFloat();
		
		Location location = target.getLocation().add(xoff, yoff, zoff);
		target.getWorld().playSound(location, sound, SoundCategory.NEUTRAL,volume, pitch);
		
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "playSound";
	}
	

}
