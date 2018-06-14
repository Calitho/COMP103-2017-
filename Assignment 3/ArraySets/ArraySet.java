// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 3
 * Name:
 * Username:
 * ID:
 */

import ecs100.UI;

import java.util.*;

/**
 * ArraySet - a Set collection;
 * <p>
 * The implementation uses an array to store the items
 * and a count variable to store the number of items in the array.
 * <p>
 * The items in the set should be stored in positions
 * 0, 1,... (count-1) of the array.
 * <p>
 * The length of the array when the set is first created should be 10.
 * <p>
 * The items need not be in any particular order, and may change the
 * order when an item is removed. There is no need to shift all the items
 * up or down to keep them in a specific order.
 * <p>
 * Note that a set does not allow null items or duplicates.
 * Attempting to add null should throw an IllegalArgumentException exception
 * Adding an item which is already present should simply return false, without
 * changing the set.
 * It should always compare items using equals()  (not using ==)
 * When the array is full, it will create a new array of double the current
 * size, and copy all the items over to the new array
 */

public class ArraySet<E> extends AbstractSet<E> {

    // Data fields
    private static final int INITIALSIZE = 10;
    private int count;
    private E[] data;


    /**
     * Intilaizes a new ArraySet with an initial size, and intializes a count varable which is responsible
     * for keeping count of the valies in the Arrayset.
     */
    // --- Constructors --------------------------------------
    @SuppressWarnings("unchecked")  // this will stop Java complaining
    public ArraySet() {
        data = (E[]) new Object[INITIALSIZE];
        count = 0;
    }
    // --- Methods --------------------------------------

    /**
     * @return the number of items in the set
     */
    public int size() {
        return count;
    }

    /**
     * Adds the specified item to this set
     * (if it is not already in the set).
     * Will not add a null value (throws an IllegalArgumentException in this case).
     *
     * @param item the item to be added to the set
     * @return true if the collection changes, and false if it did not change.
     */
    public boolean add(E item) {
        if (item == null) {     //Checks if the item is null. if the item is it throws an exception.
            throw new IllegalArgumentException("Yeah Nah, No thanks null.");
        }
        if (contains(item)) {   //also checks if the item in question is already there, a sets cannot contain duplicates
            return false;
        }


        ensureCapacity();       //Passing the above tests, ensurecapaity is called to make sure there is space for te value
        data[count] = item;     //adds item, is ensurecapicity works. and increments count, also returns true to show it has added the value
        count++;
        return true;
    }

    /**
     * @return true if this set contains the specified item.
     */
    public boolean contains(Object item) {
        boolean doesContain = false;         //This is an expesive way to do it, but shows general process
        for (int i = 0; i < count; i++) {    //Cycles through values and if the object at that index == item, then it does contain it and returns true
            if (data[i].equals(item))
                doesContain = true;
        }
        return doesContain;   // returns false otherwise.
    }

    /**
     * Removes an item matching a given item.
     *
     * @return true if the item was present and then removed.
     * Makes no changes to the set and returns false if the item is not present.
     *
     * simlar to add but decrements count instead, the way it works is buy replacing the value removed with null which
     * can be very problematic
     */
    public boolean remove(Object item) {
        int i = findIndexOf(item);
        if (i >= 0) {
            count--;               //removing so decrements count.
            data[i] = data[count];
            data[count] = null;    //revised and removed in completion
            return true;
        }
        return false;
    }

    /**
     * Ensures data array has sufficient capacity (length)
     * to accommodate a new item
     */
    @SuppressWarnings("unchecked")  // this will stop Java complaining
    private void ensureCapacity() {
        if (count == data.length) {    //Checks if the count is == to the length of data, and forms a temp collection to then cycle through the values
            E[] temp = data;
            data = (E[]) new Object[temp.length * 2];
            for (int i = 0; i < temp.length; i++) {
                data[i] = temp[i];
            }
        }
    }

    // You may find it convenient to define the following method and use it in
    // the methods above, but you don't need to do it this way.

    /**
     * Finds the index of an item in the data array.
     * Assumes that the item is not null.
     *
     * @return the index of the item, or -1 if not present
     *
     * this is what mainly sets completion apart from core, this here is a simple iterator that works through all
     * the values, THIS IS EXTREMLY EXSPENSIVE and should not be implemented in a real time situation as it takes a huge amount of time
     */
    private int findIndexOf(Object item) {

        for (int i = 0; i < data.length; i++) {    //Iterates through the collections, checking if value not null, if the item is in the collection, that object
            if (data[i] != null) {                 // at that index is returned.
                if (data[i].equals(item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /** ---------- The code below is already written for you ---------- **/

    /**
     * @return an iterator over the items in this set.
     */
    public Iterator<E> iterator() {
        return new ArraySetIterator<E>(this);
    }

    private class ArraySetIterator<E> implements Iterator<E> {

        // needs fields, constructor, hasNext(), next(), and remove()

        // fields
        private ArraySet<E> set;
        private int nextIndex = 0;
        private boolean canRemove = false;

        // constructor
        private ArraySetIterator(ArraySet<E> s) {
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

            nextIndex--;
            set.count--;
            set.data[nextIndex] = set.data[set.count];
            set.data[set.count] = null;
            canRemove = false;
        }
    }
}

