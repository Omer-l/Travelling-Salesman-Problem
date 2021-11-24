package genetic;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class AllTests {

	@Test
	public void testGeneratePopulationOfGenes() {
		ApplicationRunner app = new ApplicationRunner("trainProblem2");
		app.setAminoAcids(app.getData());
		app.setNumberOfAminoAcids(app.getAminoAcids().length);

		for(AminoAcid aminoAcid : app.getAminoAcids())
			System.out.println(aminoAcid);
		
		int[][] nextGeneration = app.initialisePopulationOfGenes();
		System.out.println(Arrays.deepToString(nextGeneration));
		for(int[] arr : nextGeneration)
			System.out.println(Arrays.toString(arr));
		
		for(int i = 0; i < nextGeneration.length; i++)
			for(int j = 1; j < nextGeneration[i].length; j++)
				assertTrue(!(nextGeneration[i][j] == nextGeneration[i][j-1]));
	}
	
	@Test
	public void testCalculatePathdistance() {
		ApplicationRunner app = new ApplicationRunner("trainProblem1");
		app.setAminoAcids(app.getData());
		app.setNumberOfAminoAcids(app.getAminoAcids().length);
		int[] path = {0, 1, 2, 3};
		double expected = 26.069053496924262;
		assertEquals(expected, app.calculatePathDistance(path), 1e-15);
	}

	@Test
	public void testGetCrossover() {
		int[] parent = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		int crossoverIndex = 9;
		int[] result = ApplicationRunner.getResultOfCrossover(parent, crossoverIndex);
		int[] expected = {9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6, 7, 8};
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testGenerateNextGenerationUsingParent() {
		ApplicationRunner app = new ApplicationRunner("trainProblem2");
		app.setAminoAcids(app.getData());
		app.setNumberOfAminoAcids(app.getAminoAcids().length);
		
		int[] parent = {0, 1, 2, 3, 4, 5, 6, 7};
		int[][] offspring = app.generateNextGenerationUsingParent(parent);
	}

}
