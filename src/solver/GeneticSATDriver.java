package solver;

import java.io.File;

public class GeneticSATDriver {

	public static void main(String[] args) {
		File problems = new File("problems");
		File[] folders = problems.listFiles();
		
		GeneticSATSolver gsats;
		
		for (File f : folders) {
			if (f.isDirectory() == false)
				continue;
			String[] fileNames = f.list();
			
			long avgTime = 0, avgFlips = 0;
			
			System.out.println("\n" + f.getName() + "\n");
			
			int instances = 20;
			
			int successes = 0;
			
			for (int i = 0; i < instances; i++) {
				String s = fileNames[i];
				gsats = new GeneticSATSolver(s, f.getName());
				if(gsats.solve() != null) {
					successes++;
				}
				avgFlips += gsats.getNumBitFlips();
				avgTime += gsats.getElapsedTime();
//				System.out.println(gsats.getElapsedTime());
			}
			
			System.out.println(avgTime/instances + "\t" + avgFlips/instances + "\t" + successes);
		}
	}
}
