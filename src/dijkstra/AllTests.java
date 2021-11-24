package dijkstra;

import static org.junit.Assert.*;

import java.util.PriorityQueue;

import org.junit.Test;

public class AllTests {

	@Test
	public void fillDistancesWithInfinityTest() {
		double[] testArray = new double[10];
		int randomIndex = (int) (Math.random() * testArray.length);
		Application.fillDistancesWithInfinity(testArray, randomIndex);

		for (int i = 0; i < testArray.length; i++)
			if (i == randomIndex)
				assertTrue(testArray[i] == 0);
			else
				assertTrue(testArray[i] == Double.MAX_VALUE);
	}

	@Test
	public void getDistanceToOtherVertexsTest() {
		Application application = new Application();
		application.vertices = new Vertex[] { new Vertex(1.0, 1.0), new Vertex(5.0, 5.0), new Vertex(10.0, 3.0),
				new Vertex(2.0, 7.0) };

		boolean[] visited = new boolean[] { true, false, false, true };
		int currentIndex = 2;

		double[] distances = Application.getDistanceToOtherVertexes( visited, currentIndex);
		assertTrue(distances[0] == Double.MAX_VALUE && visited[0] == true);
		assertTrue(distances[1] == application.vertices[2].getDistanceTo(application.vertices[1]));
		assertTrue(distances[currentIndex] == 0);
		assertTrue(distances[3] == Double.MAX_VALUE && visited[3] == true);
	}
	
	@Test 
	public void calculatePathDistanceTestPathAndVertices() {
		
		
	 Vertex v1 = new Vertex(1.0,1.0);
	 Vertex v2 = new Vertex(5.0,5.0);
	 Vertex v3 = new Vertex(10.0,3.0);
	 Vertex v4 = new Vertex(2.0,7.0);
	 
	 double expectedDistance = 24.293023070189598;
	 Integer[] path = {1 - 1, 3 - 1, 2 - 1, 4 - 1};
	 Vertex[] v = {v1,v2,v3,v4}; 
	 
	 assertEquals(expectedDistance, Application.calculatePathDistance(path,true),1e-15);
	}
	
	
	
	@Test 
	public void sortQueueTest() {
		Vertex v1 = new Vertex(1.0,1.0);
		Vertex v2 = new Vertex(5.0,5.0);
		Vertex v3 = new Vertex(10.0,3.0);
		Vertex v4 = new Vertex(2.0,7.0);
		
		Vertex[] v = {v1,v2,v3,v4};
		

		Integer[] path1 = {0, 1};
		Integer[] path2 = {0, 2};
		Integer[] path3 = {0, 3};
		Integer[] path4 = {0, 3, 2};
		Integer[] path5 = {0,1,2,3};
		Integer[] path6 = {0,2,1,3};
		
//		ArrayList<Vertex> path1  = new ArrayList<>(Arrays.asList(path1Array));
//		ArrayList<Vertex> path2 = new ArrayList<>(Arrays.asList(path2Array));
//		ArrayList<Vertex> path3 = new ArrayList<>(Arrays.asList(path3Array));
	
		PriorityQueue<Integer[]> priorityQueueOfPaths  = new PriorityQueue<Integer[]>(new PathComparator(v));
		
		priorityQueueOfPaths.add(path4);
		priorityQueueOfPaths.add(path5);
		priorityQueueOfPaths.add(path1);
		priorityQueueOfPaths.add(path2);
		priorityQueueOfPaths.add(path3);
		priorityQueueOfPaths.add(path6);
		
		
		
		while (!priorityQueueOfPaths.isEmpty()) {
			Integer[] nextShortestPath = priorityQueueOfPaths.poll();
			
			Vertex[] nextShortestPathToVerticesArray = new Vertex[nextShortestPath.length];
			
			for(int i = 0; i < nextShortestPath.length; i++)
				nextShortestPathToVerticesArray[i] = v[nextShortestPath[i]];
				
			PathComparator comparator = new PathComparator(v);
//			System.out.println(ApplicationRunner.arrayToString(nextShortestPathToVerticesArray) + "DISTANCE: " + ApplicationRunner.calculatePathDistance(nextShortestPathToVerticesArray));
			if(!priorityQueueOfPaths.isEmpty())
				assertEquals(-1, comparator.compare(nextShortestPath, priorityQueueOfPaths.poll()));
		}
	}
	
	@Test
	public void pathComparatorTest() {
		 Vertex v1 = new Vertex(1.0,1.0);
		 Vertex v2 = new Vertex(5.0,5.0);
		 Vertex v3 = new Vertex(10.0,3.0);
		 Vertex v4 = new Vertex(2.0,7.0);
		 
		Integer[] path1Array = {0,1,2,3};
		Integer[] path2Array = {0,2,1,3};
		
		
		Vertex[] v = {v1,v2,v3,v4};
		PathComparator comparator = new PathComparator(v);
		
		
		assertEquals(1, comparator.compare(path1Array, path2Array));
		assertEquals(-1, comparator.compare(path2Array, path1Array));
		
	}
	
	
	
	@Test 
	public void addPathsToQueueTest() {
		
	}

	@Test
	public void testDijkstra() {
		
	}
	
	/**
	 * MYPRIORITYQUEUE TESTS
	 */
	@Test
	public void testEnqueue() {
		MyPriorityQueue queue = new MyPriorityQueue();
	}
}
