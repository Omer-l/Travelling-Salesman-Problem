package main;

import breadthFirstSearch.BreadthFirstSearch; //breadth first search approach
import dijkstra.Dijkstra;
import nnAproach.HeuristicNearestNeighbours;
import permutation.Permutation; //permutation approach

public class ApplicationAllAlgorithmsRunner {
    //static constants
    private final static String ABSOLUTE_FILE_PATH = System.getProperty("user.dir") + "/Resources/test1_2020.txt"; //absolute path to the file
    private final static long MAXIMUM_TIME = 59000; // a second before 60 seconds, to allow time for thread quitting and choosing the best thread.
    private final static long START_TIME_MS = System.currentTimeMillis(); // starts timer
    private static final MyFileReader MY_FILE_READER = new MyFileReader(ABSOLUTE_FILE_PATH); //reads in the data.
    private static final DataPoint[] CITIES = MY_FILE_READER.getData(); //city objects, each city has a x and a y coordinate

    public static void main(String[] args) {
//        breadthFirstSearch();
//        permutation();
//        dijkstras();
        heuristicNearestNeighbours();
    }

    //gets the results for breadth first search and outputs it.
    private static void breadthFirstSearch() {
        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(CITIES);
        int startingIndex = 0;
        breadthFirstSearch.runBreadthFirstSearch(startingIndex);
        //output the results.
        System.out.println(breadthFirstSearch);
    }

    //gets the results for permutation method and outputs it.
    private static void permutation() {
        Permutation permutation = new Permutation(CITIES);
        permutation.runPermutation();
        System.out.println(permutation);
    }

    //gets results from dijkstras and outputs it.
    private static void dijkstras() {
        Dijkstra dijkstra = new Dijkstra(CITIES);
        int startingPath = 0;
        dijkstra.runDijkstras(startingPath);

        System.out.println(dijkstra);
    }

    //gets results from heuristic nearest neighbours and outputs it
    private static void heuristicNearestNeighbours() {
        HeuristicNearestNeighbours heuristicNearestNeighbours = new HeuristicNearestNeighbours(CITIES);
        heuristicNearestNeighbours.runHeuristicNearestNeighbours();

        System.out.println(heuristicNearestNeighbours);
    }
}
