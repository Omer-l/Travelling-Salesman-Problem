package dijkstra;

import main.MyArrays;
import main.DataPoint;
/**
 *
 */

public class Dijkstra {
	private final DataPoint[] vertices; // data points of each city
	private double minimumDistance = Double.MAX_VALUE;
	private int[] minimumPath = new int[1];

	public Dijkstra(DataPoint[] vertices) {
		this.vertices = vertices;
	}

	/**
	 * This function will evaluate which points are visited and mark them as true.
	 * @param currentPath 	points to mark as visited
	 * @return				a boolean array with points that are already visited.
	 */
	public boolean[] getVisitedPoints(int[] currentPath) {
		boolean[] visitedPoints = new boolean[vertices.length];

		for(int pointIndex : currentPath)
			visitedPoints[pointIndex] = true;

		return visitedPoints;
	}

	/**
	 * This function will extend the currentPath by 1 and add a new vertex's index
	 * @param currentPath	current path
	 * @param pointIndex	vertex's index to add to path
	 * @return				a new path with the new index added
	 */
	private int[] addIndexToCurrentPath(int[] currentPath, int pointIndex) {
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
	public void addPathsFromPoint(MyPriorityQueue paths, int[] currentPath) {
		boolean[] visited = getVisitedPoints(currentPath);

		for(int vertexIndexIterator = 0; vertexIndexIterator < visited.length; vertexIndexIterator++) {
			if(!visited[vertexIndexIterator]) { //then add unvisited point's index reference to currentPath
				int[] newPath = addIndexToCurrentPath(currentPath, vertexIndexIterator);
				paths.enqueue(newPath);
				System.out.println(MyArrays.toString(newPath));
			}
		}
	}

	public void runDijkstras(int startingPath) {
		MyPriorityQueue paths = new MyPriorityQueue();

		int[] initialPath = {startingPath};
		paths.enqueue(initialPath);
		int[] nextPath = paths.dequeue();
		while(paths.notEmpty()) {
			nextPath = paths.dequeue();
			if(nextPath != null) {
				if (nextPath.length == vertices.length) {
					System.out.println("HERE ARE THE PATHS: \n" + MyArrays.toString(nextPath));

					while (paths.notEmpty()) {
						int[] currentCompletePath = paths.dequeue();
						double currentCompletePathDistance = calculatePathDistance(currentCompletePath, vertices);

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
	private boolean finishedSearching(int[] nextPath) {
		return nextPath.length >= vertices.length;
	}

	/**
	 * This function calculates the distance between vertices
	 * @param path  	indexes of the path
	 * @param vertices  actual points that 'path' will refer to
	 * @return 			distance between given indexes.
	 *
	 */
	public static double calculatePathDistance(int[] path, DataPoint[] vertices) {
		double totalDistance = 0;
		if(vertices != null) {
			boolean cycle = path.length == vertices.length; //is it a cycle? then connect end DataPoint with starting DataPoint.

			for (int vertexIndexIterator = 1; vertexIndexIterator < path.length; vertexIndexIterator++) {
				DataPoint point1 = vertices[path[vertexIndexIterator - 1]];
				DataPoint point2 = vertices[path[vertexIndexIterator]];

				totalDistance += point1.getDistanceTo(point2);
			}

			if (cycle) { // ensures a cycle is made in the graph
				// Get endVertex to startVertex to create a cycle
				DataPoint endVertex = vertices[path[path.length - 1]];
				DataPoint startVertex = vertices[path[0]];

				totalDistance += endVertex.getDistanceTo(startVertex);
			}
		}

		return totalDistance;
	}

	@Override
	public String toString() {
		return "Dijkstra - Best Path:" + MyArrays.toString(minimumPath) + " " + (minimumPath[0]+1) + " - distance: " + minimumDistance;
	}
}
