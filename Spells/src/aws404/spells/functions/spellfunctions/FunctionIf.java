package aws404.spells.functions.spellfunctions;

import aws404.spells.FunctionData;
import aws404.spells.SpellFile;
import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.SpellType;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;
import aws404.spells.selectors.SpellScriptSelector;

public class FunctionIf extends SpellScriptFunction<SpellScriptSpell> {

	@Override
	public SpellScriptReturnValue runFunction(SpellScriptSpell spell, SpellScriptArgument[] args) {		
		FunctionData checkFunction = args[0].toFunction();
		String trueName = args[1].toString();
		
		SpellFile trueFunction = spell.getSpellFile().getSubScript(trueName);
		
		String checkName = checkFunction.getName();
		SpellScriptArgument[] checkArgs = checkFunction.getArgs();
		SpellScriptSelector<?> checkSelector = checkFunction.getSelector();
		
		SpellScriptReturnValue value = checkSelector.runFunction(checkName, spell, spell.getCaster(), spell.getTarget(), checkArgs);
		
		if (value == SpellScriptReturnValue.TRUE) {
			new SpellScriptSpell(spell.getSpellName(), trueFunction.getLines(), spell.getCaster(), spell.getTarget(), SpellType.IFSTATEMENT).cast();
		}
		
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "if";
	}

	


}
