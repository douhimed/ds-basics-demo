import datastructure.basics.Linked;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyLinkedTests {

    public static final String A = "A";
    public static final String B = "B";
    public static final String C = "C";
    public static final String D = "D";
    public static final String A_B_C = "A, B, C";
    public static final String EMPTY = "";
    public static final String C_B_A = "C, B, A";
    public static final String B_C = "B, C";

    private Linked<String> emptyLinkedList;
    private Linked<String> linkedList;

    @Before
    public void setUp() throws Exception {
        emptyLinkedList = new Linked<>();
        linkedList = new Linked<>();
        linkedList.addLast(A);
        linkedList.addLast(B);
        linkedList.addLast(C);
    }

    @Test
    public void addLast_Test() {
        emptyLinkedList.addLast(A);
        emptyLinkedList.addLast(B);
        emptyLinkedList.addLast(C);
        assertEquals(A_B_C, emptyLinkedList.print());
    }

    @Test
    public void addFirst_Test() {
        emptyLinkedList.addFirst(A);
        emptyLinkedList.addFirst(B);
        emptyLinkedList.addFirst(C);
        assertEquals(C_B_A, emptyLinkedList.print());
    }

    @Test
    public void deleteFirst_Tests() {
        linkedList.deleteFirst();
        assertEquals(B_C, linkedList.print());
        linkedList.deleteFirst();
        assertEquals(C, linkedList.print());
        linkedList.deleteFirst();
        assertEquals(EMPTY, linkedList.print());
    }

    @Test
    public void indexOf_Tests() {
        assertEquals(0, linkedList.indexOf(A));
        assertEquals(1, linkedList.indexOf(B));
        assertEquals(2, linkedList.indexOf(C));
        assertEquals(-1, linkedList.indexOf(D));
    }

    @Test
    public void contains_Tests() {
        assertTrue(linkedList.contains(A));
        assertTrue(linkedList.contains(B));
        assertFalse(linkedList.contains(D));
    }

    @Test
    public void deleteLast_Tests() {
        linkedList.deleteLast();
        assertEquals("A, B", linkedList.print());
        linkedList.deleteLast();
        assertEquals(A, linkedList.print());
        linkedList.deleteLast();
        assertEquals(EMPTY, linkedList.print());
    }

    @Test
    public void size_Tests() {
        assertEquals(0, emptyLinkedList.size());
        assertEquals(3, linkedList.size());
        linkedList.deleteLast();
        assertEquals(2, linkedList.size());
        linkedList.deleteLast();
        assertEquals(1, linkedList.size());
    }

    @Test
    public void reverse_Tests() {
        assertEquals(A_B_C, linkedList.print());
        linkedList.reverse();
        assertEquals(C_B_A, linkedList.print());
        assertEquals(EMPTY, emptyLinkedList.print());
    }

    @Test
    public void getKthFromTheENd_Tests() {
        linkedList.addLast(D);
        linkedList.addLast("E");
        linkedList.addLast("F");
        assertEquals("F", linkedList.getKthFromTheEnd(1));
        assertEquals("C", linkedList.getKthFromTheEnd(4));
        assertEquals("B", linkedList.getKthFromTheEnd(5));
    }

    @Test
    public void hasLoop() {
        assertFalse(linkedList.hasLoop());
        linkedList.addLast(D);
        linkedList.addLast("E");
        linkedList.addLast("F");
        linkedList.createCircularLinkedList(4);
        assertTrue(linkedList.hasLoop());

    }
}
