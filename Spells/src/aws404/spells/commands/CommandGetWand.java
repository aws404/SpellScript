package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CommandGetWand extends AbstractCommand{

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		Player player = null;
		String spell = "none";
		if (args.size() == 0) {
			if (!isPlayer) {
				sender.sendMessage("You Must Specify a Player when using this command from the console! " + usage());
				return true;
			} else {
				player = (Player) sender;
			}
		} else if (args.size() == 1) {
			player = Bukkit.getPlayer(args.get(0));
		} else if (args.size() == 2 ) {
			player = Bukkit.getPlayer(args.get(0));
			spell = args.get(1);
		} else {
			return false;
		}
		
		if (!plugin.isValid(player)) {
			sender.sendMessage("Player not found");
			return true;
		}
			player.getInventory().addItem(plugin.getWandItem(spell));
			player.sendMessage("You recived a wand!");
			return true;
	}

	@Override
	public String name() {
		return "wand";
	}

	@Override
	public String usage() {
		return "/spells wand [player] [spell]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"getwand"};
	}

	@Override
	public Permission permission() {
		return new Permission("spells.admin.wand");
	}
	
	@Override
	public String description() {
		return "Gives a wand item to the specified player, optional spell argument";
	}



}
