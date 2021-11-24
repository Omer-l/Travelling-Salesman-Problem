package NN_Approach;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Application {
	private static File file = new File(System.getProperty("user.dir") + "/Resources/test4_2020/"); // path for data file.) data file to be scanned
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		Point[] points = getData();

		printArray(points);

		double shortestDistance = Double.MAX_VALUE;
		int[] shortestPath = new int[points.length];
		for(int i = 0; i <  points.length; i++) {
			int[] path = getPath(points, i);
			printArray(path);
			double pathDistance = calculatePathDistance(path, points);
			System.out.println(pathDistance);
			
			if(pathDistance < shortestDistance) {
				shortestDistance = pathDistance;
				shortestPath = path;
			}
		}
		
		System.out.println("The shortest path is the following cycle: " + printArray(shortestPath) + ". Distance: " + shortestDistance);
		
		long endTime = System.currentTimeMillis();

		long duration = (endTime - startTime);
		System.out.println("THIS ALGORITHM TOOK: " + duration + " MILLISECONDS.");
		
	}

	/**
	 * 
	 * @param points			points to be visiting
	 * @param currentIndex		starting index
	 * @return					the path using the Nearest Neighbour algorithm.
	 */
	public static int[] getPath(Point[] points, int currentIndex) {
		int[] path = new int[points.length];
		boolean[] visited = new boolean[points.length];

		int pathIterator = 0; //current iteration of path
		
		while (!allPointsVisited(visited)) {
			visited[currentIndex] = true; //this Point is now visited.
			path[pathIterator++] = currentIndex; //add this index to path, and add 1 to the iterator.
			
			double currentMinimumDistance = Double.MAX_VALUE;
			int currentMinimumDistanceIndex = 0;
			
			//get nearest neighbour of currentIndex
			for(int i = 0; i < points.length; i++) {
				
				//has this Point been visited?
				if(visited[i] == true)
					continue; //don't even look at it.
				
				double tmpDistance = points[currentIndex].getDistanceTo(points[i]);
				
				if(tmpDistance < currentMinimumDistance) {
					currentMinimumDistance = tmpDistance;
					currentMinimumDistanceIndex = i;
				}	
			}
			
			currentIndex = currentMinimumDistanceIndex; //set's the closest point as the next index to visit
		}

		return path;

	}

	private static boolean allPointsVisited(boolean[] visited) {
		for (boolean pointVisited : visited)
			if (pointVisited == false)
				return false;

		return true;
	}

	public static double calculatePathDistance(int[] path, Point[] points) {
		
		double totalDistance = 0;
    	
    	for(int j = 1; j < points.length; j++) {
    		Point point1 = points[path[j-1]];
    		Point point2 = points[path[j]];
    		
    		totalDistance += point1.getDistanceTo(point2);
    	}
    	
    	//Get endPoint to startPoint to create a cycle
    	Point endPoint = points[path[points.length - 1]];
    	Point startPoint = points[path[0]];
    	
    	totalDistance += endPoint.getDistanceTo(startPoint);
    	
    	return totalDistance;
	}
	
	/**
	 * gets all the Points from a text file.
	 * @return
	 */
	public static Point[] getData() {

		Point[] points = new Point[getNumberOfTrainingSets()]; // An array of Points

		int pointNumber = 0; // current index in array of Points

		try {
			Scanner input = new Scanner(file); // Scanner for reading the data file.
			while (input.hasNext()) {
				String[] bits = input.nextLine().split("\\s+");
				int a = Integer.parseInt(bits[1].trim()); // adds point x
				int b = Integer.parseInt(bits[2].trim()); // add point y
				points[pointNumber] = new Point(a, b); // creates an instance of the class Point wit a and b.
				pointNumber++;
			}
			input.close(); // close scanners
			return points;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int getNumberOfTrainingSets() {
		int trainingSetsCounter = 0; // counter

		try {
			Scanner input = new Scanner(file);

			while (input.hasNext()) {
				String nextLine = input.nextLine();
				trainingSetsCounter++;
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

		return trainingSetsCounter;
	}

	public static void printArray(Point[] arr) {
    	for(Point p : arr) 
    		System.out.println(p.toString());
    }
    
    public static String printArray(int[] arr) {
    	String s = "";
    	for(int i : arr)
    		s += " " + (i+1);
    	return s;
    }
    
    public static void printArray(int[][] arr) {
    	for(int[] a : arr)
    	System.out.println(Arrays.toString(a));
    }
    
    public static void printArray(String[] arr) {
    	System.out.println(Arrays.toString(arr));
    }

}