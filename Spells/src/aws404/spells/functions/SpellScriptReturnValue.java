package aws404.spells.functions;

import aws404.spells.functions.spellfunctions.FunctionIf;
import aws404.spells.functions.spellfunctions.FunctionIfElse;

public enum SpellScriptReturnValue {
	/**
	 * Continues the script to the next function
	 */
	CONTINUE,
	/**
	 * Stops the script
	 * Acts as an unsuccessful final spell state, does not take mana (if applicable)
	 */
	STOP,
	/**
	 * Continues the script to the next function
	 * Acts as a true value for {@link FunctionIf} and {@link FunctionIfElse} statements
	 */
	TRUE,
	/**
	 * Continues the script to the next function
	 * Acts as a false value for {@link FunctionIf} and {@link FunctionIfElse} statements
	 */
	FALSE,
	/**
	 * Does not automatically start next spell function
	 * Acts as an successful final spell state, takes mana (if applicable)
	 */
	FINSIH,
	/**
	 * Stops the script
	 * Prints an error message
	 */
	ERROR
}
