package aws404.spellScriptAPIEx;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionKick extends SpellScriptFunction<LivingEntity> {
	/* To start, your class must extend SpellScriptFunction 
	 * using the same type argument as the intended selector.
	 * 
	 * Defaults:
	 * target - LivingEntity
	 * caster - LivingEntity
	 * spell - SpellScriptSpell
	 */
	

	
	/*
	 * Next define the case sensitive name used to call the function
	 * For example, returning 'kick' will make the function target.kick();
	 */
	@Override
	public String name() {
		return "kick";
	}

	
	/*
	 * Here you define what the function actually does. The runFunction arguments are:
	 * target - this is of the same type defined in the extend, in this case LivingEntity
	 * args[] - these are the arguments that are passed inside the brackets of the function
	 *          for example target.kick("being bad", 1); will have the arguments "being bad" and 1.
	 */
	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		/* Arguments are supplied as SpellScriptArguments, these can 
		 * be easily converted to the desired type by using any of the
		 * conversion functions provided.
		 * 
		 * Examples: 
		 * args[0].toBoolean(); will return a boolean 
		 * args[0].toLong(); will return a long
		 * args[0].toFunction(); will return a FunctionData type
		 * ect for most data types.
		 * 
		 * It is convention to do all data conversions before any logic/functionality.
		 */
		Integer number = args[0].toInt();
		String reason = args[1].toString();
		
		
		if (target instanceof Player) {
			Player player  = (Player) target;
			
			player.kickPlayer("You Have Been Kicked! \n Reason: " + number + ". " + reason);
			
			//The returned value determines the actions the spell will take next.
			//For all return type and their uses, see the SpellScriptReturnValue class.
			return SpellScriptReturnValue.CONTINUE;
		} else {
			target.teleport(new Location(target.getWorld(), 0, -10, 0));
			return SpellScriptReturnValue.CONTINUE;
		}
	}

}
