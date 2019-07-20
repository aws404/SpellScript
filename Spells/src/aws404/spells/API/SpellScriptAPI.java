package aws404.spells.API;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import aws404.spells.FileManager;
import aws404.spells.Main;
import aws404.spells.ManaHandler;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.SpellScriptFunction;

public class SpellScriptAPI implements APIInterface {
	protected Main plugin = Main.instance;
	protected FileManager fileManager = plugin.fileManager;
	protected ManaHandler manaHandler = plugin.manaHandler;
	
	public SpellScriptAPI() {
		plugin.apiIsEnabled = true;
	}
	
	@Override
	public boolean apiEnabled() {
		return plugin.apiIsEnabled;
	}

	@Override
	public void setMana(Player player, int amount) {
		manaHandler.setMana(amount, player);
	}

	@Override
	public boolean takeMana(Player player, int amount) {
		return manaHandler.takeMana(amount, player);
	}

	@Override
	public void addMana(Player player, int amount) {
		manaHandler.giveMana(amount, player);
	}

	@Override
	public int getMana(Player player) {
		return manaHandler.getMana(player);
	}

	@Override
	public boolean castSpell(String spellFile, LivingEntity target, Boolean isWandCasted) {
		return new SpellScriptSpell(spellFile, target, target, isWandCasted).cast();
	}
	
	@Override
	public boolean castSpell(String spellFile, LivingEntity target) {
		return new SpellScriptSpell(spellFile, target, target, false).cast();
	}

	@Override
	public boolean castSpell(String spellFile, LivingEntity target, LivingEntity caster, Boolean isWandCasted) {
		return new SpellScriptSpell(spellFile, caster, target, isWandCasted).cast();
	}

	@Override
	public boolean registerFunction(SpellScriptFunction spellScriptFunctionClass) {
		return plugin.functionsRegister.registerCustomFunction(spellScriptFunctionClass);
	}
	
	@Override
	public boolean unRegisterFunction(SpellScriptFunction spellScriptFunctionClass) {
		return plugin.functionsRegister.deRegisterCustomFunction(spellScriptFunctionClass);
	}


}
