package aws404.spells.selectors;

import org.bukkit.entity.LivingEntity;

import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class SpellScriptSpellSelector extends SpellScriptSelector<SpellScriptSpell> {

	@Override
	public String identifier() {
		return "spell";
	}

	@Override
	public SpellScriptReturnValue runFunction(SpellScriptFunction<SpellScriptSpell> function, SpellScriptSpell spell,LivingEntity target, LivingEntity caster, SpellScriptArgument[] args, Integer radius) {
		return function.runFunction(spell, args);
	}

}
