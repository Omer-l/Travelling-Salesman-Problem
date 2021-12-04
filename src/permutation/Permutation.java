package permutation;

import main.DataPoint; //for the city coordinates.
import main.MyArrays;

public class Permutation {
	private int[][] permutations;
	private static int PERMUTATION_ITERATOR = 0;
    private final DataPoint[] dataPoints;
	private double shortestDistance = Double.MAX_VALUE;
	private int[] shortestDistancePath;

    public Permutation(DataPoint[] dataPoints) {
        this.dataPoints = dataPoints;
    }

	//runs the algorithm, getting all possible permutations, iterating through them and choosing the shortest path.
    public void runPermutation() {
        final int numberOfPoints =  dataPoints.length;
        final int numberOfPermutations = getNumberOfPermutations(numberOfPoints);

        int[] indexesOfPoints = new int[numberOfPoints]; //indexes to represent the path to traverse the data points.
        
        for(int indexOfPointsIterator = 0; indexOfPointsIterator < numberOfPoints; indexOfPointsIterator++) //fill indexesOfPoints 0 to number of cities
        	indexesOfPoints[indexOfPointsIterator] = indexOfPointsIterator;
        
        permutations = new int[numberOfPermutations][numberOfPoints]; //each row represents a path.
        
        permute(indexesOfPoints, 0, indexesOfPoints.length - 1); //get all possible permutations
        
        initialiseShortestPath(permutations); //iterate through permutations, calculate distance of paths, set shortest.
	}
		
	/**
	 * Start with 1st point number (first point is point 1). function assumes that the tour will
	 * return to the first point after the last point visited.
	 * @param permutations		all the possible routes.
	 */
	private void initialiseShortestPath(int[][] permutations) {
		int numberOfPoints = permutations[0].length;
        
        for(int[] path : permutations) { //iterates through all permutations and calculates the distance and compares to current shortest distance path
        	
        	double totalDistance = 0;
        	
        	for(int j = 1; j < numberOfPoints; j++) {
        		DataPoint dataPoint1Index = dataPoints[path[j-1]];
        		DataPoint dataPoint2Index = dataPoints[path[j]];
        		
        		totalDistance += dataPoint1Index.getDistanceTo(dataPoint2Index);
        	}
        	
        	//Get endPoint to startPoint to create a cycle
        	DataPoint endDataPoint = dataPoints[path[numberOfPoints - 1]];
        	DataPoint startDataPoint = dataPoints[path[0]];
        	
        	totalDistance += endDataPoint.getDistanceTo(startDataPoint);
        	
        	if(shortestDistance > totalDistance) {
        		shortestDistance = totalDistance;
        		shortestDistancePath = path;
        	}
        }
	}
	
    private void permute(int[] pointsIndexes, int l, int r) {
        if (l == r) {
        	int[] tempArray = pointsIndexes.clone(); //Cloned to avoid pointer and reference bugs.
            permutations[PERMUTATION_ITERATOR] = tempArray;
            PERMUTATION_ITERATOR++;
        }
            else {
            for (int i = l; i <= r; i++)
            {
                pointsIndexes = swap(pointsIndexes,l,i);
                permute(pointsIndexes, l+1, r);
                pointsIndexes = swap(pointsIndexes,l,i);
            }
        }
    }
 
    /**
    * Swap Characters at position
    * @param 	a array of integers
    * @param 	i position 1
    * @param 	j position 2
    * @return	 swapped string
    */
    public int[] swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        return a;
    }
	
	/**
	 * Recursive factorial function
	 * @param l		length of array 
	 * @return      total number of permutations the path can possibly be.
	 */
	public int getNumberOfPermutations(int l) {
		if(l == 1)
			return 1;
		else
			return l * getNumberOfPermutations(l - 1);
	}

	//outputs results
	@Override
	public String toString() {
		return "Permutation - Best Path:" + MyArrays.toString(shortestDistancePath) + " " + (shortestDistancePath[0]+1) + " - Distance: " + shortestDistance;
	}
}
