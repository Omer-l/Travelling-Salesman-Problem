package dijkstra;

/**
 * Procedure dijkstra (G, l, s)
 * input: 	Graph G (V, E), directed or undirected;
 * 			positive edge lengths {l_e : e € E}; vertex s € V
 * output:	For all vertices u reachable from s, dist(u) is 
 * 			set to the distance from s to u
 * 
 * 	for all u € V:
 * 		dist(u) = inf
 * 		prev(u) = 0
 * 	dist(s) = 0
 * 
 * 	H = makequeue(V) (using dist-values as keys)
 * 	while H is not empty:
 * 	u = deletemin(H)
 * 	for all edges (u, v) € E:
 * 		if (dist(v) > dist(u) + l(u, v)) then
 * 			dist(v) = dist(u) + l(u, v)
 * 			dist(v) = u
 * 			decreaseKey(H, v)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Application {
	private static File file = new File(System.getProperty("user.dir") + "/Resources/test1_2020/"); // path for data
	protected static DataPoint[] vertices; // to be scanned
	private static double minimumDistance = Double.MAX_VALUE;
	private static int[] minimumPath = new int[1];

	public static void main(String[] args) {
		getData();
		System.out.println(arrayToString(vertices));
		dikstrasAlgorithm(0);
		System.out.println("MINIMUM PATH: " + arrayToString(minimumPath) + " DISTANCE: " + minimumDistance);
	}

	/**
	 * gets all the Vertexes from a text file.
	 *
	 * @return
	 */
	public static void getData() {

		vertices = new DataPoint[getNumberOfTrainingSets()]; // An array of Vertexs

		int pointNumber = 0; // current index in array of Vertexs

		try {
			Scanner input = new Scanner(file); // Scanner for reading the data file.
			while (input.hasNext()) {
				String[] bits = input.nextLine().split("\\s+");
				int a = Integer.parseInt(bits[2].trim()); // adds point x
				int b = Integer.parseInt(bits[3].trim()); // add point y
				vertices[pointNumber] = new DataPoint(a, b); // creates an instance of the class DataPoint wit a and b.
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
	public static void addPathsFromPoint(MyPriorityQueue paths, int[] currentPath) {
		boolean[] visited = getVisitedPoints(currentPath);

		for(int i = 0; i < visited.length; i++) {
			if(!visited[i]) { //then add unvisited point's index reference to currentPath
				int[] newPath = addIndextoCurrentPath(currentPath, i);
				paths.enqueue(newPath);
				System.out.println(arrayToString(newPath));
			}
		}
	}

	public static void dikstrasAlgorithm(int startingIndex) {
		MyPriorityQueue paths = new MyPriorityQueue();

		int[] initialPath = {startingIndex};
		paths.enqueue(initialPath);
		int[] nextPath = paths.dequeue();
		while(!paths.empty()) {
			nextPath = paths.dequeue();
			if(nextPath != null) {
				if (nextPath.length == vertices.length) {
					System.out.println("HERE ARE THE PATHS: \n" + arrayToString(nextPath));

					while (!paths.empty()) {
						int[] currentCompletePath = paths.dequeue();
						double currentCompletePathDistance = calculatePathDistance(currentCompletePath);

						if (currentCompletePathDistance < minimumDistance && finishedSearching(currentCompletePath)) {
							minimumDistance = currentCompletePathDistance;
							minimumPath = currentCompletePath;
							System.out.println("NEW MIN: " + minimumDistance);
						}
					}
					break;
				}

				addPathsFromPoint(paths, nextPath);
			}
		}
	}

	/**
	 * evaluates the front of the dequeued element, and sees if it is the length
	 * of a complete path.
	 * @param nextPath		dequeued path (which is an array of integers, for which, each integer is an index of a vertex)
	 * @return				true if nextPath is a complete path with the same number of elements as there are vertices.
	 */
	private static boolean finishedSearching(int[] nextPath) {
		return nextPath.length >= vertices.length;
	}

	/**
	 * This function calculates the distance between vertices
	 *
	 * @param path  indexes of the path
	 * @return distance between given indexes.
	 *
	 */
	public static double calculatePathDistance(int[] path) {

		double totalDistance = 0;
		boolean cycle = path.length == vertices.length; //is it a cycle? then connect end DataPoint with starting DataPoint.

		for (int j = 1; j < path.length; j++) {
			DataPoint point1 = vertices[path[j - 1]];
			DataPoint point2 = vertices[path[j]];

			totalDistance += point1.getDistanceTo(point2);
		}

		if (cycle) { // ensures a cycle is made in the graph
			// Get endVertex to startDataPoint to create a cycle
			DataPoint endVertex = vertices[path[path.length - 1]];
			DataPoint startDataPoint = vertices[path[0]];

			totalDistance += endVertex.getDistanceTo(startDataPoint);
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
			if(a != null) {
				s += "" + arrayToString(a) + "DISTANCE: " + calculatePathDistance(a) +", ";
			}

		return s;
	}

	public static <E> String arrayToString(E[] arr) {
		String s = "";
		for (E e : arr)
			s += e + " -> ";

		return s;
	}
}
