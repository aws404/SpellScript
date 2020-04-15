package aws404.spells;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import aws404.spells.API.EntitySpellCastEvent;
import aws404.spells.functions.FunctionsRegister;
import aws404.spells.functions.SpellScriptReturnValue;
import aws404.spells.selectors.SpellScriptSelector;

public class SpellScriptSpell {

	protected Main plugin = Main.instance;
	protected FunctionsRegister functionsRegister = plugin.functionsRegister;
	protected ManaHandler manaHandler = plugin.manaHandler;

	//private Boolean wasSucessfull;
	private String spellName;
	private LivingEntity caster;
	private LivingEntity target;
	private SpellType spellType;
	private ArrayList<FunctionData> lines;
	private Integer currentLine = 0;
	private SpellFile spellFile;
	private SpellScriptReturnValue state = SpellScriptReturnValue.CONTINUE;

	/**
	 * Create a new {@link SpellScriptSpell} element
	 * @param spellName the name of the spell
	 * @param caster the {@link LivingEntity} to be the caster
	 * @param target the {@link LivingEntity} to be the target
	 * @param spellType the type of spell
	 */
	public SpellScriptSpell(String spellName, LivingEntity caster, LivingEntity target, SpellType spellType) {
		this.spellName = spellName;
		this.caster = caster;
		this.target = target;
		this.spellType = spellType;
		this.spellFile = plugin.spellFiles.get(spellName);
		this.lines = spellFile.getLines();
	}
	
	/**
	 * Create a new {@link SpellScriptSpell} element
	 * @param lines an {@link ArrayList} of {@link FunctionData}
	 * @param caster the {@link LivingEntity} to be the caster
	 * @param target the {@link LivingEntity} to be the target
	 * @param spellType whether the spell should considered wand casted
	 */
	public SpellScriptSpell(String spellName, ArrayList<FunctionData> lines, LivingEntity caster, LivingEntity target, SpellType spellType) {
		this.spellName = spellName;
		this.caster = caster;
		this.target = target;
		this.spellType = spellType;
		this.lines = lines;
	}

	/**
	 * Create a new {@link SpellScriptSpell} element
	 * @param spellName the name of the spell
	 * @param caster the {@link LivingEntity} to be the caster
	 * @param spellType whether the spell should considered wand created
	 */
	public SpellScriptSpell(String spellName, LivingEntity caster, SpellType spellType) {
		this.spellName = spellName;
		this.caster = caster;
		this.spellFile = plugin.spellFiles.get(spellName);
		this.lines = spellFile.getLines();
		this.spellType = spellType;
	}
	
	/**
	 * Create a new {@link SpellScriptSpell} element
	 * @param spellFile the {@link SpellFile} to base the spell off
	 * @param caster the {@link LivingEntity} to be the caster
	 * @param target the {@link LivingEntity} to be the target
	 * @param spellType whether the spell should considered wand casted
	 */
	public SpellScriptSpell(String spellName, SpellFile spellFile, LivingEntity caster, LivingEntity target, SpellType spellType) {
		this.spellName = spellName;
		this.caster = caster;
		this.target = target;
		this.spellFile = spellFile;
		this.lines = spellFile.getLines();
		this.spellType = spellType;
	}

	/**
	 * Change the target
	 * @param target the {@link LivingEntity} to be the target
	 */
	public void setTarget(LivingEntity target) {
		this.target = target;
	}

	/**
	 * Only casts the spell if the player has enough mana, does not take mana
	 * @param requiredMana the amount of mana required
	 * @return false if the player does not have enough mana or the spell failed, true if spell was cast sucessfully
	 */
	public boolean castWithMana(Integer requiredMana) {
		Player player;
		if (caster.getType() == EntityType.PLAYER) player = (Player) caster;
		else return true;
		if (manaHandler.getMana(player) >= requiredMana) {
			SpellScriptReturnValue value = cast();
			if (value == SpellScriptReturnValue.FINSIH) {
				manaHandler.takeMana(requiredMana, player);
				return true;
			}
		} else {
			plugin.actionBarClass.sendActionbar(player, ChatColor.RED + "Not Enough Mana!");
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
		}
		return false;
	}

	/**
	 * Casts the spell element
	 * @return the {@link SpellScriptReturnValue} of the spell
	 */
	public SpellScriptReturnValue cast() {
		EntitySpellCastEvent event = new EntitySpellCastEvent(spellName, target, caster, spellType);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			nextAction(); 
		} else {
			state = SpellScriptReturnValue.STOP;
		}
		return state;
	}

	/**
	 * The current state of the spell
	 * @return the {@link SpellScriptReturnValue} of the spell in its current state
	 */
	public SpellScriptReturnValue getState() {
		return state;
	}

	/**
	 * @return the spell name
	 */
	public String getSpellName() {
		return spellName;
	}

	/**
	 * @return the target of the spell
	 */
	public LivingEntity getTarget() {
		return target;
	}
	
	/**
	 * @return the caster of the spell
	 */
	public LivingEntity getCaster() {
		return caster;
	}
	
	/**
	 * @return the {@link SpellFile} used to generate the spell 
	 * @return null if not supplied
	 */
	public SpellFile getSpellFile() {
		return spellFile;
	}

	/**
	* Starts the next action of the spell
 	*/
	public void nextAction() {
		if (lines.size() <= currentLine) {
			state = SpellScriptReturnValue.FINSIH;
			return;
		}

		FunctionData functionData = lines.get(currentLine);

		currentLine += 1;

		SpellScriptSelector<?> selector = functionData.getSelector();
		Integer radius = functionData.getRadius();
		String functionName = functionData.getName();
		SpellScriptArgument[] args = functionData.getArgs();
		
		if (plugin.debug) {
			caster.sendMessage(ChatColor.BOLD + "Selector: " + ChatColor.RESET + selector.identifier());
			caster.sendMessage(ChatColor.BOLD + "Function: " + ChatColor.RESET + functionName);
			caster.sendMessage(ChatColor.BOLD + "Arguments:");
			for (SpellScriptArgument arg : args) {
				caster.sendMessage(" - " + arg.literalValue());
			}
		}
		
		SpellScriptReturnValue value = selector.runFunction(functionName, this, target, caster, args, radius);
		
		if (plugin.debug) caster.sendMessage(ChatColor.BOLD + "Return Code: " + ChatColor.RESET + value.toString());
			
		
		switch(value) {
			case CONTINUE:
			case TRUE:
			case FALSE:
				nextAction();
				break;
			case ERROR:
				plugin.sendError("On line " + currentLine);
			case FINSIH:
			case STOP:
				state = value;
				return;
		}
	}
	
}

	//if (plugin.debug) caster.sendMessage(ChatColor.BOLD + "Script: " + instruction);
	//if (plugin.debug) caster.sendMessage(ChatColor.BOLD + "Function Name: " + functionName);
	//if (plugin.debug) caster.sendMessage(ChatColor.ITALIC + "Amount of Targets: " + spellTarget.size());

