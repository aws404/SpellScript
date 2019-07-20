package aws404.spells.functions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import aws404.spells.SpellScriptVariable;

public class FunctionPlaySound extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptVariable[] args) {
       	Double xoff = args[0].getDouble();
       	Double yoff = args[1].getDouble();
       	Double zoff = args[2].getDouble();
       	Sound sound = Sound.valueOf(args[3].getString().toUpperCase());
		if (sound == null) {
			plugin.sendError(args[3].getDouble() + " is not a valid sound name!");
			return false;
		}
		Float volume = args[4].getFloat();
		Float pitch = args[5].getFloat();
		
		return playSound(target, xoff, yoff, zoff, sound, volume, pitch);
	}

	@Override
	public String name() {
		return "playSound";
	}
	
	public boolean playSound(LivingEntity target, double xoff, double yoff, double zoff, Sound sound, float volume, float pitch) {
		Location location = target.getLocation().add(xoff, yoff, zoff);
		target.getWorld().playSound(location, sound, SoundCategory.NEUTRAL,volume, pitch);
		return true;
	}

}
