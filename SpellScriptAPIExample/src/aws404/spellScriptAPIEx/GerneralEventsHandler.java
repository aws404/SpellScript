package aws404.spellScriptAPIEx;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import aws404.spells.SpellType;
import aws404.spells.API.EntitySpellCastEvent;
import aws404.spells.API.SpellScriptAPI;




public class GerneralEventsHandler implements Listener{
	protected Main plugin = Main.instance;
	protected SpellScriptAPI ssapi = plugin.ssapi;
	   
    
    @EventHandler
    public void damagedEvent(EntityDamageEvent e) {
    	Entity entity =  e.getEntity();
    	if (entity.getType().isAlive()) {
    		/*
    		 * Cast a spell by name, you can specify a separate caster and 
    		 * target or just supply one entity to be used as both.
    		 * 
    		 * Arguments vary for this function however generally:
    		 * spellName -  the name of the spell to cast
    		 * target - the LivingEntity to be used as the spells target
    		 * caster - the LivingEntity to be used as the spells caster
    		 * isWandCasted - if the spell should be considered wand casted.
    		 */	
    		ssapi.castSpell("heal", (LivingEntity) entity, (LivingEntity) entity, SpellType.OTHER);
    	}
    }
    
    /*
     * The EntitySpellCastEvent is called every time a spell is cast by any entity.
     * NOTE: this also includes when subscripts are started.
     */
    @EventHandler
    public void spellCastEvent(EntitySpellCastEvent e) {
    	LivingEntity caster = e.getCaster();
    	String spell = e.getSpell();
    	LivingEntity target = e.getTarget();
    	SpellType spellType = e.getSpellType();
    	
    	if (spell.contentEquals("heal") && spellType == SpellType.NORMAL) {
    		e.setCancelled(true);
    		caster.sendMessage("Nope!");
    		target.sendMessage("Nope!");
    	}
    }
  
}

	

