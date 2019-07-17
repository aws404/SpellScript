package aws404.spells;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.stream.Stream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	
	public static YamlConfiguration spells;
	private static File spellsFile;
	
	public static YamlConfiguration config;
	private static File configFile;
	
	static Main plugin;
	
	//General Configs
    public FileManager(Main pluginX) {
	    plugin = pluginX;
	 
	    spellsFile = new File(plugin.getDataFolder(), "spells.yml"); //The file (you can name it what you want)
	    spells = YamlConfiguration.loadConfiguration(spellsFile); //Take the file and basically turning it into a .yml file
	    
	    configFile = new File(plugin.getDataFolder(), "config.yml"); //The file (you can name it what you want)
	    config = YamlConfiguration.loadConfiguration(configFile);
	   
	   
	    if (!configFile.exists()) {
	    	plugin.saveDefaultConfig();
	    	configFile = new File(plugin.getDataFolder(), "config.yml"); //The file (you can name it what you want)
		    config = YamlConfiguration.loadConfiguration(configFile);
	    }
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
        if (spellsFile == null) {
        spellsFile = new File(plugin.getDataFolder(), "spells.yml");
        }
        if (configFile == null) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        spells = YamlConfiguration.loadConfiguration(spellsFile);
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    
    //Spell Files
    
	
	public ArrayList<File> listFiles() {
		File folder = new File(plugin.getDataFolder(), "spells");;
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> spellFiles = new ArrayList<File>();
	
		for (File f : listOfFiles) {
		  if (f.isFile() && f.getName().contains(".spell")) {
			  spellFiles.add(f);
		  }
		}
		return spellFiles;
	}
	
	public HashMap<String, String[]> getSpellFiles() {
		HashMap<String, String[]> filesList = new HashMap<String, String[]>();
		
		ArrayList<File> spellFiles = listFiles();
		
		for (File file : spellFiles) {
			String name = file.getName().replace(".spell", "");
			String[] lines = readLineByLineJava8(file.getAbsolutePath()).replaceAll("\n", "").split(";");
			
			filesList.put(name, lines);
		}
		
		return filesList;
	}
	
	
	private String readLineByLineJava8(String filePath) {
	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
	    {
	        stream.forEach(s -> contentBuilder.append(s).append("\n"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
    
    
}
