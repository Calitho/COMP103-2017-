// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 8
 * Name:
 * Username:
 * ID:
 */

import java.util.*;

/**
 * Implements a priority queue based on a heap that is
 * represented with an array.
 */
public class HeapArrayQueue<E extends Comparable<? super E>> extends AbstractQueue<E> {

    @SuppressWarnings("unchecked")
    private E[] data = (E[]) (new Comparable[7]);
    private int count = 0;
    public int size() {
        return count;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    /**
     * Returns the element with the top priority in the queue.
     * <p>
     * HINT: This is like 'poll()' without the removal of the element.
     *
     * @returns the next element if present, or 'null' if the queue is empty.
     */
    public E peek() {
        return data[0]; //Return the value at the first index,
    }
    /**
     * Removes the element with the top priority from the queue and returns it.
     * <p>
     * HINT: The 'data' array should contain a heap so the element with the top priority
     * sits at index '0'. After its removal, you need to restore the heap property again,
     * using 'sinkDownFromIndex(...)'.
     *
     * @returns the next element in the queue, or 'null' if the queue is empty.
     */
    public E poll() {
        if (count == 0)return null; //if the count is zero there are no nodes, if not set a temp variable to hold the fsrt indexs value.
        E dataTemp = data[0];
        data[0] = null; //set position 1 to null.
        sinkDownFromIndex(0); //sink down to chop it off. and return the tempvariable.
        count--;
        return dataTemp;
    }
    /**
     * Enqueues an element.
     * <p>
     * If the element to be added is 'null', it is not added.
     * <p>
     * HINT: Make use of 'ensureCapacity' to make sure that the array can
     * accommodate one more element.
     *
     * @param element - the element to be added to the queue
     * @returns true, if the element could be added
     */
    public boolean offer(E element) {
        if (element == null) {  //does not add the element is the elment is null, if not it checks the cpacity and sets the value at index[count]
            return false; //to the value being added, then bubbles it up.
        }
        ensureCapacity();
        data[count] = element;
        bubbleUpFromIndex(count);
        count++;
        return true;
    }
    /**
     * Moves an element down the heap by swapping it with one of
     * its children until it's in the right position
     * <p>
     * HINT: Using recursion will make this method simpler
     *
     * @param nodeIndex - the position of the element to be moved down
     */
    //The following method, checks how many if any and the location/priority of the children, whether one child is of higher priority than the other.
    private void sinkDownFromIndex(int nodeIndex) {
        int indexNext;
        if (2*nodeIndex>count)return; //if there is no children.
        if (data[1+(2*nodeIndex)] == null && data[2+(2*nodeIndex)]==null)return;//if there is no children.
        else if((data[1 + (2 * nodeIndex)] == null && data[2+(2*nodeIndex)]!=null)){indexNext=2+(2*nodeIndex);} //One Child
        else if((data[1 + (2 * nodeIndex)] != null && data[2+(2*nodeIndex)]==null)){indexNext=1+(2*nodeIndex);} //One Child
        else{indexNext = data[1+(2*nodeIndex)].compareTo(data[2+(2*nodeIndex)])>0 ? 1+(2*nodeIndex):2 +(2*nodeIndex);} //if there are 2 children

        if (data[nodeIndex]==null){
            data[nodeIndex]=data[indexNext];
            sinkDownFromIndex(indexNext);
        }
        else if (data[nodeIndex].compareTo(data[indexNext])>0){
            swap(nodeIndex,indexNext);
            sinkDownFromIndex(indexNext);
        }
    }
    /**
     * Bubbles (swaps) an element up the heap until it's in the right
     * position
     * <p>
     * HINT: Using recursion will make this method simpler
     *
     * @param nodeIndex - the position of the element to be moved up
     */
    private void bubbleUpFromIndex(int nodeIndex) {
        if(nodeIndex==0)return; //if zero its at top.
        if(data[nodeIndex].compareTo(data[nodeIndex/2])<0){ //if not zero it shifts it through the que checking its priority, to check it gets to the right place.
            swap(nodeIndex,nodeIndex/2);
            bubbleUpFromIndex(nodeIndex/2);
        }
    }
    /**
     * Swaps two elements in the supporting array.
     */
    private void swap(int from, int to) {
        E temp = data[from];
        data[from] = data[to];
        data[to] = temp;
    }

    /**
     * Increases the size of the supporting array, if necessary
     */
    private void ensureCapacity() {
        if (count == data.length) {
            @SuppressWarnings("unchecked")
            E[] newData = (E[]) new Comparable[data.length * 2];

            // copy data elements
            for (int loop = 0; loop < count; loop++) {
                newData[loop] = data[loop];
            }
            data = newData;
        }
        return;
    }
    // no iterator implementation required for this assignment
    public Iterator<E> iterator() {
        return null;
    }
}
