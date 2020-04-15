package aws404.spells;

import java.util.ArrayList;
import java.util.HashMap;
import aws404.spells.selectors.SpellScriptSelector;

public class SpellFile {

	private HashMap<String, SpellFile> subscripts;

	private ArrayList<FunctionData> lines;
	
	/**
	 * Create a new empty spell file
	 */
	public SpellFile() {
		this.lines = new ArrayList<FunctionData>();
		this.subscripts = new HashMap<String, SpellFile>();
	}
	
	/**
	 * Create a new pre-filled spell file
	 * 
	 * @param lines an ArrayList of FunctionData
	 * @param subscripts a HashMap of subscripts with name as the key
	 */
	public SpellFile(ArrayList<FunctionData> lines, HashMap<String, SpellFile> subscripts) {
		this.lines = lines;
		this.subscripts = subscripts;
	}
	
	/**
	 * Get a subscript of the script
	 * @param name the name of the subscript
	 * @return the subscript, or null if non-existent
	 */
	public SpellFile getSubScript(String name) {
		return subscripts.getOrDefault(name, null);
	}
	
	/**
	 * Add a new subscript to the script
	 * @param name the name of the subscript
	 * @param spellFile a SpellFile of the script 
	 */
	public void addSubScript(String name, SpellFile spellFile) {
		subscripts.put(name, spellFile);
	}
	
	/**
	 * Add a new function to the Spell
	 * @param selector the selector of the spell
	 * @param function the function name
	 * @param args an array of SpellScriptArguments
	 * @param radius the radius to apply the function to
	 */
	public void addLine(SpellScriptSelector<?> selector, String function, SpellScriptArgument[] args, Integer radius) {
		lines.add(new FunctionData(selector, function, args, radius));
	}
	
	/**
	 * Get the Array of FunctionData
	 * @return the ArrayList of FunctionData
	 */
	public ArrayList<FunctionData> getLines() {
		return lines;
	}
	
	/**
	 * @return the subscripts
	 */
	public HashMap<String, SpellFile> getSubscripts() {
		return subscripts;
	}
	
}
