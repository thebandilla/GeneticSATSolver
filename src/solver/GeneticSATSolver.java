package solver;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import objects.*;

public class GeneticSATSolver {

	Formula formula;
	private HashMap<Integer, TruthAssignment> population;
	private TruthAssignment solution;
	private int[] variables;

	private long elapsedTime;
	private int finalVal = 0;
	private long numBitFlips = 0;

	private class HeapArg {
		public int index;
		public int performance;

		private HeapArg(int index, int performance) {
			this.index = index;
			this.performance = performance;
		}
	}

	private Comparator<HeapArg> heapArgComparator = (new Comparator<HeapArg>() {
		@Override
		public int compare(HeapArg arg0, HeapArg arg1) {
			// TODO Auto-generated method stub
			return Integer.compare(arg1.performance, arg0.performance);
		}
	});

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

		TruthAssignment solution = null;
		
		while (true) {
						
			PriorityQueue<HeapArg> best = new PriorityQueue<HeapArg>(heapArgComparator);
			
			for (int i : population.keySet()) {
				TruthAssignment curr = population.get(i);

				if (formula.evaluate(curr)) {
					finalVal = formula.getNumTrueClauses();
					solution = curr;
				}
				best.add(new HeapArg(i, formula.getNumTrueClauses()));
			}
			
			if (solution != null) {
//				System.out.println(finalVal);
				break;
			}

			HashMap<Integer, TruthAssignment> newPop = new HashMap<Integer, TruthAssignment>();

			for (int i = 0; i < 2; i++) {
				HeapArg top = best.poll();
				newPop.put(top.index, population.remove(top.index));
//				System.out.println(formula.getNumTrueClauses());
			}

			while (!best.isEmpty()) {
				HeapArg ha1, ha2;
				TruthAssignment p1, p2;
				TruthAssignment[] children;

				ha1 = best.poll();
				p1 = population.remove(ha1.index);

				ha2 = best.poll();
				p2 = population.remove(ha2.index);

				children = TruthAssignment.crossover(p1, p2);

				numBitFlips += children[0].controlledFlip(formula);
				numBitFlips += children[1].controlledFlip(formula);
				
				newPop.put(ha1.index, children[0]);
				newPop.put(ha2.index, children[1]);
			}

			population = newPop;
		}
		
		elapsedTime = System.currentTimeMillis() - elapsedTime;
		return solution;
	}

	public HashMap<Integer, TruthAssignment> getPopulation() {
		return population;
	}


	public long getElapsedTime() {
		return elapsedTime;
	}
	
	public long getNumBitFlips() {
		return numBitFlips;
	}
}
