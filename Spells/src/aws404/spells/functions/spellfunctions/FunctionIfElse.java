package aws404.spells.functions.spellfunctions;

import aws404.spells.FunctionData;
import aws404.spells.SpellFile;
import aws404.spells.SpellScriptArgument;
import aws404.spells.SpellScriptSpell;
import aws404.spells.SpellType;
import aws404.spells.functions.SpellScriptFunction;
import aws404.spells.functions.SpellScriptReturnValue;
import aws404.spells.selectors.SpellScriptSelector;

public class FunctionIfElse extends SpellScriptFunction<SpellScriptSpell> {

	@Override
	public SpellScriptReturnValue runFunction(SpellScriptSpell spell, SpellScriptArgument[] args) {		
		FunctionData checkFunction = args[0].toFunction();
		String trueName = args[1].toString();
		String falseName = args[2].toString();
		
		SpellFile trueFunction = plugin.spellFiles.get(spell.getSpellName()).getSubScript(trueName);
		SpellFile falseFunction = plugin.spellFiles.get(spell.getSpellName()).getSubScript(falseName);
		
		String checkName = checkFunction.getName();
		SpellScriptArgument[] checkArgs = checkFunction.getArgs();
		SpellScriptSelector<?> checkSelector = checkFunction.getSelector();
		
		SpellScriptReturnValue value = checkSelector.runFunction(checkName, spell, spell.getCaster(), spell.getTarget(), checkArgs);
		
		if (value == SpellScriptReturnValue.TRUE) {
			new SpellScriptSpell(spell.getSpellName(), trueFunction, spell.getCaster(), spell.getTarget(), SpellType.IFSTATEMENT).cast();
		} else if (value == SpellScriptReturnValue.FALSE) {
			new SpellScriptSpell(spell.getSpellName(), falseFunction, spell.getCaster(), spell.getTarget(), SpellType.IFSTATEMENT).cast();
		}
		
		return SpellScriptReturnValue.CONTINUE;
	}

	@Override
	public String name() {
		return "ifElse";
	}

	


}
