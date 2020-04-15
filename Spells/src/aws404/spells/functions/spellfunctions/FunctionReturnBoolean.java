package aws404.spells.functions.spellfunctions;

import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;

public class FunctionReturnBoolean extends SpellScriptFunction<SpellScriptSpell> {

	@Override
	public SpellScriptReturnValue runFunction(SpellScriptSpell target, SpellScriptArgument[] args) {
		Boolean bool = args[0].toBoolean();
		
		if (bool)
			return SpellScriptReturnValue.TRUE;
		else
			return SpellScriptReturnValue.FALSE;
	}

	@Override
	public String name() {
		return "returnBoolean";
	}
	

}
