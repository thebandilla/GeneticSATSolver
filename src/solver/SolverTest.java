package solver;

import java.util.HashMap;

import objects.TruthAssignment;

public class SolverTest {

	private static GeneticSATSolver solver;
	
	public static void main (String[] args) {
		solver = new GeneticSATSolver("uf100-01.cnf", "uf100-430");
		TruthAssignment test = solver.solve();
		
		long time = solver.getElapsedTime();
		
		System.out.println(time + " ms\n" + test);
	}
}
