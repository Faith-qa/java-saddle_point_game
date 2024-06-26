package saddlePoints;
import java.util.Arrays;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.exit;

/**
 * Creates a number of random arrays, and checks each array to see
 * if it contains a saddle point. Prints the arrays and the results.
 *
 * @author Faith Okoth
 */
public class SaddlePoints {
    /**
     * Creates arrays various sizes (including some 2x2 arrays and some larger),
     * fills them with random values, and prints each array and information about
     * it. Keeps generating arrays until it has printed at least one with and
     * one without a saddle point.
     */
    private int numberOfRows;
    private int numberOfColumns;
    private int minValue;
    private int maxValue;

     SaddlePoints(){
        this.numberOfRows = 0;
        this.numberOfColumns = 0;
        this.minValue = 0;
        this.maxValue = 0;
    }

    void run() {

        boolean successful = false;

        while(!successful){
            System.out.println("app is starting");
            Random rand = new Random();
            numberOfRows = rand.nextInt(2,20);
            numberOfColumns = rand.nextInt(2,20);
            minValue = rand.nextInt(-2, 20);
            maxValue = rand.nextInt(20, 30);

            int [][] arr = createRandomArray(numberOfRows, numberOfColumns, minValue, maxValue);
            printArray(arr);
            if (arr != null){
                if(hasSaddlePoint(arr)){
                    printArrayInfo(arr);
                    successful = true;
                   // System.exit(0);
                }else{
                    printArrayInfo(arr);

                }

            }

        }




    }

    /**
     * Prints the array.
     *
     * @param array The array to be printed.
     */
    void printArray(int[][] array) {
        System.out.println(Arrays.deepToString(array));

    }

    /**
     * Prints whether the given array has a saddle point, and if so, where it is (row and column)
     * and what its value is. (If there are multiple saddle points, just prints the first.)
     *
     * @param array The array to be checked.
     */
    void printArrayInfo(int[][] array) {
        if(hasSaddlePoint(array)){
            int row = saddlePointRow(array);
            int column = saddlePointColumn(array);
            System.out.println(array + "has a saddle point at (" + row + "," + column + ") and its value is "+ array[row][column]);

        }else{
            System.out.println(array + " has no saddle point");
        }




    }

    /**
     * Creates and returns an array of the given size and fills it with random
     * values in the specified range.
     *
     * @param numberOfRows    The number of rows desired.
     * @param numberOfColumns The number of columns desired.
     * @param minValue        The smallest number allowable in the array.
     * @param maxValue        The largest number allowable in the array.
     * @return
     */
    int[][] createRandomArray(int numberOfRows, int numberOfColumns, int minValue, int maxValue) {

        int [][] randomArr = new int [numberOfRows][numberOfColumns];
        Random rand = new Random();
        if(minValue == maxValue || maxValue < minValue){
            return null;
        }

        for (int i = 0; i < numberOfRows; i++){
            for(int j = 0; j < numberOfColumns; j++){
                randomArr[i][j] = rand.nextInt(minValue, maxValue)+minValue;
            }
        }
        return randomArr;

    }

    /**
     * Finds the largest value in an array of integers.
     *
     * @param array The array to be searched.
     * @return The largest value in the array.
     */
    int largest(int[] array) {
        int maxVal = Integer.MIN_VALUE;

        for (int j : array) {
            if (j > maxVal) {
                maxVal = j;
            }
        }

        return maxVal;
    }

    /**
     * Finds the smallest value in an array of integers.
     *
     * @param array The array to be searched.
     * @return The smallest value in the array.
     */
    int smallest(int[] array) {
        int minVal = Integer.MAX_VALUE;

        for (int j : array){
            if(j < minVal){
                minVal = j;
            }

        }
        return minVal;
    }

    /**
     * Returns an array containing the largest values in each column of the given array.
     *
     * @param array The array to be searched.
     * @return An array of the largest values in each column.
     */
    int[] largestValues(int[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        //transpose the 3d
        int[][] transposed = new int[cols][rows];
        int[] largestValsCol = new int [cols];

        //fill the transposed array

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                transposed[j][i] = array[i][j];
            }
        }
        // fill the largest vals col

        for (int i = 0; i < transposed.length; i++ ){
            largestValsCol[i] = largest(transposed[i]);
        }
        return largestValsCol;
    }

    /**
     * Returns an array containing the smallest values in each row of the given array.
     *
     * @param array The array to be searched.
     * @return An array of the smallest values in each row.
     */
    int[] smallestValues(int[][] array) {
        int[] smallestVals = new int[array.length];

        for(int i = 0; i < array.length; i++){
            smallestVals[i] = smallest(array[i]);
        }

        return smallestVals;    }


    /**
     * Returns true if the given array has a saddle point, and false if it does not.
     *
     * @param array The array to be checked.
     * @return True if the array has a saddle point, else false.
     */
    boolean hasSaddlePoint(int[][] array) {


        int[] smallestVals = smallestValues(array);
        int[] largestVals = largestValues(array);

        //convert largest value in hashset
        Set<Integer> largestValsSet = new HashSet<>();
        for(int val: largestVals){
            largestValsSet.add(val);
        }

        for (int val: smallestVals){
            if (largestValsSet.contains(val)){
                return true;
            }
        }

        return false;
    }

    /**
     * Given an array that is known to have a saddle point, returns the number of a
     * row containing a saddle point. If more than one row contains a saddle point,
     * the first such row will be returned.
     *
     * @param array An array containing one or more saddle points.
     * @return The lowest-numbered row containing a saddle point.
     */
    int saddlePointRow(int[][] array) {
        if (hasSaddlePoint(array)){
            int[] smallestVals = smallestValues(array);
            int[] largestVals = largestValues(array);

            //convert largest value in hashset
            Set<Integer> largestValsSet = new HashSet<>();
            for(int val: largestVals){
                largestValsSet.add(val);
            }
            for (int i = 0; i < smallestVals.length;i++ ){
                if (largestValsSet.contains(smallestVals[i])){
                    return i;
                }
            }


        }
        return -1;
    }

    /**
     * Given an array that is known to have a saddle point, returns the number of a
     * column containing a saddle point. If more than one column contains a saddle point,
     * the first such column will be returned.
     *
     * @param array An array containing one or more saddle points.
     * @return The lowest-numbered column containing a saddle point.
     */

    int saddlePointColumn(int[][] array) {
        if (hasSaddlePoint(array)) {
            int[] smallestVals = smallestValues(array);
            int[] largestVals = largestValues(array);
            //convert largest value in hashset
            Set<Integer> smallestValsSet = new HashSet<>();
            for(int val: smallestVals){
                smallestValsSet.add(val);
            }
            for(int i = 0; i <= largestVals.length; i++){
                if (smallestValsSet.contains(largestVals[i])){
                    return i;
                }
            }

        }

            return -1;
    }
}