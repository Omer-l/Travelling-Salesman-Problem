package dijkstra;

import main.DataPoint;//used for indexes in the a path.
import main.MyArrays; //printing arrays

/**
 * This class is a priority queue with functions for adding, removing paths to the queue.
 * Paths with a shorter distance have a higher priority.
 */
public class MyPriorityQueue {

	private int size = 1;
	private int backOfQueueIndex = 0;
	private int frontOfQueueIndex = 0;
	private int[][] arrayOfPaths = new int[size][];
	private DataPoint[] vertices; //vertices to refer to with the new paths.

	public MyPriorityQueue(DataPoint[] vertices) {
		this.vertices = vertices;
	}

	public MyPriorityQueue() {
	}

	/**
	 * In the event that the number of elements have filled the queue, this function doubles the size.
	 */
	public void resize() {
		size *= 2; // doubles size, allowing for more data.

		// create and initialise a new array, copy contents of current array.
		int[][] newArrayOfPaths = new int[size][];
		System.arraycopy(arrayOfPaths, 0, newArrayOfPaths, 0, arrayOfPaths.length);

		arrayOfPaths = newArrayOfPaths;
	}

	//Gets the element at the front of the queue
	public int[] peek() {
		return arrayOfPaths[frontOfQueueIndex];
	}

	//returns true if the queue has no elements.
	public boolean notEmpty() {
		return backOfQueueIndex != 0;
	}

	//gets the size of the queue
	public int getSize() {
		return backOfQueueIndex;
	}

	/**
	 * Iterates through the queue. Shorter distance is priority, and thus, the path will be inserted accordingly.
	 * @param newPath	a new path to add to the queue
	 */
	public void enqueue(int[] newPath) {

		if (backOfQueueIndex == size)
			resize();

		double newPathDistance = Dijkstra.calculatePathDistance(newPath, vertices);

		for(int vertexIndexIterator = 0; vertexIndexIterator < arrayOfPaths.length; vertexIndexIterator++) {
			if(arrayOfPaths[vertexIndexIterator] == null) {
				arrayOfPaths[backOfQueueIndex] = newPath;
				break;
			}
			int[] currentPath = arrayOfPaths[vertexIndexIterator];
			double currentPathDistance = Dijkstra.calculatePathDistance(currentPath, vertices);

			if(newPathDistance < currentPathDistance) {
				int[][] newArray = new int[arrayOfPaths.length + 1][];
				newArray[vertexIndexIterator] = newPath;

				System.arraycopy(arrayOfPaths, vertexIndexIterator, newArray, vertexIndexIterator+1, arrayOfPaths.length - vertexIndexIterator);

				arrayOfPaths = newArray;
				break;
			}
		}
		backOfQueueIndex++;
	}

	/**
	 * Removes the path at the front of the queue.
	 * @return	the path removed from the front of the queue.
	 */
	public int[] dequeue() {
		if(frontOfQueueIndex == backOfQueueIndex) { //reset
			size = 1;
			arrayOfPaths = new int[size][];
			frontOfQueueIndex = 0;
			backOfQueueIndex = 0;
			int[] emptyPath = {};
			return emptyPath;
		} else {
			return arrayOfPaths[frontOfQueueIndex++];
		}
	}

	@Override
	public String toString() {
		return MyArrays.toString(arrayOfPaths);
	}
}
