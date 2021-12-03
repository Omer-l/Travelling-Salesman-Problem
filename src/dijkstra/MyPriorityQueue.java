package dijkstra;

import main.DataPoint;//used for indexes in the a path.
import main.MyArrays; //printing arrays

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

	public void resize() {
		size *= 2; // doubles size, allowing for more data.

		// create and initialise a new array, copy contents of current array.
		int[][] newArrayOfPaths = new int[size][];
		System.arraycopy(arrayOfPaths, 0, newArrayOfPaths, 0, arrayOfPaths.length);

		arrayOfPaths = newArrayOfPaths;
	}

	public int[] peek() {
		return arrayOfPaths[backOfQueueIndex - 1];
	}

	public boolean empty() {
		return backOfQueueIndex == 0;
	}

	public int getSize() {
		return backOfQueueIndex;
	}

	public void enqueue(int[] newPath) {

		if (backOfQueueIndex == size)
			resize();

		double newPathDistance = Dijkstra.calculatePathDistance(newPath, vertices);

		for(int i = 0; i < arrayOfPaths.length; i++) {
			if(arrayOfPaths[i] == null) {
				arrayOfPaths[backOfQueueIndex] = newPath;
				break;
			}
			int[] currentPath = arrayOfPaths[i];
			double currentPathDistance = Dijkstra.calculatePathDistance(currentPath, vertices);

			if(newPathDistance < currentPathDistance) {
				int[][] newArray = new int[arrayOfPaths.length + 1][];
				newArray[i] = newPath;

				System.arraycopy(arrayOfPaths, i, newArray, i+1, arrayOfPaths.length - i);

				arrayOfPaths = newArray;
				break;
			}
		}
		backOfQueueIndex++;
	}

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
