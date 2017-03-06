package solver;

import java.util.HashMap;

import objects.TruthAssignment;

public class SolverTest {

	private static GeneticSATSolver solver;
	
	public static void main (String[] args) {
		solver = new GeneticSATSolver("uf20-01.cnf", "uf20-91");
		HashMap<Integer, TruthAssignment> test = solver.getPopulation();
		
		for (int i : test.keySet()) {
			System.out.println(test.get(i));
		}
	}
}
