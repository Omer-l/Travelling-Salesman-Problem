public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDistanceTo(Point point2) {
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
    	return "(" + x + ", " + y + ")";
    }
}
