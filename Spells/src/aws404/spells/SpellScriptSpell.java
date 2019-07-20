package aws404.spells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import aws404.spells.API.EntitySpellCastEvent;
import aws404.spells.functions.FunctionsRegister;
import aws404.spells.functions.SpellScriptFunction;
import net.md_5.bungee.api.ChatColor;

public class SpellScriptSpell {
	
	protected Main plugin = Main.instance;
	protected FunctionsRegister functionsRegister = plugin.functionsRegister;
	protected ManaHandler manaHandler = plugin.manaHandler;
	
	private Boolean wasSucessfull;
	private String spellName;
	private LivingEntity caster;
	private LivingEntity target;
	private Boolean castedByWand;
	private String[] lines;
	
	
	public SpellScriptSpell(String spellName, LivingEntity caster, LivingEntity target, Boolean castedByWand) {
		this.spellName = spellName;
		this.caster = caster;
		this.target = target;
		this.castedByWand = castedByWand;
		this.lines = plugin.spellFiles.get(spellName);
	}
	
	public boolean castWithMana(Integer requiredMana) {
		Player player;
		if (caster.getType() == EntityType.PLAYER) player = (Player) caster;
		else return true;
		if (manaHandler.getMana(player) >= requiredMana) {
	    	EntitySpellCastEvent event = new EntitySpellCastEvent(spellName, target, caster, castedByWand);
	    	Bukkit.getPluginManager().callEvent(event);
	    	if (!event.isCancelled()) {
	    		this.wasSucessfull = true;
	        	doActionRecursively(0);  
	    	} else {
	    		this.wasSucessfull = false;
	    	}
	    	return this.wasSucessfull;
		} else {
			plugin.actionBarClass.sendActionbar(player, ChatColor.RED + "Not Enough Mana!");
	    	return false;
		}
	}
	
	public boolean cast() {
    	EntitySpellCastEvent event = new EntitySpellCastEvent(spellName, target, caster, castedByWand);
    	Bukkit.getPluginManager().callEvent(event);
    	if (!event.isCancelled()) {
    		this.wasSucessfull = true;
        	doActionRecursively(0);  
    	} else {
    		this.wasSucessfull = false;
    	}
    	return this.wasSucessfull;
	}
	
	public void setSucessfull(Boolean value) {
		this.wasSucessfull = value;
	}
	
	public Boolean wasSucessfull() {
		return this.wasSucessfull;
	}
	
	private void doActionRecursively(Integer line) {
		if (lines.length <= line) {
    		if (plugin.debug) target.sendMessage("Done");
    		return;
    	}
    	int newLine = line +1;
    	String[] segments = lines[line].split("\\.", 2);
		String selector = segments[0];


		//If no selector then it is a comment
		if (selector.contentEquals("")) {
			doActionRecursively(newLine);
    	    return;
		}

		//Spell Selector
    	if (selector.contentEquals("spell")) {
		      for(int i = 1; i < segments.length; i++){
		    	  String instruction = segments[i];	
		    	  if (plugin.debug) target.sendMessage(instruction);
		    	  
		    	  if (instruction.contains("wait")) {
		    		  Long arg = SpellScriptVariable.decompileInstruction(instruction)[0].getLong();
		    		  Long amount = arg *2L/100L;;
		    	        BukkitScheduler scheduler = plugin.getServer().getScheduler();
		    	        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
		    	            @Override
		    	            public void run() {
		    	            	if (plugin.debug) target.sendMessage("finished " + String.valueOf(amount));
		    	            	doActionRecursively(newLine);
		    	            	return;
		    	            }
		    	        }, amount);
		    	        return;
		    	  }
		    	  if (instruction.contains("stop")) {
		    		  if (plugin.debug) target.sendMessage(instruction + "STOPPING");
		    		  return;
		    	  }
		    	  
		      }
		}
    	
    	//Target and Caster Selector
    	if (selector.contains("target") || selector.contains("caster")) {
    		List<LivingEntity> spellTarget = new ArrayList<LivingEntity>();
    		
    		if(!selector.contains("[")) {
    			
    			
    			//Targeting Single Entity
    			if (selector.equalsIgnoreCase("target")) spellTarget.add(target);
    			if (selector.equalsIgnoreCase("caster")) spellTarget.add(caster);
    		} else {
    			
    			
    			//Radius Target - target
    			int radius = Integer.parseInt(StringUtils.substringBetween(selector, "[", "]"));
    			if (selector.contains("target")) {
    				List<Entity> nearby = target.getNearbyEntities(radius, radius, radius);
    				for (Entity e : nearby) {
    					if (e.getType().isAlive() && !e.equals(caster)) spellTarget.add((LivingEntity) e);
    				}
    			}
    			
    			//Radius target - caster
    			if (selector.contains("caster")) {
    				List<Entity> nearby = caster.getNearbyEntities(radius, radius, radius);
    				for (Entity e : nearby) {
    					if (e.getType().isAlive() && !e.equals(caster)) spellTarget.add((LivingEntity) e);
    				}
    			}
    		}
    		
    		
			String instruction = segments[1];
			SpellScriptVariable[] args = SpellScriptVariable.decompileInstruction(instruction);
			String functionName = instruction.substring(0, instruction.indexOf("("));
			SpellScriptFunction function = functionsRegister.getFunction(functionName);
			if (plugin.debug) caster.sendMessage(ChatColor.BOLD + "Script: " + instruction);
			if (plugin.debug) caster.sendMessage(ChatColor.BOLD + "Function Name: " + function.name());
			if (plugin.debug) caster.sendMessage(ChatColor.ITALIC + "Arguments: " + Arrays.toString(args));
			if (plugin.debug) caster.sendMessage(ChatColor.ITALIC + "Amount of Targets: " + spellTarget.size());
			
    		for (LivingEntity currentTarget : spellTarget) {
    			if (plugin.debug) caster.sendMessage("Target: " + currentTarget.toString());;
    			if (!function.runFunction(currentTarget, args)) this.wasSucessfull = false;		
    		}
			if (plugin.debug) caster.sendMessage(" ");
			
			//Recurse
			if (this.wasSucessfull) doActionRecursively(newLine);
    	    return;
		    	  
    	}
    	//if target type isnt caught 
    	plugin.sendError("On line "+ newLine + ", " + selector + " is an unkown selector");
	}

}
