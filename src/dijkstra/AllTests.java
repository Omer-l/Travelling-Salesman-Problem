package dijkstra;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class AllTests {

	@Test
	public void testDijkstras() {
		Application app = new Application();
//		app.dikstrasAlgorithm(0);
		app.dikstrasAlgorithm(new int[] {10, 26, 7, 22, 4, 6, 23, 9, 2, 17, 5, 30, 32, 11, 20, 16, 8, 24, 27, 21, 19, 15, 13, 12, 18, 31, 3, 1, 25, 29, 14, 28,});

	}
	
	@Test
	public void testaddPathsFromPoint() {
		Application app = new Application();
		MyPriorityQueue paths = new MyPriorityQueue();
		
		int[] initialPath = {0, 3};
		
		paths.enqueue(initialPath);
		
		app.addPathsFromPoint(paths, initialPath);
	}
	
	@Test
	public void testGetVisitedPoints() {
		Application app = new Application();
		app.getData();
		
		int[] path = {1, 2};
		
		boolean[] expecteds = {false, true, true, false};
		
		boolean[] actuals = app.getVisitedPoints(path);
		
		assertArrayEquals(expecteds, actuals);
	}
	
	// @Test
	// public void testAddPathsToQueueFromVertex() {
	// }

	/**
	 * Queue tests
	 */
	@Test
	public void testEnqueue() {
		Application app = new Application();
		app.getData();

		MyPriorityQueue queueOfPaths = new MyPriorityQueue();
		int[] newPath = {0, 1, 2};
		int[] newPath2 = { 0, 3, 2};
		int[] newPath3 = { 0, 1, 3};

		queueOfPaths.enqueue(newPath);
		queueOfPaths.enqueue(newPath2);
		queueOfPaths.enqueue(newPath3);

		int[] expecteds = { 1, 2, 3, 4 };
		int[] actuals = queueOfPaths.peek();
		System.out.println(queueOfPaths);
//		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testDequeue() {
		MyPriorityQueue queueOfPaths = new MyPriorityQueue();
		int[] newPath = { 1, 2 };
		int[] newPath2 = { 1, 2, 3 };
		int[] newPath3 = { 1, 2, 3, 4 };

		queueOfPaths.enqueue(newPath);
		queueOfPaths.enqueue(newPath2);
		queueOfPaths.enqueue(newPath3);

		int[] expecteds = { 1, 2 };
		int[] actuals = queueOfPaths.dequeue();

		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testDequeuePastEmpty() {
		MyPriorityQueue queueOfPaths = new MyPriorityQueue();
		int[] newPath = { 1, 2 };
		int[] newPath2 = { 1, 2, 3 };
		int[] newPath3 = { 1, 2, 3, 4 };

		queueOfPaths.enqueue(newPath);
		queueOfPaths.enqueue(newPath2);
		queueOfPaths.enqueue(newPath3);

		int[] expecteds = { 1, 2 };
		int[] actuals = queueOfPaths.dequeue();
		assertArrayEquals(expecteds, actuals);

		expecteds = new int[] { 1, 2, 3 };
		actuals = queueOfPaths.dequeue();
		assertArrayEquals(expecteds, actuals);
		
		expecteds = new int[] { 1, 2, 3, 4 };
		actuals = queueOfPaths.dequeue();
		assertArrayEquals(expecteds, actuals);
		
		expecteds = new int[] { };
		actuals = queueOfPaths.dequeue();
		assertArrayEquals(expecteds, actuals);
	}

}
