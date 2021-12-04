package main;

import breadthFirstSearch.BreadthFirstSearch; //Breadth first search approach
import dijkstra.Dijkstra; //Dijkstra's approach
import nnAproach.HeuristicNearestNeighbours; //Heuristic nearest neighbours approach
import permutation.Permutation; //Permutation approach

/**
 * This class presents all the algorithms I have attempted thus far. The maximum time for each algorithm is 59 seconds
 * Here is the list of algorithms attempted and the main function will run them in order.
 *      Permutation
 *      Heuristic nearest neighbours
 *      Breadth First Search
 *      Dijkstra's
 *      Genetic Algorithm.
 */
public class ApplicationAllAlgorithmsRunner {
    //static constants
    private final static String ABSOLUTE_FILE_PATH = System.getProperty("user.dir") + "/Resources/trainProblem3.txt"; //absolute path to the file
    private final static long MAXIMUM_TIME = 59000; // a second before 60 seconds, to allow time for thread quitting and choosing the best thread.
    private final static long START_TIME_MS = System.currentTimeMillis(); // starts timer
    private static final MyFileReader MY_FILE_READER = new MyFileReader(ABSOLUTE_FILE_PATH); //reads in the data.
    private static final DataPoint[] CITIES = MY_FILE_READER.getData(); //city objects, each city has an x and a y coordinate

    public static void main(String[] args) {
        permutation();
        heuristicNearestNeighbours();
        breadthFirstSearch();
        dijkstras();

        //BEST ALGORITHM
        System.out.println("///////////////\n" +
                "BEST ALGORITHM:");
        String[] bestAlgorithmClassArgs = {ABSOLUTE_FILE_PATH};
        ApplicationBestAlgorithmRunner.main(bestAlgorithmClassArgs); //run best algorithm.
        System.out.println("///////////////");
    }

    //gets the results for permutation method and outputs the result
    private static void permutation() {
        Permutation permutation = new Permutation(CITIES);
        permutation.runPermutation();
        //output results
        System.out.println(permutation);
    }

    //gets results from heuristic nearest neighbours and outputs
    private static void heuristicNearestNeighbours() {
        HeuristicNearestNeighbours heuristicNearestNeighbours = new HeuristicNearestNeighbours(CITIES);
        heuristicNearestNeighbours.runHeuristicNearestNeighbours();
        //outputs results
        System.out.println(heuristicNearestNeighbours);
    }

    //gets the results for breadth first search and outputs the result
    private static void breadthFirstSearch() {
        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(CITIES);
        int startingIndex = 0;
        breadthFirstSearch.runBreadthFirstSearch(startingIndex);
        //output the results.
        System.out.println(breadthFirstSearch);
    }

    //gets results from dijkstras and outputs the result
    private static void dijkstras() {
        Dijkstra dijkstra = new Dijkstra(CITIES);
        int startingPath = 0;
        dijkstra.runDijkstras(startingPath);
        //outputs results
        System.out.println(dijkstra);
    }
}
