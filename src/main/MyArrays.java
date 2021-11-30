package main;

public class MyArrays {

    public static String toString(int[] arr) {
        String s = "";
        for (int i : arr)
            s += " " + (i);
        return s;
    }

    public static String toString(double[] arr) {
        String s = "";
        for (double i : arr)
            s += " " + i;
        return s + "\n";
    }

    public static String toString(int[][] arr) {
        String s = "";

        for (int[] a : arr)
            if(a != null)
                s += "" + toString(a);

        return s;
    }

    public static <E> String toString(E[] arr) {
        String s = "";
        for (E e : arr)
            s += e + " ";

        return s;
    }
}
