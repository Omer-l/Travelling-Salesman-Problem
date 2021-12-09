package convexHull;

import main.DataPoint;
import main.MyArrays;
import main.MyFileReader;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ConvexHullInsertionTest {

    @org.junit.Test
    public void getLowest() {
        MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir")
            + "/Resources/trainProblem2.txt");
        ConvexHullInsertion convexHullInsertion = new ConvexHullInsertion(fileReader.getData());
        int expected = 7;
        int actual = convexHullInsertion.getLowestDataPointIndex();

        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void calculateAngleToPoint() {
        MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir")
                + "/Resources/trainProblem2.txt");
        ConvexHullInsertion convexHullInsertion = new ConvexHullInsertion(fileReader.getData());
//        DataPoint point1 = convexHullInsertion.getDataPoints()[0]
//        DataPoint point2 = convexHullInsertion.getDataPoints()[1];

        double actual = convexHullInsertion.getAngleToPoint(7, 6);
        double expected = 1.2277723863741932;

        assertEquals(expected, actual, 1e15);
    }

    @org.junit.Test
    public void getAnglesToAllPoints() {
        MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir")
                + "/Resources/trainProblem2.txt");
        ConvexHullInsertion convexHullInsertion = new ConvexHullInsertion(fileReader.getData());
        int startPointIndex = convexHullInsertion.getLowestDataPointIndex();
        ArrayList<double[]> indexesAndAngles = convexHullInsertion.getAnglesToAllPoints(startPointIndex);

        for(double[] indexAndAngle : indexesAndAngles)
            System.out.println(Arrays.toString(indexAndAngle));
    }

    @org.junit.Test
    public void getNextPoint1stRun() {
        MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir")
                + "/Resources/trainProblem2.txt");
        ConvexHullInsertion convexHullInsertion = new ConvexHullInsertion(fileReader.getData());
        int startPointIndex = convexHullInsertion.getLowestDataPointIndex();
        ArrayList<double[]> indexesAndAngles = convexHullInsertion.getAnglesToAllPoints(startPointIndex);

        int expected = 6;
        int actual = convexHullInsertion.getNextPoint(new MyStack(), indexesAndAngles, 2);

        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void getNextPoint() {
        MyFileReader fileReader = new MyFileReader(System.getProperty("user.dir")
                + "/Resources/DELETE.txt");
        ConvexHullInsertion convexHullInsertion = new ConvexHullInsertion(fileReader.getData());
        int startPointIndex = convexHullInsertion.getLowestDataPointIndex();
        ArrayList<double[]> indexesAndAngles = convexHullInsertion.getAnglesToAllPoints(startPointIndex);
        MyStack path = new MyStack();
        path.push(startPointIndex);
        path.push((int)indexesAndAngles.get(0)[0]);
        convexHullInsertion.getNextPoint(path, indexesAndAngles, 2);
        convexHullInsertion.getNextPoint(path, indexesAndAngles, 3);
        convexHullInsertion.getNextPoint(path, indexesAndAngles, 4);
        convexHullInsertion.getNextPoint(path, indexesAndAngles, 5);
        convexHullInsertion.getNextPoint(path, indexesAndAngles, 6);
        System.out.println(path);
    }

}