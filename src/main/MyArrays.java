package main;

/**
 * This class contains toString() methods to print arrays used in this project.
 */
public class MyArrays {

    /**
     * Adds all elements of an integer array to a single line string
     * @param intArray   an integer array to print
     * @return           a string containing a single line of integer type elements
     */
    public static String toString(int[] intArray) {
        String arrayToString = "";
        for (int arrayElement : intArray)
            arrayToString += " " + (arrayElement+1);
        return arrayToString;
    }

    /**
     * Adds all elements of a 2D String array to a single line string
     * @param arr2D a 2D integer array to print
     * @return      a string containing a single line of integer type elements
     */
    public static String toString(int[][] arr2D) {
        String arrayToString = "";

        for (int[] intArray : arr2D)
            if(intArray != null)
                arrayToString += "" + toString(intArray);

        return arrayToString;
    }

    /**
     * Adds all elements of a 2D String array to a single line string
     * @param anyTypeArray       an array of any type to print
     * @param <E>       type of array (can be any type i.e. DataPoint)
     * @return          a string containing a single line of any type of elements
     */
    public static <E> String toString(E[] anyTypeArray) {
        String arrayToString = "";
        for (E element : anyTypeArray)
            arrayToString += element + "\n";

        return arrayToString;
    }
}
