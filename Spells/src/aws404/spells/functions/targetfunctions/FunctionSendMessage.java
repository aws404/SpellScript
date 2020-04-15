package aws404.spells.functions.targetfunctions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.SpellScriptArgument;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionSendMessage extends SpellScriptFunction<LivingEntity> {

	@Override
	public SpellScriptReturnValue runFunction(LivingEntity target, SpellScriptArgument[] args) {
		if (!(target instanceof Player))
			return SpellScriptReturnValue.CONTINUE;
		
		Player player = (Player) target;
       	
		String message = args[0].toString();
		
		player.sendMessage(message);
		
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "sendMessage";
	}
}
