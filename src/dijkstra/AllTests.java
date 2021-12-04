package dijkstra;

import main.DataPoint;
import main.MyFileReader;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

//Tests Dijkstra's and the priority queue.
public class AllTests {
	MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir") + "/Resources/trainProblem1.txt");
	Dijkstra dijkstra = new Dijkstra(fileReader.getData());
	DataPoint[] points = fileReader.getData();

	@Test
	public void testInitialiseNextAndPrevious() {
		int[] previousElement = {1, 2, 3, 4};
		int[] nodeElement = {1, 2, 3};
		int[] nextElement = {1, 2};

		QueueNode previousNode = new QueueNode(previousElement);
		QueueNode nextNode = new QueueNode(nextElement);
		QueueNode queueNode = new QueueNode(nodeElement, previousNode, nextNode);

		assertArrayEquals(previousElement, queueNode.getPreviousNode().getPath());
		assertArrayEquals(nodeElement, queueNode.getPath());
		assertArrayEquals(nextElement, queueNode.getNextNode().getPath());
	}

	@Test
	public void runDijkstra() {
		dijkstra.runDijkstras(0);
	}
//	@Test
//	public void testDijkstras() {
//		dijkstra.runDijkstras(0);
//	}
//
//	@Test
//	public void testaddPathsFromPoint() {
//		MyPriorityQueue paths = new MyPriorityQueue();
//
//		int[] initialPath = {0, 3};
//
//		paths.enqueue(initialPath);
//
//		dijkstra.addPathsFromPoint(paths, initialPath);
//	}
//
//	@Test
//	public void testGetVisitedPoints() {
//
//		int[] path = {1, 2};
//
//		boolean[] expecteds = {false, true, true, false};
//
//		boolean[] actuals = dijkstra.getVisitedPoints(path);
//
//		assertArrayEquals(expecteds, actuals);
//	}
//
//	// @Test
//	// public void testAddPathsToQueueFromVertex() {
//	// }
//
//	/**
//	 * Queue tests
//	 */
//	@Test
//	public void testEnqueue() {
//
//		MyPriorityQueue queueOfPaths = new MyPriorityQueue();
//		int[] newPath = {0, 1, 2};
//		int[] newPath2 = { 0, 3, 2};
//		int[] newPath3 = { 0, 1, 3};
//
//		queueOfPaths.enqueue(newPath);
//		queueOfPaths.enqueue(newPath2);
//		queueOfPaths.enqueue(newPath3);
//
//		int[] expecteds = { 1, 2, 3, 4 };
//		int[] actuals = queueOfPaths.peek();
//		System.out.println(queueOfPaths);
//		assertArrayEquals(expecteds, actuals);
//	}
//
//	@Test
//	public void testDequeue() {
//		MyPriorityQueue queueOfPaths = new MyPriorityQueue();
//		int[] newPath = { 1, 2 };
//		int[] newPath2 = { 1, 2, 3 };
//		int[] newPath3 = { 1, 2, 3, 4 };
//
//		queueOfPaths.enqueue(newPath);
//		queueOfPaths.enqueue(newPath2);
//		queueOfPaths.enqueue(newPath3);
//
//		int[] expecteds = { 1, 2 };
//		int[] actuals = queueOfPaths.dequeue();
//
//		assertArrayEquals(expecteds, actuals);
//	}
//
//	@Test
//	public void testDequeuePastEmpty() {
//		MyPriorityQueue queueOfPaths = new MyPriorityQueue();
//		int[] newPath = { 1, 2 };
//		int[] newPath2 = { 1, 2, 3 };
//		int[] newPath3 = { 1, 2, 3, 4 };
//
//		queueOfPaths.enqueue(newPath);
//		queueOfPaths.enqueue(newPath2);
//		queueOfPaths.enqueue(newPath3);
//
//		int[] expecteds = { 1, 2 };
//		int[] actuals = queueOfPaths.dequeue();
//		assertArrayEquals(expecteds, actuals);
//
//		expecteds = new int[] { 1, 2, 3 };
//		actuals = queueOfPaths.dequeue();
//		assertArrayEquals(expecteds, actuals);
//
//		expecteds = new int[] { 1, 2, 3, 4 };
//		actuals = queueOfPaths.dequeue();
//		assertArrayEquals(expecteds, actuals);
//
//		expecteds = new int[] { };
//		actuals = queueOfPaths.dequeue();
//		assertArrayEquals(expecteds, actuals);
//	}

}
