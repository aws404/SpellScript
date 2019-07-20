package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptArgument;

public class FunctionSendMessage extends SpellScriptFunction{

	@Override
	public boolean runFunction(LivingEntity target, SpellScriptArgument[] args) {
		Player player;
       	if (target.getType().equals(EntityType.PLAYER)) player = (Player) target;
       	else return false;
       	
		String message = args[0].getString();
		
		return sendMessage(player, message);
	}

	@Override
	public String name() {
		return "sendMessage";
	}
	
	public boolean sendMessage(Player player, String message) {
		player.sendMessage(message);
		return true;
	}

}
