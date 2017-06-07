package asteroids.model.program.expression;

import java.util.List;
import java.util.Set;

import asteroids.model.program.*;
import asteroids.part3.programs.SourceLocation;

public class FunctionCallExpression extends Element implements Expression{
	
	private String name;
	private List<Expression> arguments;
	private boolean breakDiscovered;

	public FunctionCallExpression(SourceLocation sourceLocation, String name, List<Expression> arguments) {
		super(sourceLocation);
		this.setName(name);
		this.setArguments(arguments);
	}

	private List<Expression> getArguments() {
		return arguments;
	}

	private String getName() {
		return name;
	}

	private boolean isBreakDiscovered() {
		return breakDiscovered;
	}

	private void setArguments(List<Expression> arguments) {
		this.arguments = arguments;
	}
	
	private void setBreakDiscovered(boolean breakDiscovered) {
		this.breakDiscovered = breakDiscovered;
	}

	private void setName(String name) {
		this.name = name;
	}

	@Override
	public Object calculate() throws Exception {
		Function function =  getProgram().getFunction(name);
		Object[] args = getArguments().stream().map(arg -> arg.calculate()).toArray();
		Object result = function.calculate(args);
		if (function.hasBreak()) setBreakDiscovered(true);
		else setBreakDiscovered(false);
		return result;
	}

	@Override
	public Object calculate(Object[] actualArgs, Set localVars) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


}
