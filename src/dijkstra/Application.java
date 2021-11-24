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

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Application {
	private static File file = new File(System.getProperty("user.dir") + "/Resources/trainProblem1/"); // path for data
	protected static Vertex[] vertices; // to be referenced with int[] indexes

	public static void main(String[] args) {
		vertices = getData(); //create an array of vertices

		dikjstras();

	}

	/**
	 * gets all the Vertexes from a text file.
	 * 
	 * @return
	 */
	public static Vertex[] getData() {

		Vertex[] vertices = new Vertex[getNumberOfTrainingSets()]; // An array of Vertexs

		int pointNumber = 0; // current index in array of Vertexs

		try {
			Scanner input = new Scanner(file); // Scanner for reading the data file.
			while (input.hasNext()) {
				String[] bits = input.nextLine().split("\\s+");
				int a = Integer.parseInt(bits[2].trim()); // adds point x
				int b = Integer.parseInt(bits[3].trim()); // add point y
				vertices[pointNumber] = new Vertex(a, b); // creates an instance of the class Vertex wit a and b.
				pointNumber++;
			}
			input.close(); // close scanners
			return vertices;
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
				String tmp = input.nextLine();
				trainingSetsCounter++;
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

		return trainingSetsCounter;
	}

	/**
	 * This function calculates the distance between vertices
	 * 
	 * @param path  indexes of the path
	 * @param cycle connect endVertex to startVertex
	 * 
	 * @return distance between given indexes.
	 * 
	 */
	public static double calculatePathDistance(Integer[] path, boolean cycle) {

		double totalDistance = 0;

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

	/**
	 * fills all indexes except currentIndex with infinity
	 * 
	 * @param distanceToOtherVertexs array to fill with infinities and a 0 for the currentIndex
	 * @param currentIndex           index to fill as 0 units away
	 */
	public static void fillDistancesWithInfinity(double[] distanceToOtherVertexs, int currentIndex) {
		for (int i = 0; i < distanceToOtherVertexs.length; i++)
			distanceToOtherVertexs[i] = Double.MAX_VALUE;
		distanceToOtherVertexs[currentIndex] = 0; //fills currentIndex with distance = 0
	}

	
	/**
	 * This function gets the distance to all the other unvisited nodes
	 * 
	 * @param vertices     All vertices
	 * @param visited      currently visited vertices
	 * @param currentIndex current point's index
	 * @return an array of distances to other vertices
	 */
	public static double[] getDistanceToOtherVertexes(boolean[] visited, int currentIndex) {
		double[] distanceToOtherVertexs = new double[vertices.length];
		fillDistancesWithInfinity(distanceToOtherVertexs, currentIndex); // currentIndex set to 0.

		for (int i = 0; i < vertices.length; i++) {
			if (visited[i] == false) //only gets the distances to unvisited points.
				distanceToOtherVertexs[i] = vertices[currentIndex].getDistanceTo(vertices[i]);
		}

		return distanceToOtherVertexs;
	}

	/**
	 * This function adds the new paths to the queue
	 * 
	 * @param queueOfPaths a queue of paths/indexes of vertices
	 */
	public static void addPathsToQueue(PriorityQueue<Integer[]> queueOfPaths, Integer[][] allPaths) {

		for (Integer[] path : allPaths)
			if (!queueOfPaths.contains(path))
				queueOfPaths.add(path);

		while (!queueOfPaths.isEmpty()) {
			Integer[] nextShortestPath = queueOfPaths.poll();
			
			Vertex[] nextShortestPathToVerticesArray = new Vertex[nextShortestPath.length];
			
			for (int i = 0; i < nextShortestPath.length; i++)
				if (!queueOfPaths.isEmpty()) {
					nextShortestPathToVerticesArray[i] = vertices[nextShortestPath[i]];
					
					System.out.println(arrayToString(nextShortestPathToVerticesArray) + "DISTANCE: "
							+ calculatePathDistance(nextShortestPath, false));
				}
		}

	}

	/**
	 * This function evaluates the number of available paths from the currentIndex
	 * depending on the number of unvisited nodes...
	 * 
	 * @param visited currently visited vertices boolean array
	 * @return number of unvisited vertices AKA number of available paths from
	 *         currentIndex
	 */
	public static int getNumberOfUnvisited(boolean[] visited) {

		int counter = 0;
		for (boolean vertexVisited : visited)
			if (!vertexVisited)
				counter++;
		return counter;

	}

	public static void dikjstras() { // starts at index 0
		boolean[] visited = new boolean[vertices.length];

		int[] vertexIndex = new int[vertices.length];
		Integer[] initialPath = { 0 };

		PriorityQueue<Integer[]> priorityQueueOfPaths = new PriorityQueue<>(new PathComparator(vertices));
		priorityQueueOfPaths.add(initialPath);
		Integer[] shortestDistance = priorityQueueOfPaths.poll();
		int currentIndex = 0;
		visited[currentIndex] = true;

		Integer[][] allPaths = new Integer[getNumberOfUnvisited(visited)][vertices.length];
		int pathIterator = 0;
		double[] distanceToOtherVertices = getDistanceToOtherVertexes(visited, currentIndex);

		for (int i = 0; i < distanceToOtherVertices.length; i++) {
			Integer[] newPath = new Integer[initialPath.length + 1];
			if (distanceToOtherVertices[i] > 0) {
				System.arraycopy(initialPath, 0, newPath, 0, initialPath.length);
				newPath[newPath.length - 1] = i;
				allPaths[pathIterator] = newPath;
				pathIterator++;
			}
		}
		addPathsToQueue(priorityQueueOfPaths, allPaths);

		System.out.println(arrayToString(distanceToOtherVertices));

	}

	public static String arrayToString(int[] arr) {
		String s = "";
		for (int i : arr)
			s += " " + (i + 1);
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
