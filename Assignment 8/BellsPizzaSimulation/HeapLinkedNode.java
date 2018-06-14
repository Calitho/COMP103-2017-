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

public class HeapLinkedNode <E extends Comparable<? super E> > {
    private E value;
    private HeapLinkedNode<E> left;
    private HeapLinkedNode<E> right;
    private HeapLinkedNode<E> parent;
    private HeapLinkedNode<E> previous;


    //Creates new node with value
    public HeapLinkedNode(E value){
        this.value=value;
    }
    //Get methods. returns values of certain nodes
    public E getValue() {
        return value;
    }

    public HeapLinkedNode<E> getParent() {
        return parent;
    }

    public HeapLinkedNode<E> getLeft() {
        return left;
    }

    public HeapLinkedNode<E> getRight() {
        return right;
    }

    public HeapLinkedNode<E> getPrevious() {
        return previous;
    }
    //Set methods, which change several values to entered values.
    public void setValue(E value) {
        this.value = value;
    }

    public void setLeft(HeapLinkedNode<E> left) {
        this.left = left;
    }

    public void setRight(HeapLinkedNode<E> right) {
        this.right = right;
    }

    public void setParent(HeapLinkedNode<E> parent) {
        this.parent = parent;
    }
    public void setPrevious(HeapLinkedNode<E> previous) {
        this.previous = previous;
    }
}

