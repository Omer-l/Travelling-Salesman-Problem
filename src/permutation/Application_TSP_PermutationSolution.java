package permutation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Application_TSP_PermutationSolution {
	public static int[][] permutations;
	public static int permutationCounter = 0;

	public static void main(String[] args) {
        Point[] points = getData();
        
//        printArray(points); //TESTING PURPOSES
        final int numberOfPoints = points.length;
        final int numberOfPermutations = getNumberOfPermutations(numberOfPoints);
        

        int[] indexes = new int[numberOfPoints];
        
        for(int i = 0; i < numberOfPoints; i++)
        	indexes[i] = i;
        
        permutations = new int[numberOfPermutations][numberOfPoints];
        
        permute(indexes, 0, indexes.length - 1);
        
        double shortestPathDistance = getShortestPath(points, permutations);
	}
		
	/**
	 * Start with 1st point number (first point is point 1). function assumes that the tour will
	 * return to the first point after the last.
	 * point listed.
	 * @param points			points to travel to
	 * @param permutations		all the possible routes.
	 */
	private static double getShortestPath(Point[] points, int[][] permutations) {
		int numberOfPoints = permutations[0].length;
		
		double shortestDistance = Double.MAX_VALUE;
        int[] shortestDistanceIndex = permutations[0];
        
        for(int i = 0; i < permutations.length; i++) {
        	//get permuted end point to start point
        	int[] permutedIndexes = permutations[i];
        	
        	double totalDistance = 0;
        	
        	for(int j = 1; j < numberOfPoints; j++) {
        		Point point1Index = points[permutedIndexes[j-1]];
        		Point point2Index = points[permutedIndexes[j]];
        		
        		totalDistance += point1Index.getDistanceTo(point2Index);
        	}
        	
        	//Get endPoint to startPoint to create a cycle
        	Point endPoint = points[permutedIndexes[numberOfPoints - 1]];
        	Point startPoint = points[permutedIndexes[0]];
        	
        	totalDistance += endPoint.getDistanceTo(startPoint);
        	
        	if(shortestDistance > totalDistance) {
        		shortestDistance = totalDistance;
        		shortestDistanceIndex = permutations[i];
        	}
//        	System.out.print("ROUTE: ");
//        	printArray(permutedIndexes);
//        	System.out.println("TOTAL DISTANCE " + totalDistance);
        }
        
        System.out.println("SHORTEST DISTANCE " + Arrays.toString(shortestDistanceIndex) + ": " + shortestDistance);
        
        return shortestDistance;
	}
	
    private static void permute(int[] pointsIndexes, int l, int r) {	
        if (l == r) {
        	int[] tempArray = pointsIndexes.clone(); //Cloned to avoid pointer and reference bugs.
            permutations[permutationCounter] = tempArray;
            permutationCounter++;
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
    public static int[] swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        return a;
    }
	
	/**
	 * factorial recursive function
	 * @param l		length of array 
	 * @return
	 */
	public static int getNumberOfPermutations(int l) {
		if(l == 1)
			return 1;
		else
			return l * getNumberOfPermutations(l - 1);
	}
    
    public static void printArray(Point[] arr) {
    	for(Point p : arr) 
    		System.out.println(p.toString());
    }
    
    public static void printArray(int[] arr) {
    	System.out.println(Arrays.toString(arr));
    }
    
    public static void printArray(int[][] arr) {
    	for(int[] a : arr)
    	System.out.println(Arrays.toString(a));
    }
    
    public static void printArray(String[] arr) {
    	System.out.println(Arrays.toString(arr));
    }
//TRAIN PROBLEM 1 SHORTEST DISTANCE [0, 2, 1, 3]: 24.293023070189598
//TRAIN PROBLEM 2 SHORTEST DISTANCE [0, 6, 7, 4, 5, 3, 1, 2]: 65.65395780324545
//TRAIN PROBLEM 3 SHORTEST DISTANCE [0, 1, 8, 5, 7, 2, 3, 4, 6]: 229.50916652583456
    public static Point[] getData() {
    	
    	String pathName = "/Users/omerkacar/eclipse-workspace/Coursework1_CST3170/Resources/trainProblem3/"; //path for data file.
    	
    	File file = new File(pathName); //data file to be scanned
    	Point[] points = new Point[9]; //An array of Points
    	
    	
        
        int pointNumber = 0; //current index in array of Points

        try {
            Scanner input = new Scanner(file); //Scanner for reading the data file.
            while (input.hasNext()) {
                String[] bits = input.nextLine().split(" ");
                int a = Integer.parseInt(bits[2].trim()); //adds point x
                int b = Integer.parseInt(bits[3].trim()); //add point y
                points[pointNumber] = new Point(a, b); //creates an instance of the class Point wit a and b.
                pointNumber++;
            }
            input.close(); //close scanners
            return points;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
