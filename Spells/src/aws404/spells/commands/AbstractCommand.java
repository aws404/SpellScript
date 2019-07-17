package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import aws404.spells.FileManager;
import aws404.spells.Main;

public abstract class AbstractCommand {
	protected Main plugin = Main.instance;
	protected FileManager fileManager = plugin.fileManager;
	
	public AbstractCommand() {
	}

    public abstract boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer);

    public abstract String name();
    
    public abstract String usage();
    
    public abstract String[] aliases();
    
    public abstract Permission permission();
    
}
