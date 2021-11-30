package main;

import breadthFirstSearch.BreadthFirstSearchAlgorithm;

public class ApplicationAllAlgorithmsRunner {
    //static constants
    private final static String ABSOLUTE_FILE_PATH = System.getProperty("user.dir") + "/Resources/trainProblem1"; //absolute path to the file
    private final static long MAXIMUM_TIME = 59000; // a second before 60 seconds, to allow time for thread quitting and choosing the best thread.
    private final static long START_TIME_MS = System.currentTimeMillis(); // starts timer
    private static final MyFileReader MY_FILE_READER = new MyFileReader(ABSOLUTE_FILE_PATH); //reads in the data.
    private static final DataPoint[] CITIES = MY_FILE_READER.getData(); //city objects, each city has a x and a y coordinate

    public static void main(String[] args) {
        breadthFirstSearch();
    }

    //gets the results for breadth first search and outputs it.
    private static void breadthFirstSearch() {
        BreadthFirstSearchAlgorithm breadthFirstSearchAlgorithm = new BreadthFirstSearchAlgorithm(CITIES);
        int startingIndex = 0;
        breadthFirstSearchAlgorithm.runBreadthFirstSearch(startingIndex);

        //output the results.
        System.out.println(breadthFirstSearchAlgorithm);
    }

}
