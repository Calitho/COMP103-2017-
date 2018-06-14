// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 6
 * Name:
 * Username:
 * ID:
 */

import java.io.*;
import java.util.ArrayList;

import ecs100.*;

/**
 * Implements a binary search tree node.
 *
 * @author: Thomas Kuehne (based on previous code)
 */

public class BSTNode<E extends Comparable<E>> {

    private E value;
    public BSTNode<E> left;
    public BSTNode<E> right;

    // constructs a node with a value
    public BSTNode(E value) {
        this.value = value;
    }

    // Getters...

    public E getValue() {
        return value;
    }

    public BSTNode<E> getLeft() {
        return left;
    }

    public BSTNode<E> getRight() {
        return right;
    }

    /**
     * Returns true if the subtree formed by the receiver contains 'item'
     * <p>
     * CORE
     * <p>
     * ASSUMPTION: 'item' is not 'null'.
     * <p>
     * HINT: A recursive approach leads to a very short and simple code.
     * <p>
     * HINT: use 'compareTo(...)' in order to compare the parameter
     * with the data in the node.
     * <p>
     * HINT: Make sure that you invoke 'compareTo' by always using
     * the same receiver / argument ordering throughout the program, e.g.,
     * always use the item as the receiver of 'compareTo'.
     *
     * @param item - the item to check for
     * @returns true if the subtree contained 'item'
     * <p>
     * <p>
     * <p>
     * If the value of current nodes = the item, return true, if compareto item is greater then 0
     * return the recurse left, else recurse right
     */
    public boolean contains(E item) {
        if (this.value.equals(item)) {
            return true;
        }
        if (this.value.compareTo(item) < 0) {
            if (left != null) {
                return left.contains(item);
            } //needs check
        } else {
            if (right != null) {
                return right.contains(item);
            }
        }

        // no matching node was found
        return false;
    }

    /**
     * Adds an item to the subtree formed by the receiver.
     * <p>
     * CORE
     * <p>
     * Must not add an item, if it is already in the tree.
     * <p>
     * HINT: use 'compareTo(...)' in order to compare the parameter
     * with the data in the node.
     *
     * @param item - the value to be added
     * @returns false, if the item was in the subtree already. Returns true otherwise.
     */

    public boolean add(E item) {
        if (this.value.equals(item)) {  //already present
            return false;
        }
        if (this.value.compareTo(item) < 0) {//insert as a new left child, and return true
            if (left == null) {
                left = new BSTNode<>(item);
                return true;
            } else {
                return left.add(item);
            }//else call add(value) on left child
        } else if (this.value.compareTo(item) > 0) {
            if (right == null) { // else belongs on right && insert as a new right child, and return true
                right = new BSTNode<>(item);//if there is no right child
                return true;
            } else {
                return right.add(item);
            }//else call add(value) on right child
        }
        return false;
    }

    /**
     * Returns the height of the receiver node.
     * <p>
     * CORE
     * <p>
     * HINT: The number of children the receiver node may have, implies
     * four cases to deal with (none, left, right, left & right).
     *
     * @returns the height of the receiver
     * <p>
     * Initializes two count variables, left and right to counts the elements then returns the max of the two count variables
     */
    public int height() {
        int leftHeight = 0;
        int rightHeight = 0;
        if (left == null && right == null) {
            return 0;
        }
        if (left != null) {
            leftHeight = left.height() + 1;
        }
        if (right != null) {
            rightHeight = right.height() + 1;
        }
        return Math.max(leftHeight, rightHeight);
    }

    /**
     * Returns the length of the shortest branch in the subtree formed by the receiver.
     * <p>
     * COMPLETION
     *
     * @returns the minimum of all branch lenghts starting from the receiver.
     */
    public int minDepth() {  //Almost the opposite to Height. (Its the opposite)
        int leftHeight = 0; //Sets initial count variables to 0
        int rightHeight = 0;
        if (getLeft() == null && getRight() == null) { //if left and right is null return 0 else go left else go right
            return 0;
        }
        if (getLeft() != null) {
            leftHeight = left.minDepth() + 1;
        }
        if (getRight() != null) {
            rightHeight = right.minDepth() + 1;
        }
        return Math.min(leftHeight, rightHeight);
    }

    /**
     * Removes an item in the subtree formed by the receiver.
     * <p>
     * COMPLETION
     * <p>
     * ASSUMPTION: The item to be removed does exist.
     * The case that it cannot be found, should be dealt with before this method is called.
     * <p>
     * Performs two tasks:
     * 1. locates the node to be removed, and
     * 2. replaces the node with a suitable node from its subtrees.
     * <p>
     * HINT: use 'compareTo(...)' in order to compare the parameter
     * with the data in the node.
     * <p>
     * HINT: For task 2, you should use call method 'replacementSubtreeFromChildren'
     * to obtain this node.
     * <p>
     * HINT: When replacing a node, it is sufficient to change the value of the existing node
     * with the value of the node that conceptually replaces it. There is no need to actually
     * replace the node object as such.
     *
     * @param item - the item to be removed
     * @returns the reference to the subtree with the item removed.
     * <p>
     * HINT: Often the returned reference will be the receiver node, but it is possible that
     * the receiver itself needs to be removed. If you use a recursive approach, the
     * latter case is the base case.
     */
    public BSTNode<E> remove(E item) {
        if (item.equals(value)) {
            return replacementSubtreeFromChildren(this.left, this.right);
        }
// could not solve easily, have to defer to children
        else if (value.compareTo(item) < 0) {  //if left not null remove the left item recursively else go right
            if (left != null)
                left = left.remove(item);
        } else if (value.compareTo(item) > 0) {
            if (right != null)
                right = right.remove(item);
        }
        // there was no need to replace the receiver node
        return this;
    }

    /**
     * Returns a replacement subtree for the receiver node (which is to be removed).
     * <p>
     * COMPLETION
     * <p>
     * The replacement subtree is determined from the children of the node to be removed.
     * <p>
     * HINT: There are several cases:
     * - node has no children    => return null
     * - node has only one child => return the child
     * - node has two children   => return the current subtree but with
     * a) its (local) root replaced by the leftmost node in the right subtree, and
     * b) the leftmmost node in the right subtree removed.
     *
     * @param left  - the left subtree from which to include items.
     * @param right - the right subtree from which to include items.
     * @returns a reference to a subtree which contains all items from 'left' and 'right' combined.
     */
    private BSTNode<E> replacementSubtreeFromChildren(BSTNode<E> left, BSTNode<E> right) { //lecture slides
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        BSTNode<E> leftMostNode = right.getLeftmostNode();  //set leftmostNode to the leftmost node within this.right
        value = leftMostNode.value; //set this.value to be leftmostNode.value now that leftmostNodes value is copied, leftmostNode can be removed

        this.right = right.remove(value);//set this.right to be the result of right.remove(value)
        return this;
    }

    /**
     * Returns the leftmost node in the subtree formed by the receiver.
     * <p>
     * COMPLETION
     * <p>
     * HINT: The code is very simple. Just keep descending left branches,
     * until it is no longer possible.
     *
     * @returns a reference to the leftmost node, starting from the receiver.
     */
    private BSTNode<E> getLeftmostNode() {
        if (left == null) {  //if left is null that is the furthest left it can go else, go further left recursively
            return this;
        } else return left.getLeftmostNode();
    }

    /**
     * Prints all the nodes in a subtree to a stream.
     *
     * @param stream - the output stream
     */
    public void printAllToStream(PrintStream stream) {
        if (left != null)
            left.printAllToStream(stream);

        stream.println(value);

        if (right != null)
            right.printAllToStream(stream);
    }

    /**
     * Prints all the nodes in a subtree on the text pane.
     * <p>
     * Can be useful for debugging purposes, but
     * is most useful on small sample trees.
     * <p>
     * Usage: node.printAll("").
     */
    public void printAll(String indent) {
        if (right != null)
            right.printAll(indent + "    ");

        UI.println(indent + value);

        if (left != null)
            left.printAll(indent + "    ");
    }

    public ArrayList<BSTNode<E>> List() {
        ArrayList<BSTNode<E>> Nodes = new ArrayList<>();
        if (right != null) Nodes.addAll(right.List());
        Nodes.add(this);
        if (left != null) Nodes.addAll(left.List());
        return Nodes;
    }

}
