package main;

/**
 * This class represents a city in the travelling salesman problem. A data point/city holds an x and a y axis followed
 * by methods such as getting the distance to another city, and overriding the toString() method to print the coordinate
 */
public class DataPoint {

    private double x;
    private double y;

    public DataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDistanceTo(DataPoint point2) {
        return Math.sqrt( (Math.pow(this.x - point2.getX(), 2)) + ( (Math.pow(this.y - point2.getY(), 2)) ) );
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public String toString() {
    	return "" + x + "," + y + "";
    }
}
