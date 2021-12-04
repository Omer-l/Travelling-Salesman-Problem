package genetic;
//This file is used to test functions in the GeneticAlgorithm.java class
import static org.junit.Assert.*;

import java.util.Arrays;

import main.DataPoint;
import main.MyFileReader;
import org.junit.Test;

public class AllTests {

	//checks if there are any duplicate cities.
	@Test
	public void testGeneratePopulationOfGenes() {
		MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir") + "/Resources/trainProblem2");
		DataPoint[] dataPoints = fileReader.getData();

		GeneticAlgorithm app = new GeneticAlgorithm(50, 15, dataPoints);

		for(DataPoint aminoAcid : app.getAminoAcids())
			System.out.println(aminoAcid);
		
		int[][] nextGeneration = app.initialisePopulationOfGenes();
		System.out.println(Arrays.deepToString(nextGeneration));
		for(int[] arr : nextGeneration)
			System.out.println(Arrays.toString(arr));

		for(int[] gene : nextGeneration) {
			//like a selection sort, except, the elements, don't move, compares each currentAminoAcid to all other amino acids.
			for (int currentAminoAcidIterator = 0; currentAminoAcidIterator < nextGeneration.length; currentAminoAcidIterator++) {
				int currentAminoAcid = gene[currentAminoAcidIterator];
				for (int aminoAcidIterator = 0; aminoAcidIterator < nextGeneration[currentAminoAcidIterator].length; aminoAcidIterator++) {
					int iteratedAminoAcid = gene[aminoAcidIterator];
					if (!(aminoAcidIterator == currentAminoAcidIterator))
						assertNotEquals(currentAminoAcid, iteratedAminoAcid);
				}
			}
		}
	}
	
	@Test
	public void testCalculatePathDistance() {
		MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir") + "/Resources/trainProblem1");
		DataPoint[] dataPoints = fileReader.getData();

		GeneticAlgorithm app = new GeneticAlgorithm(50, 15, dataPoints);
		int[] path = {0, 1, 2, 3};
		double expected = 26.069053496924262;
		assertEquals(expected, app.calculatePathDistance(path), 1e-15);
	}

	@Test
	public void testGetCrossover() {
		MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir") + "/Resources/trainProblem2");
		DataPoint[] dataPoints = fileReader.getData();

		GeneticAlgorithm app = new GeneticAlgorithm(50, 15, dataPoints);
		int[] parent = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		int crossoverIndex = 9;
		int[] result = app.getResultOfCrossover(parent, crossoverIndex);
		int[] expected = {9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8};
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testGenerateNextGenerationUsingParent() {
		MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir") + "/Resources/trainProblem2");
		DataPoint[] dataPoints = fileReader.getData();

		GeneticAlgorithm app = new GeneticAlgorithm(50, 15, dataPoints);
		
		int[] parent = {0, 1, 2, 3, 4, 5, 6, 7};
		int[][] offspring = app.generateNextGenerationUsingParent(parent);
		assertNotNull(offspring);
	}

}
