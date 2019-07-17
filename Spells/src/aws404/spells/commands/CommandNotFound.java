package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class CommandNotFound extends AbstractCommand{

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		sender.sendMessage("Command Not Found!");
		sender.sendMessage("Try /spells help for a list of commands!");
		return true;
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public String usage() {
		return null;
	}

	@Override
	public String[] aliases() {
		return null;
	}

	@Override
	public Permission permission() {
		return new Permission("spells.help");
	}
	
}
