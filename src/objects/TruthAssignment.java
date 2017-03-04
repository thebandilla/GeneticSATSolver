package objects;

import java.util.HashMap;
import java.util.Random;

public class TruthAssignment {

	private HashMap<Integer, Boolean> variableAssignments;

	public TruthAssignment (int[] variables, boolean randomAssignments) {
		variableAssignments = new HashMap<Integer, Boolean>();

		if (randomAssignments) {
			Random rand = new Random();
			for (int i : variables) {
				variableAssignments.put(Math.abs(i), rand.nextBoolean());
			}
		}
		else {
			for (int i : variables) {
				variableAssignments.put(Math.abs(i), false);
			}
		}
	}

	public boolean getAssignment(int var) {
		return variableAssignments.get(var);
	}
	

}
