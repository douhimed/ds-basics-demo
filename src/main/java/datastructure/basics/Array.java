package datastructure.basics;

import java.util.Objects;

public class Array {

    private Object[] values;
    private int count = 0;

    public Array(int capacity) {
        values = new Object[capacity];
    }

    public Array() {
        values = new Object[0];
    }

    public Object add(Object value) {
        resize();
        values[count++] = value;
        return value;
    }

    public void deleteAt(int index) {
        checkIfIndexIsValid(index);
        for (int i = index; i < count - 1; i++) {
            values[i] = values[i + 1];
        }
        values[count - 1] = null;
        count--;
    }

    public int indexOf(Object o) {
        Objects.requireNonNull(o);
        for (int i = 0; i < values.length; i++) {
            if (o.equals(values[i]))
                return i;
        }
        return -1;
    }

    public int size() {
        return count;
    }

    public Object get(int i) {
        checkIfIndexIsValid(i);
        return values[i];
    }

    public Array intersect(Array array) {
        Array res = new Array();
        for (int i = 0; i < array.size(); i++) {
            if (indexOf(array.get(i)) > -1)
                res.add(array.get(i));
        }
        return res;
    }

    public void reverse() {
        int midle = (count - 1) / 2;
        Object temp = null;
        for (int i = 0; i <= midle; i++) {
            temp = values[i];
            values[i] = values[count - 1 - i];
            values[count - 1 - i] = temp;
        }
        temp = null;
    }


    private void resize() {
        if (count == values.length) {
            Object[] newValues = new Object[values.length == 0 ? 1 : (int) (count * 2)];
            System.arraycopy(values, 0, newValues, 0, values.length);
            values = newValues;
        }
    }

    private void checkIfIndexIsValid(int index) {
        if (index < 0 || index > count)
            throw new IllegalArgumentException(String.format("Index %s not valid", index));
    }

}
