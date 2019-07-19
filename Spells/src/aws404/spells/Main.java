package aws404.spells;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import aws404.spells.API.SpellScriptAPI;
import aws404.spells.commands.Manager;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.FunctionsRegister;
import net.md_5.bungee.api.ChatColor;


public class Main extends JavaPlugin {
	
	//Define variables
	public static Main instance;
    public Manager commandClass;
    public ActionBarClass actionBarClass;
    public FunctionsRegister functionsRegister; 
    public FileManager fileManager;
    public InventoryGUI inventoryGUI;
    public GerneralEventsHandler generalEventsHandler;
    public SpellScriptAPI API;
    public ManaHandler manaHandler;
    
	public Boolean debug;
	public Boolean usingMana;
	public Inventory spellGUI;
	public boolean apiIsEnabled = false;
	
	public HashMap<String, String[]> spellFiles = new HashMap<String, String[]>();
	
	
	public ArrayList<Player> frozenPlayers = new ArrayList<Player>();
    
    static Logger log;
    
    //On Enable
    @Override
    public void onEnable() {
    	//Misc Variables
    	instance = this;
        log = this.getLogger();
        
    	//Register File Manager
        fileManager = new FileManager(this);
        
        //Assign Config Options
        debug = fileManager.getConfig().getBoolean("debug-mode");
        usingMana = fileManager.getConfig().getBoolean("use-mana");
        
        //Mana Handler
        if (usingMana) manaHandler = new ManaHandler(this);
        
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
        
        //Start API
        API = new SpellScriptAPI();
        
        //Message
        System.out.println("Spells System Plugin");
        System.out.println("Version 1.0 by aws404");
        System.out.println("Loaded Spells: " + spellFiles.size());
        System.out.println(Arrays.toString(spellFiles.keySet().toArray()));
        System.out.println("Using Mana: " + usingMana);
        System.out.println("API enabled: " + apiIsEnabled);
        
       
    }
 

    //On Disable
    @Override
    public void onDisable() {
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
    
    
   
    
    public SpellScriptVariable[] getArgs(String instruction) {
    	ArrayList<SpellScriptVariable> rv = new ArrayList<SpellScriptVariable>();
    	String[] args = StringUtils.substringBetween(instruction, "(", ")").split(",");
    	for (String a : args) {
    		rv.add(new SpellScriptVariable(a));
    	}
    	
    	return rv.toArray(new SpellScriptVariable[rv.size()]);
    }
    
    
    public void sendError(String error) {
    	log.log(Level.SEVERE, error);
    	for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("spells.debug")) p.sendMessage("[SPELLS] ERROR: " + error);
		}
    }
    
    public boolean isValid(Player player) {
    	if (player != null) {
    		if (player.isOnline()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    
    public void castSpell(LivingEntity target, LivingEntity caster, String spell) {
    	String[] lines = spellFiles.get(spell);
    	doAction(target, caster, 0, lines);        	
    }
    
    
    
    private void doAction(LivingEntity target, LivingEntity caster, int line, String[] lines) {
    	if (lines.length <= line) {
    		if (debug) target.sendMessage("Done");
    		return;
    	}
    	int newLine = line +1;
    	String[] segments = lines[line].split("\\.", 2);
		String selector = segments[0];


		//If no selector then it is a comment
		if (selector.contentEquals("")) {
		    doAction(target, caster, newLine, lines);
    	    return;
		}

		//Spell Selector
    	if (selector.contentEquals("spell")) {
		      for(int i = 1; i < segments.length; i++){
		    	  String instruction = segments[i];	
		    	  if (debug) target.sendMessage(instruction);
		    	  
		    	  if (instruction.contains("wait")) {
		    		  Long arg = getArgs(instruction)[0].getLong();
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
			SpellScriptVariable[] args = getArgs(instruction);
			String functionName = instruction.substring(0, instruction.indexOf("("));
			SpellScriptFunction function = functionsRegister.getFunction(functionName);
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
    	sendError("On line "+ newLine + ", " + selector + " is an unkown selector");
    		
    }

    
    
    
      
}
