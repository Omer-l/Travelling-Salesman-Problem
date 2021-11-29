package main;

import  genetic.*; //to create instances of the GeneticAlgorithm and GeneticAlgorithmThreads
import static genetic.GeneticAlgorithmThread.getThreadWithMinimumPathGene; //for finding the best thread that contains the best gene.

/**
 * This file contains my best algorithm for the Travelling Salesman Problem
 * The file creates many threads. For each thread, there is a new instance of the genetic algorithm.
 * All threads/algorithms are stopped after 59 seconds. Then, the threads are all evaluated and the thread
 * with the best parent (also known as, the best path to all the cities) will be nominated.
 *
 * For instance, a 1000 threads means, 1000 genetic algorithms will be run. After 59 seconds, one thread
 * out of the 1000 threads will contain the best gene (path to cities).
 */
public class ApplicationBestAlgorithmRunner {
    //static constants
    private final static String ABSOLUTE_FILE_PATH = System.getProperty("user.dir") + "/Resources/test4_2018"; //absolute path to the file
    private final static long MAXIMUM_TIME = 59000; // a second before 60 seconds, to allow time for thread quitting and choosing the best thread.
    private final static long START_TIME_MS = System.currentTimeMillis(); // starts timer

    /**
     *  calls the function that runs the genetic algorithm threads
     *  Then, outputs the thread with the best gene (path)
     */
	public static void main(String[] args) {
        GeneticAlgorithmThread threadWithBestGene = runGeneticAlgorithm();

        //calculate elapsed time
        long elapsedTimeMs = calculateElapsedTime();
        long elapsedTimeSeconds = convertMsToSeconds(elapsedTimeMs);
        //output elapsed time
        System.out.println("BEST THREAD - " + threadWithBestGene + " - ELAPSED TIME: " + elapsedTimeSeconds + " seconds (or more precisely: " + elapsedTimeMs + " milliseconds).");
	}

    //runs the genetic algorithm threads and returns the thread with the best gene.
	public static GeneticAlgorithmThread runGeneticAlgorithm() {
        final MyFileReader fileReader = new MyFileReader(ABSOLUTE_FILE_PATH); //reads in the data.
        final DataPoint[] aminoAcids = fileReader.getData(); //city objects, each city has a x and a y coordinate
        final int probabilityOfMutation = 15; //probability than a city will be mutated and swapped places
        final int sizeOfGeneration = 50; //size of each generation
        final int maximumGenerations = 1000; //maximum number of generations.
        final int numberOfThreads = 5000;

        GeneticAlgorithmThread[] geneticAlgorithmThreads = new GeneticAlgorithmThread[numberOfThreads];

        // run all threads
        for (int threadIterator = 0; threadIterator < geneticAlgorithmThreads.length && !(passedMaximumTime()); threadIterator++) {
            //new instance of algorithm to run in the thread
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(sizeOfGeneration, probabilityOfMutation, aminoAcids);
            //new instance of thread that runs genetic algorithm and all of it's generations.
            geneticAlgorithmThreads[threadIterator] = new GeneticAlgorithmThread("THREAD " + threadIterator, maximumGenerations, geneticAlgorithm);
            //run the instance of the thread containing the algorithm.
            geneticAlgorithmThreads[threadIterator].run(); // TEST PROBLEMS 2020 WILL NOT WORK BECAUSE OF GETDATA() FUNCTION

            //condition to see if elapsed time is over 59 seconds.
            if (passedMaximumTime()) { // end this genetic algorithm search.
                for (int runningThreadsIterator = threadIterator; runningThreadsIterator >= 0; runningThreadsIterator--)
                    geneticAlgorithmThreads[runningThreadsIterator].setExit(true); // exit all threads so that null threads aren't evaluated when finding the best gene.
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
