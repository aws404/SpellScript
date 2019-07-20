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

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.isCancelled = arg0;
	}
	
	public String getSpell() {
		return spellName;
	}
	
	public LivingEntity getCaster() {
		return caster;
	}
	
	public LivingEntity getTarget() {
		return target;
	}
	
	public Boolean castedByWand() {
		return castByWand;
	}
	
	public void setSpell(String spellName) {
		this.spellName = spellName;
	}
	
	public void setTarget(LivingEntity target) {
		this.target = target;
	}
	
	public void setCaster(LivingEntity caster) {
		this.caster = caster;
	}
	
	public void setCastedByWand(Boolean castedByWand) {
		this.castByWand = castedByWand;
	}



}
