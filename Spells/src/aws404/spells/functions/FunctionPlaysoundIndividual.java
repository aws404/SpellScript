package aws404.spells.functions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.DataType;

public class FunctionPlaysoundIndividual extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		Player player;
       	if (target.getType().equals(EntityType.PLAYER)) player = (Player) target;
       	else return;

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
		
		playSoundPlayer(player, xoff, yoff, zoff, sound, volume, pitch);
	}

	@Override
	public String name() {
		return "playSoundPlayer";
	}
	
	public boolean playSoundPlayer(Player player, double xoff, double yoff, double zoff, Sound sound, float volume, float pitch) {
		Location location = player.getLocation().add(xoff, yoff, zoff);
		player.playSound(location, sound, SoundCategory.NEUTRAL,volume, pitch);
		return true;
	}

}
