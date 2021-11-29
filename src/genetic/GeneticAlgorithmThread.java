package genetic;

/**
 *	This is the thread file. This file runs the genetic algorithm in the ApplicationRunner.java file.
 *
 *		Using the run() function, this file enables multiple threads to run parallel, in the hope that the
 *		genetic algorithm can be completed faster.
 *
 *		Chooses the best gene from the generation of genes. This is called the parent.
 *
 *		Then this algorithm produces offsprings from one parent.
 *
 *		After producing an offspring, each index of the offspring has a probability of being mutated.
 *
 *		The mutation for this genetic algorithms modifies the path by swapping the indexes between two cities (One
 *		is the mutated index, and the other is a randomised index
 */

public class GeneticAlgorithmThread implements Runnable{
	private final String threadName;
	private double bestPathDistance = Double.MAX_VALUE;
	private int[] bestPath;
	private boolean exit = false;
	private final int maximumGenerations;
	private final GeneticAlgorithm geneticAlgorithm;
	
	
	public GeneticAlgorithmThread(String threadName,int maximumGenerations, GeneticAlgorithm geneticAlgorithm) {
		this.threadName = threadName;
		this.maximumGenerations = maximumGenerations;
		this.geneticAlgorithm = geneticAlgorithm;
	}

	@Override
	public void run() {
		
		int[][] populationOfGenes = geneticAlgorithm.initialisePopulationOfGenes();

		for (int generationIterator = 0; generationIterator < maximumGenerations; generationIterator++) {
			
			if(exit)
				break;
			
			int indexOfParent = geneticAlgorithm.getBestGeneIndex(populationOfGenes);
			
			int[] parent = populationOfGenes[indexOfParent];
			double parentDistance = geneticAlgorithm.calculatePathDistance(parent);

			if(parentDistance < bestPathDistance) { //new global best parent .
				bestPathDistance = parentDistance;
				bestPath = parent;
			}
			populationOfGenes = geneticAlgorithm.generateNextGenerationUsingParent(parent);
		}
		
	}

	/**
	 * This function goes through each thread that is run and chooses the thread with the best path for the TSP
	 *
	 * @param threads 	array of threads to be evaluated
	 * @return 			the thread that contains the minimum distance and path.
	 */
	public static GeneticAlgorithmThread getThreadWithMinimumPathGene(GeneticAlgorithmThread[] threads) {
		GeneticAlgorithmThread shortestDistanceThread = threads[0];

		for (GeneticAlgorithmThread thread : threads)
			if (thread != null) { //guard that ensures a null thread is not accessed.
				if (thread.getBestPathDistance() < shortestDistanceThread.getBestPathDistance())
					shortestDistanceThread = thread;
			} else //there exists no more threads
				break;

		return shortestDistanceThread;
	}

	public double getBestPathDistance() {
		return bestPathDistance;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	@Override
	public String toString() {
		return threadName + " - PATH: [" + GeneticAlgorithm.arrayToString(bestPath) + " ] ->  DISTANCE: " + bestPathDistance;
	}

}
