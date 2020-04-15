package aws404.spellScriptAPIEx;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import aws404.spells.API.SpellScriptAPI;
import aws404.spells.functions.FunctionsRegister;


public class Main extends JavaPlugin {
	
	
	//Define API variable
	public SpellScriptAPI ssapi;
	
	
	public static Main instance;
	
	
    //On Enable
    @Override
    public void onEnable() {
    	instance = this;
    	
    	//Ensure SpellScript is Enabled
    	if (Bukkit.getPluginManager().isPluginEnabled("SpellScript")) {
    		//Assign SpellScriptAPI variable to new instance of the API
    		ssapi = new SpellScriptAPI();
    		
    		//Register the new Function
    		FunctionsRegister.registerFunction(new FunctionKick(), "caster", "target");

    		
    		getServer().getPluginManager().registerEvents(new GerneralEventsHandler(), this);
    	} else {
    		Bukkit.getPluginManager().disablePlugin(this);
    	}
    }
 

    //On Disable
    @Override
    public void onDisable() {
     }
    
    

}