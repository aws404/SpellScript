package aws404.spells;

import org.apache.commons.lang.StringUtils;
import org.bukkit.craftbukkit.libs.jline.internal.Log;

public class SpellScriptVariable {

	private final String input;
	protected Main plugin = Main.instance;
	
	public SpellScriptVariable(String variable) {
		input = variable;
	}
	
	public String getString() {
		try {
			return StringUtils.substringBetween(input, "\"", "\"");
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type STRING");
	    	return null;
		}
	}
	
	public Double getDouble() {
		try {
			return Double.parseDouble(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type DOUBLE");
	    	return null;
		}
	}
	
	public Integer getInt() {
		try {
			return Integer.parseInt(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type INTEGER");
	    	return null;
		}
	}
	
	public Long getLong() {
		try {
			return Long.parseLong(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type LONG");
	    	return null;
		}
	}
	
	public Float getFloat() {
		try {
			return Float.parseFloat(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type FLOAT");
	    	return null;
		}
	}
	
	public Boolean getBoolean() {
		try {
			return Boolean.valueOf(input.trim());
		} catch (Exception e) {
			plugin.sendError("UNABLE TO CONVERT '" + input + "' to type BOOLEAN");
	    	return null;
		}
	}
	
	
}