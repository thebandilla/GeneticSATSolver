package objects;

import java.util.HashMap;
import java.util.Random;

public class TruthAssignment {
	
	private static Random rand = new Random();
	
	private static final int bound = Integer.MAX_VALUE;

	private HashMap<Integer, Boolean> variableAssignments;
	
	private int[] vars;

	public TruthAssignment (int[] variables, boolean randomAssignments) {
		variableAssignments = new HashMap<Integer, Boolean>();
		
		vars = variables;

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
	
	public TruthAssignment (HashMap<Integer, Boolean> hm) {
		this.variableAssignments = hm;
		int numVars = hm.keySet().size();
		
		this.vars = new int[numVars];
		
		for (int i = 1; i < numVars + 1; i++) {
			vars[i-1] = i;
		}
	}
	
	public int[] getVars() {
		return vars;
	}

	public boolean getAssignment(int var) {
		return variableAssignments.get(var);
	}
	
	@Override
	public String toString() {
		return variableAssignments.toString();
	}
	
	public void mutate() {
		int prob = rand.nextInt(bound);
		
		if (prob % 10 == 9) {
			// 10% chance of no mutation
			return;
		}
		
		for (int i : variableAssignments.keySet()) {
			prob = rand.nextInt(bound);
			boolean val = variableAssignments.get(i);
			if (prob % 2 == 0)
				continue;
			else
				//flips the assignment for a variable with a 50% probability
				variableAssignments.replace(i, !val);
		}
		
		
	}
	
	public void controlledFlip(Formula f) {
		boolean res = f.evaluate(this);
		
		int threshold = f.getNumTrueClauses();
		
		//for each assignment i check if flipping the bit results in more true clauses
		for (int i : variableAssignments.keySet()) {
			boolean temp = variableAssignments.get(i);
			
			variableAssignments.replace(i, !temp);
			
			f.evaluate(this);
			
			if (f.getNumTrueClauses() >= threshold) {
				threshold = f.getNumTrueClauses();
				continue;
			} else {
				variableAssignments.replace(i, temp);
			}
		}
	}
	
	public HashMap<Integer, Boolean> getAssignments() {
		return variableAssignments;
	}
	
	/**
	 * @param ta1
	 * First parent to be crossed-over
	 * @param ta2
	 * Second parent to be crossed-over
	 * @return
	 * A two element array containin the children that are resultant of the crossover
	 */
	public static TruthAssignment[] crossover(TruthAssignment ta1, TruthAssignment ta2) {
		TruthAssignment[] ret = new TruthAssignment[2];
		
		HashMap<Integer, Boolean> assign1, assign2;
		TruthAssignment child1, child2;
		
		assign1 = ta1.getAssignments();
		assign2 = ta2.getAssignments();
		
		if(assign1.size() != assign2.size())
			return null;
		
		for (int i : assign1.keySet()) {
			int prob = rand.nextInt(bound);
			
			boolean b1 = assign1.get(i), b2 = assign2.get(i);
			if (prob % 2 == 0) {
				continue;
			} else {
				assign1.replace(i, b2);
				assign2.replace(i, b1);
			}
		}
		
		child1 = new TruthAssignment(assign1);
		child2 = new TruthAssignment(assign2);
		
		ret[0] = child1;
		ret[1] = child2;
		
		return ret;
	}
}
