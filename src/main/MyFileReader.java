package main;

import java.io.File; //used to get the data from the file
import java.io.FileNotFoundException; //error in the case of a file is not found.
import java.util.Scanner; //used to take in the data from the data file

public class MyFileReader {
		public final File DATA_FILE;
	    
	    //error message constant(s)
	    private final String ERR_MSG_FILE_NOT_FOUND = "\nFILE NOT FOUND";
		
	    public MyFileReader(String absoluteFilePath) {
	        this.DATA_FILE = new File(absoluteFilePath); // path for data //create constants for user.dir etc..
	    }
		
		/**
	     * This function gets the data points that each line the data file contains.
	     * @return an array of data points, every aminoAcid contains a data point (x, y).
	     */
	    public DataPoint[] getData() {
	        // get number of lines in the data DATA_FILE, for which, each line there is a data point.
	        int numberOfDataPoints = getNumberOfTrainingSets();

	        DataPoint[] dataPoints = new DataPoint[numberOfDataPoints]; // An array of amino acids per gene.

	        int pointNumber = 0; // current index in array of Vertices

	        try {
	            Scanner fileInput = new Scanner(DATA_FILE); // Scanner for reading the data DATA_FILE.
	            while (fileInput.hasNext()) {
					String regex = "\\s+"; // split all space occurences in the line.
	                String[] splittedLine = fileInput.nextLine().split(regex);
	                int dataX = Integer.parseInt(splittedLine[1].trim()); // adds point x
	                int dataY = Integer.parseInt(splittedLine[2].trim()); // add point y
	                dataPoints[pointNumber] = new DataPoint(dataX, dataY); // creates an instance of the class DataPoint with dataX and dataY.
	                pointNumber++;
	            }
	            fileInput.close(); // close scanners
	        } catch (FileNotFoundException fileNotFoundException) {
	            System.out.println(ERR_MSG_FILE_NOT_FOUND);
	            fileNotFoundException.printStackTrace();
	        }

	        return dataPoints;
	    }
	    

	    /**
	     * counts the number of lines (data points) in the DATA_FILE
	     *
	     * @return the number of lines in the DATA_FILE, which is the number of data points.
	     */
	    public int getNumberOfTrainingSets() {
	        int trainingSetsCounter = 0; // counter

	        try {
	            Scanner fileInput = new Scanner(DATA_FILE);

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
}
