// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 9
 * Name: Daniel Armstrong
 * Username: armstrdani1
 * ID:300406381
 */

import java.util.Queue;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * ArrayQueueTest
 * A JUnit class for testing an ArrayQueue
 */

public class ArrayQueueTest {
    private Queue<String> testQueue;

    @Before
    public void initializeEmptyQueue() {
        testQueue = new ArrayQueue<String>();
    }

    @Test
    public void testEmptyQueueIsEmpty() {
        assertTrue("ArrayQueue Should be Empty", testQueue.isEmpty());
    }

    @Test
    public void isEmptyTest() {
        assertEquals("Empty Queue Should have 0 size", 0, testQueue.size());
    }

    @Test
    public void testPeek() {
        assertTrue("V1 should be added", testQueue.offer("V1"));
        assertEquals("Queue Should contain V1", "V1", testQueue.peek());
        assertEquals("Queue size should be 1", 1, testQueue.size());
    }

    @Test
    public void testOffer() {
        for (int i = 1; i <= 20; i++) {
            assertTrue("Queue Should add item: V" + i, testQueue.offer("V" + i));
            assertFalse("Queue Should no longer be empty", testQueue.isEmpty());
            assertEquals("Queue Size should be" + i + " after: V" + i + "adds", i, testQueue.size());
        }
    }

    @Test
    public void sizeTest() {
        assertTrue("V1 should be added", testQueue.offer("V1"));
        assertEquals("Queue size should be 1", 1, testQueue.size());
    }

    @Test
    public void testAddingNull() {
        assertFalse("null should not be able to be added", testQueue.offer(null));
    }

    @Test
    public void testPoll() {
        assertTrue("Queue Should add item: V1", testQueue.offer("V1"));
        assertEquals("Queue Should remove using poll", "V1", testQueue.poll());
        assertEquals("Queue Size should be 0 after: V1 is removed", 0, testQueue.size());
    }

    @Test
    public void testRemovingAllItems() {
        for (int i = 1; i <= 20; i++) {
            assertTrue("Queue Should add item: V" + i, testQueue.offer("V" + i));
            assertFalse("Queue Should no longer be empty", testQueue.isEmpty());
        }
        for (int j = 1; j <= 20; j++) {
            assertEquals("Queue Should remove item: V" + j, "V" + j, testQueue.poll());
        }
        assertEquals("Queue Size should be 0 after all items are removed", 0, testQueue.size());
    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("ArrayQueueTest");
    }

}
