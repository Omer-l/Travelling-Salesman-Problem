package convexHull;

import main.DataPoint; //holds the coordinates to the cities.

import java.util.ArrayList;

public class ConvexHullInsertion {

    private final DataPoint[] dataPoints;

    public ConvexHullInsertion(DataPoint[] dataPoints) {
        this.dataPoints = dataPoints;
    }

    /**
     * Gets the lowest data point, the city with the lowest y-coordinate
     *
     * @return the index of the city with the lowest y-coordinate.
     */
    public int getLowestDataPointIndex() {

        int currentLowestIndex = 0;
        DataPoint currentLowest = dataPoints[currentLowestIndex];

        for (int dataPointIterator = 1; dataPointIterator < dataPoints.length; dataPointIterator++) {
            DataPoint dataPoint = dataPoints[dataPointIterator];

            if(dataPoint.getY() == currentLowest.getY()) {
                if(dataPoint.getX() < currentLowest.getX())
                    currentLowest = dataPoint;
            }
            else if (dataPoint.getY() < currentLowest.getY()) { //ensures the lowest index is changed if dataPoint's y-coordinate is lower.
                currentLowest = dataPoint;
                currentLowestIndex = dataPointIterator;
            }
        }

        return currentLowestIndex;
    }

//    /** DEL POSSIBLY
//     * Gets the x2,y2 and x1,y1 in the form of an array for the gradient formula.
//     * @param point1    is the first point's x and y
//     * @param point2    is the second point's x and y
//     * @return          an array, in which, indexes: 0 is x2, 1 is y2, 2 is x1, 3 is y1
//     */
//    public double[] calculateInputsForGradient(DataPoint point1, DataPoint point2) {
//        double[] inputsForGradient;
//
//        if(point1.getY() > point2.getY()) {
//            inputsForGradient = new double[] {point1.getX(), point1.getY(), point2.getX(), point2.getY()};
//        } else {
//            inputsForGradient = new double[] {point2.getX(), point2.getY(), point1.getX(), point1.getY()};
//        }
//        return inputsForGradient;
//    }

    /**
     * Calculates the angle to a point
     *
     * @param index1 is the first point's index.
     * @param index2 is the second point's index.
     * @return the angle to point 2
     */
    public double getAngleToPoint(int index1, int index2) {
        DataPoint point1 = dataPoints[index1];
        DataPoint point2 = dataPoints[index2];

        //by calculating the gradient, we can calculate the angle.
        double gradient = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());

        //use tan(θ)=m formula to calculate angle
        return Math.atan(gradient);
    }

    /**
     * Calculates all the angles to the points from a point.
     *
     * @param startPointIndex index of the point to calculate angles to other points
     * @return a sorted list of angles to other data points
     */
    public ArrayList<double[]> getAnglesToAllPoints(int startPointIndex) {
        ArrayList<double[]> indexesAndAngles = new ArrayList<>();

        for (int dataPointIterator = 0; dataPointIterator < dataPoints.length; dataPointIterator++) {
            if (dataPointIterator != startPointIndex) { //there isn't an angle to itself.
                //gets angle to points
                double angleToPoint = getAngleToPoint(startPointIndex, dataPointIterator);
                double[] indexAndAngle = {dataPointIterator, angleToPoint}; //fills index and angle to point into a double array, 0 being the index, 1 being the angle.
                //traverse arraylist and see if angle is less than angle at index.
                boolean inserted = false;
                for (int indexAndAngleIterator = 0; indexAndAngleIterator < indexesAndAngles.size() && !inserted; indexAndAngleIterator++) { //insertion sort iteration.
                    double[] currentIndexAndAngle = indexesAndAngles.get(indexAndAngleIterator);
                    if (indexAndAngle[1] < currentIndexAndAngle[1]) {
                        indexesAndAngles.add(indexAndAngleIterator, indexAndAngle);
                        inserted = true;
                    }
                }
                if (!inserted) //ensures it's added to the list if either empty or traversed to the end.
                    indexesAndAngles.add(indexAndAngle);

            }
        }
        return indexesAndAngles;
    }

    /**
     * By the proof that the cross product of two vectors can be used to calculate the signed area of a parallelogram.
     * The signed tells the direction of the length of the parallelogram. The cross product is a perpendicular vector
     * who's length is equal to the area of the parallelogram.
     * @param point1    the 2nd previous point from the current point
     * @param point2    the 1st previous point from the current point
     * @param point3    the current point
     * @return          -1 if point 3 is clockwise, -1 anti-clockwise, 0 collinear.
     */
    public int counterClockwise(DataPoint point1, DataPoint point2, DataPoint point3) {
        double area = (point2.getX() - point1.getX()) * (point3.getY() - point1.getY()) - (point2.getY() - point1.getY()) * (point3.getX() - point1.getX());

        if(area < 0)
            return -1; //means point3 is clockwise
        else if(area > 0)
            return 1; //means point3 is counter-clockwise
        else
            return 0; //point3 is collinear.
    }

    /**
     * Adds next point's index depending on the sorted list of angles and previous angles.
     * @param path                  is the current traversed path
     * @param indexesAndAngles      is a sorted list of arrays, for which, each array contains the index of the city and an angle from starting index
     * @param iteratedPointIndex    is the current potential index to add to the path.
     * @return                      next point to visit.
     */
    public int getNextPoint(MyStack path, ArrayList<double[]> indexesAndAngles, int iteratedPointIndex) {
        DataPoint point = dataPoints[path.pop()];
        DataPoint nextPoint = dataPoints[iteratedPointIndex];
        int nextPointIndex = iteratedPointIndex;

        while(!path.isEmpty() && counterClockwise(dataPoints[path.peek()], point, nextPoint) <= 0) {
            nextPointIndex = path.pop();
            nextPoint = dataPoints[nextPointIndex];
        }

        path.add(nextPointIndex);
        return -1;
    }

    //Runs the necessary functions and the convex hull algorithm.
    public void runConvexHullInsertion(ArrayList<double[]> indexesAndAngles) {
        MyStack path = new MyStack(); //this is a stack-like path. Paths creating a clockwise turn are deleted.
        path.push(getLowestDataPointIndex()); //adds the lowest city to path as it is a guarantee.
        path.push((int)indexesAndAngles.get(0)[0]); //adds 1st in the sorted list for evaluation.

        for(int dataPointIterator = 2; dataPointIterator < indexesAndAngles.size(); dataPointIterator++) {
            int nextCityToVisit = getNextPoint(path, indexesAndAngles, 2);
        }
    }

    public DataPoint[] getDataPoints() {
        return dataPoints;
    }
}
