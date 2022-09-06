import datastructure.basics.MyHashMap;
import org.junit.Before;

public class MyMapTests {

    private MyHashMap<Integer, String> emptyMap;
    private MyHashMap<Integer, String> values;
    
    @Before
    public void setUp() throws Exception {
        emptyMap = new MyHashMap<>();
        values = new MyHashMap<>();
        for (int i = 65; i < 70; i++) {
            values.put(i, String.valueOf((char) i));
        }
    }
}
