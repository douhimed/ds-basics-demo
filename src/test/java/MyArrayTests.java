import ds1.Array;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MyArrayTests {

    private Array emptyArray;
    private Array array;

    @Before
    public void setUp() throws Exception {
        emptyArray = new Array(3);
        array = new Array(10);
        for (int i = 1; i <= 10; i++)
            array.add(i * 10);
    }

    @Test
    public void add_ShouldAddTheElementAndReturnIt() {
        assertEquals(10, emptyArray.add(10));
        assertEquals(20, emptyArray.add(20));
        assertEquals(30, emptyArray.add(30));
    }

    @Test
    public void add_IfArrayIsFullShouldExtends() {
        for (int i = 1; i <= 10; i++) {
            assertEquals(i * 10, emptyArray.add(i * 10));
        }
    }

    @Test
    public void size_ShouldReturnTheCorrectSIze() {
        assertEquals(0, emptyArray.size());
        assertEquals(10, array.size());
    }

    @Test(expected = NullPointerException.class)
    public void indexOf_ReturnTheIndexOfELementIfNullThrowException() {
        for (int i = 1; i <= 10; i++) {
            assertEquals(i - 1, array.indexOf(i * 10));
        }
        assertEquals(-1, array.indexOf(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAt() {
        for (int i = 1; i <= 10; i++) {
            assertEquals(i * 10 , array.get(i - 1));
        }
        assertEquals(10, array.get(1000));
        assertEquals(10, array.get(-1));
    }

    @Test
    public void deleteAT_ShouldDeleteANdShiftELementToLeft() {
        array.deleteAt(0);
        array.deleteAt(5);
        array.deleteAt(4);
        emptyArray.add(20);
        emptyArray.add(30);
        emptyArray.add(40);
        emptyArray.add(50);
        emptyArray.add(80);
        emptyArray.add(90);
        emptyArray.add(100);

        for (int i = 0; i < emptyArray.size(); i++) {
            assertEquals(emptyArray.get(i), array.get(i));
        }
    }

    @Test
    public void intersect_ReturnArrayOfCommunValues() {
        emptyArray.add(10);
        emptyArray.add(40);
        emptyArray.add(500);
        emptyArray.add(400);
        emptyArray.add(30);
        emptyArray.add(70);

        Array intersect = array.intersect(emptyArray);
        assertEquals(10, intersect.get(0));
        assertEquals(40, intersect.get(1));
        assertEquals(30, intersect.get(2));
        assertEquals(70, intersect.get(3));
    }

    @Test
    public void reverst_SOuldReverseELements() {
        array.reverse();
        for (int i = 10; i > 0; i--) {
            assertEquals(i * 10, array.get(Math.abs(i - 10)));
        }
    }
}
