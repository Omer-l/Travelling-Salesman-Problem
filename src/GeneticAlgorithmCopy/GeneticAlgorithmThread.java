package GeneticAlgorithmCopy;

import java.util.Arrays;

public class GeneticAlgorithmThread implements Runnable {
	private String fileName;
	private String threadName;
	protected boolean exit = false;

	public GeneticAlgorithmThread(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void run() {
		ApplicationTestProblem app = new ApplicationTestProblem(fileName);
		app.chromosomes = app.getData();
		app.numberOfChromosomes = app.chromosomes.length;
//		for(AminoAcid c : app.chromosomes)
//			System.out.println(c);
		int[][] populationOfGenes = app.generatePopulationOfGenes();

		for (int i = 0; i < app.maximumGenerations; i++) {

			if (exit)
				break;

			int indexOfParent = app.getBestGeneIndex(populationOfGenes);

			int[] parent = populationOfGenes[indexOfParent];
			double bestDistance = app.calculatePathDistance(parent);

			if (app.calculatePathDistance(parent) < app.minimumPathDistance) {
				app.minimumPathDistance = bestDistance;
				app.minimumPath = parent;
			}
			System.out.println("PARENT: " + app.arrayToString(parent));
			populationOfGenes = app.generateNextGenerationUsingParent(parent);
		}
		
		

	}

	public String getFileName() {
		return fileName;
	}

	public String getThreadName() {
		return threadName;
	}
}
