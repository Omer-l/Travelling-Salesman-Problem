package genetic;

import java.util.Random; //Used for the seed for the probability of mutation. That is, for each index in a gene.
import main.DataPoint; //The class that represents the cities. In this case, amino acids.

/**
 * This file contains the necessary functions for the genetic algorithm.
 * Begins with creating a generation, for which, each gene in the generation contains randomised indexes of
 * possible paths. A group of amino acids' indices will form a gene.
 * Chooses the best gene (the shortest path) from the generation of genes. This is called the parent.
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
     * @return an arbitrarily created population of genes, for which, any gene has no duplicate indexes.
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
        double currentGeneWithShortestPath = calculatePathDistance(generation[0]); // best of the best
        int currentBestGeneIndex = 0; //index of the best of the best is set to 0 for now.

        for (int geneIterator = 1; geneIterator < generation.length; geneIterator++) { //starts with 1st gene
            double sum = calculatePathDistance(generation[geneIterator]);

            if (sum < currentGeneWithShortestPath) { //sets shorter distance gene as new best gene.
                currentGeneWithShortestPath = sum;
                currentBestGeneIndex = geneIterator;
            }
        }

        return currentBestGeneIndex;
    }

    /**
     * This function swaps the given index with another random index.
     * @return gene with a modified digit at the given index.
     */
    public int[] getMutation(int[] offspring, int indexToMutate) {

        int randomIndexToSwapWith = (int) (Math.random() * numberOfAminoAcids); // index to swap with
        while (randomIndexToSwapWith == indexToMutate)
            randomIndexToSwapWith = (int) (Math.random() * numberOfAminoAcids);

        int tmp = offspring[indexToMutate];
        offspring[indexToMutate] = offspring[randomIndexToSwapWith];
        offspring[randomIndexToSwapWith] = tmp;
        return offspring;
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

    // ***********   getters/setters below. ************

    public DataPoint[] getAminoAcids() {
        return aminoAcids;
    }
}
