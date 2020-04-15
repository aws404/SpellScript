package aws404.spells;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import aws404.spells.API.SpellScriptAPI;
import aws404.spells.commands.Manager;
import aws404.spells.functions.FunctionsRegister;


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
	
	public HashMap<String, SpellFile> spellFiles = new HashMap<String, SpellFile>();
	
	
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
        functionsRegister.registerSelectors();
        functionsRegister.registerFunctions();
        
        spellFiles = fileManager.getSpellFiles();
        
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
    		String displayName = fileManager.getSpells().getString(spell + ".displayName");
    		if (displayName == null) displayName = "No Spell Set"; 
    		meta.setDisplayName(ChatColor.BOLD + "Magic Wand: " + ChatColor.RESET + displayName);
			
    		NamespacedKey key = new NamespacedKey(instance, "currentSpell");
    		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, spell);
  		
    		wand.setItemMeta(meta);
    		
    		return wand;
    }
   
    public void sendError(String error) {
    	log.log(Level.SEVERE, error);
    	for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission("spells.debug")) p.sendMessage("[SPELLS] ERROR: " + error);
		}
    }
    
   
      
}
