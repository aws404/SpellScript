package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class CommandPrint extends AbstractCommand{

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		if (args.size() == 1) {
			String[] lines = plugin.spellFiles.get(args.get(0));
			if (lines == null) {
				sender.sendMessage("That spell does not exist!");
				return true;
			}
			sender.sendMessage(ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Script for the spell: " + args.get(0));
			for (String line : lines) {
				sender.sendMessage(line);
			}
			return true;
		}
		return false;
	}

	@Override
	public String name() {
		return "print";
	}

	@Override
	public String usage() {
		return "/spell print <spell file>";
	}

	@Override
	public String[] aliases() {
		return new String[] {};
	}

	@Override
	public Permission permission() {
		return new Permission("spells.admin.print");
	}

}
