package solver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import objects.*;

public class GeneticSATSolver {

	Formula formula;
	private HashMap<Integer, TruthAssignment> population;
	private TruthAssignment solution;
	private int[] variables;
	
	private List<Integer> elites;
			
	private long elapsedTime;
	
	
	public GeneticSATSolver (String filename, String directory) {
		this.formula = new Formula(filename, directory);
		
		this.elites = new ArrayList<Integer>(2);
		
		this.population = new HashMap<Integer, TruthAssignment>();
		int numVars = formula.getNumVars();
		variables = new int[numVars];
		
		for (int i = 1; i < numVars + 1; i++)
			variables[i-1] = i;
		
		for (int i = 0; i < 10; i++) {
			population.put(i, new TruthAssignment(variables, true));
		}
	}
	
	
	public TruthAssignment solve() {
		elapsedTime = System.currentTimeMillis();
		HashMap<Integer, Integer> assignmentPerformance = new HashMap<Integer, Integer>();
		
		for (int i : population.keySet()) {
			TruthAssignment curr = population.get(i);
			formula.evaluate(curr);
			
			int performance = formula.getNumTrueClauses();
			
			assignmentPerformance.put(i, performance);
		}
		
		

		
		elapsedTime = elapsedTime - System.currentTimeMillis();
		return null;
	}
	
	public HashMap<Integer, TruthAssignment> getPopulation() {
		return population;
	}
	
	
	public long getElapsedTime() {
		return elapsedTime;
	}
}
