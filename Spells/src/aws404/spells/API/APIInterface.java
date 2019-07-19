package aws404.spells.API;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.functions.SpellScriptFunction;

public interface APIInterface {
	/**
	* Returns true is the api is enabled
	* @return A boolean depending on if the API is enabled
	*/
	public boolean apiEnabled();
	
	/**
	* Sets the players mana to a specified amount
	* @param player - the target player
	* @param amount - the amount to set the targets mana to
	*/
	public void setMana(Player player, int amount);
	
	/**
	* Takes the determined amount of mana from the player if they have enough
	* @param player - the target player
	* @param amount - the amount to set the targets mana to
	* @return True if the player had enough mana and it was taken. False if the player did not have enough mana
	*/
	public boolean takeMana(Player player, int amount);
	
	/**
	* Adds the specified amount to the specified player
	* @param player - the target player
	* @param amount - the amount to add to the targets mana
	*/
	public void addMana(Player player, int amount);
	
	/**
	* Gets the players mana amount
	* @param player - the target player
	* @return The integer of the amount of mana
	*/
	public int getMana(Player player);
	
	/**
	* Casts the specified spell using the target player as the target and caster;
	* @param target - the player that will be used as the caster and target
	*/
	public void castSpell(String spellFile, LivingEntity target);
	
	/**
	* Casts the specified spell using the target and caster players
	* @param target - the player that will be used as the target
	* @param caster - the player that will be used as the caster
	*/
	public void castSpell(String spellFile, LivingEntity target, LivingEntity caster);
	
	/**
	* Registers a new function to the function register. The function must be a class that extends the SpellScriptFunction class
	* @param spellScriptFunctionClass - the class which extends SpellScriptFunction and contains the required methods
	*/
	public boolean registerFunction(SpellScriptFunction spellScriptFunctionClass);
	
}
