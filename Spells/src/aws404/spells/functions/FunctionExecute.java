package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;

public class FunctionExecute extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptArgument[] args) {
		String cmd = args[0].getString();
		
		return execute(target, cmd);
	}

	@Override
	public String name() {
		return "execute";
	}
	
	public boolean execute(LivingEntity target, String cmd) {
		//this allows for commands to be ran even if the player dosent have the usual permissions
		
		//scoreboard tags are used as @e no longer can select using UUID
		String tag ="entityExecution"+target.getUniqueId(); //make a unique scoreboard tag to apply
		
		target.addScoreboardTag(tag); //apply the tag
		
		Boolean sucess = plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "execute as @e[tag="+tag+"] run " + cmd); //execute the command as the entity with the tag
		
		target.removeScoreboardTag(tag); //remove the tag
		
		return sucess;
	}

}
