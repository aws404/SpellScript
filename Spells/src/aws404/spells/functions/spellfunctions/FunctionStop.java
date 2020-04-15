package aws404.spells.functions.spellfunctions;

import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionStop extends SpellScriptFunction<SpellScriptSpell> {

	@Override
	public SpellScriptReturnValue runFunction(SpellScriptSpell spell, SpellScriptArgument[] args) {
		plugin.sendError("Spell Stoped");
		
		return SpellScriptReturnValue.FINSIH;
	}

	@Override
	public String name() {
		return "stop";
	}

	


}
