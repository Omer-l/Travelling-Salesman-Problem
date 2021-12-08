package main;

import breadthFirstSearch.BreadthFirstSearch; //Breadth first search approach
import dijkstra.Dijkstra; //Dijkstra's approach
import nnAproach.HeuristicNearestNeighbours; //Heuristic nearest neighbours approach
import permutation.Permutation; //Permutation approach

import java.util.Scanner; //for user input to, run the next algorithm.

/**
 * This class presents all the algorithms I have attempted thus far.
 * Here is the list of algorithms attempted and the main function will run them in order.
 * Permutation
 * Heuristic Nearest Neighbour
 * Breadth First Search
 * Dijkstra's
 * Genetic Algorithm.
 */
public class ApplicationAllAlgorithmsRunner {
    //static constants
    private final static String ABSOLUTE_FILE_PATH = System.getProperty("user.dir") + "/Resources/test3_2020.txt"; //absolute path to the file
    private static final MyFileReader MY_FILE_READER = new MyFileReader(ABSOLUTE_FILE_PATH); //reads in the data.
    private static final DataPoint[] CITIES = MY_FILE_READER.getData(); //city objects, each city has an x and a y coordinate
    private static final String OUT_OF_MEMORY_ERR = "Too much memory used";
    private static final String NEGATIVE_ARRAY_SIZE_ERR = "The array size became too large";

    public static void main(String[] args) {
        permutation();
        promptToContinue("Heuristic Nearest Neighbour");
        heuristicNearestNeighbours();
        promptToContinue("Breadth First Search");
        breadthFirstSearch();
        promptToContinue("Dijkstra's");
        dijkstras();

        //My best algorithm below.
        promptToContinue("Genetic");
        System.out.println("///////////////\n" +
                "RUNNING BEST ALGORITHM:");
        String[] bestAlgorithmClassArgs = {ABSOLUTE_FILE_PATH};
        ApplicationBestAlgorithmRunner.main(bestAlgorithmClassArgs); //run the best algorithm.
        System.out.println("///////////////");
    }

    /**
     * Prompts the user to enter any key to continue to the given next algorithm.
     *
     * @param nextAlgorithmName is the next algorithm's name
     */
    private static void promptToContinue(String nextAlgorithmName) {
        Scanner inputToContinue = new Scanner(System.in);
        System.out.print("\nPress any key to continue to " + nextAlgorithmName + " algorithm...");
        inputToContinue.nextLine(); //only benefit is to pause the program.
    }

    //gets the shortest distance path from the permutation method and outputs the result
    private static void permutation() {
        try {
            System.out.println("RUNNING PERMUTATION...");
            Permutation permutation = new Permutation(CITIES);
            permutation.runPermutation();
            //output results
            System.out.println(permutation);
        } catch (NegativeArraySizeException negativeArraySizeException) {
            System.out.println(NEGATIVE_ARRAY_SIZE_ERR + " for the permutation algorithm.");
        }
    }

    //gets the shortest distance path from the heuristic nearest neighbour approach and outputs the result
    private static void heuristicNearestNeighbours() {
        try {
            HeuristicNearestNeighbours heuristicNearestNeighbours = new HeuristicNearestNeighbours(CITIES);
            heuristicNearestNeighbours.runHeuristicNearestNeighbours();
            //outputs results
            System.out.println(heuristicNearestNeighbours);
        } catch (OutOfMemoryError outOfMemoryError) {
            System.out.println(OUT_OF_MEMORY_ERR + " by the heuristic nearest neighbour algorithm");
        }
    }

    //gets the shortest distance path from breadth first search and outputs the result
    private static void breadthFirstSearch() {
        try {
            System.out.println("RUNNING BREADTH FIRST SEARCH...");
            BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(CITIES);
            int startingIndex = 0;
            breadthFirstSearch.runBreadthFirstSearch(startingIndex);
            //output the results.
            System.out.println(breadthFirstSearch);
        } catch (OutOfMemoryError outOfMemoryError) {
            System.out.println(OUT_OF_MEMORY_ERR + " by the breadth first search algorithm");
        }
    }

    //gets the shortest distance path from Dijkstra's and outputs the result
    private static void dijkstras() {
        try {
            System.out.println("RUNNING DIJKSTRA'S (End program and go to and run ApplicationBestAlgorithmRunner.java if this is taking too long)");
            Dijkstra dijkstra = new Dijkstra(CITIES);
            int startingPath = 0;
            dijkstra.runDijkstras(startingPath);
            //outputs results
            System.out.println(dijkstra);
        } catch (OutOfMemoryError outOfMemoryError) {
            System.out.println(OUT_OF_MEMORY_ERR + " by the Dijkstra's algorithm");
        }
    }
}
