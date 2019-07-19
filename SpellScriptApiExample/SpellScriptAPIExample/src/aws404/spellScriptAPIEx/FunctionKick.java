package aws404.spellScriptAPIEx;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptVariable;
import aws404.spells.functions.SpellScriptFunction;

public class FunctionKick extends SpellScriptFunction {

	
	//This provided the name that is used for the function.
	@Override
	public String name() {
		return "kick";
		//Example: target.kick();
	}

	
	//This is the function that will be ran when the function is called by a script 
	@Override
	public void runFunction(LivingEntity target, SpellScriptVariable[] args) {
		//Arguments are passed in as SpellScriptVariableTypesbthese can 
		// be easily converted to the desird type by using the provided functions
		//Examples: args[0].getBoolean(); args[0].getLong(); ect
		Integer number = args[0].getInt();
		String reason = args[1].getString();
		
		
		if (target.getType() == EntityType.PLAYER) {
			Player player  = (Player) target;
			
			player.kickPlayer("You Have Been Kicked! \n Reason: " + number + ". " + reason);
		} else {
			target.teleport(new Location(target.getWorld(), 0, -10, 0));
		}
	}

}
