package main;

import  genetic.*; //to create instances of the GeneticAlgorithm and GeneticAlgorithmThreads
import static genetic.GeneticAlgorithmThread.getThreadWithMinimumPathGene; //for finding the best thread that contains the best gene.

// THE TABLE BELOW SHOWS MY RESULTS
//																																												MY GENETIC ALGORITHM's BEST
//- FILE: test1_2018 - PATH: [ 7 1 10 11 2 9 4 8 5 3 12 6 ] 																							   					-> DISTANCE: 273.85967307938483
//- FILE: test2_2018 - PATH: [ 9 3 5 12 4 2 11 7 1 13 6 14 8 10 ]	 																										-> DISTANCE: 661.494844616538
//- FILE: test3_2018 - PATH: [ 6 5 4 14 12 8 13 11 2 9 10 1 15 7 16 17 3 ] 																									-> DISTANCE: 126265.42652170827
//- ^FILE: test4_2018 - PATH: [ 22 1 38 27 34 9 18 4 31 24 14 37 25 30 39 8 2 40 10 12 42 20 21 17 5 41 3 33 28 7 35 13 36 23 29 11 26 16 15 19 6 32 ] 						-> DISTANCE: 188695.65436701436
//- FILE: test1_2019 - PATH: [ 7 9 11 10 4 3 2 5 8 6 1 ] 																													-> DISTANCE: 10605.096933318093
//- FILE: test2_2019 - PATH: [ 2 1 10 11 12 13 9 8 7 6 5 4 3 ] 																												-> DISTANCE: 196.00581416951627
//- FILE: test3_2019 - PATH: [ 1 5 10 11 12 9 14 8 3 6 4 2 7 15 13 ] 																										-> DISTANCE: 333.7959964703076
//- ^FILE: test4_2019 - PATH: [ 5 29 2 4 26 42 10 35 45 24 32 39 21 13 47 20 12 33 46 38 31 44 18 7 36 28 30 6 37 19 27 17 43 15 40 3 22 1 9 8 16 41 34 23 11 14 25 48 ] 	-> DISTANCE: 34964.13174220773
//- FILE: test1_2020 - PATH: [ 3 1 7 12 2 9 11 10 6 4 8 5] 																													-> DISTANCE: 463.95774842296623
//- FILE: test2_2020 - PATH: [ 10 3 13 4 2 12 7 8 1 14 9 5 6 11] 																											-> DISTANCE: 731.4567329318198
//- FILE: test3_2020 - PATH: [ 13 17 1 12 4 10 15 3 14 11 6 16 5 2 9 18 8 7] 																								-> DISTANCE: 135664.84173227008
//- ^FILE: test4_2020 - PATH: [ 24 27 21 19 13 15 31 12 18 3 1 25 29 14 28 20 11 32 9 6 23 2 17 5 30 4 22 7 16 26 10 8 ] 													-> DISTANCE: 613804.1893564754

/**
 * This file contains the best algorithm for the Travelling Salesman Problem
 * The file creates many threads. For each thread, there is a new instance of the genetic algorithm.
 * All threads/algorithms are stopped after 59 seconds. Then, the threads are all evaluated and the thread
 * with the best parent (also known as, the best path to all the cities) will be nominated.
 *
 * For instance, a 1000 threads means, 1000 genetic algorithms will be run. After 59 seconds, one thread
 * out of the 1000 threads will contain the best gene (path to cities).
 */
public class ApplicationBestAlgorithmRunner {
    //The file path can change when ApplicationAllAlgorithmsRunner.java runs this class.
    private static String absoluteFilePath = System.getProperty("user.dir") + "/Resources/trainProblem1.txt"; //absolute path to the file

    //static constants
    private final static long MAXIMUM_TIME = 10000; // a second before 60 seconds, to allow time for thread quitting and choosing the best thread.
    private final static long START_TIME_MS = System.currentTimeMillis(); // starts timer

    /**
     *  calls the function that runs the genetic algorithm threads
     *  Then, outputs the thread with the best gene (path)
     */
	public static void main(String[] args) {
        if(args.length > 0) // In case a different absolute file path is given.
            absoluteFilePath = args[0];

        GeneticAlgorithmThread threadWithBestGene = runGeneticAlgorithm();

        //calculate elapsed time
        long elapsedTimeMs = calculateElapsedTime();
        long elapsedTimeSeconds = convertMsToSeconds(elapsedTimeMs);
        //output elapsed time
        System.out.println(threadWithBestGene + " - elapsed time: " + elapsedTimeSeconds + " seconds (or more precisely: " + elapsedTimeMs + " milliseconds).");
	}

    //runs the genetic algorithm threads and returns the thread with the best gene.
	public static GeneticAlgorithmThread runGeneticAlgorithm() {
        final MyFileReader fileReader = new MyFileReader(absoluteFilePath); //reads in the data.
        final DataPoint[] aminoAcids = fileReader.getData(); //city objects, each city has an x and a y coordinate
        final int probabilityOfMutation = 15; //probability than a city will be mutated and swapped places
        final int sizeOfGeneration = 50; //size of each generation
        final int maximumGenerations = 1000; //maximum number of generations.
        final int numberOfThreads = 5000;

        GeneticAlgorithmThread[] geneticAlgorithmThreads = new GeneticAlgorithmThread[numberOfThreads];

        // run all threads
        for (int threadIterator = 0; threadIterator < geneticAlgorithmThreads.length && !passedMaximumTime(); threadIterator++) {
            //new instance of algorithm to run in the thread
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(sizeOfGeneration, probabilityOfMutation, aminoAcids);
            //new instance of thread that runs genetic algorithm and all of its generations.
            geneticAlgorithmThreads[threadIterator] = new GeneticAlgorithmThread(maximumGenerations, geneticAlgorithm);
            //run the instance of the thread containing the algorithm.
            geneticAlgorithmThreads[threadIterator].run();

            //condition to see if elapsed time is over 59 seconds.
            if (passedMaximumTime()) { // end this genetic algorithm search.
                for (int runningThreadsIterator = threadIterator; runningThreadsIterator >= 0; runningThreadsIterator--)
                    geneticAlgorithmThreads[runningThreadsIterator].setEndThreadLoop(true); // exit all threads so that null threads aren't evaluated when finding the best gene.
            }
        }

        return getThreadWithMinimumPathGene(geneticAlgorithmThreads);
	}

    /** Evaluates whether 59 seconds have passed since START_TIME.
     * After 59 seconds, this will return true, that means, all threads should end.
     * @return  true if 59 seconds has passed.
     */
    public static boolean passedMaximumTime() {
        long elapsedTime = calculateElapsedTime();
        return elapsedTime >= MAXIMUM_TIME;
    }

    //calculates the elapsed time so far
    public static long calculateElapsedTime() {
        return System.currentTimeMillis() - START_TIME_MS;
    }

    //converts milliseconds to seconds, useful for displaying the elapsed time at the end of the algorithm.
    public static long convertMsToSeconds(long milliseconds) {
        return milliseconds / 1000;
    }
}
