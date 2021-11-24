package breadthFirstSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Application {
	private static File file = new File(System.getProperty("user.dir") + "/Resources/trainProblem2/"); // path for data
	protected static Vertex[] vertices; // to be scanned
	
	public static void main(String[] args) {
		getData();
		breadthFirstSearch(0);
	}
	
	/**
	 * gets all the Vertexes from a text file.
	 * 
	 * @return
	 */
	public static void getData() {

		vertices = new Vertex[getNumberOfTrainingSets()]; // An array of Vertexs

		int pointNumber = 0; // current index in array of Vertexs

		try {
			Scanner input = new Scanner(file); // Scanner for reading the data file.
			while (input.hasNext()) {
				String[] bits = input.nextLine().split("\\s+");
				int a = Integer.parseInt(bits[1].trim()); // adds point x
				int b = Integer.parseInt(bits[2].trim()); // add point y
				vertices[pointNumber] = new Vertex(a, b); // creates an instance of the class Vertex wit a and b.
				pointNumber++;
			}
			input.close(); // close scanners
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int getNumberOfTrainingSets() {
		int trainingSetsCounter = 0; // counter

		try {
			Scanner input = new Scanner(file);

			while (input.hasNext()) {
				String tmp = input.nextLine();
				trainingSetsCounter++;
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

		return trainingSetsCounter;
	}

	/**
	 * This function will evaluate which points are visited and mark them as true.
	 * @param currentPath 	points to mark as visited
	 * @return				a boolean array with points that are already visited.
	 */
	public static boolean[] getVisitedPoints(int[] currentPath) {
		boolean[] visitedPoints = new boolean[vertices.length];
		
		for(int i = 0; i < currentPath.length; i++)
			visitedPoints[currentPath[i]] = true;
		
		return visitedPoints;
	}
	
	/**
	 * This function will extend the currentPath by 1 and add a new vertex's index
	 * @param currentPath	current path
	 * @param pointIndex	vertex's index to add to path
	 * @return				a new path with the new index added
	 */
	private static int[] addIndextoCurrentPath(int[] currentPath, int pointIndex) {
		int[] pathWithAddedIndex = new int[currentPath.length + 1];
		System.arraycopy(currentPath, 0, pathWithAddedIndex, 0, currentPath.length);
		pathWithAddedIndex[pathWithAddedIndex.length - 1] = pointIndex;
		
		return pathWithAddedIndex;
	}
	
	/**
	 * This function adds paths to the `queue of paths` from a given path
	 * @param paths			queue of paths currently in the queue
	 * @param currentPath	path to add a new point to
	 */
	public static void addPathsFromPoint(MyQueue paths, int[] currentPath) {
		boolean[] visited = getVisitedPoints(currentPath);
		
		for(int i = 0; i < visited.length; i++) {
			if(!visited[i]) { //then add unvisited point's index reference to currentPath
				int[] newPath = addIndextoCurrentPath(currentPath, i);
				paths.enqueue(newPath);
				System.out.println(arrayToString(newPath));
			}
		}
	}

	public static void breadthFirstSearch(int startingIndex) {
		MyQueue paths = new MyQueue();
		
		int[] initialPath = {startingIndex};
		paths.enqueue(initialPath);
		int[] nextPath = paths.dequeue();
		while(!paths.empty()) {
			nextPath = paths.dequeue();
			if(nextPath.length == vertices.length) {
				System.out.println("HERE ARE THE PATHS: \n");
				double currentMinimumDistance = calculatePathDistance(nextPath);
				int[] currentMinimumPath = nextPath;
				
				while(!paths.empty()) {
					nextPath = paths.dequeue();
					double dequeuedPathDistance = calculatePathDistance(nextPath);
					if( dequeuedPathDistance < currentMinimumDistance) {
						currentMinimumDistance =  dequeuedPathDistance;
						currentMinimumPath = nextPath;
					}
				}
				System.out.println(arrayToString(currentMinimumPath) + " DISTANCE: " + currentMinimumDistance);
//				System.out.println(arrayToString(currentMinimumPath) + " DISTANCE: " + currentMinimumDistance);
				break;
			}
			System.out.println(arrayToString(nextPath));
			addPathsFromPoint(paths, nextPath);
		}
	}
//	System.out.println(arrayToString(paths.dequeue()));
	
	/**
	 * This function calculates the distance between vertices
	 * 
	 * @param path  indexes of the path
	 * @return distance between given indexes.
	 * 
	 */
	public static double calculatePathDistance(int[] path) {

		double totalDistance = 0;
		boolean cycle = path.length == vertices.length; //is it a cycle? then connect end Vertex with starting Vertex.
		
		for (int j = 1; j < path.length; j++) {
			Vertex point1 = vertices[path[j - 1]];
			Vertex point2 = vertices[path[j]];

			totalDistance += point1.getDistanceTo(point2);
		}

		if (cycle) { // ensures a cycle is made in the graph
			// Get endVertex to startVertex to create a cycle
			Vertex endVertex = vertices[path[path.length - 1]];
			Vertex startVertex = vertices[path[0]];

			totalDistance += endVertex.getDistanceTo(startVertex);
		}

		return totalDistance;
	}
	
	public static String arrayToString(int[] arr) {
		String s = "";
		for (int i : arr)
//			s += " " + (i + 1);
			s += " " + (i);
		return s;
	}

	public static String arrayToString(double[] arr) {
		String s = "";
		for (double i : arr)
			s += " " + i;
		return s + "\n";
	}

	public static String arrayToString(int[][] arr) {
		String s = "";

		for (int[] a : arr)
			if(a != null)
				s += "" + arrayToString(a);

		return s;
	}

	public static <E> String arrayToString(E[] arr) {
		String s = "";
		for (E e : arr)
			s += e + " -> ";

		return s;
	}
}
