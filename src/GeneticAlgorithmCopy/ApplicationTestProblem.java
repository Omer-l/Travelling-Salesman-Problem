package GeneticAlgorithmCopy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import DijkstrasApproach.Vertex;
import NN_Approach.Point;

public class ApplicationTestProblem {
	private static File file;

	public ApplicationTestProblem(String fileName) {
		this.file = new File(System.getProperty("user.dir") + "/Resources/" + fileName + "/"); // path for data
	}
	// SIM MY BEST
	// 282.22963288784723 - FILE: test1_2018 - PATH: [ 7 1 10 11 2 9 4 8 5 3 12 6 ]
	// -> DISTANCE: 273.85967307938483
	// 702.0589801285125 - FILE: test2_2018 - PATH: [ 9 3 5 12 4 2 11 7 1 13 6 14 8
	// 10 ] -> DISTANCE: 661.494844616538
	// 138753.37233384346 - FILE: test3_2018 - PATH: [ 6 5 4 14 12 8 13 11 2 9 10 1
	// 15 7 16 17 3 ] -> DISTANCE: 126265.42652170827
	// 186854.89928444562 - ^FILE: test4_2018 - PATH: [ 22 1 38 27 34 9 18 4 31 24
	// 14 37 25 30 39 8 2 40 10 12 42 20 21 17 5 41 3 33 28 7 35 13 36 23 29 11 26
	// 16 15 19 6 32 ] -> DISTANCE: 196917.46994668932
	// 10605.096933318093 - FILE: test1_2019 - PATH: [ 7 9 11 10 4 3 2 5 8 6 1 ] ->
	// DISTANCE: 10605.096933318093
	// 215.16686897202064 - FILE: test2_2019 - PATH: [ 2 1 10 11 12 13 9 8 7 6 5 4 3
	// ] -> DISTANCE: 196.00581416951627
	// 336.6810973535036 - FILE: test3_2019 - PATH: [ 1 5 10 11 12 9 14 8 3 6 4 2 7
	// 15 13 ] -> DISTANCE: 333.7959964703076
	// 35942.00740697837 - ^FILE: test4_2019 - PATH: [ 5 29 2 4 26 42 10 35 45 24 32
	// 39 21 13 47 20 12 33 46 38 31 44 18 7 36 28 30 6 37 19 27 17 43 15 40 3 22 1
	// 9 8 16 41 34 23 11 14 25 48 ] -> DISTANCE: 34964.13174220773
	// SAME - FILE: test1_2020 - PATH: [ 3 1 7 12 2 9 11 10 6 4 8 5] -> DISTANCE:
	// 463.95774842296623
	// SAME - FILE: test2_2020 - PATH: [ 10 3 13 4 2 12 7 8 1 14 9 5 6 11] ->
	// DISTANCE: 731.4567329318198
	// - FILE: test3_2020 - PATH: [ 13 17 1 12 4 10 15 3 14 11 6 16 5 2 9 18 8 7] ->
	// DISTANCE: 135664.84173227008
	// - ^FILE: test4_2020 - PATH: [ 24 27 21 19 13 15 31 12 18 3 1 25 29 14 28 20
	// 11 32 9 6 23 2 17 5 30 4 22 7 16 26 10 8 ] -> DISTANCE: 631597.4299742716
	// -

	public ApplicationTestProblem() {
	}

	protected final static int SIZE = 50; // population size
	protected static int numberOfChromosomes = 0; // number of chromosomes per gene gene -> population -> generation
	protected final static int PROBABILITY_OF_MUTATION = 15; // 1/100 chance of getting mutated
	protected static int maximumGenerations = 1000;
	protected final static boolean MINIMISE = true; // determines whether to minimise or maximise genes.
	protected static Chromosome[] chromosomes;
	protected static int[] minimumPath;
	protected static double minimumPathDistance = Double.MAX_VALUE;

	public static void main(String[] args) {
		long startTimeForFile = System.currentTimeMillis(); // starts timer
		int testNumber = 1;
		int year = 2020;
		
		if(testNumber == 4)
			maximumGenerations = 5000; //A LOT OF CITIES ~ A LOT MORE GENERATIONS REQUIRED
		
		String fileName = "test" + testNumber + "_" + year;

//				file = new File(System.getProperty("user.dir") + "/Resources/" + fileName + "/");
//				chromosomes = getData();
//				System.out.println("\n---------\n" + fileName + "\n---------\n");
//				for(AminoAcid chromosome : chromosomes)
//					System.out.println(chromosome);

//				maximum number of available threads in the JVM
		GeneticAlgorithmThread[] threads = new GeneticAlgorithmThread[256];
		
		// run threads
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new GeneticAlgorithmThread(fileName);
			threads[i].run(); // TEST PROBLEMS 2020 WILL NOT WORK BECAUSE OF GETDATA FUNCTION
			long elapsedTimeSoFar = (System.currentTimeMillis() - startTimeForFile) / 1000;
			if (elapsedTimeSoFar >= 59) { // end this genetic algorithm search.
				for (int j = i; j >= 0; j--)
					threads[j].exit = true; // exit all threads
				break;
			}
		}
//		GeneticAlgorithmThread bestThread = getMinimumThread(threads);
		long elapsedTime = (System.currentTimeMillis() - startTimeForFile) / 1000;
		System.out.println(fileName + " - BEST PATH - " + arrayToString(minimumPath) + "-> DISTANCE: " + minimumPathDistance + " - ELAPSED TIME: " + elapsedTime + " seconds");
	}

//	public static GeneticAlgorithmThread getMinimumThread(GeneticAlgorithmThread[] threads) {
//		GeneticAlgorithmThread shortestDistanceThread = threads[0];
//
//		for (GeneticAlgorithmThread t : threads)
//			if (t != null) {
//				if (t.getMinimumPathDistance() < shortestDistanceThread.getMinimumPathDistance())
//					shortestDistanceThread = t;
//			} else //there exists no more threads
//				break;
//
//		return shortestDistanceThread;
//	}

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
				int a = Integer.parseInt(bits[1].trim()); // adds point x
				int b = Integer.parseInt(bits[2].trim()); // add point y
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

	// boolean array[] of currently picked indexes, every loop see if while(index is
	// true) then keep randomising.
	/**
	 * @return an arbitrarily created population of genes with no duplicate indexes
	 *         (index of chromosomes)
	 */
	public static int[][] generatePopulationOfGenes() {
		int[][] population = new int[SIZE][numberOfChromosomes]; // SIZE number of genes in the population

		for (int i = 0; i < SIZE; i++) {
			boolean[] indexChosen = new boolean[numberOfChromosomes];
			for (int j = 0; j < numberOfChromosomes; j++) {
				int randomNumber = (int) (Math.random() * numberOfChromosomes);

				while (indexChosen[randomNumber]) { // ensures duplicate indexes are not chosen
//					if(randomNumber >= numberOfChromosomes - 1) {
//						randomNumber = 0;
//						continue;
//					}
					randomNumber = (int) (Math.random() * numberOfChromosomes);
				}
				population[i][j] = randomNumber;
				indexChosen[randomNumber] = true; // this point index is now added
			}
		}

		return population;
	}

//    simple evaluation function that returns the distance of a path.
	public static double calculatePathDistance(int[] path) {
		double totalDistance = 0;

		for (int j = 1; j < chromosomes.length; j++) {
			int index1 = path[j - 1];
			int index2 = path[j];
			Chromosome point1 = chromosomes[index1];
			Chromosome point2 = chromosomes[index2];
			totalDistance += point1.getDistanceTo(point2);
		}
		// Get endPoint to startPoint to create a cycle
		int endPointIndex = path[chromosomes.length - 1];
		int startPointIndex = path[0];
		Chromosome endPoint = chromosomes[endPointIndex];
		Chromosome startPoint = chromosomes[startPointIndex];

		totalDistance += endPoint.getDistanceTo(startPoint);

		return totalDistance;
	}

	/**
	 * @return a random index to crossover
	 */
	public static int getCrossOverIndex() {
		return (int) (Math.random() * (numberOfChromosomes + 1));
	}

	public static int[] getCrossover(int[] parent, int crossoverIndex) {
		int[] result = new int[parent.length];
		int[] part1 = new int[crossoverIndex];
		int[] part2 = new int[parent.length - crossoverIndex];

		// splitting into parts
		System.arraycopy(parent, 0, part1, 0, crossoverIndex);
		System.arraycopy(parent, crossoverIndex, part2, 0, parent.length - crossoverIndex);

		// adding it to result, AKA FLIPPING THE POSITION OF THE PARTS
		System.arraycopy(part2, 0, result, 0, part2.length);
		System.arraycopy(part1, 0, result, part2.length, parent.length - part2.length);

		return result;
	}

	/**
	 * 
	 * @param parent1 best parent 1
	 * @param parent2 best parent 2.
	 * @return an offspring from the crossover parts of both parents
	 */
	public static int[] getOffspring(int[] parent) {

		int crossoverIndex = getCrossOverIndex();

		int[] offspring = getCrossover(parent, crossoverIndex);
		offspring = mutateOffspring(offspring);

		return offspring;
	}

	/**
	 * 
	 * @param parent1 best parent 1
	 * @param parent2 best parent 2.
	 * @return a generation of genes from two parents.
	 */
	public static int[][] generateNextGenerationUsingParent(int[] parent) {
		int[][] nextGeneration = new int[SIZE][numberOfChromosomes];

		for (int i = 0; i < SIZE; i++) {
			nextGeneration[i] = getOffspring(parent);
//			System.out
//					.println(arrayToString(nextGeneration[i]) + " -> SUM: " + calculatePathDistance(nextGeneration[i]));
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
	public static int getBestGeneIndex(int[][] generation) {
		double currentMaximumSum1 = calculatePathDistance(generation[0]); // best of the best
		int currentBestGeneIndex1 = 0;

		for (int i = 0; i < generation.length; i++) {
			double sum = calculatePathDistance(generation[i]);

			if (sum < currentMaximumSum1) {
				currentMaximumSum1 = sum;
				currentBestGeneIndex1 = i;
			}
		}

		return currentBestGeneIndex1;
	}

	public static int[] getMutation(int[] offspring, int indexToMutate) {

		int randomIndexToSwapWith = (int) (Math.random() * numberOfChromosomes); // index to swap with
		while (randomIndexToSwapWith == indexToMutate)
			randomIndexToSwapWith = (int) (Math.random() * numberOfChromosomes);

//		System.out.println("SWAPPING " + randomIndexToSwapWith + " WITH INDEX: " + indexToMutate);
		int[] mutation = offspring.clone();

		int tmp = mutation[indexToMutate];
		mutation[indexToMutate] = mutation[randomIndexToSwapWith];
		mutation[randomIndexToSwapWith] = tmp;
		return mutation;
	}

	/**
	 * This function determines whether a digit should be mutated
	 * 
	 * @return true if a digit should be mutated.
	 */
	public static boolean mutate() {
		return ((int) (Math.random() * 100) + 1) == PROBABILITY_OF_MUTATION;
	}

	/**
	 * 
	 * @param offspring gene to mutate
	 * @return possibly a mutated/different gene.
	 */
	public static int[] mutateOffspring(int[] offspring) {

		// for each digit in the gene, get probability to mutate
		for (int i = 0; i < offspring.length; i++)
			if (mutate())
				offspring = getMutation(offspring, i);

		return offspring;
	}

	public static String arrayToString(int[] arr) {
		String s = "";
		for (int i : arr)
			s += " " + (i + 1);
		return s;
	}

	public static String arrayToString(double[] arr) {
		String s = "";
		for (double i : arr)
			s += " " + i;
		return s + "\n";
	}

	public static String arrayToString(int[][] arr) {
		String s = "";

		for (int[] a : arr)
			s += "" + arrayToString(a);

		return s;
	}

	public static <E> String arrayToString(E[] arr) {
		String s = "";
		for (E e : arr)
			s += e + " -> ";

		return s;
	}
}