package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CommandMana extends AbstractCommand{

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		Player player = null;
		Integer amount;
		if (args.size() == 1) {
			if (isPlayer) {
				player = (Player) sender;
				amount = Integer.valueOf(args.get(0));
			} else {
				sender.sendMessage("You Must Specify a Player when using this command from the console! " + usage());
				return true;
			}
		} else if (args.size() == 2) {
			player = Bukkit.getPlayer(args.get(1));
			amount = Integer.valueOf(args.get(0));
		} else {
			return false;
		}
		
		if (!isValidPlayer(player)) {
			sender.sendMessage("Player not found");
			return true;
		} else {
			if (amount > manaHandler.maxMana) amount = manaHandler.maxMana;
			else if (amount < 0) amount = 0;
			
			sender.sendMessage("Set " + player.getName() + "'s mana to " + amount);
			manaHandler.manaLevels.replace((Player) sender, amount);
			return true;
		}
	}

	@Override
	public String name() {
		return "mana";
	}

	@Override
	public String usage() {
		return "/spells mana <amount> [player]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"setmana"};
	}

	@Override
	public Permission permission() {
		return new Permission("spells.admin.mana");
	}
	
	@Override
	public String description() {
		return "Allows for setting of a players mana level";
	}

	

}
