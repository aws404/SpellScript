package aws404.spells.functions;

import aws404.spells.SpellScriptArgument;

@SuppressWarnings("rawtypes")
public class FunctionNotFound extends SpellScriptFunction {

	@Override
	public String name() {
		return "notfound";
	}

	@Override
	public SpellScriptReturnValue runFunction(Object target, SpellScriptArgument[] args) {
		plugin.sendError("Function not found");
		return SpellScriptReturnValue.ERROR;
	}

}
