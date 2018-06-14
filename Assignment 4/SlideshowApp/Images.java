// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 4
 * Name:
 * Username:
 * ID:
 */

import ecs100.UI;

import java.util.*;

/**
 * Class Images implements a list of images.
 * <p>
 * Each image is represented with an ImageNode object.
 * The ImageNode objects form a linked list.
 * <p>
 * An object of this class maintains the reference to the first image node and
 * delegates operations to image nodes as necessary.
 * <p>
 * An object of this class furthermore maintains a "cursor", i.e., a reference to
 * a location in the list.
 * <p>
 * The references to both first node and cursor may be null, representing an empty
 * collection.
 *
 * @author Thomas Kuehne
 */

public class Images implements Iterable<String> {
    private ImageNode head;     // the first image node
    private ImageNode cursor;   // the current point for insertion, removal, etc.
    private Stack<String> reverseImage = new Stack<String>(); //Stack initialized to reverse items

    /**
     * Creates an empty list of images.
     */
    public Images() {
        cursor = head = null;
    }

    /**
     * @return the fileName of the image at the current cursor position.
     * <p>
     * This method relieves clients of Images from knowing about image
     * nodes and the 'getFileName()' method.
     */
    public String getImageFileNameAtCursor() {
        // deal with an inappropriate call gracefully
        if (cursor == null)
            return "";  // the correct response would be to throw an exception.

        return cursor.getFileName();
    }

    /**
     * @return the current cursor position.
     * <p>
     * Used by clients that want to save the current selection
     * in order to restore it after an iteration.
     */
    public ImageNode getCursor() {
        return cursor;
    }

    /**
     * Sets the cursor to a new node.
     *
     * @param newCursor the new cursor position
     */
    public void setCursor(ImageNode newCursor) {
        cursor = newCursor;
    }

    /**
     * Positions the cursor at the start
     * <p>
     * For the core part of the assignment.
     */
    public void moveCursorToStart() {
        cursor = head;  //cursor is set to the head position.

    }

    /**
     * Positions the cursor at the end
     * <p>
     * For the core part of the assignment.
     * <p>
     * HINT: Consider the list could be empty.
     */
    public void moveCursorToEnd() {
        if (cursor == null || cursor.getNext() == null)
            return;

        while (cursor.getNext() != null) {   //Iterates through the linked list and is probably memory intensive.
            cursor = cursor.getNext();
        }

    }

    /**
     * Moves the cursor position to the right.
     */
    public void moveCursorRight() {
        // is it impossible for the cursor to move right?
        if (cursor == null || cursor.getNext() == null)
            return;

        // advance the cursor
        cursor = cursor.getNext();
    }

    /**
     * Moves the cursor position to the left.
     * <p>
     * Assumption: 'cursor' points to a node in the list!
     */
    public void moveCursorLeft() {
        // is it impossible for the cursor to move left?
        if (head == null || cursor == head)
            return;

        // setup an initial attempt to a reference to the node before the current cursor 
        ImageNode previous = head;

        // while the node before the cursor has not been found yet, keep advancing
        while (previous.getNext() != cursor) {
            previous = previous.getNext();
        }

        cursor = previous;
    }

    /**
     * Returns the number of images
     *
     * @return number of images
     */
    public int count() {
        if (head == null)          // is the list empty?
            return 0;                // yes -> return zero

        return head.count();   // no -> delegate to linked structure
    }

    /**
     * Adds an image after the cursor position
     * <p>
     * For the core part of the assignment.
     *
     * @param imageFileName the file name of the image to be added
     *                      <p>
     *                      HINT: Consider that the current collection may be empty.
     *                      HINT: Create a new image node here and and delegate further work to method 'insertAfter' of class ImageNode.
     *                      HINT: Pay attention to the cursor position after the image has been added.
     */
    public void addImageAfter(String imageFileName) {
        if (head == null) {cursor = head = new ImageNode(imageFileName, null);}
        //Checks if head == null if so the head = cursor, and a new image node is added.

        else{cursor.insertAfter(cursor = new ImageNode(imageFileName, cursor));
        //creates a new node, after the current cursor. then shifts the cursor right.
        }
    }

    /**
     * Adds an image before the cursor position
     * <p>
     * For the completion part of the assignment.
     *
     * @param imageFileName the file name of the image to be added
     *                      <p>
     *                      HINT: Create a new image node here and then
     *                      1. Consider that the current collection may be empty.
     *                      2. Consider that the head may need to be adjusted.
     *                      3. if necessary, delegate further work to 'insertBefore' of class ImageNode.
     *                      HINT: Pay attention to the cursor position after the image has been added.
     */
    public void addImageBefore(String imageFileName) {  //CANNOT CHANGE TILL COMP IS IMPLEMENTED
        if (head == null) {head = cursor = new ImageNode(imageFileName, null);
        //Checks if head == null,if so the head = cursor, and a new image node is added.

        } else if (!cursor.equals(head)) { head.insertBefore((new ImageNode(imageFileName, cursor)), cursor);
            moveCursorLeft();
            // if the cursor does not = the head, then it runs insertbefore method, at the cursor point, then moves the cursor to be on the added image.

        } else {head = new ImageNode(imageFileName, head);
            moveCursorToStart();
            //the last of the cases. what if the head is not null, but that is he only item in the list, and cursor == the head.
            // creates a new image node. and then shifts the cursor back to the start.
        }
    }

    /**
     * Removes all images.
     * <p>
     * For the core part of the assignment.
     */
    public void removeAll() {
        head = cursor = null;  //Basically it means the pointers forget where they are pointing too.
    }

    /**
     * Removes an image at the cursor position
     * <p>
     * For the core part of the assignment.
     * <p>
     * HINT: Consider that the list may be empty.
     * <p>
     * HINT: Handle removing at the very start of the list in this method and
     * delegate the removal of other nodes by using method 'removeNodeUsingPrevious' from class ImageNode.
     * <p>
     * HINT: Make sure that the cursor position after the removal is correct.
     */

    public void remove() {
        if (head == null || cursor == null) {return;}
            //Checks if the head or the cursor, equal null. if so moves them forward to the same position

        if (cursor == head) {head = cursor = head.getNext(); return;}

        ImageNode tempNode = head.nodeBefore(cursor);
        cursor.removeNodeUsingPrevious(tempNode);
        if(tempNode.getNext()!=null){cursor = tempNode.getNext();}
        else {cursor = tempNode;}
    }

    /**
     * Reverses the order of the image list so that the last node is now the first node, and
     * and the second-to-last node is the second node, and so on
     * <p>
     * For the completion part of the assignment.
     * <p>
     * HINT: Make sure there is something worth reversing first.
     * HINT: You will have to use temporary variables.
     * HINT: Don't forget to update the head of the list.
     */
    public void reverseImages() {
        if (head == null || cursor == null) {
            return;
        }
        //Checks if the head or the cursor, equal null. if so moves them forward to the same position  --> Is something worth removing?

        cursor = head;
        while (cursor.getNext() != null) {
            ImageNode temporary = cursor;
            reverseImage.push(temporary.getFileName());
            cursor = cursor.getNext();
            // While the cursors next is not null, creates a temp imagenode, pushes that node on a stack, cursor is incremented (if you forget this its hilarious, never crashed the UI before this assignment.)
        }
        if (cursor.getNext() == null) {head = cursor;}
        //Since the final image is not added, this is very useful. and this makes it the new head

        while (!reverseImage.isEmpty()) {
            if (head == null) {cursor = head = new ImageNode(reverseImage.pop(), null);}
            //While the stack is not empty, Checks if head == null if so the head = cursor, and a new image node is added. (This is my add method.)
            else {
                cursor.insertAfter(cursor = new ImageNode(reverseImage.pop(), cursor));
                //creates a new node, after the current cursor. then shifts the cursor right.
            }
        }
    }


    public ImageNode getHead() {//needs fixing
        return head;
    } //Returns the position of the head

    /**
     * @return an iterator over the items in this list of images.
     * <p>
     * For the completion part of the assignment.
     */
    public Iterator<String> iterator() {return new ImagesIterator(this);} // creates a new iterator object,

    /**
     * Support for iterating over all images in an "Images" collection.
     * <p>
     * For the completion part of the assignment.
     */
    private class ImagesIterator implements Iterator<String> {
        ImageNode image; //stores the head variable

        private ImagesIterator(Images images) {  //Needs Fixing
            image =images.getHead();
        }

        /**
         * @return true if iterator has at least one more item
         * <p>
         * For the completion part of the assignment.
         */
        public boolean hasNext() {
            return (image != null);
        }
        //while the next image does not = null there is a next value

        /**
         * Returns the next element or throws a
         * NoSuchElementException exception if none exists.
         * <p>
         * For the completion part of the assignment.
         *
         * @return next item in the set
         */
        public String next() {
            if (!hasNext()) throw new NoSuchElementException("Yeah nah");  //throws an exception if there is no next image
            String imageName = image.getFileName(); //retrives the filename at the current position
            image = image.getNext(); //increments along
            return imageName; //returns the file name
        }

        /**
         * Removes the last item returned by the iterator from the set.
         * Not supported by this iterator.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

