package aws404.spells.functions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import aws404.spells.DataType;

public class FunctionPlaySound extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
       	Double xoff = (Double) plugin.convertType(args[0], DataType.DOUBLE);
       	Double yoff = (Double) plugin.convertType(args[1], DataType.DOUBLE);
       	Double zoff = (Double) plugin.convertType(args[2], DataType.DOUBLE);
       	Sound sound = Sound.valueOf(((String) plugin.convertType(args[3], DataType.STRING)).toUpperCase());
		if (sound == null) {
			plugin.sendError((String) plugin.convertType(args[3], DataType.STRING) + " is not a valid sound name!");
			return;
		}
		Float volume = (Float) plugin.convertType(args[4], DataType.FLOAT);
		Float pitch = (Float) plugin.convertType(args[5], DataType.FLOAT);
		
		playSound(target, xoff, yoff, zoff, sound, volume, pitch);
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
