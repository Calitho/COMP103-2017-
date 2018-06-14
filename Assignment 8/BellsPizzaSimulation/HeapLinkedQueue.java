// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 8
 * Name:
 * Username:
 * ID:
 */

import java.util.AbstractQueue;
import java.util.Iterator;

/**
 * Implements a priority queue based on a heap that is
 * represented with an array.
 */
public class HeapLinkedQueue<E extends Comparable<? super E>> extends AbstractQueue<E> {

    //This class is a modified copy of the original HeapArrayQueue class. modified with previous expereince from the assignments before. this one.

    @SuppressWarnings("unchecked")
    private HeapLinkedNode<E> root;
    private HeapLinkedNode<E> cursor;
    private int count = 0;

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the element with the top priority in the queue.
     * <p>
     * @returns the next element if present, or 'null' if the queue is empty.
     */
    public E peek() {
        if (isEmpty()) return null;
        return root.getValue();
    }

    /**
     * Removes the element with the top priority from the queue and returns it.
     * <p>
     *
     * @returns the next element in the queue, or 'null' if the queue is empty.
     */
    public E poll() {
        if (isEmpty()) return null; //Checks if the collection is empty, if not creates a temp variable, checks if the list has only one value, if so, it sets it all to null
        E dataTemp = root.getValue(); //and returns the single value
        if (root == cursor) {
            root = null;
            cursor = null;
            count--;
            return dataTemp;
        }
        E tempData = cursor.getValue();  //otherwise continues to remove the cursors parent value, as long as that is not null
        HeapLinkedNode<E> parent = cursor.getParent();
        if (parent.getRight() != null) {
            parent.setRight(null);
        } else {
            parent.setLeft(null);
        }
        cursor = cursor.getPrevious();
        root.setValue(tempData);
        sinkDownFromIndex(root);
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
        if (element == null) return false; //aslong as the element is not null, creates a new node "added node" if root is not null it looks for the distance between the cursor and the root
        HeapLinkedNode<E> addedNode = new HeapLinkedNode<E>(element);  //then with a for loop, it checks the parent node(copy of the cursor to prevent breaking the program.)
        if (root == null) {
            root = addedNode;
            cursor = addedNode;
            count++;
        } else {
            int distance = count-(((count-1)/2)+1); //count - (((1 / 2) * count - 1) + 1);
            HeapLinkedNode<E> parent = cursor;
            for (int i = 0; i < distance; i++) { //if the left is null, places it there otherwise places it in the right section.
                parent = parent.getPrevious(); //sets the cursor to the added node then calls bubble up(i have not changed method names though i probably should have).
            }
            if (parent.getLeft() == null) {
                parent.setLeft(addedNode);
            } else {
                parent.setRight(addedNode);
            }
            addedNode.setParent(parent);
            addedNode.setPrevious(cursor);
            cursor = addedNode;

            bubbleUpFromIndex(cursor);
            count++;
        }
        return true;
    }


    /**
     * Moves an element down the heap by swapping it with one of
     * its children until it's in the right position
     * <p>
     * HINT: Using recursion will make this method simpler
     *
     * //@param nodeIndex - the position of the element to be moved down
     */

    private void sinkDownFromIndex(HeapLinkedNode<E> node) {
        //if there is no children
        if (node.getLeft() == null && node.getRight() == null) {
            return; //returns as there are no children
        }
        //if there is one child node, either left or right.
        if (node.getLeft() != null && node.getRight() == null) {
            if (node.getValue().compareTo(node.getLeft().getValue()) > 0) {
                swap(node, node.getLeft());
            }
            return; //there is a left child
        }
        if (node.getLeft() == null && node.getRight() != null) {
            if (node.getValue().compareTo(node.getRight().getValue()) > 0) {
                swap(node, node.getRight());
            }
            return; //there is a right child
        }
        // if there are 2 children nodes.
        if (node.getRight().getValue().compareTo(node.getLeft().getValue()) < 0) { //Checks the priority of both children, to find the one with the highest priority.
            swap(node, node.getRight());
            sinkDownFromIndex(node.getRight()); //recursively calls down,
            return;
        } else {
            swap(node, node.getLeft());
            sinkDownFromIndex(node.getLeft());
            return;
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

    private void bubbleUpFromIndex(HeapLinkedNode<E> nodeIndex) {
        if (root == nodeIndex)return;  //if the node is equal to the root, the node is at the top, and so does not move.
        if (nodeIndex.getValue().compareTo(nodeIndex.getParent().getValue())<0) { //otherwise compares the priority and swaps the node to move the one with the highest priority up the queue
            swap(nodeIndex, nodeIndex.getParent());
            bubbleUpFromIndex(nodeIndex.getParent());
        }
        return;
    }

    /**
     * Swaps two elements in the supporting array.
     */
    private void swap(HeapLinkedNode<E> from, HeapLinkedNode<E> to) { //Modified to accommodate the node structure. does the same job as arrayQueue
        E temp = from.getValue();
        from.setValue(to.getValue());
        to.setValue(temp);
    }

    // no iterator implementation required for this assignment
    public Iterator<E> iterator() {
        return null;
    }
}
