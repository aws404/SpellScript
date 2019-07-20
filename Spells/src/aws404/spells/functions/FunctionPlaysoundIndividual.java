package aws404.spells.functions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptArgument;

public class FunctionPlaysoundIndividual extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target,  SpellScriptArgument[] args) {
		Player player;
       	if (target.getType().equals(EntityType.PLAYER)) player = (Player) target;
       	else return false;
       	
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
		
		return playSoundPlayer(player, xoff, yoff, zoff, sound, volume, pitch);
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
