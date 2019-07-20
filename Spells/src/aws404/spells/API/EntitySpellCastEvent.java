package aws404.spells.API;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntitySpellCastEvent extends Event implements Cancellable{
	
	private Boolean isCancelled;
	
	private String spellName;
	private LivingEntity target;
	private LivingEntity caster;
	private Boolean castByWand;
	
    private static final HandlerList HANDLERS = new HandlerList();
    
    public EntitySpellCastEvent(String spellName, LivingEntity target, LivingEntity caster, Boolean castByWand) {
        this.isCancelled = false;
        this.spellName = spellName;
        this.target = target;
        this.caster = caster;
        this.castByWand = castByWand;
	}

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Tests if the event has been cancled
     * @return Whether the event has been canceled
     */
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * Set whether the event should be canceled or not
	 * @param value - Whether the event should be canceled or not
	 */
	@Override
	public void setCancelled(boolean value) {
		this.isCancelled = value;
	}
	
	/**
	 * Gets the name of the spell that has been cast
	 * @return The name of the spell file
	 */
	public String getSpell() {
		return spellName;
	}
	
	/**
	 * Gets the entity that is the caster of the spell
	 * @return A LivingEntity of the caster
	 */
	public LivingEntity getCaster() {
		return caster;
	}
	
	/**
	 * Gets the entity that is the target of the spell
	 * @return A LivingEntity of the target
	 */
	public LivingEntity getTarget() {
		return target;
	}
	
	/**
	 * Gets whether the spell was initiated by a wand or not
	 * @return True if the spell was cast from a wand
	 */
	public Boolean castedByWand() {
		return castByWand;
	}
	
	/**
	 * Allows modification of the spell that was cast
	 * @param spellName - the new name of the spell
	 */
	public void setSpell(String spellName) {
		this.spellName = spellName;
	}
	
	/**
	 * Allows modification of the target 
	 * @param target - the new spell target
	 */
	public void setTarget(LivingEntity target) {
		this.target = target;
	}
	
	/**
	 * Allows modification of the caster
	 * @param caster - the new spell caster
	 */
	public void setCaster(LivingEntity caster) {
		this.caster = caster;
	}
	
	/**
	 * Modify whether the spell was initiated by a wand or not
	 * @param castedByWand - the new value
	 */
	public void setCastedByWand(Boolean castedByWand) {
		this.castByWand = castedByWand;
	}



}
