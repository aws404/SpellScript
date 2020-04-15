package aws404.spells.selectors;

import java.util.HashMap;

import org.bukkit.entity.LivingEntity;

import aws404.spells.Main;
import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.FunctionNotFound;
import aws404.spells.functions.FunctionsRegister;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public abstract class SpellScriptSelector<T> {
	
	
	protected Main plugin = Main.instance;
	private HashMap<String, SpellScriptFunction<T>> functions;
	
	@SuppressWarnings("unchecked")
	public SpellScriptSelector() {
		functions = new HashMap<String, SpellScriptFunction<T>>();
		functions.put("notfound", new FunctionNotFound());
		FunctionsRegister.registerSelector(this);
	}
    
    public abstract String identifier();
    
    public SpellScriptFunction<T> getFunction(String name) {
    	return functions.getOrDefault(name, functions.get("notfound"));
    }
    
    public void addFunction(SpellScriptFunction<T> function) {
    	functions.put(function.name(), function);
    } 
    
    public void removeFunction(String function) {
    	functions.remove(function);
    } 
    
    public SpellScriptReturnValue runFunction(String name, SpellScriptSpell spell, LivingEntity target, LivingEntity caster, SpellScriptArgument[] args, Integer radius) {
    	SpellScriptFunction<T> function = getFunction(name);
    	return runFunction(function, spell, target, caster, args, radius);
    }
    public SpellScriptReturnValue runFunction(String name, SpellScriptSpell spell, LivingEntity target, LivingEntity caster, SpellScriptArgument[] args) {
    	SpellScriptFunction<T> function = getFunction(name);
    	return runFunction(function, spell, target, caster, args, 0);
    }
    
    public abstract SpellScriptReturnValue runFunction(SpellScriptFunction<T> function, SpellScriptSpell spell, LivingEntity target, LivingEntity caster, SpellScriptArgument[] args, Integer radius);
    
    
}
