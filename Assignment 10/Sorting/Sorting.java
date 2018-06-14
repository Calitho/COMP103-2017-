// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 10
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

import java.util.*;
import java.io.*;

/**
 * Code for Sorting Experiment
 * - testing code
 * - sorting algorithms
 * - utility methods for creating, testing, printing, copying arrays
 */

public class Sorting {
    private String[] sortTypes = new String[9];
    File out;
    File Unsorted = new File("Unsorted.csv");
    File Sorted = new File("Sorted.csv");
    File Reversed = new File("Reversed.csv");
    private String[] usedArray;
    private int startPoint = 3;
    private int endPoint = 4;
    private float average;


    Sorting() {
        dataToFile("Unsorted","SortType, ArrayType, ArraySize, AverageTime\n");
        dataToFile("Sorted","SortType, ArrayType, ArraySize, AverageTime\n");
        dataToFile("Reversed","SortType, ArrayType, ArraySize, AverageTime\n");

        //Search types
        sortTypes[8] = "selectionSort";
        sortTypes[7] = "selectionSort2";
        sortTypes[6] = "insertionSort";
        sortTypes[5] = "insertionSort2";
        sortTypes[4] = "mergeSort";
        sortTypes[3] = "mergeSort2";
        sortTypes[2] = "quickSort";
        sortTypes[1] = "quickSort2";
        sortTypes[0] = "Array.sort";
    }

    /* Example method for testing and timing sorting algorithms.
     *  You will need to modify and extend this heavily to do your
     *  performance testing. It should probably run tests on each of the algorithms,
     *  on different sized arrays, and multiple times on each size.
     *  Make sure you create a new array each time you sort - it is not a good test if
     *  you resort the same array after it has been sorted.
     *  Hint: if you want to copy an array, use copyArray (below)
     */
    private void testSorts() {
        UI.setDivider(1);
        int size;


        //Unsorted
        //forms arrays
        //for (int value = 0; value <= arraySize.length; value++) {
        //for (int i = 0; i <= 16000000; i += 10000) {
        for (int i = 0; i <= 24; i++) {
            //exponential
            size = (int) Math.pow(2, i);
            //Array
            //size = arraySize[value];
            //Linear
            //size = i;
            UI.println(size);
            usedArray = createArray(size);


            //Loops through Sort arrays
            for (int j = startPoint; j < endPoint; j++) {
                //amount of test runs
                for (int m = 0; m < 5; m++) {
                    if ((average / 5)/1000 > 60) {
                        break;
                    } else {
                        tests ("Unsorted", sortTypes[j], usedArray);
                    }
                }
                String selectionToText = sortTypes[j] + ", " + "Unsorted" + ", " + usedArray.length + ", " + (average/ 5)/1000 + ", \n";
                dataToFile("Unsorted" , selectionToText);
                UI.printf("Average Time taken:       %.3f s\n", (average/5)/1000.0);
                average = 0;
            }
            //sorted
            Arrays.sort(usedArray);
            for (int j = startPoint; j < endPoint; j++) {
                //amount of test runs
                average = 0;
                for (int m = 0; m < 5; m++) {
                    if ((average/5)/1000 > 60) {
                        break;
                    } else {
                        tests("Sorted", sortTypes[j], usedArray);
                    }
                }
                String selectionToText = sortTypes[j] + ", " + "Sorted" + ", " + usedArray.length + ", " + (average/ 5)/1000 + ", \n";
                dataToFile("Sorted", selectionToText);
                UI.printf("Average Time taken:       %.3f s\n", (average/5)/1000.0);
                average = 0;
            }

            //reversed
            reverseArray(usedArray);
            for (int j = startPoint; j < endPoint; j++) {
                //amount of test runs
                average = 0;
                for (int m = 0; m < 5; m++) {
                    if ((average/5)>60) {
                        break;
                    } else {
                        tests("Reversed", sortTypes[j], usedArray);
                    }
                }
                String selectionToText = sortTypes[j] + ", " + "Reversed" + ", " + usedArray.length + ", " + (average/ 5)/1000 + ", \n";
                dataToFile("Reversed",selectionToText);
                UI.printf("Average Time taken:       %.3f s\n", (average/5)/1000.0);
                average = 0;
            }
        }
    }


    private void tests(String stuff, String sortType, String[] data) {
        //Time variables
        long start;
        long time;
        //Actual calculation
        start = System.currentTimeMillis();
        runMethod(sortType, data);
        time = System.currentTimeMillis() - start;
        //Average
        average += time;
        //To print
        UI.println("\n\n====== " + sortType + " with " + stuff + " arrays" + " =======\n");
        //UI.println();
        UI.printf("Number of items:  %,d\n", data.length);
        /*UI.printf("Sorted correctly: %b\n", testSorted(data));
        UI.printf("Time taken:       %.3f s\n", time / 1000.0);
        UI.println();*/
        //UI.printf("Average Time taken:       %.3f s\n", (average/5) / 1000.0);
        UI.println("\n=======DONE=========\n");
        //To write
        /*String selectionToText = sortType + ", " + stuff + ", " + data.length + ", " + (average) / 5 / 1000 + ", \n";
        dataToFile(selectionToText);*/
    }



    private void runMethod(String sortType, String[] sortTypes) {
        if (sortType.equals("selectionSort")) {
            selectionSort(sortTypes);
        }
        if (sortType.equals("selectionSort2")) {
            selectionSort2(sortTypes);
        }
        if (sortType.equals("insertionSort")) {
            insertionSort2(sortTypes);
        }
        if (sortType.equals("insertionSort2")) {
            insertionSort(sortTypes);
        }
        if (sortType.equals("mergeSort")) {
            mergeSort(sortTypes);
        }
        if (sortType.equals("mergeSort2")) {
            mergeSort2(sortTypes);
        }
        if (sortType.equals("quickSort")) {
            quickSort(sortTypes);
        }
        if (sortType.equals("quickSort2")) {
            quickSort2(sortTypes);
        }
        if (sortType.equals("Array.sort")) {
            Arrays.sort(sortTypes);
        }
    }

    private void dataToFile(String filetype, String s) {
        if (filetype.equals("Unsorted")){
            out=Unsorted;
        }
        else if (filetype.equals("Sorted")){
            out=Sorted;
        }
        else if (filetype.equals("Reversed")){
            out=Reversed;
        }
        BufferedWriter bWriter = null;
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(out, true);
            bWriter = new BufferedWriter(fWriter);
            bWriter.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bWriter != null)
                    bWriter.close();
                if (fWriter != null)
                    fWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /* =============== SWAP ================= */

    /**
     * Swaps the specified elements of an array.
     * Used in several of the sorting algorithms
     */

    private void swap(String[] data, int index1, int index2) {
        if (index1 == index2) return;
        String temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    /* ===============SELECTION SORT================= */

    /**
     * Sorts the elements of an array of String using selection sort
     */
    private void selectionSort(String[] data) {
        // for each position, from 0 up, find the next smallest item 
        // and swap it into place
        for (int place = 0; place < data.length - 1; place++) {
            int minIndex = place;
            for (int sweep = place + 1; sweep < data.length; sweep++) {
                if (data[sweep].compareTo(data[minIndex]) < 0)
                    minIndex = sweep;
            }
            swap(data, place, minIndex);
        }
    }

    public void selectionSort2(String[] data) {
        int backIndex = data.length - 1;
        for (int frontIndex = 0; frontIndex < data.length / 2; frontIndex++) {
            int minIndex = frontIndex;
            int maxIndex = backIndex;
            for (int sweep = frontIndex + 1; sweep < data.length; sweep++) {
                if (data[sweep].compareTo(data[minIndex]) < 0)
                    minIndex = sweep;
            }
            for (int sweep = backIndex - 1; sweep > 0; sweep--) {
                if (data[sweep].compareTo(data[maxIndex]) > 0)
                    minIndex = sweep;
            }
            swap(data, frontIndex, minIndex);
            swap(data, backIndex, maxIndex);
            backIndex--;
        }
    }

    /* ===============INSERTION SORT================= */

    /**
     * Sorts the  elements of an array of String using insertion sort
     */
    private void insertionSort(String[] data) {
        // for each item, from 0, insert into place in the sorted region (0..i-1)
        for (int i = 1; i < data.length; i++) {
            String item = data[i];
            int place = i;
            while (place > 0 && item.compareTo(data[place - 1]) < 0) {
                data[place] = data[place - 1];       // move up
                place--;
            }
            data[place] = item;
        }
    }

    public void insertionSort2(String[] data) {

        for (int i = 0; i < data.length; ++i) {
            String temp = data[i];
            int left = 0;
            int right = i;
            while (left < right) {
                int middle = (left + right) / 2;
                if (temp.compareTo(data[middle]) >= 0)
                    left = middle + 1;
                else
                    right = middle;
            }
            for (int j = i; j > left; --j) {
                swap(data, j - 1, j);
            }

        }
    }


    /* ===============MERGE SORT================= */

    /**
     * non-recursive, wrapper method
     * copy data array into a temporary array
     * call recursive mergeSort method
     */
    private void mergeSort(String[] data) {
        String[] other = new String[data.length];
        for (int i = 0; i < data.length; i++)
            other[i] = data[i];
        mergeSort(data, other, 0, data.length); //call to recursive merge sort method
    }

    /**
     * Recursive mergeSort method
     */
    private void mergeSort(String[] data, String[] temp, int low, int high) {
        if (low < high - 1) {
            int mid = (low + high) / 2;
            mergeSort(temp, data, low, mid);
            mergeSort(temp, data, mid, high);
            merge(temp, data, low, mid, high);
        }
    }


    /**
     * Merge method
     * Merge from[low..mid-1] with from[mid..high-1] into to[low..high-1]
     * Print data array after merge using printData
     */
    private void merge(String[] from, String[] to, int low, int mid, int high) {
        int index = low;      //where we will put the item into "to"
        int indxLeft = low;   //index into the lower half of the "from" range
        int indxRight = mid; // index into the upper half of the "from" range
        while (indxLeft < mid && indxRight < high) {
            if (from[indxLeft].compareTo(from[indxRight]) <= 0)
                to[index++] = from[indxLeft++];
            else
                to[index++] = from[indxRight++];
        }
        // copy over the remainder. Note only one loop will do anything.
        while (indxLeft < mid)
            to[index++] = from[indxLeft++];
        while (indxRight < high)
            to[index++] = from[indxRight++];
    }


    //FIX
    public void mergeSort2(String[] data) {
        String[] other = new String[data.length];
        for (int i = 0; i < data.length; i++)
            other[i] = data[i];
        mergeSort2(data, other, 0, data.length); //call to recursive merge sort method
    }

    public void mergeSort2(String[] data, String[] temp, int low, int high) {
        if (low < high - 1) {
            int mid = (low + high) / 2;
            mergeSort(temp, data, low, mid);
            mergeSort(temp, data, mid, high);
            merge2(temp, data, low, mid, high);
        }
    }

    public void merge2(String[] from, String[] to, int low, int mid, int high) {
        int index = low;      //where we will put the item into "to"
        int indxLeft = low;   //index into the lower half of the "from" range
        int indxRight = mid; // index into the upper half of the "from" range
        while (indxLeft < mid && indxRight < high) {
            if (from[indxLeft].compareTo(from[indxRight]) <= 0)
                to[index++] = from[indxLeft++];
            else
                to[index++] = from[indxRight++];
        }
        // copy over the remainder. Note only one loop will do anything.
        while (indxLeft < mid)
            to[index++] = from[indxLeft++];
        while (indxRight < high)
            to[index++] = from[indxRight++];
    }

    /*===============QUICK SORT=================*/
/** Sort data using QuickSort
 *  Print time taken by Quick sort
 *  Print number of times partition gets called
 */

    /**
     * Quick sort recursive call
     */
    private void quickSort(String[] data) {
        quickSort(data, 0, data.length);
    }

    private void quickSort(String[] data, int low, int high) {
        if (high - low < 2)      // only one item to sort.
            return;
        else {     // split into two parts, mid = index of boundary
            int mid = partition(data, low, high);
            quickSort(data, low, mid);
            quickSort(data, mid, high);
        }
    }

    /**
     * Partition into small items (low..mid-1) and large items (mid..high-1)
     * Print data array after partition
     */
    private int partition(String[] data, int low, int high) {
        String pivot = data[(low + high) / 2];
        int left = low - 1;
        int right = high;
        while (left < right) {
            do {
                left++;       // just skip over items on the left < centre
            }
            while (left < high && data[left].compareTo(pivot) < 0);

            do {
                right--;     // just skip over items on the right > centre
            }
            while (right >= low && data[right].compareTo(pivot) > 0);

            if (left < right)
                swap(data, left, right);
        }
        return left;
    }


    /**
     * Quick sort, second version:  simpler partition method
     * faster or slower?
     */
    private void quickSort2(String[] data) {
        quickSort2(data, 0, data.length);
    }

    private void quickSort2(String[] data, int low, int high) {
        if (low + 1 >= high) {
            return;
            // no items to sort.
        } else {     // split into two halves, mid = index of centre
            int mid = partition2(data, low, high);
            quickSort2(data, low, mid);
            quickSort2(data, mid + 1, high);
        }
    }

    private int partition2(String[] data, int low, int high) {
        swap(data, low, (low + high) / 2);    // choose centre and put at position low
        String pivot = data[low];
        int mid = low;
        for (int i = low + 1; i < high; i++) {  // for each item after the centre
            if (data[i].compareTo(pivot) < 0) {
                mid++;                      // move mid point up
                swap(data, i, mid);
            }
        }
        swap(data, low, mid);   // move centre to the mid point
        return mid;
    }

    /* =====   Utility methods ============================================ */

    /**
     * Tests whether an array is in sorted order
     */
    public boolean testSorted(String[] data) {
        for (int i = 1; i < data.length; i++) {
            if (data[i].compareTo(data[i - 1]) < 0)
                return false;
        }
        return true;
    }

    public void printData(String[] data) {
        for (String str : data) {
            UI.println(str);
        }
    }

    /**
     * Constructs an array of Strings by making random String values
     */
    public String[] createArray(int size) {
        Random randGenerator = new Random();
        String[] data = new String[size];
        for (int i = 0; i < size; i++) {
            char[] chars = new char[5];
            for (int c = 0; c < chars.length; c++) {
                chars[c] = (char) ('a' + randGenerator.nextInt(26));
            }
            String str = new String(chars);
            data[i] = str;
        }
        return data;
    }

    /**
     * Constructs an array of Strings by reading a file
     * The size of the array will be the specified size, unless the
     * file is too short, or size is -ve, in which cases the array will
     * contain all the tokens in the file.
     */
    public String[] readArrayFromFile(int size, String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            UI.println("file " + filename + " does not exist");
            return null;
        }
        List<String> temp = new ArrayList<String>();
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNext())
                temp.add(scan.next());
            scan.close();
        } catch (IOException ex) {   // what to do if there is an io error.
            UI.println("File reading failed: " + ex);
        }
        if (temp.size() < size || size < 0)
            size = temp.size();

        String[] data = new String[size];
        for (int i = 0; i < size; i++) {
            data[i] = temp.get(i);
        }
        return data;
    }

    /**
     * Create a new copy of an array of data
     */
    public String[] copyArray(String[] data) {
        String[] newData = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        return newData;
    }

    /**
     * Create a new copy of the first size elements of an array of data
     */
    public String[] copyArray(String[] data, int size) {
        if (size > data.length) size = data.length;
        String[] newData = new String[size];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        return newData;
    }

    public void reverseArray(String[] data) {
        int bot = 0;
        int top = data.length - 1;
        while (bot < top) {
            swap(data, bot++, top--);
        }
    }

    public static void main(String[] args) {
        Sorting sorter = new Sorting();
        sorter.testSorts();
    }


}

