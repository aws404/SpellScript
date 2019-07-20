package aws404.spells;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class SpellScriptArgument {

	private final String input;
	protected Main plugin = Main.instance;
	
	
	public SpellScriptArgument(String variable) {
		input = variable;
	}
	
	/**
	 * Attempts to convert the argument to a {@link String}
	 * @return the argument represented in {@link String} form or <code>null</code> if unsucessfull 
	 */
	public String getString() {
		try {
			return StringUtils.substringBetween(input, "\"", "\"");
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type STRING");
	    	return null;
		}
	}

	/**
	 * Attempts to convert the argument to a {@link Double}
	 * @return the argument represented in {@link Double} form or <code>null</code> if unsucessfull 
	 */
	public Double getDouble() {
		try {
			return Double.parseDouble(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type DOUBLE");
	    	return null;
		}
	}
	
	/**
	 * Attempts to convert the argument to a {@link Integer}
	 * @return the argument represented in {@link Integer} form or <code>null</code> if unsucessfull 
	 */
	public Integer getInt() {
		try {
			return Integer.parseInt(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type INTEGER");
	    	return null;
		}
	}
	
	/**
	 * Attempts to convert the argument to a {@link Long}
	 * @return the argument represented in {@link Long} form or <code>null</code> if unsucessfull 
	 */
	public Long getLong() {
		try {
			return Long.parseLong(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type LONG");
	    	return null;
		}
	}
	
	/**
	 * Attempts to convert the argument to a {@link Float}
	 * @return the argument represented in {@link Float} form or <code>null</code> if unsucessfull 
	 */
	public Float getFloat() {
		try {
			return Float.parseFloat(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type FLOAT");
	    	return null;
		}
	}
	
	/**
	 * Attempts to convert the argument to a {@link Boolean}
	 * @return the argument represented in {@link Boolean} form or <code>null</code> if unsucessfull 
	 */
	public Boolean getBoolean() {
		try {
			return Boolean.valueOf(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type BOOLEAN");
	    	return null;
		}
	}
	
	/**
	 * Decompiles a dirrect instruction and returns an array of {@link SpellScriptArgument}
	 * @param instruction the full instruction 
	 * @return array of the arguments in {@link SpellScriptArgument} form
	 */
	public static SpellScriptArgument[] decompileInstruction(String instruction) {
    	ArrayList<SpellScriptArgument> rv = new ArrayList<SpellScriptArgument>();
    	String[] args = StringUtils.substringBetween(instruction, "(", ")").split(",");
    	for (String a : args) {
    		rv.add(new SpellScriptArgument(a));
    	}
    	
    	return rv.toArray(new SpellScriptArgument[rv.size()]);
	}
	
	
}
