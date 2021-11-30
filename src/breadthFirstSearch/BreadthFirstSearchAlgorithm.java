package breadthFirstSearch;

import main.DataPoint; //data of each vertex.
import main.MyArrays; //printing arrays

public class BreadthFirstSearchAlgorithm {
	private DataPoint[] vertices; // to be scanned
	private double minimumDistance = Double.MAX_VALUE;
	private int[] minimumPath = new int[1]; // temporarily create an instance of this array.

	public BreadthFirstSearchAlgorithm(DataPoint[] vertices) {
		this.vertices = vertices;
	}

	/**
	 * This function will evaluate which points are visited and mark them as true.
	 * @param currentPath 	points to mark as visited
	 * @return				a boolean array with points that are already visited.
	 */
	public boolean[] getVisitedPoints(int[] currentPath) {
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
	private int[] addIndextoCurrentPath(int[] currentPath, int pointIndex) {
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
	public void addPathsFromPoint(MyQueue paths, int[] currentPath) {
		boolean[] visited = getVisitedPoints(currentPath);

		for(int i = 0; i < visited.length; i++) {
			if(!visited[i]) { //then add unvisited point's index reference to currentPath
				int[] newPath = addIndextoCurrentPath(currentPath, i);
				paths.enqueue(newPath);
				System.out.println(MyArrays.toString(newPath));
			}
		}
	}

	/**
	 * This is the function that runs the breadth first search algorithm
	 * @param startingIndex		index to start algorithm from.
	 */
	public void breadthFirstSearch(int startingIndex) {
		MyQueue paths = new MyQueue();

		int[] initialPath = {startingIndex};
		paths.enqueue(initialPath);
		int[] nextPath = paths.dequeue();
		while(!paths.empty()) {
			nextPath = paths.dequeue();
			if(nextPath.length == vertices.length) {
				System.out.println("HERE ARE THE PATHS: \n" + MyArrays.toString(nextPath));

				while(!paths.empty()) {
					int[] currentCompletePath = paths.dequeue();
					double currentCompletePathDistance = calculatePathDistance(currentCompletePath);

					if(currentCompletePathDistance < minimumDistance && finishedSearching(currentCompletePath)) {
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

	/**
	 * evaluates the front of the dequeued element, and sees if it is the length
	 * of a complete path.
	 * @param nextPath		dequeued path (which is an array of integers, for which, each integer is an index of a vertex)
	 * @return				true if nextPath is a complete path with the same number of elements as there are vertices.
	 */
	private boolean finishedSearching(int[] nextPath) {
		return nextPath.length >= vertices.length;
	}
//	System.out.println(arrayToString(paths.dequeue()));
	
	/**
	 * This function calculates the distance between vertices
	 * 
	 * @param path  indexes of the path
	 * @return distance between given indexes.
	 * 
	 */
	public double calculatePathDistance(int[] path) {

		double totalDistance = 0;
		boolean cycle = path.length == vertices.length; //is it a cycle? then connect end DataPoint with starting DataPoint.

		for (int j = 1; j < path.length; j++) {
			DataPoint point1 = vertices[path[j - 1]];
			DataPoint point2 = vertices[path[j]];

			totalDistance += point1.getDistanceTo(point2);
		}

		if (cycle) { // ensures a cycle is made in the graph
			// Get endVertex to startVertex to create a cycle
			DataPoint endDataPoint = vertices[path[path.length - 1]];
			DataPoint startDataPoint = vertices[path[0]];

			totalDistance += endDataPoint.getDistanceTo(startDataPoint);
		}

		return totalDistance;
	}

}
