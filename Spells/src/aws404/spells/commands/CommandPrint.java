package aws404.spells.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import aws404.spells.SpellFile;
import aws404.spells.selectors.SpellScriptSelector;

public class CommandPrint extends AbstractCommand{

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		if (args.size() == 1) {
			SpellFile lines = plugin.spellFiles.get(args.get(0));
			if (lines == null) {
				sender.sendMessage("That spell does not exist!");
				return true;
			}
			sender.sendMessage(ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Script for the spell: " + args.get(0));
			lines.getLines().forEach((functionData) -> {
				SpellScriptSelector<?> selector = functionData.getSelector();
				sender.sendMessage("Selector: " + selector.identifier() + " Function: " + functionData.getName() + " Arguments:" + functionData.getArgs().length);
			});
			
			
			HashMap<String, SpellFile> subs = lines.getSubscripts();
			
			sender.sendMessage(ChatColor.BOLD + "Subscripts: " + ChatColor.ITALIC + subs.size());
			subs.forEach((name, spellFile) -> {
				sender.sendMessage(ChatColor.UNDERLINE + "SubScript: " + name);
				spellFile.getLines().forEach((functionData) -> {
					SpellScriptSelector<?> selector = functionData.getSelector();
					sender.sendMessage("Selector: " + selector.identifier() + " Function: " + functionData.getName() + " Arguments:" + functionData.getArgs().length);
				});
			});
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
	
	@Override
	public String description() {
		return "Prints the specified spell file into the chat";
	}


}
