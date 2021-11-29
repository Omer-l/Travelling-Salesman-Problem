package main;

import genetic.*;

import static genetic.GeneticAlgorithmThread.getThreadWithMinimumPathGene; //for finding the best thread that contains the best gene.

public class ApplicationBestAlgorithmRunner {
    //static constants
    private final static String ABSOLUTE_FILE_PATH = System.getProperty("user.dir") + "/Resources/test4_2020"; //absolute path for the file
    private final static long MAXIMUM_TIME = 59000; // a second before 60 seconds, to allow time for thread quitting and choosing the best thread.
    private final static long START_TIME_MS = System.currentTimeMillis(); // starts timer

    /**
     * This function runs the genetic algorithm threads.
     */
	public static void main(String[] args) {
        GeneticAlgorithmThread threadWithBestGene = runGeneticAlgorithm();
        long elapsedTimeMs = calculateElapsedTime();
        long elapsedTimeSeconds = convertMsToSeconds(elapsedTimeMs);
        System.out.println("BEST THREAD - " + threadWithBestGene + " - ELAPSED TIME: " + elapsedTimeSeconds + " seconds");
	}

    //runs the genetic algorithm threads and returns the thread with the best gene.
	public static GeneticAlgorithmThread runGeneticAlgorithm() {
		int numberOfThreads = 256;
        final MyFileReader fileReader = new MyFileReader(ABSOLUTE_FILE_PATH);
        final DataPoint[] aminoAcids = fileReader.getData();
        final int probabilityOfMutation = 15;
        final int sizeOfGeneration = 50;
        final int maximumGenerations = 10000;

//		maximum number of available threads in the JVM
        GeneticAlgorithmThread[] geneticAlgorithmThreads = new GeneticAlgorithmThread[numberOfThreads];

        // run threads
        for (int threadIterator = 0; threadIterator < geneticAlgorithmThreads.length || !(passedMaximumTime()); threadIterator++) {
            //new instance of algorithm
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(sizeOfGeneration, probabilityOfMutation, aminoAcids);
            //new instance of thread that runs algorithm
            geneticAlgorithmThreads[threadIterator] = new GeneticAlgorithmThread("THREAD " + threadIterator, maximumGenerations, geneticAlgorithm);
            //run the instance of the thread containing the algorithm.
            geneticAlgorithmThreads[threadIterator].run(); // TEST PROBLEMS 2020 WILL NOT WORK BECAUSE OF GETDATA() FUNCTION

            //condition to see if elapsed time is over 59 seconds.
            if (passedMaximumTime()) { // end this genetic algorithm search.
                System.out.println("AT MAX TIME NOW: " + calculateElapsedTime());
                for (int runningThreadsIterator = threadIterator; runningThreadsIterator >= 0; runningThreadsIterator--)
                    geneticAlgorithmThreads[runningThreadsIterator].setExit(true); // exit all threads
            }
        }

        return getThreadWithMinimumPathGene(geneticAlgorithmThreads);
	}

    //evaluates whether 59 seconds have passed. After 59 seconds, this will return true, that means, the algorithm should end.
    public static boolean passedMaximumTime() {
        long elapsedtime = calculateElapsedTime();
        return elapsedtime >= MAXIMUM_TIME;
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
