package aws404.spells.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import aws404.spells.FileManager;
import aws404.spells.Main;
import aws404.spells.ManaHandler;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.SpellScriptFunction;

public class SpellScriptAPI {
	protected Main plugin = Main.instance;
	protected FileManager fileManager = plugin.fileManager;
	protected ManaHandler manaHandler = plugin.manaHandler;

	/**
	 * Get an instance of the API, the building blocks!
	 * @return A new instance of the API
	 */
	public SpellScriptAPI() {
		plugin.apiIsEnabled = true;
	}

	/**
	 * Returns true is the api is enabled
	 * @return A boolean depending on if the API is enabled
	 */
	public boolean apiEnabled() {
		return plugin.apiIsEnabled;
	}

	/**
	 * Sets the players mana to a specified amount
	 * @param player the target player
	 * @param amount the amount to set the targets mana to
	 */
	public void setMana(Player player, int amount) {
		manaHandler.setMana(amount, player);
	}

	/**
	 * Takes the determined amount of mana from the player if they have enough
	 * @param player the target player
	 * @param amount the amount to set the targets mana to
	 * @return True if the player had enough mana and it was taken. False if the player did not have enough mana
	 */
	public boolean takeMana(Player player, int amount) {
		return manaHandler.takeMana(amount, player);
	}

	/**
	 * Adds the specified amount to the specified player
	 * @param player the target player
	 * @param amount the amount to add to the targets mana
	 */
	public void addMana(Player player, int amount) {
		manaHandler.giveMana(amount, player);
	}

	/**
	 * Gets the players mana amount
	 * @param player the target player
	 * @return The integer of the amount of mana
	 */
	public int getMana(Player player) {
		return manaHandler.getMana(player);
	}

	/**
	 * Casts the specified spell using the target player as the target and caster;
	 * @param spell the spell file to use (excluding the .spell)
	 * @param target the player that will be used as the caster and target
	 * @return if the spell was sucessfull or not
	 */
	public boolean castSpell(String spellFile, LivingEntity target, Boolean isWandCasted) {
		return new SpellScriptSpell(spellFile, target, target, isWandCasted).cast();
	}

	/**
	 * Casts the specified spell using the target player as the target and caster;
	 * @param spell the spell file to use (excluding the .spell)
	 * @param target the player that will be used as the caster and target
	 * @param isWandCasted whether the spell should be counted as casted from a wand
	 * @return if the spell was sucessfull or not
	 */
	public boolean castSpell(String spellFile, LivingEntity target) {
		return new SpellScriptSpell(spellFile, target, target, false).cast();
	}

	/**
	 * Casts the specified spell using the target and caster players
	 * @param spell the spell file to use (excluding the .spell)
	 * @param target the player that will be used as the target
	 * @param caster the player that will be used as the caster
	 * @param isWandCasted whether the spell should be counted as casted from a wand
	 * @return if the spell was sucessfull or not
	 */
	public boolean castSpell(String spellFile, LivingEntity target, LivingEntity caster, Boolean isWandCasted) {
		return new SpellScriptSpell(spellFile, caster, target, isWandCasted).cast();
	}

	/**
	 * Registers a new function to the function register. The function must be a class that extends the {@link SpellScriptFunction} class
	 * @param spellScriptFunctionClass the class which extends {@link SpellScriptFunction} and contains the required methods
	 * @return whether the addition was sucessfull
	 */
	public boolean registerFunction(SpellScriptFunction spellScriptFunctionClass) {
		return plugin.functionsRegister.registerCustomFunction(spellScriptFunctionClass);
	}

	/**
	 * Un register a function from the function register. The function must be a class that extends the SpellScriptFunction class
	 * @param spellScriptFunctionClass the class which extends {@link SpellScriptFunction} that you wish to remove
	 * @return whether the removal was sucessfull
	 * @return false if the function wasn't registered in the first place
	 */
	public boolean unRegisterFunction(SpellScriptFunction spellScriptFunctionClass) {
		return plugin.functionsRegister.deRegisterCustomFunction(spellScriptFunctionClass);
	}

	/**
	 * Get the itemstack for the wand item
	 * @param spell name
	 * @return wand item
	 */
	public ItemStack getWandItem(String spell) {
		if (spell == null) spell = "none";
		return plugin.getWandItem(spell);

	}

	public LivingEntity raytraceAs(Player p, Integer distance) {
		RayTraceResult rt = p.rayTraceBlocks(20, FluidCollisionMode.NEVER);
		Location loc1;

		if (rt == null) {
			loc1 = p.getLocation();
			Vector dir = loc1.getDirection();

			dir.normalize();
			dir.multiply(distance); 
			loc1.add(dir);

		} else {
			loc1 = rt.getHitBlock().getLocation();
		}

		ArrayList<LivingEntity> list = new ArrayList<LivingEntity>();

		addNearbys(loc1, p, list, 3);
		if (!list.isEmpty()) {
			return ((LivingEntity) list.get(0));
		}

		addNearbys(loc1, p, list, 5);
		if (!list.isEmpty()) {
			return ((LivingEntity) list.get(0));
		}

		addNearbys(loc1, p, list, 7);
		if (!list.isEmpty()) {
			return ((LivingEntity) list.get(0));
		}

		Entity dummy = loc1.getWorld().spawnEntity(loc1, EntityType.ARMOR_STAND);
		dummy.setInvulnerable(true);
		((ArmorStand) dummy).setSmall(true);
		dummy.setCustomName("Dummy");

		if (plugin.debug) {
			dummy.setCustomNameVisible(true);
			dummy.setGlowing(true);
		} else {
			((ArmorStand) dummy).setVisible(false);
		}

		return (LivingEntity) dummy;


	}

	private void addNearbys(Location loc, Player p, ArrayList<LivingEntity> list, double range) {
		Collection<Entity> nearby = p.getWorld().getNearbyEntities(loc, range, range, range, e -> (e.getType().isAlive()));
		if (nearby.contains(p)) nearby.remove(p);
		List<LivingEntity> additions = Arrays.asList(nearby.toArray(new LivingEntity[nearby.size()]));
		list.addAll(additions);
	}


}
