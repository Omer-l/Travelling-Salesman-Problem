package genetic;

import main.MyArrays;

/**
 *	This is the thread class. An instance of this class runs the genetic algorithm.
 *	The class enables multiple threads to run parallel, in the hope that multiple
 *	genetic algorithms can be completed faster than if they were to run separately
 *	in a for loop.
 *	As the genetic algorithm is run, the best gene from the generation of genes
 *	is chosen, and stored as 'bestPath' and 'bestPathDistance' in this thread.
 */

public class GeneticAlgorithmThread implements Runnable{

	private double bestPathDistance = Double.MAX_VALUE; //keeps track of the best distance, kept MAX_VALUE as a start
	private int[] bestPath; //keeps track of the best complete path so far.
	private boolean exit = false; //exits thread when true
	private final int maximumGenerations; //maximum generations to run the genetic algorithm
	private final GeneticAlgorithm geneticAlgorithm; //instance of the genetic algorithm to run.
	
	
	public GeneticAlgorithmThread(int maximumGenerations, GeneticAlgorithm geneticAlgorithm) {
		this.maximumGenerations = maximumGenerations;
		this.geneticAlgorithm = geneticAlgorithm;
	}

	/**
	 * Runs the genetic algorithm, creating the generations and choosing the best parents until exit or
	 * generationIterator is at maximum number of generations
	 */
	@Override
	public void run() {
		
		int[][] populationOfGenes = geneticAlgorithm.initialisePopulationOfGenes();

		for (int generationIterator = 0; generationIterator < maximumGenerations && exit ; generationIterator++) {

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

	/**
	 * returns the best path's distance.
	 */
	public double getBestPathDistance() {
		return bestPathDistance;
	}

	/**
	 * When exit is true, the for loop in the run function will stop
	 */
	public void setExit(boolean exit) {
		this.exit = exit;
	}

	@Override
	public String toString() {
		int startingIndex = bestPath[0]+1; //to print a path, that is, a cycle that returns to the starting index in the path
		return "PATH: [" + MyArrays.toString(bestPath) + " " + startingIndex + " ] ->  DISTANCE: " + bestPathDistance;
	}

}
