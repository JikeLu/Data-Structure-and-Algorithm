package part2;

import java.util.ArrayList;
import java.util.List;

public class BruteForce {
	private double[][] adjMatrix;
	private ArrayList<ArrayList<Integer>> set = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> result = new ArrayList<>();

	// Constructor to initialize BruteForce object with adjacency matrix
	public BruteForce(double[][] matrix){
		adjMatrix = matrix;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		permutationHelper(matrix.length, temp);
	}

	// Method to get the result
	public ArrayList<Integer> getResult() {
		return result;
	}

	// Method to generate all permutations of vertices
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

	// Method to find the minimum cycle information
	private CycleInfo findMinimumCycleInfo() {
		double minDistance = Double.MAX_VALUE;
		int minIndex = 0;

		for (int i = 0; i < set.size(); i++) {
			double distance = calculateCycleDistance(set.get(i));
			if (distance < minDistance) {
				minDistance = distance;
				minIndex = i;
			}
		}
		return new CycleInfo(minDistance, minIndex);
	}

	// Method to calculate the distance of a cycle
	private double calculateCycleDistance(List<Integer> cycle) {
		double distance = 0;
		for (int j = 1; j < cycle.size(); j++) {
			distance += adjMatrix[cycle.get(j - 1)][cycle.get(j)];
		}
		distance += adjMatrix[cycle.get(cycle.size() - 1)][cycle.get(0)];
		return distance;
	}

	// Method to generate cycle representation
	private String generateCycleRepresentation(CycleInfo cycleInfo) {
		StringBuilder cycleRepresentation = new StringBuilder();
		for (Integer vertex : set.get(cycleInfo.minIndex)) {
			cycleRepresentation.append(vertex).append(", ");
			result.add(vertex);
		}
		cycleRepresentation.append(set.get(cycleInfo.minIndex).get(0));
		result.add(0);
		return "\nLooking at every permutation to find the optimal solution: " + cycleRepresentation.toString() + "\n";
	}

	// Method to compute cycle length information
	private String computeCycleLengthInfo(double minDistance) {
		return "Length of Cycle :" + minDistance / 5280 + " miles";
	}

	// Method to format results
	private String formatResults(String cycleRepresentation, String cycleLengthInfo) {
		return cycleRepresentation + cycleLengthInfo;
	}

	// Method to get the result using brute-force algorithm
	public String bfResult() {
		CycleInfo cycleInfo = findMinimumCycleInfo();
		String cycleRepresentation = generateCycleRepresentation(cycleInfo);
		String cycleLengthInfo = computeCycleLengthInfo(cycleInfo.minDistance);
		return formatResults(cycleRepresentation, cycleLengthInfo);
	}

	// Inner class to store cycle information
	private static class CycleInfo {
		double minDistance;
		int minIndex;

		CycleInfo(double minDistance, int minIndex) {
			this.minDistance = minDistance;
			this.minIndex = minIndex;
		}
	}
}
