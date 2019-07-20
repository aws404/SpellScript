package aws404.spells.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import aws404.spells.FileManager;
import aws404.spells.Main;
import aws404.spells.ManaHandler;

public abstract class AbstractCommand {
	protected Main plugin = Main.instance;
	protected FileManager fileManager = plugin.fileManager;
	protected ManaHandler manaHandler = plugin.manaHandler;
	
	public AbstractCommand() {
	}

    public abstract boolean run(CommandSender sender, ArrayList<String> args, Boolean isPlayer);

    public abstract String name();
    
    public abstract String usage();
    
    public abstract String[] aliases();
    
    public abstract String description();
    
    public abstract Permission permission();
    
    protected boolean isValidPlayer(Player player) {
    	if (player != null) {
    		if (player.isOnline()) {
    			return true;
    		}
    	}
    	return false;
    }
    
}
