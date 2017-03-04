package objects;

public class Clause {

	private int[] variables;
	private int size;
	
	public Clause (int[] vars) {
		this.variables = vars;
		this.size = vars.length;
	}
	
	public int size() {
		return size;
	}
	
	public boolean evaluate(boolean[] assignments) {
		boolean result = false;
		
		for (int i = 0; i < assignments.length; i++) {
			boolean val = (variables[i] > 0) ? assignments[i] : !assignments[i];
			//gets the value for the variable based on the variable being negated or not
			result = result || val;
		}
		
		//will return false iff all of the variable assignments result in false.
		//if any single variable assignment results in true the entire clause is true
		return result;
	}
	
	public int[] getVars() {
		return variables;
	}
	
	@Override
	public String toString() {
		String result = "(" + variables[0];
		
		if (variables.length > 1) {
			for (int i = 1; i < variables.length; i++) {
				result += " or " + variables[i];
			}
		}
		return result + ")";		
	}
}
