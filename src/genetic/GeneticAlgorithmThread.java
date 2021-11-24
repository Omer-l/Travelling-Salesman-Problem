package genetic;

/**
 *	This is the thread file. This file runs the runs the genetic algorithm in the ApplicationRunner.java file.
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
	private final String fileName;
	private final String threadName;
	private double bestPathDistance = Double.MAX_VALUE;
	private int[] bestPath;
	private boolean exit = false;
	private final int MAXIMUM_GENERATIONS = 1000;
	
	
	public GeneticAlgorithmThread(String threadName, String fileName) {
		this.threadName = threadName;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		ApplicationRunner app = new ApplicationRunner(fileName);
		app.setAminoAcids(app.getData());
		app.setNumberOfAminoAcids(app.getAminoAcids().length);
		
		int[][] populationOfGenes = app.initialisePopulationOfGenes();

		for (int generationIterator = 0; generationIterator < MAXIMUM_GENERATIONS; generationIterator++) {
			
			if(exit)
				break;
			
			int indexOfParent = app.getBestGeneIndex(populationOfGenes);
			
			int[] parent = populationOfGenes[indexOfParent];
			double parentDistance = app.calculatePathDistance(parent);

			if(parentDistance < bestPathDistance) { //new global best parent.
				bestPathDistance = parentDistance;
				bestPath = parent;
			}
			populationOfGenes = app.generateNextGenerationUsingParent(parent);
		}
		
	}
	
	public String getFileName() {
		return fileName;
	}

	public String getThreadName() {
		return threadName;
	}

	public double getBestPathDistance() {
		return bestPathDistance;
	}

	public int[] getBestPath() {
		return bestPath;
	}

	public void setBestPathDistance(double bestPathDistance) {
		this.bestPathDistance = bestPathDistance;
	}

	public void setBestPath(int[] bestPath) {
		this.bestPath = bestPath;
	}

	public boolean exit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	@Override
	public String toString() {
		return threadName + " - FILE: " + fileName + " - PATH: [" + ApplicationRunner.arrayToString(bestPath) + " ] ->  DISTANCE: " + bestPathDistance;
	}

}
