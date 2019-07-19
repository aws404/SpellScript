package aws404.spells.functions;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptVariable;

public class FunctionSendMessage extends SpellScriptFunction{

	@Override
	public void runFunction(LivingEntity target, SpellScriptVariable[] args) {
		Player player;
       	if (target.getType().equals(EntityType.PLAYER)) player = (Player) target;
       	else return;
       	
		String message = args[0].getString();
		
		sendMessage(player, message);
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