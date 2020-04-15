package aws404.spells.functions;

import java.util.HashMap;

import org.bukkit.entity.LivingEntity;

import aws404.spells.Main;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.spellfunctions.FunctionIf;
import aws404.spells.functions.spellfunctions.FunctionIfElse;
import aws404.spells.functions.spellfunctions.FunctionReturnBoolean;
import aws404.spells.functions.spellfunctions.FunctionSetName;
import aws404.spells.functions.spellfunctions.FunctionStartSubScript;
import aws404.spells.functions.spellfunctions.FunctionStop;
import aws404.spells.functions.spellfunctions.FunctionWait;
import aws404.spells.functions.targetfunctions.FunctionAddMotion;
import aws404.spells.functions.targetfunctions.FunctionAddRelativeMotion;
import aws404.spells.functions.targetfunctions.FunctionAdjustHealth;
import aws404.spells.functions.targetfunctions.FunctionAdjustHunger;
import aws404.spells.functions.targetfunctions.FunctionAdjustMana;
import aws404.spells.functions.targetfunctions.FunctionEffect;
import aws404.spells.functions.targetfunctions.FunctionExecute;
import aws404.spells.functions.targetfunctions.FunctionFire;
import aws404.spells.functions.targetfunctions.FunctionGetTag;
import aws404.spells.functions.targetfunctions.FunctionGetType;
import aws404.spells.functions.targetfunctions.FunctionParticle;
import aws404.spells.functions.targetfunctions.FunctionPlaySound;
import aws404.spells.functions.targetfunctions.FunctionPlaysoundIndividual;
import aws404.spells.functions.targetfunctions.FunctionRayTraceTeleport;
import aws404.spells.functions.targetfunctions.FunctionSendMessage;
import aws404.spells.functions.targetfunctions.FunctionSetFrozen;
import aws404.spells.functions.targetfunctions.FunctionSetHealth;
import aws404.spells.functions.targetfunctions.FunctionSetHunger;
import aws404.spells.functions.targetfunctions.FunctionSetInvulnerable;
import aws404.spells.functions.targetfunctions.FunctionSetMana;
import aws404.spells.functions.targetfunctions.FunctionSetTag;
import aws404.spells.functions.targetfunctions.FunctionTakeTag;
import aws404.spells.functions.targetfunctions.FunctionTeleportRelative;
import aws404.spells.selectors.SpellScriptCasterSelector;
import aws404.spells.selectors.SpellScriptSelector;
import aws404.spells.selectors.SpellScriptSpellSelector;
import aws404.spells.selectors.SpellScriptTargetSelector;

public class FunctionsRegister {
    @SuppressWarnings("unused")
	private Main plugin;
    
	private static HashMap<String, SpellScriptSelector<?>> selectors = new HashMap<String, SpellScriptSelector<?>>();
    
	public FunctionsRegister(Main plugin) {
        this.plugin = plugin;
	}
	
	public void registerSelectors() {
    	new SpellScriptTargetSelector();
    	new SpellScriptCasterSelector();
    	new SpellScriptSpellSelector();
	}
	
	
    public void registerFunctions() {
    	SpellScriptSelector<LivingEntity> target = getSelector("target");
    	SpellScriptSelector<LivingEntity> caster = getSelector("caster");
    	SpellScriptSelector<SpellScriptSpell> spell = getSelector("spell");
    	
    	//Register Target and Caster Functions
    	registerFunction(new FunctionAddMotion(), target, caster);
        registerFunction(new FunctionAddRelativeMotion(), target, caster);
        registerFunction(new FunctionAdjustHealth(), target, caster);
        registerFunction(new FunctionAdjustHunger(), target, caster);
        registerFunction(new FunctionAdjustMana(), target, caster);
        registerFunction(new FunctionEffect(), target, caster);
        registerFunction(new FunctionFire(), target, caster);
        registerFunction(new FunctionSetFrozen(), target, caster);
        registerFunction(new FunctionParticle(), target, caster);
        registerFunction(new FunctionPlaySound(), target, caster);
        registerFunction(new FunctionPlaysoundIndividual(), target, caster);
        registerFunction(new FunctionRayTraceTeleport(), target, caster);
        registerFunction(new FunctionSendMessage(), target, caster);
        registerFunction(new FunctionSetHealth(), target, caster);
        registerFunction(new FunctionSetHunger(), target, caster);
        registerFunction(new FunctionSetInvulnerable(), target, caster);
        registerFunction(new FunctionSetMana(), target, caster);
        registerFunction(new FunctionTeleportRelative(), target, caster);
        registerFunction(new FunctionExecute(), target, caster);
        registerFunction(new FunctionGetType(), target, caster);
        registerFunction(new FunctionGetTag(), target, caster);
        registerFunction(new FunctionSetTag(), target, caster);
        registerFunction(new FunctionTakeTag(), target, caster);
        
    	registerFunction(new FunctionStop(), spell);
    	registerFunction(new FunctionWait(), spell);
    	registerFunction(new FunctionIf(), spell);
    	registerFunction(new FunctionIfElse(), spell);
    	registerFunction(new FunctionReturnBoolean(), spell);
    	registerFunction(new FunctionStartSubScript(), spell);
    	registerFunction(new FunctionSetName(), spell);
    }
    
    
    public static void registerSelector(SpellScriptSelector<?> sel) {
    	selectors.put(sel.identifier(), sel);	
    }
    
    @SuppressWarnings("unchecked")
    public static <T> SpellScriptSelector<T> getSelector(String name) {
    	SpellScriptSelector<T> sel = (SpellScriptSelector<T>) selectors.getOrDefault(name, null);
    	return sel;
    }
    
    @SafeVarargs
	public static <T> void registerFunction(SpellScriptFunction<T> function, SpellScriptSelector<T>... newSelectors) {
    	for (SpellScriptSelector<T> selector : newSelectors) {
    		selector.addFunction(function);
    	}
    }
    
    @SuppressWarnings("unchecked")
	public static <T> boolean registerFunction(SpellScriptFunction<T> function, String... newSelectors) {
    	for (String selector : newSelectors) {
	    	SpellScriptSelector<T> sel = (SpellScriptSelector<T>) selectors.getOrDefault(selector, null);
	    	
	    	if (sel == null)
	    		return false;
	    	
	    	sel.addFunction(function);
    	}
    	return true;
    }
    
    @SafeVarargs
	public static <T> void unRegisterFunction(String function, SpellScriptSelector<T>... newSelectors) {
    	for (SpellScriptSelector<T> selector : newSelectors) {
    		selector.removeFunction(function);
    	}
    }
    
    @SuppressWarnings("unchecked")
    public static <T> boolean unRegisterFunction(String function, String... newSelectors) {
    	for (String selector : newSelectors) {
	    	SpellScriptSelector<T> sel = (SpellScriptSelector<T>) selectors.getOrDefault(selector, null);
	    	
	    	if (sel == null)
	    		return false;
	    	
	    	sel.removeFunction(function);
    	}
    	return true;
    }
    
    
}
