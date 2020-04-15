package aws404.spells.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import aws404.spells.Main;

public class Manager implements CommandExecutor{
	 @SuppressWarnings("unused")
		private Main plugin;
	    
		public Manager(Main plugin) {
	        this.plugin = plugin;
		}

	    public ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>();

	    public void registerCommands() { //trigged on server startup
	        this.commands.add(new CommandGetWand());
	        this.commands.add(new CommandHelp());
	        this.commands.add(new CommandPrint());
	        this.commands.add(new CommandReload());
	        this.commands.add(new CommandCast());
	        this.commands.add(new CommandCastSub());

	    }
	    
	    public void registerManaCommands() { //Trigged on server startup if mana is enabled
	    	this.commands.add(new CommandMana());
	    }
	    
	    public AbstractCommand getCommand(String commandName) {
	    	Iterator<AbstractCommand> subcommands = this.commands.iterator();

	        while (subcommands.hasNext()) {
	        	AbstractCommand func = (AbstractCommand) subcommands.next();
	        	
	        	if (func.name().equalsIgnoreCase(commandName)) return func;
	        	if (Arrays.asList(func.aliases()).contains(commandName)) return func;
	        }
	        
	        return null;
	    }
	    
		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {		
			if (cmd.getName().equalsIgnoreCase("spells")) {
				Boolean isPlayer;
				if (sender instanceof Player) isPlayer = true;
				else isPlayer = false;
				
				ArrayList<String> arguments = new ArrayList<String>();
				
				if (args.length == 0) {
					new CommandHelp().run(sender, arguments, isPlayer);
					return true;
				}
				
				
				AbstractCommand subCmd = getCommand(args[0]);
				if (subCmd == null) {
					subCmd = new CommandNotFound();
				}
				
				
				for (int i=1; i<args.length; i++) {
					arguments.add(args[i]);
				}
				if (sender.hasPermission(subCmd.permission())) {
					if (subCmd.run(sender, arguments, isPlayer)) {
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect Usage! " + ChatColor.ITALIC + subCmd.usage());
						return true;
					}
				} else {
					sender.sendMessage("You dont have permission for that command! Permission Required: " + subCmd.permission().getName());
				}
			}
			return false;
		}
}
