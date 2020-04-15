package aws404.spells.functions.spellfunctions;

import aws404.spells.SpellFile;
import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.SpellType;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionStartSubScript extends SpellScriptFunction<SpellScriptSpell> {

	@Override
	public SpellScriptReturnValue runFunction(SpellScriptSpell spell, SpellScriptArgument[] args) {
		String name = args[0].toString();
		
		SpellFile subScript = plugin.spellFiles.get(spell.getSpellName()).getSubScript(name);
		
		if (subScript == null) {
			plugin.sendError("Subscript " + name + " could not be found.");
			return SpellScriptReturnValue.ERROR;
		}
		
		
		SpellScriptSpell sub = new SpellScriptSpell(spell.getSpellName(), subScript, spell.getCaster(), spell.getTarget(), SpellType.SUBSCRIPT);
		
		sub.cast();
		
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "startSubScript";
	}

	


}
