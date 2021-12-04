package main;

/**
 * This class contains toString methods to print arrays used in this project.
 */
public class MyArrays {

    /**
     * Adds all elements of an integer array to a single line string
     * @param arr   an array to print
     * @return      a string containing a single line of integer type elements
     */
    public static String toString(int[] arr) {
        String s = "";
        for (int i : arr)
            s += " " + (i+1);
        return s;
    }

    /**
     * Adds all elements of a 2D String array to a single line string
     * @param arr 2D integer array to print
     * @return a string containing a single line of integer type elements
     */
    public static String toString(int[][] arr) {
        String s = "";

        for (int[] a : arr)
            if(a != null)
                s += "" + toString(a);

        return s;
    }

    /**
     * Adds all elements of a 2D String array to a single line string
     * @param arr       an array of any type to print
     * @param <E>       type of array (can be any type i.e. DataPoint)
     * @return          a string containing a single line of any type of elements
     */
    public static <E> String toString(E[] arr) {
        String s = "";
        for (E e : arr)
            s += e + " ";

        return s;
    }
}
