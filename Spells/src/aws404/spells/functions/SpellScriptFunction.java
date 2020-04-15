package aws404.spells.functions;

import aws404.spells.Main;
import aws404.spells.ManaHandler;
import aws404.spells.SpellScriptArgument;

public abstract class SpellScriptFunction<T> {
	
	
	protected Main plugin = Main.instance;
	protected ManaHandler manaHandler = plugin.manaHandler;
	protected FunctionsRegister functionsRegister = plugin.functionsRegister;
	protected T selector;
	
    /**
     * The name/identifier that will be used to dirrect the function. This is used to dirrect the arguments to the correct function class
     * @return the exact case sensitive name of the function
     */
    public abstract String name();
    
	/**
	 * This is the function that is ran when a function is called
	 * @param args the arguments passed in {@link SpellScriptArgument} form
	 * @return the function should return whether it was sucessfull or not, if a function is unsucessfull then the caster's mana is not taken and the spell is haulted where the function failed.
	 */
    public abstract SpellScriptReturnValue runFunction(T target, SpellScriptArgument[] args);
}
