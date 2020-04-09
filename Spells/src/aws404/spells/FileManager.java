package aws404.spells;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	
	public static YamlConfiguration spells;
	private static File spellsFile;
	
	public static YamlConfiguration config;
	private static File configFile;
	
	private File spellsFolder;
	
	 Main plugin;
	
	//General Configs
    public FileManager(Main pluginX) {
	    plugin = pluginX;
	 
	    spellsFile = new File(plugin.getDataFolder(), "spells.yml"); //The file (you can name it what you want)
	    spells = YamlConfiguration.loadConfiguration(spellsFile); //Take the file and basically turning it into a .yml file
	    
	    configFile = new File(plugin.getDataFolder(), "config.yml"); //The file (you can name it what you want)
	    config = YamlConfiguration.loadConfiguration(configFile);
	    
	    
	    spellsFolder = new File(plugin.getDataFolder(), "spells");
	   
	    if (!configFile.exists() || !spellsFile.exists() || !spellsFolder.exists()) {
	    	reloadConfigs();
        }
        
        plugin.spellFiles = getSpellFiles();
        
    }
    
    
    
    public FileConfiguration getSpells() {
        if (spells == null) {
            reloadConfigs();
        }
        return spells;
    }
    
    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfigs();
        }
        return config;
    }

    public void saveConfigs() {
        if (config == null || configFile == null || spells == null || spellsFile == null) {
            return;
        }
        try {
            getSpells().save(spellsFile);
            getConfig().save(configFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config files" , ex);
        }
    }
    
    public void reloadConfigs() {
	    if (!configFile.exists()) {
	    	plugin.saveDefaultConfig();
	    	configFile = new File(plugin.getDataFolder(), "config.yml");
	    }
        if (!spellsFile.exists()) {
            spellsFile.getParentFile().mkdirs();
            plugin.saveResource("spells.yml", false);
    	    spellsFile = new File(plugin.getDataFolder(), "spells.yml");
         }
        if (!spellsFolder.exists()) {
        	spellsFolder.mkdirs();
        	plugin.saveResource("spells/meteor.spell", false);
        	plugin.saveResource("spells/heal.spell", false);
        	plugin.saveResource("spells/teleport.spell", false);
    	    spellsFolder = new File(plugin.getDataFolder(), "spells");
        }
        
        plugin.spellFiles = getSpellFiles();
	    config = YamlConfiguration.loadConfiguration(configFile);
	    spells = YamlConfiguration.loadConfiguration(spellsFile);
	    plugin.getLogger().info("Configurations and Spells Reloaded");

    }
    
    //Spell Files
    
	
	private ArrayList<File> listFiles() {
		File[] listOfFiles = spellsFolder.listFiles();
		ArrayList<File> spellFiles = new ArrayList<File>();
	
		for (File f : listOfFiles) {
		  if (f.isFile() && f.getName().contains(".spell")) {
			  spellFiles.add(f);
		  }
		}
		return spellFiles;
	}
	
	private HashMap<String, String[]> getSpellFiles() {
		HashMap<String, String[]> filesList = new HashMap<String, String[]>();
		
		ArrayList<File> spellFiles = listFiles();
		
		for (File file : spellFiles) {
			String name = file.getName().replace(".spell", "");
			String[] lines = new String[0];
			try {
				lines = getContents(file).split(";");
			} catch (IOException e) {
				plugin.sendError("Error Loading File '" + file.getName() + "' Error:" + e.getMessage());
			}
			
			filesList.put(name, lines);
		}
		
		return filesList;
	}
	
	
	private String getContents(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader (file));
	    String line = null;
	    StringBuilder stringBuilder = new StringBuilder();

	    try {
	        while((line = reader.readLine()) != null) {
	        	if (!(line.startsWith("//") || line.trim().contentEquals(""))) {
	        		stringBuilder.append(line);
	        	} else {
	        		stringBuilder.append(";");
	        	}
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
    
    
}
