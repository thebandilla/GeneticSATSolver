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
			
	private long elapsedTime;
	
	
	public GeneticSATSolver (String filename, String directory) {
		this.formula = new Formula(filename, directory);
		
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
		
		int bestIndex1 = -1, bestIndex2 = -1;
		int bestPerf1 = -1, bestPerf2 = -1;
		
		//get the best two and save their indexes for later
		for (int i : assignmentPerformance.keySet()) {
			int perf = assignmentPerformance.get(i);
			
			if (perf >= bestPerf1) {
				bestIndex2 = bestIndex1;
				bestPerf2 = bestPerf1;
				
				bestIndex1 = i;
				bestPerf1 = perf;
			} else if (perf > bestPerf2) {
				bestIndex2 = i;
				bestPerf2 = perf;
			}
		}
		
		HashMap<Integer, TruthAssignment> newPop = new HashMap<Integer, TruthAssignment>();
		
		newPop.put(bestIndex1, population.remove(bestIndex1));
		newPop.put(bestIndex2, population.remove(bestIndex2));
		
		
		
		
		
		

		
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
