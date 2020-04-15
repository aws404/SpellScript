package aws404.spells.functions.spellfunctions;

import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionSetName extends SpellScriptFunction<SpellScriptSpell> {

	@Override
	public SpellScriptReturnValue runFunction(SpellScriptSpell spell, SpellScriptArgument[] args) {
		//Dummy function used to set the names of subscripts
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "setName";
	}

	


}
