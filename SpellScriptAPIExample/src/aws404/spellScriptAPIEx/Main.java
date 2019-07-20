package aws404.spellScriptAPIEx;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import aws404.spells.API.SpellScriptAPI;


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
    		//Asign SpellScriptAPI variable to new instance of the API
    		ssapi = new SpellScriptAPI();
    		
    		//Register the new Function
    		ssapi.registerFunction(new FunctionKick());
    		
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