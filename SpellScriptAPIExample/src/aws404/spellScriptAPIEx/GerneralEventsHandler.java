package aws404.spellScriptAPIEx;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import aws404.spells.API.EntitySpellCastEvent;
import aws404.spells.API.SpellScriptAPI;




public class GerneralEventsHandler implements Listener{
	protected Main plugin = Main.instance;
	protected SpellScriptAPI ssapi = plugin.ssapi;
	   
    
    //If a player takes damage then cast the heal spell on them
    @EventHandler
    public void damagedEvent(EntityDamageEvent e) {
    	if (e.getEntityType() == EntityType.PLAYER) {
    		ssapi.castSpell("heal", (LivingEntity) e);
    	}
    }
    
    @EventHandler
    public void spellCastEvent(EntitySpellCastEvent e) {
    	LivingEntity caster = e.getCaster();
    	String spell = e.getSpell();
    	LivingEntity target = e.getTarget();
    	Boolean castedByWand = e.castedByWand();
    	if (spell.contentEquals("heal") && castedByWand) {
    		e.setCancelled(true);
    		caster.sendMessage("Nope!");
    		target.sendMessage("Nope!");
    	}
    }
  
}

	

