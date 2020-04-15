package aws404.spells;


import aws404.spells.selectors.SpellScriptSelector;

public class FunctionData {
	private SpellScriptSelector<?> selector;
	private String name;
	private SpellScriptArgument[] args;
	private Integer radius;
	
	/**
	 * Create new FunctionData
	 * @param selector the {@link SpellScriptSelector} for the function to apply to
	 * @param name the name of the function
	 * @param args an {@link Array} of {@link SpellScriptArgument}
	 * @param radius the radius for the function to be applied to
	 */
	public FunctionData(SpellScriptSelector<?> selector, String name, SpellScriptArgument[] args, Integer radius) {
		this.selector = selector;
		this.name = name;
		this.args = args;
		this.radius = radius;
	}
	
	/**
	 * @return the functions selector
	 */
	public SpellScriptSelector<?> getSelector() {
		return selector;
	}
	
	/**
	 * @return the functions name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the functions radius
	 */
	public Integer getRadius() {
		return radius;
	}

	/**
	 * Sets the name of the function
	 * @param name the function name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the functions arguments
	 */
	public SpellScriptArgument[] getArgs() {
		return args;
	}

	/**
	 * Sets the functions arguments
	 * @param args an {@link Array} of {@link SpellScriptArgument}s
	 */
	public void setArgs(SpellScriptArgument[] args) {
		this.args = args;
	}
	

}
