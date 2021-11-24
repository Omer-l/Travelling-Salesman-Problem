package breadthFirstSearch;

import static org.junit.Assert.*;

import org.junit.Test;

public class AllTests {

	@Test
	public void testBreadthFirstSearch() {
		Application app = new Application();
		app.breadthFirstSearch(0);
	}
	
	@Test
	public void testaddPathsFromPoint() {
		Application app = new Application();
		MyQueue paths = new MyQueue();
		
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

		MyQueue queueOfPaths = new MyQueue();
		int[] newPath = { 1, 2 };
		int[] newPath2 = { 1, 2, 3 };
		int[] newPath3 = { 1, 2, 3, 4 };

		queueOfPaths.enqueue(newPath);
		queueOfPaths.enqueue(newPath2);
		queueOfPaths.enqueue(newPath3);

		int[] expecteds = { 1, 2, 3, 4 };
		int[] actuals = queueOfPaths.peek();

		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testDequeue() {
		MyQueue queueOfPaths = new MyQueue();
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
		MyQueue queueOfPaths = new MyQueue();
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
