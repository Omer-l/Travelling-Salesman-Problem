package main;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class MyFileReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {"trainProblem1.txt", "trainProblem2.txt", "trainProblem3.txt", "test1_2018.txt",
            "test2_2018.txt", "test3_2018.txt", "test4_2018.txt", "test1_2019.txt", "test2_2019.txt", "test3_2019.txt",
            "test4_2019.txt", "test1_2020.txt", "test2_2020.txt", "test3_2020.txt", "test4_2020.txt"})
    public void testIndexIsNotAssignedAsX(String filename) {
        final String filePath = System.getProperty("user.dir") + "/Resources/" + filename; //absolute path to the file
        System.out.println(filePath);
        MyFileReader myFileReader = new MyFileReader(filePath);

        DataPoint[] dataPoints = myFileReader.getData();
        System.out.println(MyArrays.toString(dataPoints));
        int numberOfPoints = dataPoints.length;

        double[] xValues = new double[numberOfPoints];
        double[] expecteds = new double[numberOfPoints];

        for(int pointIterator = 0; pointIterator < dataPoints.length; pointIterator++) {
            xValues[pointIterator] = dataPoints[pointIterator].getX();
            expecteds[pointIterator] = pointIterator+1;
        }

        Assert.assertThat(xValues, IsNot.not(IsEqual.equalTo(expecteds)));
    }
}
