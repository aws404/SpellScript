package aws404.spells;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import aws404.spells.functions.FunctionsRegister;
import aws404.spells.selectors.SpellScriptSelector;

public class SpellScriptArgument {

	private final String input;
	protected Main plugin = Main.instance;
	
	
	public SpellScriptArgument(String variable) {
		input = variable;
	}
	
	/**
	 * Attempts to convert the argument to a {@link String}
	 * @return the argument represented in {@link String} form or <code>null</code> if unsuccessful 
	 */
	@Override
	public String toString() {
		try {
			return StringUtils.substringBetween(input, "\"", "\"");
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type STRING");
	    	return null;
		}
	}

	/**
	 * Attempts to convert the argument to a {@link Double}
	 * @return the argument represented in {@link Double} form or <code>null</code> if unsuccessful 
	 */
	public Double toDouble() {
		try {
			return Double.parseDouble(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type DOUBLE");
	    	return null;
		}
	}
	
	/**
	 * Attempts to convert the argument to a {@link Integer}
	 * @return the argument represented in {@link Integer} form or <code>null</code> if unsuccessful 
	 */
	public Integer toInt() {
		try {
			return Integer.parseInt(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type INTEGER");
	    	return null;
		}
	}
	
	/**
	 * Attempts to convert the argument to a {@link Long}
	 * @return the argument represented in {@link Long} form or <code>null</code> if unsuccessful 
	 */
	public Long toLong() {
		try {
			return Long.parseLong(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type LONG");
	    	return null;
		}
	}
	
	/**
	 * Attempts to convert the argument to a {@link Float}
	 * @return the argument represented in {@link Float} form or <code>null</code> if unsuccessful 
	 */
	public Float toFloat() {
		try {
			return Float.parseFloat(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type FLOAT");
	    	return null;
		}
	}
	
	/**
	 * Attempts to convert the argument to a {@link Boolean}
	 * @return the argument represented in {@link Boolean} form or <code>null</code> if unsuccessful 
	 */
	public Boolean toBoolean() {
		try {
			return Boolean.valueOf(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type BOOLEAN");
	    	return null;
		}
	}
		
	/**
	 * Attempts to convert the argument to a {@link FunctionData}
	 * @return the argument represented in {@link FunctionData} form or <code>null</code> if unsuccessful 
	 */
	public FunctionData toFunction() {
		String[] segments = input.split("\\.", 2);
		String selectorName = segments[0];
		Integer radius = 0;
		
		if (selectorName.contains("[") && selectorName.contains("]")) {
			radius = Integer.parseInt(StringUtils.substringBetween(selectorName, "[", "]"));
			selectorName = selectorName.substring(0, selectorName.indexOf("["));
		}

		SpellScriptSelector<?> selector = FunctionsRegister.getSelector(selectorName);
		
		if (selector == null) {
			//if selector type isn't caught 
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type FUNCTION, Unkown selector");
			return null;
		}
		
		String instruction = segments[1];
		String functionName = instruction.substring(0, instruction.indexOf('('));
		SpellScriptArgument[] args = SpellScriptArgument.decompileInstruction(instruction);

		
		return new FunctionData(selector, functionName, args, radius);
	}
	
	/**
	 * Decompiles a dirrect instruction and returns an array of {@link SpellScriptArgument}
	 * @param instruction the full instruction 
	 * @return array of the arguments in {@link SpellScriptArgument} form
	 */
	public static SpellScriptArgument[] decompileInstruction(String instruction) {
    	ArrayList<SpellScriptArgument> rv = new ArrayList<SpellScriptArgument>();
    	
    	String arguments = instruction.substring(instruction.indexOf('(')+1, instruction.lastIndexOf(')')+1);
		
    	StringBuilder builder = new StringBuilder();
    	Character searcher = null;
    	
    	for (Character c : arguments.toCharArray()) {
    		if (c == '\t' || (searcher == null && c == ' ')) {
    			continue;
    		}
    		if (c.equals(searcher)) {
    			searcher = null;
    			builder.append(c);
    			continue;
    		}
    		if (searcher == null && c == '{') {
    			searcher = '}';
    		}
    		if (searcher == null && c == '"') {
    			searcher = '"';
    		}
    		if (searcher == null && c == '\'') {
    			searcher = '\'';
    		}
    		if (searcher == null && c == '(') {
    			searcher = ')';
    		}
    		if ((c == ',' || c ==')') && searcher == null) {
    			rv.add(new SpellScriptArgument(builder.toString()));
    			builder.replace(0, builder.length(), "");
    			continue;
    		}
    		builder.append(c);
    	}
    	
    	return rv.toArray(new SpellScriptArgument[rv.size()]);
	}
	
	
	public String literalValue() {
		return input;
	}
	
}
