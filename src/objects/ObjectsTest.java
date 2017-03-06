package objects;

public class ObjectsTest {

	public static void main(String[] args) {
		TruthAssignment t1, t2;
		
		int[] vars = {1,2,3,4,5,6,7,8,9,10};
		
		t1 = new TruthAssignment(vars, true);
		t2 = new TruthAssignment(vars, true);
		
		System.out.println("Parent Assignment 1: " + t1);
		System.out.println("Parent Assignment 2: " + t2);
		
		TruthAssignment[] offspring = TruthAssignment.crossover(t1, t2);
		
		System.out.println("Child Assignment 1: " + offspring[0]);
		System.out.println("Child Assignment 2: " + offspring[1]);
	}
}
