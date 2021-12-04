package nnAproach;

import main.DataPoint; //a DataPoint is a city.
import main.MyArrays;

/**
 * Based on the nearest neighbour algorithm, this class offers a modified version of the travelling salesman problem.
 * The modification is to change the starting city for the nearest neighbour algorithm and choose which starting city
 * produces the shortest path.
 */
public class HeuristicNearestNeighbours {
	private final DataPoint[] neighbours;
	private double minimumDistance = Double.MAX_VALUE;
	private int[] minimumPath;


	public HeuristicNearestNeighbours(DataPoint[] neighbours) {
		this.neighbours = neighbours;
	}

	//Runs the algorithm and times it.
	public void runHeuristicNearestNeighbours() {
		minimumPath = new int[neighbours.length];

		for(int startingIndexIterator = 0; startingIndexIterator <  neighbours.length; startingIndexIterator++) {

			int[] path = getPath(neighbours, startingIndexIterator);

			double pathDistance = calculatePathDistance(path, neighbours);
			
			if(pathDistance < minimumDistance) {
				minimumDistance = pathDistance;
				minimumPath = path;
			}
		}
	}

	/**
	 * Gets the path by getting the closest neighbour every iteration
	 * @param neighbours		are referenced by index (i.e., currentIndex)
	 * @param currentIndex		starting city's index
	 * @return					the path after closest neighbour is added.
	 */
	public static int[] getPath(DataPoint[] neighbours, int currentIndex) {
		int[] path = new int[neighbours.length];
		boolean[] visited = new boolean[neighbours.length];

		int pathIterator = 0; //current iteration of path
		
		while (!allPointsVisited(visited)) {
			visited[currentIndex] = true; //this DataPoint is now visited.
			path[pathIterator++] = currentIndex; //add this index to path, and add 1 to the iterator.
			
			double currentMinimumDistance = Double.MAX_VALUE;
			int currentMinimumDistanceIndex = 0;
			
			//get nearest neighbour of currentIndex
			for(int neighbourIterator = 0; neighbourIterator < neighbours.length; neighbourIterator++) {

				//has this DataPoint been visited?
				if(visited[neighbourIterator])
					continue; //don't even look at it.
				
				double tmpDistance = neighbours[currentIndex].getDistanceTo(neighbours[neighbourIterator]);
				
				if(tmpDistance < currentMinimumDistance) {
					currentMinimumDistance = tmpDistance;
					currentMinimumDistanceIndex = neighbourIterator;
				}	
			}
			currentIndex = currentMinimumDistanceIndex; //set's the closest DataPoint as the next index to visit
		}
		return path;
	}

	//once all points are visited, this function will return true.
	private static boolean allPointsVisited(boolean[] visited) {
		for (boolean pointVisited : visited)
			if (!pointVisited)
				return false;

		return true;
	}

	//calculates the path's distance as a cycle.
	public static double calculatePathDistance(int[] path, DataPoint[] points) {
		
		double totalDistance = 0;
    	
    	for(int j = 1; j < points.length; j++) {
    		DataPoint point1 = points[path[j-1]];
    		DataPoint point2 = points[path[j]];
    		
    		totalDistance += point1.getDistanceTo(point2);
    	}
    	
    	//Get endPoint to startPoint to create a cycle
    	DataPoint endPoint = points[path[points.length - 1]];
    	DataPoint startPoint = points[path[0]];
    	
    	totalDistance += endPoint.getDistanceTo(startPoint);
    	
    	return totalDistance;
	}

	//Prints the results
	@Override
	public String toString() {
		return "Heuristic Nearest Neighbours - Best Path:" + MyArrays.toString(minimumPath) + " " + (minimumPath[0]+1) + " - Distance: " + minimumDistance;
	}
}