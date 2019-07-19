package aws404.spells.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import net.md_5.bungee.api.ChatColor;

public class CommandHelp extends AbstractCommand{

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		sender.sendMessage("--------------- Spells Plugin Ver1.0 ---------------");
		ArrayList<AbstractCommand> commands = plugin.commandClass.commands;
		for (AbstractCommand cmd : commands) {
			sender.sendMessage(ChatColor.BOLD + "- " + cmd.name() + ChatColor.RESET + " - " + ChatColor.ITALIC + cmd.description());
			sender.sendMessage("   - Usage: " + ChatColor.ITALIC + cmd.usage());
			String[] aliases = cmd.aliases();
			if (aliases.length >= 1) sender.sendMessage("   - Aliases: " + ChatColor.ITALIC + Arrays.toString(aliases));
		}
		sender.sendMessage("-----------------------------------------------------");
		return true;
	}

	@Override
	public String name() {
		return "help";
	}

	@Override
	public String usage() {
		return "/spells help";
	}

	@Override
	public String[] aliases() {
		return new String[] {};
	}

	@Override
	public Permission permission() {
		return new Permission("spells.help");
	}
	
	@Override
	public String description() {
		return "This help message";
	}


}
