package GeneticAlgorithmCopy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import DijkstrasApproach.Vertex;
import NN_Approach.Point;

public class ApplicationTrainProblem {
	private static File file = new File(System.getProperty("user.dir") + "/Resources/trainProblem1/"); // path for data
	
	private final static int SIZE = 40; //population size
	private static int numberOfChromosomes = 0; //number of chromosomes per gene       gene -> population -> generation
	private final static int PROBABILITY_OF_MUTATION = 10; // 1/100 chance of getting mutated
	private final static int MAXIMUM_GENERATIONS = 100;
	private final static boolean MINIMISE = true; // determines whether to minimise or maximise genes.
	private static Chromosome[] chromosomes;
	
	public static void main(String[] args) {
		chromosomes = getData();
		for(Chromosome chromosome : chromosomes)
			System.out.println(chromosome);
		
		numberOfChromosomes = chromosomes.length;
		System.out.println(Arrays.deepToString(generatePopulationOfGenes()));
		String[] populationOfGenes = generatePopulationOfGenes();
		System.out.println(Arrays.toString(populationOfGenes));

		for (int i = 0; i < MAXIMUM_GENERATIONS; i++) {
			int[] indexesOfParents = getBest2GenesIndexes(populationOfGenes);

			System.out.println(Arrays.toString(indexesOfParents));

			String parent1 = populationOfGenes[indexesOfParents[0]];
			String parent2 = populationOfGenes[indexesOfParents[1]];

			System.out.println("PARENT 1: " + parent1 + " -> " + getDistanceOfGene(parent1) + " \nPARENT 2: " + parent2
					+ " -> " + getDistanceOfGene(parent1));
			System.out.println("OFFSPRINGS:");

			populationOfGenes = generateNextGenerationUsing2Parents(parent1, parent1);

		}
	}
	
	/**
	 * gets all the Vertexes from a text file.
	 * 
	 * @return
	 */
	public static Chromosome[] getData() {

		Chromosome[] chromosomes = new Chromosome[getNumberOfTrainingSets()]; // An array of Vertexs

		int pointNumber = 0; // current index in array of Vertexs

		try {
			Scanner input = new Scanner(file); // Scanner for reading the data file.
			while (input.hasNext()) {
				String[] bits = input.nextLine().split("\\s+");
				int a = Integer.parseInt(bits[2].trim()); // adds point x
				int b = Integer.parseInt(bits[3].trim()); // add point y
				chromosomes[pointNumber] = new Chromosome(a, b); // creates an instance of the class Vertex wit a and b.
				pointNumber++;
			}
			input.close(); // close scanners
			return chromosomes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static int getNumberOfTrainingSets() {
		int trainingSetsCounter = 0; // counter

		try {
			Scanner input = new Scanner(file);

			while (input.hasNext()) {
				String tmp = input.nextLine();
				trainingSetsCounter++;
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}

		return trainingSetsCounter;
	}
	
	//boolean array[] of currently picked indexes, every loop see if while(index is true) then keep randomising.
	/**
	 * @param	number of Chromosomes
	 * @return an arbitrarily created population of genes with no duplicate indexes (index of chromosomes)
	 */
	public static String[] generatePopulationOfGenes() {
		String[] population = new String[SIZE]; // SIZE number of genes in the population
		
		for (int i = 0; i < SIZE; i++) {
			boolean[] indexChosen = new boolean[numberOfChromosomes];
			population[i] = new String();
			for (int j = 0; j < numberOfChromosomes; j++) {
				int randomNumber = (int) (Math.random() * numberOfChromosomes);
				
				while(indexChosen[randomNumber]) //ensures duplicate indexes are not chosen
					randomNumber = (int) (Math.random() * numberOfChromosomes);
				
				population[i] += "" + randomNumber;
				indexChosen[randomNumber] = true; //this point index is now added
			}
		}

		return population;
	}

//    simple evaluation function that returns the sum of the digits.
	public static double getDistanceOfGene(String s) {
		double sum = 0;
		
		for (int i = 1; i < s.length(); i++) {
			int index1 = new Integer(s.charAt(i-1) + "").intValue();
			int index2 = new Integer(s.charAt(i) + "").intValue();
//			System.out.println("STRING: " + s +" INDEXES: " + index1 + " AND " + index2);
			
			sum += chromosomes[index1].getDistanceTo(chromosomes[index2]);
		}

		
		//Get endPoint to startPoint to create a cycle
		int endPointIndex = new Integer(s.charAt(numberOfChromosomes - 1) + "").intValue();
		int startPointIndex = new Integer(s.charAt(0) + "").intValue();
    	Chromosome endPoint = chromosomes[endPointIndex];
    	Chromosome startPoint = chromosomes[startPointIndex];
    	
    	sum += endPoint.getDistanceTo(startPoint);
    	
		return sum;
	}
	/*
	 * double totalDistance = 0;
    	
    	for(int j = 1; j < points.length; j++) {
    		Point point1 = points[path[j-1]];
    		Point point2 = points[path[j]];
    		
    		totalDistance += point1.getDistanceTo(point2);
    	}
    	
    	return totalDistance;
	 */

	/**
	 * 
	 * @return a random index to crossover
	 */
	public static int getCrossOverIndex() {
		return (int) (Math.random() * (numberOfChromosomes + 1));
	}

	public static String getCrossover(String parent1, String parent2, int crossoverIndex) {

		String part1Parent1 = parent1.substring(0, crossoverIndex);
		String part2Parent2 = parent2.substring(crossoverIndex, parent2.length());

		String result = part1Parent1 + part2Parent2;

		return result;
	}

	/**
	 * 
	 * @param parent1	best parent 1
	 * @param parent2 	best parent 2.
	 * @return 			an offspring from the crossover parts of both parents
	 */
	public static String getOffspring(String parent1, String parent2) {

		int crossoverIndex = getCrossOverIndex();

		String offspring = getCrossover(parent1, parent2, crossoverIndex);
		offspring = mutateOffspring(offspring);
		return offspring;
	}

	/**
	 * 
	 * @param parent1 best parent 1
	 * @param parent2 best parent 2.
	 * @return a generation of genes from two parents.
	 */
	public static String[] generateNextGenerationUsing2Parents(String parent1, String parent2) {
		String[] nextGeneration = new String[SIZE];

		for (int i = 0; i < SIZE; i++) {
			nextGeneration[i] = getOffspring(parent1, parent2);
			System.out.println(nextGeneration[i] + " -> SUM: " + getDistanceOfGene(nextGeneration[i]));
		}

		return nextGeneration;
	}

	/**
	 * This function gets the two best genes, you can change the if statements in
	 * the for loop to minimise.
	 * 
	 * @param generation a generation of genes
	 * @return the two best genes to produce an offspring from.
	 */
	public static int[] getBest2GenesIndexes(String[] generation) {
		double currentMaximumSum1 = getDistanceOfGene(generation[0]); // best of the best
		int currentBestGeneIndex1 = 0;
        
		double currentMaximumSum2 = getDistanceOfGene(generation[0]); // 2nd best
		int currentBestGeneIndex2 = 0;

		if (MINIMISE) {
			for (int i = 0; i < generation.length; i++) {
				double sum = getDistanceOfGene(generation[i]);
				System.out.println(generation[i] + " -> SUM: " + sum);

				if (sum < currentMaximumSum1) {
					currentMaximumSum2 = currentMaximumSum1;
					currentBestGeneIndex2 = currentBestGeneIndex1;

					currentMaximumSum1 = sum;
					currentBestGeneIndex1 = i;
				} else if ((sum < currentMaximumSum2) && (sum >= currentMaximumSum1)) {
					currentMaximumSum2 = sum;
					currentBestGeneIndex2 = i;
				}
			}
		} else {
			for (int i = 0; i < generation.length; i++) {
				double sum = getDistanceOfGene(generation[i]);
				System.out.println(generation[i] + " -> SUM: " + sum);

				if (sum > currentMaximumSum1) {
					currentMaximumSum2 = currentMaximumSum1;
					currentBestGeneIndex2 = currentBestGeneIndex1;

					currentMaximumSum1 = sum;
					currentBestGeneIndex1 = i;
				} else if ((sum > currentMaximumSum2) && (sum <= currentMaximumSum1)) {
					currentMaximumSum2 = sum;
					currentBestGeneIndex2 = i;
				}
			}
		}

		return new int[] { currentBestGeneIndex1, currentBestGeneIndex2 };
	}

	/**
	 * 
	 * @param offspring gene to mutate
	 * @return possibly a mutated/different gene.
	 */
	public static String mutateOffspring(String offspring) {
		char[] offspringToCharArray = offspring.toCharArray();

		// for each digit in the gene, get probability to mutate
		for (int i = 0; i < offspringToCharArray.length; i++)
			if (mutate())
				offspringToCharArray = getMutation(offspringToCharArray, i);

		return new String(offspringToCharArray);
	}

	/**
	 * This function determines whether a digit should be mutated
	 * 
	 * @return true if a digit should be mutated.
	 */
	public static boolean mutate() {
		return ((int) (Math.random() * 100) + 1) == PROBABILITY_OF_MUTATION;
	}

	public static char[] getMutation(char[] offspring, int indexToMutate) {
		
		int randomIndexToSwapWith = (int) (Math.random() * numberOfChromosomes); //index to swap with
		while(randomIndexToSwapWith == indexToMutate)
			randomIndexToSwapWith = (int) (Math.random() * numberOfChromosomes);
		
		System.out.println("SWAPPING " + randomIndexToSwapWith + " WITH INDEX: " + indexToMutate);
		char[] mutation = offspring.clone();
		
		char tmp = mutation[indexToMutate];
		mutation[indexToMutate] = mutation[randomIndexToSwapWith];
		mutation[randomIndexToSwapWith] = tmp;
		return mutation;
	}

	public static boolean finished(String[] generation) {
		if (MINIMISE)
			for (int i = 0; i < generation.length; i++) {
				if (getDistanceOfGene(generation[i]) == 0) {
					System.out.print(generation[i] + " = 0 ");
					return true;
				}
			}
		else
			for (int i = 0; i < generation.length; i++) {
				if (getDistanceOfGene(generation[i]) == numberOfChromosomes * 9) {
					System.out.print(generation[i] + " =  " + (numberOfChromosomes * 9));
					return true;
				}
			}
		return false;
	}
}