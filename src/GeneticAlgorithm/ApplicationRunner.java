package GeneticAlgorithm;

import java.io.File; //used to get the data from the file
import java.io.FileNotFoundException; //error in the case of a file is not found.
import java.util.Random; //used for the seed of probability of mutation for an index in a gene.
import java.util.Scanner; //used to take in the data from the data file

/**
 * This is the main file. This file runs threads that use genetic algorithm functions in this file.
 * Begins with creating a generation, for which, each gene in the generation contains randomised indexes of
 * possible paths.
 * Chooses the best gene from the generation of genes. This is called the parent.
 * Then this algorithm produces offsprings from one parent.
 * After producing an offspring, each index of the offspring has a probability of being mutated.
 * The mutation for this genetic algorithms modifies the path by swapping the indexes between two cities (One
 * is the mutated index, and the other is a randomised index
 */
public class ApplicationRunner {
    //file and directory path constants
    private static File file;
    private final String ROOT_DIRECTORY_OF_FILE = System.getProperty("user.dir") + "/Resources/";
    //genetic algorithm constants and variables
    private final int SIZE_OF_GENERATION = 50; // population size
    private final int PROBABILITY_OF_MUTATION = 15; // PROBABILITY_OF_MUTATION / PERCENTAGE chance of getting mutated
    private final int PERCENTAGE = 100; //seed for RANDOM_FOR_PROBABILITY_OF_MUTATION_SEED
    //	protected final static int NUMBER_OF_THREADS = 256; DELETE POSSIBLY?
    private static int NUMBER_OF_THREADS = 256;
    private AminoAcid[] aminoAcids;
    private int MAXIMUM_TIME = 59; // a second before 60, to allow for thread quitting and choosing the best thread.
    private int numberOfAminoAcids; // number of Amino Acids per gene gene -> population -> generation
    private final Random RANDOM_FOR_PROBABILITY_OF_MUTATION_SEED = new Random();

    //error message constant(s)
    private final static String ERR_MSG_FILE_NOT_FOUND = "\nFILE NOT FOUND";

    public ApplicationRunner(String fileName) {
        this.file = new File(ROOT_DIRECTORY_OF_FILE + fileName + "/"); // path for data //create constants for user.dir etc..
    }
    // MY BEST
    // 282.22963288784723 	- FILE: test1_2018 - PATH: [ 7 1 10 11 2 9 4 8 5 3 12 6 ] -> DISTANCE: 273.85967307938483
    // 702.0589801285125 	- FILE: test2_2018 - PATH: [ 9 3 5 12 4 2 11 7 1 13 6 14 8 10 ] -> DISTANCE: 661.494844616538
    // 138753.37233384346 	- FILE: test3_2018 - PATH: [ 6 5 4 14 12 8 13 11 2 9 10 1 15 7 16 17 3 ] -> DISTANCE: 126265.42652170827
    // 186854.89928444562 	- ^FILE: test4_2018 - PATH: [ 22 1 38 27 34 9 18 4 31 24 14 37 25 30 39 8 2 40 10 12 42 20 21 17 5 41 3 33 28 7 35 13 36 23 29 11 26 16 15 19 6 32 ] -> DISTANCE: 196917.46994668932
    // 10605.096933318093 	- FILE: test1_2019 - PATH: [ 7 9 11 10 4 3 2 5 8 6 1 ] -> DISTANCE: 10605.096933318093
    // 215.16686897202064 	- FILE: test2_2019 - PATH: [ 2 1 10 11 12 13 9 8 7 6 5 4 3 ] -> DISTANCE: 196.00581416951627
    // 336.6810973535036 	- FILE: test3_2019 - PATH: [ 1 5 10 11 12 9 14 8 3 6 4 2 7 15 13 ] -> DISTANCE: 333.7959964703076
    // 35942.00740697837 	- ^FILE: test4_2019 - PATH: [ 5 29 2 4 26 42 10 35 45 24 32 39 21 13 47 20 12 33 46 38 31 44 18 7 36 28 30 6 37 19 27 17 43 15 40 3 22 1 9 8 16 41 34 23 11 14 25 48 ] -> DISTANCE: 34964.13174220773
    // SAME 			 	- FILE: test1_2020 - PATH: [ 3 1 7 12 2 9 11 10 6 4 8 5] -> DISTANCE: 463.95774842296623
    // SAME 			 	- FILE: test2_2020 - PATH: [ 10 3 13 4 2 12 7 8 1 14 9 5 6 11] -> DISTANCE: 731.4567329318198
    // 						- FILE: test3_2020 - PATH: [ 13 17 1 12 4 10 15 3 14 11 6 16 5 2 9 18 8 7] -> DISTANCE: 135664.84173227008
    // 						- ^FILE: test4_2020 - PATH: [ 24 27 21 19 13 15 31 12 18 3 1 25 29 14 28 20 11 32 9 6 23 2 17 5 30 4 22 7 16 26 10 8 ] -> DISTANCE: 631597.4299742716

    /**
     * This function runs the genetic algorithm threads.
     *
     * @param args array of arguments that can be made for this file before running it.
     */
    public static void main(String[] args) {

        long startTimeForFile = System.currentTimeMillis(); // starts timer

//				maximum number of available threads in the JVM
        GeneticAlgorithmThread[] threads = new GeneticAlgorithmThread[NUMBER_OF_THREADS];

        long elapsedTimeSoFar = (System.currentTimeMillis() - startTimeForFile) / 1000;
        // run threads
        for (int threadIterator = 0; threadIterator < threads.length || elapsedTimeSoFar >= 59; threadIterator++) {
            threads[threadIterator] = new GeneticAlgorithmThread("THREAD " + threadIterator, file.getName());
            threads[threadIterator].run(); // TEST PROBLEMS 2020 WILL NOT WORK BECAUSE OF GETDATA FUNCTION

            if (elapsedTimeSoFar >= 59) { // end this genetic algorithm search.
                for (int runningThreadsIterator = threadIterator; runningThreadsIterator >= 0; runningThreadsIterator--)
                    threads[runningThreadsIterator].setExit(true); // exit all threads
            }
            elapsedTimeSoFar = (System.currentTimeMillis() - startTimeForFile) / 1000;
        }
        GeneticAlgorithmThread bestThread = getThreadWithMinimumPathGene(threads);
        long elapsedTime = (System.currentTimeMillis() - startTimeForFile) / 1000;
        System.out.println("BEST THREAD - " + bestThread + " - ELAPSED TIME: " + elapsedTime + " seconds");
    }


    /**
     * This function goes through each thread that is run and chooses the thread with the best path for the TSP
     *
     * @param threads array of threads to be evaluated
     * @return thread that contains the minimum distance and path.
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
     * This function gets the AminoAcids/data points that each line the datafile contains. These aminoAcid will form genes
     *
     * @return an array of aminoAcids, every aminoAcid contains a data point (x, y).
     */
    public AminoAcid[] getData() {
        // get number of lines in the data file, for which, each line there is a data point.
        int numberOfDataPoints = getNumberOfTrainingSets();

        AminoAcid[] aminoAcids = new AminoAcid[numberOfDataPoints]; // An array of amino acids per gene.

        int pointNumber = 0; // current index in array of Vertexs

        try {
            Scanner fileInput = new Scanner(file); // Scanner for reading the data file.
            while (fileInput.hasNext()) {
                String[] splittedLine = fileInput.nextLine().split("\\s+");
                int dataX = Integer.parseInt(splittedLine[1].trim()); // adds point x
                int dataY = Integer.parseInt(splittedLine[2].trim()); // add point y
                aminoAcids[pointNumber] = new AminoAcid(dataX, dataY); // creates an instance of the class Vertex wit dataX and dataY.
                pointNumber++;
            }
            fileInput.close(); // close scanners
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(ERR_MSG_FILE_NOT_FOUND);
            fileNotFoundException.printStackTrace();
        }

        return aminoAcids;
    }

    /**
     * counts the number of lines (data points) in the file
     *
     * @return the number of lines in the file, which is the number of data points.
     */
    public int getNumberOfTrainingSets() {
        int trainingSetsCounter = 0; // counter

        try {
            Scanner fileInput = new Scanner(file);

            while (fileInput.hasNext()) {
                fileInput.nextLine(); //does not need to initialise a variable as it is just counting.
                trainingSetsCounter++;
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(ERR_MSG_FILE_NOT_FOUND);
            fileNotFoundException.printStackTrace();
        }

        return trainingSetsCounter;
    }

    /**
     * This generates a population of genes. Each index of a gene is the index of a city.
     *
     * @return an arbitrarily created population of genes. Each gene has no duplicate indexes
     */
    public int[][] initialisePopulationOfGenes() {
        int[][] population = new int[SIZE_OF_GENERATION][numberOfAminoAcids]; // SIZE number of genes in the population

        for (int geneIterator = 0; geneIterator < SIZE_OF_GENERATION; geneIterator++) {
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
            AminoAcid point1 = aminoAcids[index1];
            AminoAcid point2 = aminoAcids[index2];
            totalDistance += point1.getDistanceTo(point2);
        }
        // Get endPoint to startPoint to create a cycle
        int endPointIndex = path[aminoAcids.length - 1];
        int startPointIndex = path[0];
        AminoAcid endPoint = aminoAcids[endPointIndex];
        AminoAcid startPoint = aminoAcids[startPointIndex];

        totalDistance += endPoint.getDistanceTo(startPoint);

        return totalDistance;
    }

    /**
     * This function gets the index to crossover
     *
     * @return a random index to crossover
     */
    public int getCrossOverIndex() {
        return (int) (Math.random() * (numberOfAminoAcids + 1));
    }

    /**
     * This function gets the result from flipping one parent's parts from the crossover index.
     *
     * @param parent         best parent
     * @param crossoverIndex index to exchange from
     * @return a new gene. This gene's parts are from the same parent but flipped from the crossover index.
     */
    public static int[] getResultOfCrossover(int[] parent, int crossoverIndex) {
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
     *
     * @param parent best parent
     * @return an offspring from the crossover parts of both parents
     */
    public int[] getOffspring(int[] parent) {
        //pre and post guards. delete?
        int crossoverIndex = getCrossOverIndex();

        int[] offspring = getResultOfCrossover(parent, crossoverIndex);
        offspring = mutateOffspring(offspring);

        return offspring;
    }

    /**
     * This function generates the next population of genes using one parent
     *
     * @param parent best parent
     * @return a generation of genes from one parents.
     */
    public int[][] generateNextGenerationUsingParent(int[] parent) {
        int[][] nextGeneration = new int[SIZE_OF_GENERATION][numberOfAminoAcids];

        for (int geneIterator = 0; geneIterator < SIZE_OF_GENERATION; geneIterator++) {
            nextGeneration[geneIterator] = getOffspring(parent);
        }

        return nextGeneration;
    }

    /**
     * This function gets the best gene's index in a population,
     *
     * @param generation a generation of genes
     * @return the two best genes to produce an offspring from.
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
     *
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
        return (int) (RANDOM_FOR_PROBABILITY_OF_MUTATION_SEED.nextInt(PERCENTAGE) + 1) == PROBABILITY_OF_MUTATION;
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

    public boolean maximumTime(long elapsedTimeSoFar) {
        return elapsedTimeSoFar >= MAXIMUM_TIME;
    }

    /**
     * adds all elements of an integer array to a single line string
     *
     * @param arr integer array to print
     * @return a string containing a single line of integer type elements
     */
    public static String arrayToString(int[] arr) {
        String stringOfElements = "";
        for (int arrayIterator : arr)
            stringOfElements += " " + (arrayIterator + 1);
        return stringOfElements;
    }

    /**
     * adds all elements of a double array to a single line string
     *
     * @param arr double array to print
     * @return a string containing a single line of double type elements
     */
    public static String arrayToString(double[] arr) {
        String stringOfElements = "";
        for (double arrayIterator : arr)
            stringOfElements += " " + arrayIterator;
        return stringOfElements + "\n";
    }

    /**
     * adds all elements of a 2D String array to a single line string
     *
     * @param arr 2D integer array to print
     * @return a string containing a single line of integer type elements
     */
    public static String arrayToString(int[][] arr) {
        String stringOfElements = "";

        for (int[] arrayIterator : arr)
            stringOfElements += "" + arrayToString(arrayIterator);

        return stringOfElements;
    }

    /**
     * adds all elements of a 2D String array to a single line string
     *
     * @param arr    array of any type to print
     * @param <Type> type of array (can be any type i.e. AminoAcid)
     * @return a string containing a single line of any type of elements
     */
    public static <Type> String arrayToString(Type[] arr) {
        String stringOfElements = "";
        for (Type wildCardArrayIterator : arr)
            stringOfElements += wildCardArrayIterator + " -> ";

        return stringOfElements;
    }

    public AminoAcid[] getAminoAcids() {
        return aminoAcids;
    }

    public void setAminoAcids(AminoAcid[] aminoAcids) {
        this.aminoAcids = aminoAcids;
    }

    public int getNumberOfAminoAcids() {
        return numberOfAminoAcids;
    }

    public void setNumberOfAminoAcids(int numberOfAminoAcids) {
        this.numberOfAminoAcids = numberOfAminoAcids;
    }
}