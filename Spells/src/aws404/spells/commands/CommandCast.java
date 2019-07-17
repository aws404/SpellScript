package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CommandCast extends AbstractCommand{

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		Player target = null;
		Player caster = null;
		String spell = null;
		if (args.size() == 1) {
			if (isPlayer) {
				spell = args.get(0);
				target = (Player) sender;
				caster = (Player) sender;
			} else {
				sender.sendMessage("Console users must specify a player! " + usage());
			}
		} else if (args.size() == 2) {
			spell = args.get(0);
			target = Bukkit.getPlayer(args.get(1));
			caster = (Player) sender;
		} else if (args.size() ==3) {
			spell = args.get(0);
			target = Bukkit.getPlayer(args.get(1));
			caster = Bukkit.getPlayer(args.get(2));
		} else return false;
		
		
		if (!plugin.isValid(target)) {
			sender.sendMessage("That target could not be found!");
			return true;
		}
		
		if (!plugin.isValid(caster)) {
			sender.sendMessage("That target could not be found!");
			return true;
		}
		
		sender.sendMessage("Casting Spell: " + spell + ", with caster: " + caster.getName() + " and target " + target.getName());
		plugin.castSpell(target, caster, spell);
		return true;
	}

	@Override
	public String name() {
		return "cast";
	}

	@Override
	public String usage() {
		return "/spells cast <spell> [target] [caster]";
	}

	@Override
	public String[] aliases() {
		return new String[] {};
	}

	@Override
	public Permission permission() {
		return new Permission("spells.admin.cast");
	}

}
