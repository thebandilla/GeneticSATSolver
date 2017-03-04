package objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Formula {

	private String filename;
	private String directory;
	private int numVars;
	private int numClauses;
	
	private List<Clause> clauses;
	
	private TruthAssignment assignments;
	
	public Formula(String filename, String directory) {
		this.filename = filename;
		this.directory = directory;
		
		File cnfFile = new File("problems/" + directory + "/" + filename);
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(cnfFile));
			
			String currLine = null;
			
			while((currLine = br.readLine()) != null) {
				
				if (currLine.equals(""))
					continue;
				
				if (currLine.charAt(0) == 'c'
						|| currLine.charAt(0) == '0'
						|| currLine.charAt(0) == '%')
					continue;
												
				if (currLine.charAt(0) == 'p') {
					String[] args = currLine.split("\\s+");
					numVars = Integer.parseInt(args[2]);
					numClauses = Integer.parseInt(args[3]);
					
					int[] vars = new int[numVars];
					
					for (int i = 0; i < numVars; i++)
						vars[i] = i + 1;
					
					assignments = new TruthAssignment(vars, true);
					
					clauses = new ArrayList<Clause>(numClauses);
					
					continue;
				}
				currLine = currLine.trim();
				String[] clause = currLine.split(" ");
				
				int[] temp = {
						Integer.parseInt(clause[0]),
						Integer.parseInt(clause[1]),
						Integer.parseInt(clause[2])
						};
				
				clauses.add(new Clause(temp));
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean evaluate() {		
		for (Clause c : clauses) {
			int[] vars = c.getVars();
			boolean[] assigns = new boolean[vars.length];
			for (int i = 0; i < assigns.length; i++) {
				assigns[i] = assignments.getAssignment(vars[i]);
			}
			
			if (!c.evaluate(assigns))
				return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		String result = clauses.get(0).toString();
		for (int i = 1; i < clauses.size(); i++)
			result += " and " + clauses.get(i);
		
		return result;
	}
}
