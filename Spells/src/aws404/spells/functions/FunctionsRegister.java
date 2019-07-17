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

    private ArrayList<Function> functions = new ArrayList<Function>();

    public void registerFunctions() {
        this.functions.add(new FunctionAddMotion());
        this.functions.add(new FunctionAddRelativeMotion());
        this.functions.add(new FunctionAdjustHealth());
        this.functions.add(new FunctionAdjustHunger());
        this.functions.add(new FunctionAdjustMana());
        this.functions.add(new FunctionEffect());
        this.functions.add(new FunctionFire());
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
    }
    
    public Function getFunction(String functionName) {
    	Iterator<Function> subcommands = this.functions.iterator();

        while (subcommands.hasNext()) {
        	Function func = (Function) subcommands.next();
        	
        	if (func.name().matches(functionName)) {
                return func;
            }
        }
        
        return null;
    }
}
