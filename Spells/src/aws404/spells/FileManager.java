package aws404.spells;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.stream.Stream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	
	public static void listFiles() {
	File folder = new File(plugin.getDataFolder(), "spells");;
	File[] listOfFiles = folder.listFiles();

	for (File f : listOfFiles) {
	  if (f.isFile()) {
	    System.out.println("File " + f.getName());
	  } else if (f.isDirectory()) {
	    System.out.println("Directory " + f.getName());
	  }
	}
	}
	
	public static String[] getLines(String spell) {
		File spellFile;
		try {
			spellFile = new File(plugin.getDataFolder(), "spells/" + spell + ".spell");
		} catch (Exception e) {
			return null;
		}
		
		String contents = readLineByLineJava8(spellFile.getAbsolutePath());
		String[] lines = contents.replace("\n", "").split(";");
		return lines;
	}
	
	private static String readLineByLineJava8(String filePath) {
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
	
	
	public static YamlConfiguration spells;
	private static File spellsFile;
	
	public static YamlConfiguration config;
	private static File configFile;
	
	static Main plugin;
    public FileManager(Main pluginX) {
    plugin = pluginX;
 
    spellsFile = new File(plugin.getDataFolder(), "spells.yml"); //The file (you can name it what you want)
    spells = YamlConfiguration.loadConfiguration(spellsFile); //Take the file and basically turning it into a .yml file
    
    configFile = new File(plugin.getDataFolder(), "config.yml"); //The file (you can name it what you want)
    config = YamlConfiguration.loadConfiguration(configFile);
    
    try {
        spells.save(spellsFile);
        config.save(configFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
    

    
}
    public static FileConfiguration getSpells() {
        if (spells == null) {
            reloadConfigs();
        }
        return spells;
    }
    
    public static FileConfiguration getConfig() {
        if (config == null) {
            reloadConfigs();
        }
        return config;
    }

    public static void saveConfigs() {
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
    
    public static void reloadConfigs() {
        if (spellsFile == null) {
        spellsFile = new File(plugin.getDataFolder(), "spells.yml");
        }
        if (configFile == null) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        spells = YamlConfiguration.loadConfiguration(spellsFile);
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    
    
}
