import ds1.MyHashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
