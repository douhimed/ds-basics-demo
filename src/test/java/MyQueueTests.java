import ds1.MyQueue;
import ds1.MyQueueV2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyQueueTests {

    public static final String EMPTY_QUEUE_STRING = "[]";
    public static final String FULL_10_20_30 = "[10, 20, 30]";

    private MyQueue emptyQueue;
    private MyQueue values;

    private MyQueueV2<Integer> emptyQueueV2;
    private MyQueueV2<Integer> valuesV2;

    @Before
    public void setUp() throws Exception {
        emptyQueue = new MyQueue(3);
        values = new MyQueue(3);
        emptyQueueV2 = new MyQueueV2<>();
        valuesV2 = new MyQueueV2<>();
        for (int i = 1; i <= 3; i++) {
            values.add(String.valueOf(i * 10));
            valuesV2.add(i * 10);
        }
    }

    @Test
    public void isFull_SHouldReturnTrueIfFillOtherwiseFalse() {
        assertTrue(values.isFull());
        assertFalse(emptyQueue.isFull());
    }

    @Test
    public void isEMpty_ShouldReturnTrueIFEMpty() {
        assertFalse(values.isEmpty());
        assertTrue(emptyQueue.isEmpty());
    }

    @Test
    public void size_ShouldReturnSize() {
        assertEquals(3, values.size());
        assertEquals(0, emptyQueue.size());
    }

    @Test
    public void toString_Test() {
        assertEquals(EMPTY_QUEUE_STRING, emptyQueue.toString());
        assertEquals(FULL_10_20_30, values.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void remove_ShouldRemoveHeadOtherwiseIfEmptyThrowException() {
        for (int i = 1; i <= 3; i++)
            assertEquals(String.valueOf( i * 10), values.remove());
        values.remove();
    }

    @Test
    public void add_CaseCircularArray() {
        assertEquals(String.valueOf(10), values.remove());
        assertEquals("[20, 30]", values.toString());
        assertEquals(String.valueOf(20), values.remove());
        assertEquals("[30]", values.toString());
        values.add(String.valueOf(40));
        assertEquals("30", values.element());
        assertEquals("30", values.remove());
        assertEquals("40", values.element());
    }

    @Test
    public void variantMethodsOfQueueV2() {
        assertTrue(emptyQueueV2.isEmpty());
        assertFalse(valuesV2.isEmpty());
        for (int i = 1; i <= 3; i++) {
            assertEquals(Integer.valueOf(i * 10), valuesV2.peek());
            assertEquals(Integer.valueOf(i * 10), valuesV2.remove());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void remove_IfEMptyShouldThrowIllegalStateException() {
        emptyQueueV2.remove();
    }
}


