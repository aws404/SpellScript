package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import aws404.spells.SpellFile;
import aws404.spells.SpellScriptSpell;
import aws404.spells.SpellType;

public class CommandCastSub extends AbstractCommand{

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		Player target = null;
		Player caster = null;
		String spellName = null;
		String subName = null;
		if (args.size() == 2) {
			if (isPlayer) {
				spellName = args.get(0);
				subName = args.get(1);
				target = (Player) sender;
				caster = (Player) sender;
			} else {
				sender.sendMessage("Console users must specify a player! " + usage());
			}
		} else if (args.size() == 3) {
			spellName = args.get(0);
			subName = args.get(1);
			target = Bukkit.getPlayer(args.get(2));
			caster = (Player) sender;
		} else if (args.size() == 4) {
			spellName = args.get(0);
			subName = args.get(1);
			target = Bukkit.getPlayer(args.get(2));
			caster = Bukkit.getPlayer(args.get(3));
		} else return false;
		
		
		if (!isValidPlayer(target)) {
			sender.sendMessage("That target could not be found!");
			return true;
		}
		
		if (!isValidPlayer(caster)) {
			sender.sendMessage("That target could not be found!");
			return true;
		}
		
		sender.sendMessage("Casting Spell: " + spellName + "'s Subscript " + subName + " , with caster: " + caster.getName() + " and target " + target.getName());
		SpellFile spellFile = plugin.spellFiles.get(spellName);
		
		if (spellFile == null) {
			sender.sendMessage("The spell " + spellName + " could not be found");
			return true;
		}
		
		SpellFile subFile = spellFile.getSubScript(subName);
		
		if (subFile == null) {
			sender.sendMessage("The subscript " + subName + " could not be found in the spell file " + spellName);
			return true;
		}
		
		SpellScriptSpell spell = new SpellScriptSpell(spellName, subFile, caster, target, SpellType.OTHER);
		spell.cast();
		sender.sendMessage("Spell Returned: " + spell.getState().toString());
		
		return true;
	}

	@Override
	public String name() {
		return "castsubscript";
	}

	@Override
	public String usage() {
		return "/spells cast <spell> <subscript name> [target] [caster]";
	}

	@Override
	public String[] aliases() {
		return new String[] {};
	}

	@Override
	public Permission permission() {
		return new Permission("spells.admin.cast");
	}

	@Override
	public String description() {
		return "Casts a spell from the script file with optional target and caster";
	}

}
