package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class CommandReload extends AbstractCommand {

	@Override
	public boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer) {
		fileManager.reloadConfigs();
		Boolean useMana = fileManager.getConfig().getBoolean("use-mana");
		if (useMana != plugin.usingMana) sender.sendMessage("Changing the 'use-mana' option required a server restart");
		Integer manaRegenTime = fileManager.getConfig().getInt("mana-regen-time");
		if (manaRegenTime != plugin.manaRegenTime) sender.sendMessage("Changing the 'mana-regen-time' option required a server restart");
		
		
        plugin.debug = fileManager.getConfig().getBoolean("debug-mode");
        plugin.usingMana = useMana;
        plugin.manaRegenAmount = fileManager.getConfig().getInt("mana-regen-per");
        plugin.manaRegenTime = manaRegenTime;
        plugin.maxMana = fileManager.getConfig().getInt("max-mana");
        plugin.spellGUI = plugin.inventoryGUI.createGUI();
        plugin.spellFiles = fileManager.getSpellFiles();
		sender.sendMessage("Configs Reloaded!");
		return true;
	}

	@Override
	public String name() {
		return "reload";
	}

	@Override
	public String usage() {
		return "/spells reload";
	}

	@Override
	public String[] aliases() {
		return new String[] {"rl"};
	}

	@Override
	public Permission permission() {
		return new Permission("spells.admin.reload");
	}

}
