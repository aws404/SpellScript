package aws404.spells.functions;

import java.util.ArrayList;
import java.util.Iterator;

import aws404.spells.Main;

public class FunctionsRegister {
    @SuppressWarnings("unused")
	private Main plugin;
    
	public FunctionsRegister(Main plugin) {
        this.plugin = plugin;
	}

    private ArrayList<SpellScriptFunction> functions = new ArrayList<SpellScriptFunction>();

    public void registerFunctions() {
        this.functions.add(new FunctionAddMotion());
        this.functions.add(new FunctionAddRelativeMotion());
        this.functions.add(new FunctionAdjustHealth());
        this.functions.add(new FunctionAdjustHunger());
        this.functions.add(new FunctionAdjustMana());
        this.functions.add(new FunctionEffect());
        this.functions.add(new FunctionFire());
        this.functions.add(new FunctionSetFrozen());
        this.functions.add(new FunctionParticle());
        this.functions.add(new FunctionPlaySound());
        this.functions.add(new FunctionPlaysoundIndividual());
        this.functions.add(new FunctionRayTraceTeleport());
        this.functions.add(new FunctionSendMessage());
        this.functions.add(new FunctionSetHealth());
        this.functions.add(new FunctionSetHunger());
        this.functions.add(new FunctionSetInvulnerable());
        this.functions.add(new FunctionSetMana());
        this.functions.add(new FunctionTeleportRelative());
        this.functions.add(new FunctionExecute());
    }
    
    public boolean registerCustomFunction(SpellScriptFunction functionClass) {
    	this.functions.add(functionClass);
    	return true;
    }
    
    public boolean deRegisterCustomFunction(SpellScriptFunction functionClass) {
    	if (this.functions.contains(functionClass)) {
    		this.functions.remove(functionClass);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    public SpellScriptFunction getFunction(String functionName) {
    	Iterator<SpellScriptFunction> subcommands = this.functions.iterator();

        while (subcommands.hasNext()) {
        	SpellScriptFunction func = (SpellScriptFunction) subcommands.next();
        	
        	if (func.name().matches(functionName)) {
                return func;
            }
        }
        
        return new FunctionStop();
    }
}
