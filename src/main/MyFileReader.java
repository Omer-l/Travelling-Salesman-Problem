package main;

import java.io.File; //used to get the data from the file
import java.io.FileNotFoundException; //error in the case of a file is not found.
import java.util.Scanner; //used to take in the data from the data file

public class MyFileReader {
		public final File dataFile; //file holding coordinates.
	    
	    //error message constant(s)
	    private final String ERR_MSG_FILE_NOT_FOUND = "\nFILE NOT FOUND";
		
	    public MyFileReader(String absoluteFilePath) {
	        this.dataFile = new File(absoluteFilePath); // path for data //create constants for user.dir etc..
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
	            Scanner fileInput = new Scanner(dataFile); // Scanner for reading the data DATA_FILE.
	            while (fileInput.hasNext()) {
					String regex = "\\s+"; // split all space occurrences in the line.
	                String[] splitLine = fileInput.nextLine().split(regex);

					double[] dataPointsXAndY = getXandY(splitLine); //index 0 is x value, index 1 is y value

	                double dataX = dataPointsXAndY[0]; // gets point x
	                double dataY = dataPointsXAndY[1]; // gets point y
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
		 * Gets x and y coordinates while avoiding the index number (first number in the line)
		 * @param splitLine		a line containing the index - x coordinate - y coordinate
		 * @return				an array containing the x coordinate at index 0 and y coordinate at index 1
		 */
		private double[] getXandY(String[] splitLine) {
			double[] xAndY = new double[2];
			int xyIterator = 0; //at 0 x will be initialised, at 1 y will be initialised
			boolean passedIndex = false; //ensures index is not taken as an x or y coordinate.

			for(String partOfLine : splitLine) {
				partOfLine = partOfLine.trim();
				if(partOfLine.length() > 0) { //then it is a number
					if(passedIndex) {
						xAndY[xyIterator] = Double.parseDouble(partOfLine);
						xyIterator++;
					} else
						passedIndex = true;
				}
			}
			return xAndY;
		}


	/**
	     * Counts the number of lines (data points) in the DATA_FILE
	     * @return the number of lines in the DATA_FILE, which is the number of data points.
	     */
	    public int getNumberOfTrainingSets() {
	        int trainingSetsCounter = 0; // counter

	        try {
	            Scanner fileInput = new Scanner(dataFile);

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
