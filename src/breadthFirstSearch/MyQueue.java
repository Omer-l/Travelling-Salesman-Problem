package breadthFirstSearch;

public class MyQueue {

	private int size = 1;
	private int backOfQueueIndex = 0;
	private int frontOfQueueIndex = 0;
	private int[][] arrayOfPaths = new int[size][];

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

		arrayOfPaths[backOfQueueIndex] = newPath;
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
}
