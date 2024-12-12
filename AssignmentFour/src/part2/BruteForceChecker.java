package part2;
import java.util.ArrayList;
/**
 * class to brute force check all permutations of possible paths
 */
public class BruteForceChecker {
	private double[][] adjMatrix;
	private ArrayList<ArrayList<Integer>> set = new ArrayList<ArrayList<Integer>>();
	
	public BruteForceChecker(double[][] matrix){
		adjMatrix = matrix;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		permutationHelper(matrix.length, temp);
		
	}
	
	/**
	 * permute the node number to get all possible paths
	 */
	public void permutationHelper(int n, ArrayList<Integer>temp){
		if(temp.size() == n){
			set.add(new ArrayList<Integer>(temp));
			return;
		}
		for(int i=0; i<n; i++){
			if(temp.contains(i)){
				continue;
			}
			temp.add(i);
			permutationHelper(n, temp);
			temp.remove(temp.size() - 1);
			
		}
	}
	
	/**
	 * check and get minimum path return a string as result
	 */
	public String check(){
		String ret = "";
		double min = Double.MAX_VALUE;
		int mark = 0;
		for(int i = 0; i < set.size(); i++){
			double distance = 0;
			for(int j=1; j<set.get(i).size(); j++){
				distance += adjMatrix[set.get(i).get(j-1)][set.get(i).get(j)];
			}
			distance += adjMatrix[set.get(i).get(set.get(i).size()-1)][set.get(i).get(0)];
			if(distance < min){
				min = distance;
				mark = i;
			}
		}
		
		for(Integer i:set.get(mark)){
			ret = ret + Integer.toString(i) + ", ";
		}
		
		ret = ret + set.get(mark).get(0);
		
		ret = "\nHamiltonan Cycle (not necessarily optimum): " + ret + "\n";

		ret = ret + "Length of Cycle :" + min / 5280 + " miles";
		return ret;
	}

}
