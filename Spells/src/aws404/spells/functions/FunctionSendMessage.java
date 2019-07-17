package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.DataType;

public class FunctionSendMessage extends Function{

	@Override
	public void runFunction(LivingEntity target, String[] args) {
		String message = (String) plugin.convertType(args[0], DataType.STRING);
		Player player = (Player) target;
		
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
