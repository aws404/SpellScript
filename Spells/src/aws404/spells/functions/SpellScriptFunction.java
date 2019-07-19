package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.Main;
import aws404.spells.ManaHandler;
import aws404.spells.SpellScriptVariable;

public abstract class SpellScriptFunction {
	
	protected Main plugin = Main.instance;
	protected ManaHandler manaHandler = plugin.manaHandler;
	
	public SpellScriptFunction() {
	}

    public abstract void runFunction(LivingEntity target, SpellScriptVariable[] args);

    public abstract String name();
	
}
