package aws404.spells.functions.spellfunctions;

import org.bukkit.scheduler.BukkitScheduler;

import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionWait extends SpellScriptFunction<SpellScriptSpell> {
	
	@Override
	public SpellScriptReturnValue runFunction(SpellScriptSpell spell, SpellScriptArgument[] args) {
		Long arg = args[0].toLong();
		Long amount = arg *2L/100L;;
	      
		BukkitScheduler scheduler = plugin.getServer().getScheduler();
		  
	    scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
	    	@Override
	        public void run() {
	    		spell.nextAction();
	        }
	    }, amount);
	    
	    return SpellScriptReturnValue.FINSIH;
	}

	@Override
	public String name() {
		return "wait";
	}

}
