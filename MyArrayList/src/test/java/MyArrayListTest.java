import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    @Test
    public void testSize() {
        MyArrayList<Integer> list = new MyArrayList<>();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testContains() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(null);

        assertTrue(list.contains(1));
        assertTrue(list.contains(null));
        assertEquals(1, list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
    }

    @Test
    public void testAdd() {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(1);
        list.add(2);
        list.add(1, 3);

        assertEquals(3, list.size());
        assertEquals(3, list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(4, 4));
    }

    @Test
    public void testAddAll() {
        MyArrayList<Integer> list1 = new MyArrayList<>();
        MyArrayList<Integer> list2 = new MyArrayList<>();
        list1.add(1);
        list1.add(2);

        list2.addAll(list1);

        assertEquals(2, list2.size());
        assertTrue(list2.contains(1));
    }

    @Test
    public void testAddAllByIndex() {
        MyArrayList<Integer> list1 = new MyArrayList<>();
        MyArrayList<Integer> list2 = new MyArrayList<>();
        list1.add(0);
        list1.add(0);
        list2.add(1);
        list2.add(1);

        list2.addAll(1, list1);

        assertEquals(4, list2.size());
        assertEquals(0, list2.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list2.addAll(-1, list1));
    }

    @Test
    public void iteratorTest() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);

        Iterator<Integer> iterator = list.iterator();

        assertEquals(iterator.next(), 0);
        assertTrue(iterator.hasNext());
    }

    @Test
    public void testRemoveByIndex() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.remove(2);
        list.remove(0);

        assertEquals(1, list.size());
        assertFalse(list.contains(1));
        assertTrue(list.contains(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
    }

    @Test
    public void testRemoveByValue() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);

        list.remove(Integer.valueOf(2));

        assertEquals(1, list.size());
        assertFalse(list.contains(2));
    }

    @Test
    public void testRemoveAll() {
        MyArrayList<Integer> list1 = new MyArrayList<>();
        MyArrayList<Integer> list2 = new MyArrayList<>();
        list1.add(1);
        list1.add(2);
        list2.add(2);
        list2.add(3);

        list1.removeAll(list2);

        assertEquals(1, list1.size());
        assertTrue(list1.contains(1));
    }

    @Test
    public void testContainsAll() {
        MyArrayList<Integer> list1 = new MyArrayList<>();
        MyArrayList<Integer> list2 = new MyArrayList<>();
        list1.add(1);
        list1.add(2);
        list2.add(2);
        list2.add(1);

        assertTrue(list1.containsAll(list2));
    }

    @Test
    public void testRetainAll() {
        MyArrayList<Integer> list1 = new MyArrayList<>();
        MyArrayList<Integer> list2 = new MyArrayList<>();
        list1.add(1);
        list1.add(2);
        list2.add(2);
        list2.add(3);

        list1.retainAll(list2);

        assertEquals(1, list1.size());
        assertTrue(list1.contains(2));
    }

    @Test
    public void testSet() {
        int old;
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);

        old = list.set(1, 3);

        assertEquals(2, list.size());
        assertEquals(2, old);
        assertEquals(3, list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(2, 4));
    }

    @Test
    public void testIndexOfLastIndexOf() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(4);

        assertEquals(1, list.indexOf(2));
        assertEquals(3, list.lastIndexOf(3));
        assertEquals(-1, list.indexOf(5));
    }

    @Test
    public void testQuickSort() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(2);
        list.add(1);

        list.quickSort(list, 0, list.size() - 1);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testClear() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }
}