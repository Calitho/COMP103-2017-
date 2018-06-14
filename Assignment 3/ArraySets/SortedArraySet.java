// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 3
 * Name:
 * Username:
 * ID:
 */

import java.util.*;

/**
 * SortedArraySet - a Set collection;
 * <p>
 * The implementation uses an array to store the items
 * and a count variable to store the number of items in the array.
 * <p>
 * The items in the set should be stored in positions
 * 0, 1,... (count-1) of the array.
 * <p>
 * The length of the array when the set is first created should be 10.
 * <p>
 * Items in the array are kept in order, based on the "compareTo()" method.
 * <p>
 * Binary search is used for searching for items.
 * <p>
 * Note that a set does not allow null items or duplicates.
 * Attempting to add null should throw an IllegalArgumentException exception
 * Adding an item which is already present should simply return false, without
 * changing the set.
 * It should always compare items using equals()  (not using ==)
 * When the array is full, it will create a new array of double the current
 * size, and copy all the items over to the new array
 *
 *
 *
 * Intilaizes a new ArraySet with an initial size, and intializes a count varable which is responsible
 * for keeping count of the valies in the Arrayset.
 */

// We need "extends Comparable" so that we can use the "compareTo()" method
public class SortedArraySet<E extends Comparable> extends AbstractSet<E> {
    // Data fields
    int count;
    E[] data;

    // --- Constructors -------------------------------------
    @SuppressWarnings("unchecked")  // this will stop Java complaining about the cast
    public SortedArraySet() {
        data = (E[]) new String[10];
        count = 0;
    }
    // --- Methods --------------------------------------

    /**
     * @return the number of items in the set
     */
    public int size() {
        return count;
    }      //returns the count variable.

    /**
     * Adds the specified item to this set
     * (if it is not already in the set).
     * Will not add a null value (throws an IllegalArgumentException in this case).
     *
     * @param item the item to be added to the set
     * @return true if the collection changes, and false if it did not change.
     */
    public boolean add(E item) {
        if (item == null) {                  //Checks if the item is null if it is throws a new exception
            throw new IllegalArgumentException("Yeah Nah, No thanks null.");
        }

        int index = findIndexOf(item);      //For simplification

        if (data[index] != null) {          //if the item at the index is not null
            if (data[index].equals(item))
                return false;
        }
        for (int i = count; i >= index; i--) { //iterates backward through the collection.

            if (i == index || i == 0) {    //while iterating it checks if the value i is the same as index or 0, increments count and swaps the item.
                count++;
                ensureCapacity();          //checks capacity works, and if so adds item to and returns true.
                data[i] = item;
                return true;
            }
            data[i] = data[i - 1];
        }
        return false;
    }

    /**
     * @return true if this set contains the specified item.
     */
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare
    public boolean contains(Object item) {
        E itm = (E) item;
        if (itm == null) return false;                           //This could effectively be reduced to a single line,
        if (data[findIndexOf(itm)] == null) return false;
        if (data[findIndexOf(itm)].compareTo(itm) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Removes an item matching a given item.
     *
     * @return true if the item was present and then removed.
     * Makes no changes to the set and returns false if the item is not present.
     */
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare
    public boolean remove(Object item) {
        E itm = (E) item;    //this here is cast statment, which allows an object of anytype to be cast as a generic element, which means multiple types of elements can be use in this program
        if (itm == null) {   //if the item is null it sends but null, if the item at index is null rerun false, and if the item does not equal the item at the position it returns null
            return false;
        }
        int indexValue = findIndexOf(itm);
        if (data[indexValue] == null) return false;
        if (!data[indexValue].equals(item))
            return false;

        for (int i = indexValue; i < count; i++) {  //iterates through the list, moving all the elements to the on in front,
            data[i] = data[i + 1];
        }
        count--;  //the decrments the count to show an item has been remove, it has not been removed  but instead moved to the end, from there the item is overwritten when something is added.
        return true; //the count prevents the item from being seen.
    }

    /**
     * Ensures data array has sufficient capacity (length)
     * to accommodate a new item
     */
    @SuppressWarnings("unchecked")  // this will stop Java complaining about the cast
    private void ensureCapacity() {
        if (count == data.length) {   //if count is equal to the length of data a new array is made doubling the initial size, it then makes the data collection == to the temp collection
            E[] temp = (E[]) new String[data.length * 2];   //effectivly increasing the size of the array
            for (int i = 0; i < data.length; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
    }


// It is much more convenient to define the following method
// and use it in the methods above.

    /**
     * Finds the index of where an item is in the dataarray,
     * (or where it ought to be, if it's not there).
     * Assumes that the item is not null.
     * <p>
     * Uses binary search and requires that the items are kept in order.
     * Uses the "compareTo()" method to compare two items with each other, e.g., as in
     * "item1.compareTo(item2)", resulting in
     * 0, if the items are equal,
     * a value lower than 0, if item1 is smaller than item2,
     * a value greater than 0, if item1 is greater than item2.
     *
     * @return the index of the item, or
     * the index where it should be inserted, if it is not present
     *
     * This is the modified findindex of which now runs binary search
     */
    @SuppressWarnings("unchecked")  // stops Java complaining about the call to compare
    private int findIndexOf(Comparable<E> item) {
        if (count == 0) {        //this is where the binary search happens that reduces the time and computational power it takes to search the collection
            return count;        // if count == 0 it returns the count variable to break the loop and show that the collection is empty
        }
        int low = 0;             //Sets up three variables to keep track of the start and end values of index and the middle of the collection, this helps
        int high = count - 1;    //the search algorithm becase the array always knows where the middle is and so the collection can contnually half using the three
        int mid;                 //variables to find a single value, while the low is less or equal to the high variable,
        while (low <= high) {

            mid = (low + high) / 2;

            int compareValue = item.compareTo(data[mid]);   // the value we are trying to find
            if (compareValue == 0) return mid;       //returns the middle value,
            if (compareValue > 0) low = mid + 1;     //if greater than it changes low value to equal the middle, plus one(as mid is already checked)
            else high = mid - 1;                     //else it changes to the lower half of the collecton to search there.
        }
        return low;                  //Hopefully if found it will then return low,
    }


    /**
    Interpolation test.
     i think the idea is here? but it most certianly does not work, it has to be a sorted file, and alterations would need to be made to th rest of the class
     ultametly there would be huge amounts of for loops used to find the string, especially in cases where the string is very large. this actually takes more power
     and acts like multple iterators, still as O(n) therefore is not plausable.
     */
    private String findIndexOfI (Comparable<E> item) {
        String compareString = item.toString();
        for (int i = 0; i < data.length; i++) {
            if (data[i].toString().equals(item)) {
                return data[i].toString();
            } else {
                String compareSecond = compareString.substring(2, 3);
                if (data[i].toString().substring(2, 3).equals(compareSecond)) {
                    return data[i].toString();
                }

            }
        }
        return ("failed to find");
    }

// --- Iterator and Comparator --------------------------------------
/** ---------- The code below is already written for you ---------- **/

    /**
     * @return an iterator over the items in this set.
     */
    public Iterator<E> iterator() {
        return new SortedArraySetIterator(this);
    }

    private class SortedArraySetIterator implements Iterator<E> {
        // needs fields, constructor, hasNext(), next(), and remove()

        // fields
        private SortedArraySet<E> set;
        private int nextIndex = 0;
        private boolean canRemove = false;

        // constructor
        private SortedArraySetIterator(SortedArraySet<E> s) {
            set = s;
        }

        /**
         * @return true if iterator has at least one more item
         */
        public boolean hasNext() {
            return (nextIndex < set.count);
        }

        /**
         * Returns the next element or throws a
         * NoSuchElementException exception if none exists.
         *
         * @return next item in the set
         */
        public E next() {
            if (nextIndex >= set.count)
                throw new NoSuchElementException();

            canRemove = true;

            return set.data[nextIndex++];
        }

        /**
         * Removes the last item returned by the iterator from the set.
         * Can only be called once per call to next.
         */
        public void remove() {
            if (!canRemove)
                throw new IllegalStateException();

            set.remove(set.data[nextIndex - 1]);
            canRemove = false;
        }
    }
}

