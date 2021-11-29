package genetic;

import java.util.Random; //Used for the seed for the probability of mutation. That is, for each index in a gene.
import main.DataPoint; //The class that represents the cities. In this case, amino acids.

// THE TABLE BELOW SHOWS MY RESULTS
//																																												MY GENETIC ALGORITHM's BEST
//- FILE: test1_2018 - PATH: [ 7 1 10 11 2 9 4 8 5 3 12 6 ] 																							   					-> DISTANCE: 273.85967307938483
//- FILE: test2_2018 - PATH: [ 9 3 5 12 4 2 11 7 1 13 6 14 8 10 ]	 																										-> DISTANCE: 661.494844616538
//- FILE: test3_2018 - PATH: [ 6 5 4 14 12 8 13 11 2 9 10 1 15 7 16 17 3 ] 																									-> DISTANCE: 126265.42652170827
//- ^FILE: test4_2018 - PATH: [ 22 1 38 27 34 9 18 4 31 24 14 37 25 30 39 8 2 40 10 12 42 20 21 17 5 41 3 33 28 7 35 13 36 23 29 11 26 16 15 19 6 32 ] 						-> DISTANCE: 196917.46994668932
//- FILE: test1_2019 - PATH: [ 7 9 11 10 4 3 2 5 8 6 1 ] 																													-> DISTANCE: 10605.096933318093
//- FILE: test2_2019 - PATH: [ 2 1 10 11 12 13 9 8 7 6 5 4 3 ] 																												-> DISTANCE: 196.00581416951627
//- FILE: test3_2019 - PATH: [ 1 5 10 11 12 9 14 8 3 6 4 2 7 15 13 ] 																										-> DISTANCE: 333.7959964703076
//- ^FILE: test4_2019 - PATH: [ 5 29 2 4 26 42 10 35 45 24 32 39 21 13 47 20 12 33 46 38 31 44 18 7 36 28 30 6 37 19 27 17 43 15 40 3 22 1 9 8 16 41 34 23 11 14 25 48 ] 	-> DISTANCE: 34964.13174220773
//- FILE: test1_2020 - PATH: [ 3 1 7 12 2 9 11 10 6 4 8 5] 																													-> DISTANCE: 463.95774842296623
//- FILE: test2_2020 - PATH: [ 10 3 13 4 2 12 7 8 1 14 9 5 6 11] 																											-> DISTANCE: 731.4567329318198
//- FILE: test3_2020 - PATH: [ 13 17 1 12 4 10 15 3 14 11 6 16 5 2 9 18 8 7] 																								-> DISTANCE: 135664.84173227008
//- ^FILE: test4_2020 - PATH: [ 24 27 21 19 13 15 31 12 18 3 1 25 29 14 28 20 11 32 9 6 23 2 17 5 30 4 22 7 16 26 10 8 ] 													-> DISTANCE: 613804.1893564754

/**
 * This file contains the necessary functions for the genetic algorithm.
 * Begins with creating a generation, for which, each gene in the generation contains randomised indexes of
 * possible paths. A group of amino acids' indices will form a gene.
 * Chooses the best gene from the generation of genes. This is called the parent.
 * Then this algorithm produces offsprings from one parent.
 * After producing an offspring, each index of the offspring has a probability of being mutated.
 * The mutation for this genetic algorithms modifies the path by swapping the indexes between two cities. (One
 * is the mutated index, and the other is a randomised index.)
 */
public class GeneticAlgorithm {

    //genetic algorithm constants and variables
    private final int sizeOfGeneration; // population size
    private final int probabilityOfMutation; // PROBABILITY_OF_MUTATION / PERCENTAGE chance of getting mutated
    private final int percentage = 100; //seed for RANDOM_FOR_PROBABILITY_OF_MUTATION_SEED
    private final DataPoint[] aminoAcids; //data points in the file, these are the amino acids, the cities.
    private final int numberOfAminoAcids; // number of amino acids per gene
    private final Random probabilityOfMutationSeed = new Random(); //seed for the probability of mutation.

    //necessary inputs for the algorithm
    public GeneticAlgorithm(int sizeOfGeneration, int probabilityOfMutation, DataPoint[] aminoAcids) {
        this.sizeOfGeneration = sizeOfGeneration;
        this.probabilityOfMutation = probabilityOfMutation;
        this.aminoAcids = aminoAcids;
        this.numberOfAminoAcids = aminoAcids.length;
    }

    /**
     * Generates a population of genes. Each index of a gene is an index of a city/amino acid.
     * @return an arbitrarily created population of genes. For any gene has no duplicate indexes.
     */
    public int[][] initialisePopulationOfGenes() {
        int[][] population = new int[sizeOfGeneration][numberOfAminoAcids]; // SIZE number of genes in the population

        for (int geneIterator = 0; geneIterator < sizeOfGeneration; geneIterator++) {
            boolean[] indexChosen = new boolean[numberOfAminoAcids];
            for (int aminoAcidIndexIterator = 0; aminoAcidIndexIterator < numberOfAminoAcids; aminoAcidIndexIterator++) {
                int randomNumber = (int) (Math.random() * numberOfAminoAcids);

                while (indexChosen[randomNumber]) { // ensures duplicate indexes are not chosen
                    randomNumber = (int) (Math.random() * numberOfAminoAcids); //seed create it for random
                }
                population[geneIterator][aminoAcidIndexIterator] = randomNumber;
                indexChosen[randomNumber] = true; // this point index is now added
            }
        }

        return population;
    }

    //    simple evaluation function that returns the distance of a path.
    public double calculatePathDistance(int[] path) {
        double totalDistance = 0;

        for (int aminoAcidIndexIterator = 1; aminoAcidIndexIterator < aminoAcids.length; aminoAcidIndexIterator++) {
            int index1 = path[aminoAcidIndexIterator - 1];
            int index2 = path[aminoAcidIndexIterator];
            DataPoint point1 = aminoAcids[index1];
            DataPoint point2 = aminoAcids[index2];
            totalDistance += point1.getDistanceTo(point2);
        }
        // Get endPoint to startPoint to create a cycle
        int endPointIndex = path[aminoAcids.length - 1];
        int startPointIndex = path[0];
        DataPoint endPoint = aminoAcids[endPointIndex];
        DataPoint startPoint = aminoAcids[startPointIndex];

        totalDistance += endPoint.getDistanceTo(startPoint);

        return totalDistance;
    }

    /**
     * gets the index to crossover
     * @return a random index to crossover
     */
    public int getCrossOverIndex() {
        return (int) (Math.random() * (numberOfAminoAcids + 1));
    }

    /**
     * This function gets the result from flipping one parent's parts from the crossover index.
     *
     * @param parent            the best parent
     * @param crossoverIndex    the index to exchange parts of the parent from
     * @return                  a new gene. This gene's parts are from the same parent but flipped from the crossover index.
     */
    public int[] getResultOfCrossover(int[] parent, int crossoverIndex) {
        int[] offspring = new int[parent.length];
        int[] part1 = new int[crossoverIndex];
        int[] part2 = new int[parent.length - crossoverIndex];

        // splitting into parts
        System.arraycopy(parent, 0, part1, 0, crossoverIndex);
        System.arraycopy(parent, crossoverIndex, part2, 0, parent.length - crossoverIndex);

        // adding it to offspring, AKA FLIPPING THE POSITION OF THE PARTS
        System.arraycopy(part2, 0, offspring, 0, part2.length);
        System.arraycopy(part1, 0, offspring, part2.length, parent.length - part2.length);

        return offspring;
    }

    /**
     * This function returns an offspring from two parents.
     * @param parent best parent
     * @return an offspring from the crossover parts of both parents
     */
    public int[] getOffspring(int[] parent) {
        int crossoverIndex = getCrossOverIndex();

        int[] offspring = getResultOfCrossover(parent, crossoverIndex);
        offspring = mutateOffspring(offspring);

        return offspring;
    }

    /**
     * This function generates the next population of genes using one parent
     * @param parent    the best parent to generate offsprings from.
     * @return          a generation of genes from one parents.
     */
    public int[][] generateNextGenerationUsingParent(int[] parent) {
        int[][] nextGeneration = new int[sizeOfGeneration][numberOfAminoAcids];

        for (int geneIterator = 0; geneIterator < sizeOfGeneration; geneIterator++) {
            nextGeneration[geneIterator] = getOffspring(parent);
        }

        return nextGeneration;
    }

    /**
     * This function gets the best gene's index in a population
     * @param generation    a generation of genes
     * @return              the two best genes to produce an offspring from.
     */
    public int getBestGeneIndex(int[][] generation) {
        double currentMaximumSum1 = calculatePathDistance(generation[0]); // best of the best
        int currentBestGeneIndex1 = 0;

        for (int geneIterator = 0; geneIterator < generation.length; geneIterator++) {
            double sum = calculatePathDistance(generation[geneIterator]);

            if (sum < currentMaximumSum1) {
                currentMaximumSum1 = sum;
                currentBestGeneIndex1 = geneIterator;
            }
        }

        return currentBestGeneIndex1;
    }

    /**
     * This function replaces the given index with a random number 0-9.
     * @return gene with a modified digit at the given index.
     */
    public int[] getMutation(int[] offspring, int indexToMutate) {

        int randomIndexToSwapWith = (int) (Math.random() * numberOfAminoAcids); // index to swap with
        while (randomIndexToSwapWith == indexToMutate)
            randomIndexToSwapWith = (int) (Math.random() * numberOfAminoAcids);

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
    public boolean mutate() {
        return (probabilityOfMutationSeed.nextInt(percentage) + 1) == probabilityOfMutation;
    }

    /**
     * @param offspring gene to mutate
     * @return possibly a mutated/different gene.
     */
    public int[] mutateOffspring(int[] offspring) {

        // for each digit in the gene, get probability to mutate
        for (int aminoAcidIndexIterator = 0; aminoAcidIndexIterator < offspring.length; aminoAcidIndexIterator++)
            if (mutate())
                offspring = getMutation(offspring, aminoAcidIndexIterator);

        return offspring;
    }

    /**
     * Adds all elements of an integer array to a single line string
     * @param arr   an array to print
     * @return      a string containing a single line of integer type elements
     */
    public static String arrayToString(int[] arr) {
        String stringOfElements = "";
        for (int arrayIterator : arr)
            stringOfElements += " " + (arrayIterator + 1);
        return stringOfElements;
    }

    /**
     * Adds all elements of a 2D String array to a single line string
     * @param arr 2D integer array to print
     * @return a string containing a single line of integer type elements
     */
    public String arrayToString(int[][] arr) {
        String stringOfElements = "";

        for (int[] arrayIterator : arr)
            stringOfElements += "" + arrayToString(arrayIterator);

        return stringOfElements;
    }

    /**
     * Adds all elements of a 2D String array to a single line string
     * @param arr       an array of any type to print
     * @param <T>       type of array (can be any type i.e. DataPoint)
     * @return          a string containing a single line of any type of elements
     */
    public <T> String arrayToString(T[] arr) {
        String stringOfElements = "";
        for (T wildCardArrayIterator : arr)
            stringOfElements += wildCardArrayIterator + " -> ";

        return stringOfElements;
    }

    // ***********   getters/setters below. ************

    public DataPoint[] getAminoAcids() {
        return aminoAcids;
    }
}