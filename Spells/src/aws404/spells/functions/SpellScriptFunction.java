package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.Main;
import aws404.spells.ManaHandler;
import aws404.spells.SpellScriptArgument;

public abstract class SpellScriptFunction {
	
	protected Main plugin = Main.instance;
	protected ManaHandler manaHandler = plugin.manaHandler;
	
	public SpellScriptFunction() {
	}

	/**
	 * This is the function that is ran when a function is called
	 * @param target the target {@link LivingEntity} in which the function should action on
	 * @param args the arguments passed in {@link SpellScriptArgument} form
	 * @return the function should return whether it was sucessfull or not, if a function is unsucessfull then the caster's mana is not taken and the spell is haulted where the function failed.
	 */
    public abstract boolean runFunction(LivingEntity target, SpellScriptArgument[] args);

    /**
     * The name/identifier that will be used to dirrect the function. This is used to dirrect the arguments to the correct function class
     * @return the exact case sensitive name of the function
     */
    public abstract String name();
	
}
