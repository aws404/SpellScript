package aws404.spells.functions;

import org.bukkit.entity.LivingEntity;

import aws404.spells.Main;

public abstract class Function {
	
	protected Main plugin = Main.instance;
	
	public Function() {
	}

    public abstract void runFunction(LivingEntity target, String[] args);

    public abstract String name();
	
}
