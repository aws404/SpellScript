package aws404.spells;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import aws404.spells.commands.Manager;
import aws404.spells.functions.Function;
import aws404.spells.functions.FunctionsRegister;
import net.md_5.bungee.api.ChatColor;


public class Main extends JavaPlugin {
	
	//Define variables
	public static Main instance;
    public Manager commandClass;
    public ActionBarInterface actionBarClass;
    public FunctionsRegister functionsRegister; 
    public FileManager fileManager;
    public InventoryGUI inventoryGUI;
    public GerneralEventsHandler generalEventsHandler;
    
	public Boolean debug;
	public Boolean usingMana;
	public Inventory spellGUI;
	public Integer manaRegenAmount;
	public Integer manaRegenTime;
	public Integer maxMana;
	
	public HashMap<String, String[]> spellFiles;
	
	public HashMap<Player,Integer> manaLevels = new HashMap<Player,Integer>();
	public HashMap<Player,BossBar> manaBars = new HashMap<Player,BossBar>();
    
    static Logger log;
    
    //On Enable
    @Override
    public void onEnable() {
    	//Misc Variables
    	instance = this;
        log = this.getLogger();
        BukkitScheduler scheduler = getServer().getScheduler();
        
    	//Register File Manager
        fileManager = new FileManager(this);
        
        //Assign Config Options
        debug = fileManager.getConfig().getBoolean("debug-mode");
        usingMana = fileManager.getConfig().getBoolean("use-mana");
        manaRegenAmount = fileManager.getConfig().getInt("mana-regen-per");
        manaRegenTime = fileManager.getConfig().getInt("mana-regen-time");
        maxMana = fileManager.getConfig().getInt("max-mana");
        
        //Create GUI
        inventoryGUI = new InventoryGUI(this);
        spellGUI = inventoryGUI.createGUI();
        
        //Register Commands
        commandClass = new Manager(this);
        commandClass.registerCommands();
        if (usingMana) commandClass.registerManaCommands();
        getCommand("spells").setExecutor(commandClass);
        
        //Register Action Bar Class
        actionBarClass = new ActionBarClass();

        //Register Events
        generalEventsHandler = new GerneralEventsHandler(this);
        getServer().getPluginManager().registerEvents(generalEventsHandler, this);

        //Register Functions
        functionsRegister = new FunctionsRegister(this);
        functionsRegister.registerFunctions();
        
        //Get Spells
        spellFiles = fileManager.getSpellFiles();
        
        
        //Message
        System.out.println("Spells System Plugin");
        System.out.println("Version 1.0 by aws404");
        System.out.println("Loaded Spells: " + spellFiles.size());
        System.out.println(Arrays.toString(spellFiles.keySet().toArray()));
        
        //Mana Handler
        if (usingMana) {
        	//Mana Regener
	        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {@Override public void run() {doManaRegen();}}, 0L, manaRegenTime*20L);
	        
	        //Bars Updater
	        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {@Override public void run() {updateManaBars();}}, 0L, 10L);
        }
    }
 

    //On Disable
    @Override
    public void onDisable() {
     }
    
    public void doManaRegen() {
    	 Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();
         
         for (Player p : playerList) {
         	Integer newMana = manaLevels.get(p)+manaRegenAmount;
         	if (newMana>maxMana) newMana = maxMana;
         	
         	manaLevels.replace(p, newMana);
         }
    }
    
    public boolean giveWand(Player p) {
    	if (p != null) {
    	    p.getInventory().addItem(getWandItem("none"));
    	}
    	return false;
    }
    
    public ItemStack getWandItem(String spell) {
    		ItemStack wand = new ItemStack(Material.STICK, 1);
    		ItemMeta meta = wand.getItemMeta();
    		meta.setDisplayName(ChatColor.BOLD + "Magic Wand: " + ChatColor.RESET + fileManager.getSpells().getString(spell + ".displayName"));
			
    		NamespacedKey key = new NamespacedKey(instance, "currentSpell");
    		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, spell);
  		
    		wand.setItemMeta(meta);
    		
    		return wand;
    }
    
    
    public boolean takeMana(Integer manaCost, Player p) {
    	if (!usingMana) return true;
    	Integer oldMana = manaLevels.get(p);
    	Integer newMana = oldMana - manaCost;
    	
    	if (newMana < 0) return false;
    	
	    manaLevels.replace(p, newMana);
	    return true;
    }
    
    public void updateManaBars() {
    	Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();
        
        for (Player p : playerList) {
        	Integer mana = manaLevels.get(p);
        	
        	Double manaBarValue = (double) mana / (double) maxMana;
        	if (manaBarValue <= 0) manaBarValue = (double) 0;
        	BossBar ManaBar;
        	if (manaBars.containsKey(p)) {
        		ManaBar = manaBars.get(p);
        	} else {
        		ManaBar = Bukkit.createBossBar("Mana", BarColor.BLUE, BarStyle.SEGMENTED_20);
        		manaBars.put(p, ManaBar);
        		ManaBar.addPlayer(p);
        	}
        	ManaBar.setProgress(manaBarValue);
        }
    }
    
    
    public String[] getArgs(String instruction) {
    	String args = StringUtils.substringBetween(instruction, "(", ")");
    	String[] value = args.split(",");
    	return value;
    }
    
    public String getString(String input) {
    	return StringUtils.substringBetween(input, "\"", "\"");
    }
    
    public void sendError(String error) {
    	log.log(Level.SEVERE, error);
    	for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("spells.debug")) p.sendMessage("[SPELLS] ERROR: " + error);
		}
    }
    
    public boolean isValid(Player player) {
    	if (player != null) {
    		System.out.println("not null");
    		if (player.isOnline()) {
    			System.out.println("is online");
    			return true;
    		}
    	}
    	return false;
    }
    
    public Object convertType(String input, DataType type) {	
    	try {
        	if (type.name().contains("STRING")) return getString(input);
        	if (type.name().contains("DOUBLE")) return Double.parseDouble(input.trim());
        	if (type.name().contains("INTEGER")) return Integer.parseInt(input.trim());
        	if (type.name().contains("LONG")) return Long.parseLong(input.trim());
        	if (type.name().contains("FLOAT")) return Float.parseFloat(input.trim());
        	if (type.name().contains("BOOLEAN")) return Boolean.valueOf(input.trim());
		} catch (Exception e) {
			sendError("UNABLE TO CONVERT '" + input + "' to type "+ type.name());
	    	return null;
		}
    	sendError("Unkown");
    	return null;
    }
    
    
    public void castSpell(LivingEntity target, LivingEntity caster, String spell) {
    	String[] lines = spellFiles.get(spell);
    	doAction(target, caster, 0, lines);        	
    }
    
    public void doAction(LivingEntity target, LivingEntity caster, int line, String[] lines) {
    	if (lines.length <= line) {
    		if (debug) target.sendMessage("Done");
    		return;
    	}
    	int newLine = line +1;
    	String[] segments = lines[line].split("\\.", 2);
		String selector = segments[0];


		//Spell Selector
    	if (selector.equalsIgnoreCase("spell")) {
		      for(int i = 1; i < segments.length; i++){
		    	  String instruction = segments[i];	
		    	  if (debug) target.sendMessage(instruction);
		    	  
		    	  if (instruction.contains("wait")) {
		    		  Long arg = (Long) convertType(getArgs(instruction)[0], DataType.LONG);
		    		  Long amount = arg *2L/100L;;
		    	        BukkitScheduler scheduler = getServer().getScheduler();
		    	        scheduler.scheduleSyncDelayedTask(this, new Runnable() {
		    	            @Override
		    	            public void run() {
		    	            	if (debug) target.sendMessage("finished " + String.valueOf(amount));
		    	            	doAction(target, caster, newLine, lines);
		    	            	return;
		    	            }
		    	        }, amount);
		    	        return;
		    	  }
		    	  if (instruction.contains("stop")) {
		    		  if (debug) target.sendMessage(instruction + "STOPPING");
		    		  return;
		    	  }
		    	  
		      }
		}
    	
    	//Target and Caster Selector
    	if (selector.contains("target") || selector.contains("caster")) {
    		List<LivingEntity> spellTarget = new ArrayList<LivingEntity>();
    		
    		if(!selector.contains("[")) {
    			
    			
    			//Targeting Single Entity
    			if (selector.equalsIgnoreCase("target")) spellTarget.add(target);
    			if (selector.equalsIgnoreCase("caster")) spellTarget.add(caster);
    		} else {
    			
    			
    			//Radius Target - target
    			int radius = Integer.parseInt(StringUtils.substringBetween(selector, "[", "]"));
    			if (selector.contains("target")) {
    				List<Entity> nearby = target.getNearbyEntities(radius, radius, radius);
    				for (Entity e : nearby) {
    					if (e.getType().isAlive() && !e.equals(caster)) spellTarget.add((LivingEntity) e);
    				}
    			}
    			
    			//Radius target - caster
    			if (selector.contains("caster")) {
    				List<Entity> nearby = caster.getNearbyEntities(radius, radius, radius);
    				for (Entity e : nearby) {
    					if (e.getType().isAlive() && !e.equals(caster)) spellTarget.add((LivingEntity) e);
    				}
    			}
    		}
    		
    		
			String instruction = segments[1];
			String[] args = getArgs(instruction);
			String functionName = instruction.substring(0, instruction.indexOf("("));
			Function function = functionsRegister.getFunction(functionName);
			if (debug) caster.sendMessage(ChatColor.BOLD + "Script: " + instruction);
			if (debug) caster.sendMessage(ChatColor.BOLD + "Function Name: " + function.name());
			if (debug) caster.sendMessage(ChatColor.ITALIC + "Arguments: " + Arrays.toString(args));
			if (debug) caster.sendMessage(ChatColor.ITALIC + "Amount of Targets: " + spellTarget.size());
			
    		for (LivingEntity currentTarget : spellTarget) {
    			if (debug) caster.sendMessage("Target: " + currentTarget.toString());;
    			function.runFunction(currentTarget, args);		
    		}
			if (debug) caster.sendMessage(" ");
			
			//Recurse
		    doAction(target, caster, newLine, lines);
    	    return;
		    	  
    	}
    	//if target type isnt caught 
    	sendError(selector + " is an unkown selector");
    		
    }

    
    
    
      
}
