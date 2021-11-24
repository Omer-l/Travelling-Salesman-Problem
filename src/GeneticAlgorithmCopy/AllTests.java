package GeneticAlgorithmCopy;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class AllTests {

	@Test
	public void testGeneratePopulationOfGenes() {
		ApplicationTestProblem app = new ApplicationTestProblem("trainProblem2");
		app.chromosomes = app.getData();
		app.numberOfChromosomes = app.chromosomes.length;

		for(Chromosome chromosome : app.chromosomes)
			System.out.println(chromosome);
		
		int[][] nextGeneration = app.generatePopulationOfGenes();
		System.out.println(Arrays.deepToString(nextGeneration));
		for(int[] arr : nextGeneration)
			System.out.println(Arrays.toString(arr));
		
		for(int i = 0; i < nextGeneration.length; i++)
			for(int j = 1; j < nextGeneration[i].length; j++)
				assertTrue(!(nextGeneration[i][j] == nextGeneration[i][j-1]));
	}
	
	@Test
	public void testCalculatePathdistance() {
		ApplicationTestProblem app = new ApplicationTestProblem("trainProblem1");
		app.chromosomes = app.getData();
		app.numberOfChromosomes = app.chromosomes.length;
		int[] path = {0, 1, 2, 3};
		double expected = 26.069053496924262;
		assertEquals(expected, app.calculatePathDistance(path), 1e-15);
	}

	@Test
	public void testGetCrossover() {
		int[] parent = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		int crossoverIndex = 9;
		int[] result = ApplicationTestProblem.getCrossover(parent, crossoverIndex);
		int[] expected = {9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8};
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testGenerateNextGenerationUsingParent() {
		ApplicationTestProblem app = new ApplicationTestProblem("trainProblem2");
		app.chromosomes = app.getData();
		app.numberOfChromosomes = app.chromosomes.length;
		
		int[] parent = {0, 1, 2, 3, 4, 5, 6, 7};
		int[][] offspring = app.generateNextGenerationUsingParent(parent);
	}

}
