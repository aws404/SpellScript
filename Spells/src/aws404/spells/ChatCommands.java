package aws404.spells;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;


public class ChatCommands implements CommandExecutor {
	private Main plugin;

    public ChatCommands(Main plugin) {
        this.plugin = plugin;
    }
    
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String commandLabel, String[] args) {

    	if(command.getName().equalsIgnoreCase("spells")) {
    		
    		if (args.length == 0) {
        		sender.sendMessage("Spells Plugin Ver1.0");
        		sender.sendMessage("/spells - this menu");
        		sender.sendMessage("/spells cast <spell> [target] - cast a spell, if no target is specified the sender is assumed");
        		sender.sendMessage("/spells script <spell> - print out the script of the specified spell");
        		sender.sendMessage("/spells wand [player] - give a wand, if no player is specified the sender is assumed");
        		sender.sendMessage("/spells setmana <amount> - sets your current amount of mana");
        		sender.sendMessage("/spells reload - reloads the spells.yml and config.yml");
        		return true;
    		}
    		
    		if (args[0].equalsIgnoreCase("wand")) {
    			if (sender instanceof Player) {
    				Player player = (Player) sender;
    				plugin.giveWand(player);
    				player.sendMessage("You recived a wand!");
    				return true;
    			} else {
    				if (args[1] == null) {
    					sender.sendMessage("You must specify a player!");
    					return true;
    				} else {
    					Player player = Bukkit.getPlayer(args[1]);
    					if (player == null) {
    						sender.sendMessage("That Player could not be found!");
    						return true;
    					}
    					plugin.giveWand(player);
    					return true;
    				}
    			}
    		}
    		
    		if (args[0].equalsIgnoreCase("script")) {
    			if (args[1] != null) {
    				String[] lines = FileManager.getLines(args[1]);
    				if (lines == null) {
    					sender.sendMessage("That spell does not exist!");
    					return true;
    				}
    				sender.sendMessage(ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Script for the spell: " + args[1]);
    				for (String line : lines) {
    					sender.sendMessage(line);
    				}
    				return true;
    			} else {
    				sender.sendMessage("Incorrect Usage! /spells script <spell name>");
    				return true;
    			}
    		}
    		
    		if (args[0].equalsIgnoreCase("cast")) {
    			if (!(sender instanceof Player)) {
					sender.sendMessage("You must be a player to use this command!");
					return true;
				}
    		    			
    			if (args[1] == null) {
    				sender.sendMessage("Incorrect Usage! /spell cast <spell name> [target]");
    				return true;
    			}
    			
    			//Define Vars
    			String spell = args[1];
    			LivingEntity target;
    			Player caster = (Player) sender;
    			
    			if (args[2] == null) {
    				target = caster;
    			} else if (Bukkit.getPlayer(args[2]) != null) {
    				target = Bukkit.getPlayer(args[2]);
    			} else {
    				sender.sendMessage("Player not Found!");
    				return true;
    			}

    			sender.sendMessage("Casting Spell: " + spell + ", with caster: " + caster.getName() + " and target " + target.getName());
				plugin.castSpell(target, caster, spell);
				return true;
    		}
    		
    		if (args[0].equalsIgnoreCase("setmana")) {	
    			if (sender.equals(plugin.getServer().getConsoleSender())) {
					sender.sendMessage("You must be a player to use this command!");
					return true;
				}
    		    			
    			if (args[1] == null) {
    				sender.sendMessage("Incorrect Usage! /spell setmana <amount>");
    				return true;
    			}
    			
    			Integer returnedMana;
    			Integer newMana = Integer.getInteger(args[1]);
    			
    			if (newMana > plugin.maxMana) returnedMana = plugin.maxMana;
    			else if (newMana < 0) returnedMana = 0;
    			else returnedMana = newMana;	
    			
    			
    			plugin.manaLevels.replace((Player) sender, returnedMana);
    			return true;
    		}
    		
    		if (args[0].equalsIgnoreCase("reload")) {
    			FileManager.reloadConfigs();
    	        plugin.debug = FileManager.getConfig().getBoolean("debug-mode");
    	        plugin.usingMana = FileManager.getConfig().getBoolean("use-mana");
    	        plugin.manaRegenAmount = FileManager.getConfig().getInt("mana-regen-per");
    	        plugin.manaRegenTime =FileManager.getConfig().getInt("mana-regen-time");
    	        plugin.maxMana =FileManager.getConfig().getInt("max-mana");
    	        plugin.spellGUI = InventoryGUI.createGUI();
    			sender.sendMessage("Configs Reloaded!");
    			return true;
    		}
    		
    	}
    	
    	
    	
    	
    	
		return false;
	}
}

