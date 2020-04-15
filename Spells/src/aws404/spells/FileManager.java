package aws404.spells;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import aws404.spells.functions.FunctionsRegister;
import aws404.spells.selectors.SpellScriptSelector;

public class FileManager {

	
	public static YamlConfiguration spells;
	private static File spellsFile;
	
	public static YamlConfiguration config;
	private static File configFile;
	
	private File spellsFolder;
	
	Main plugin;
	Random rand = new Random();
	
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
        
	    config = YamlConfiguration.loadConfiguration(configFile);
	    spells = YamlConfiguration.loadConfiguration(spellsFile);
	    plugin.getLogger().info("Configurations and Spells Reloaded");

	    
	    plugin.spellFiles = getSpellFiles();
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
	
	public HashMap<String, SpellFile> getSpellFiles() {
		HashMap<String, SpellFile> filesList = new HashMap<String, SpellFile>();

		ArrayList<File> spellFiles = listFiles();
		
		for (File file : spellFiles) {
			String name = file.getName().replace(".spell", "");

			String contents = "";
			try {
				contents = getContents(file);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
			SpellFile spellFile = parse(contents);
	
			
			filesList.put(name, spellFile);
		}
		
        if (plugin.debug) {
        	System.out.println("Loaded Spells:");
        	filesList.forEach((name, spellFile) -> {
        		System.out.println(" - " + name + ": " + spellFile.getLines().size() + " Function(s)");
        	});
        }
		
		return filesList;
	}
	
	private SpellFile parse(String contents) {
		StringBuilder builder = new StringBuilder();
		StringBuilder builder2 = new StringBuilder();
    	Character searcher = null;
    	SpellFile spellFile = new SpellFile();
    	
    	for (Character c : contents.toCharArray()) {
    		if (c == '\t' || (searcher == null && c == ' ')) {
    			continue;
    		}
    		if (c.equals(searcher)) {
    			searcher = null;
    			if (c == '}') {
    				//End Subscript
    				String content = builder2.toString();
    				String ssname = String.valueOf(rand.nextInt());
    				System.out.println(content);
    				if (content.contains("spell.setName(\"")) {
    					//Normal Subscript
    					ssname = StringUtils.substringBetween(content, "spell.setName(\"", "\")");
    				} else {
    					//Inside and if/ifElse
    					builder.append('"' + ssname + '"');
    				}
    				spellFile.addSubScript(ssname, parse(content));	
    				builder2.replace(0, builder.length(), "");
    				continue;
    			}
    			builder.append(c);
    			continue;
    		}
    		if (searcher == null && c == '{') {
    			//Start subscript
    			searcher = '}';
    			continue;
    		}
    		if (searcher == null && c == '"') {
    			searcher = '"';
    		}
    		if (searcher == null && c == '[') {
    			searcher = ']';
    		}
    		if (c == ';' && searcher == null && builder.length() != 0) {
    			String line = builder.toString();
    			String[] segments = line.split("\\.", 2);
    			String selectorName = segments[0];
    			Integer radius = 0;
    			
    			if (selectorName.contains("[") && selectorName.contains("]")) {
    				radius = Integer.parseInt(StringUtils.substringBetween(selectorName, "[", "]"));
    				selectorName = selectorName.substring(0, selectorName.indexOf("["));
    			}

    			SpellScriptSelector<?> selector = FunctionsRegister.getSelector(selectorName);
    			
    			if (selector == null) {
    				//if selector type isn't caught 
    				plugin.sendError("In file , " + selectorName + " is an unkown selector");
    				builder.replace(0, builder.length(), "");
    				continue;
    			}
    			
    			String instruction = segments[1];
    			String functionName = instruction.substring(0, instruction.indexOf('('));
    			SpellScriptArgument[] args = SpellScriptArgument.decompileInstruction(instruction);

    			spellFile.addLine(selector, functionName, args, radius);
    				
    			builder.replace(0, builder.length(), "");
    			continue;
    		}
    		if (searcher != null && searcher == '}') {
    			builder2.append(c);
    		} else {
    			builder.append(c);
    		}
    	}
    	return spellFile;
	}
	
	
	private String getContents(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader (file));
	    String line = null;
	    StringBuilder stringBuilder = new StringBuilder();

	    try {
	        while((line = reader.readLine()) != null) {
	        	int commentStart = line.indexOf("//");
	        	if (commentStart != -1) {
	        		line = line.substring(0, commentStart);
	        	}
	        	
	        	if (!(line.trim().contentEquals(""))) {
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
	

	private String lexer(File file) throws IOException {
		SpellFile spellFile = new SpellFile();
		
		BufferedReader reader = new BufferedReader(new FileReader (file));
	    String line = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    String content = Files.asCharSource(new File("file.txt"), Charsets.UTF_8).read();
	    
	    Random rand = new Random();
	    
	    Pattern inisdeSBPattern = Pattern.compile("({)(.|\\n)*?(.|\\n)*?(})");
	    
	    Pattern ifelsePattern = Pattern.compile("(if)[ \\n]*(\\(.*\\))[ \\n]*({)(.|\\n)*?(})[ \\n]*(else)[ \\n]*({)(.|\\n)*?(})");
	    Matcher ifelseMatches = ifelsePattern.matcher(content);
	    
	    for (int i = 0; i > ifelseMatches.groupCount(); i++) {
	    	String match = ifelseMatches.group(i);
	    	
	    	Matcher subscriptMatches = inisdeSBPattern.matcher(match);
		    for (int s = 0; s > subscriptMatches.groupCount(); s++) {
		    	String match1 = ifelseMatches.group(i);
		    	
		    	String subFunction = match1.substring(1, match1.length());
		    	ArrayList<String> lines = (ArrayList<String>) Arrays.asList(subFunction.split(";"));
		    	
		    	String subScriptName = "ifelse" + rand.nextInt();
		    	//spellFile.addSubScript(subScriptName, lines);	
		    	
		    }
	    	
	    	
	    	match.replaceAll("(if)[ \\n]*(\\()[ \\n]*", "script.ifelse(\"");
	    	match.replaceAll("(\\))[ \\n]*({)[ \\n]*", "\", ");;
	    	stringBuilder.append(match);
	    }
	    
	    Pattern ifPattern = Pattern.compile("(if)[ \\n]*(\\(.*\\))[ \\n]*({)(.|\\n)*?(})[ \\n]*(else)[ \\n]*({)(.|\\n)*?(})");
	    Matcher ifMatches = ifPattern.matcher(content);
	    
	    for (int i = 0; i > ifMatches.groupCount(); i++) {
	    	String match = ifMatches.group(i);
	    	
	    	match.replaceAll("(if)[ \\n]*(\\()[ \\n]*", "script.ifelse(\"");
	    	match.replaceAll("(\\))[ \\n]*({)[ \\n]*", "\", ");;
	    	match.replaceAll("(})[ \\n]*", "{/FUNCTION}{/IF}");
	    }
	    
	    try {
	        while((line = reader.readLine()) != null) {
	        	int commentStart = line.indexOf("//");
	        	if (commentStart != -1)
	        		line = line.substring(0, commentStart);
	        	
	        	
	        	if (line.matches(".{0,}(if).{0,}(\\().{0,}(\\))")) {
	        		int start = line.indexOf('(');
	        		int end = line.lastIndexOf(')');
	        		String ifFunction = line.substring(start, end);
	        		
	        		stringBuilder.append("{IF}{TEST}");
	        		stringBuilder.append(ifFunction);
	        		stringBuilder.append("{/TEST}");
	        	}
	        	
	        	
	        	
	        	if (!(line.trim().contentEquals(""))) {
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
