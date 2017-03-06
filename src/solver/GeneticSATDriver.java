package solver;

import java.io.File;

public class GeneticSATDriver {

	public static void main(String[] args) {
		File problems = new File("problems");
		File[] folders = problems.listFiles();
		
		for (File f : folders) {
			if (f.isDirectory() == false)
				continue;
			String[] fileNames = f.list();
			for (String s : fileNames)
				System.out.println(s);
		}
	}
}
