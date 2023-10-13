import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Implementation of the List interface, which is based on an array with the possibility of resizing.
 * The initial size of the array - DEFAULT_CAPACITY - is 10, and it increases as it is filled by an
 * amount equal to the current size multiplied by 2 and plus DEFAULT_CAPACITY.
 * Contains elements of type T, may contain null.
 *
 * The methods of the List interface are implemented:
 * size, isEmpty to get the size and check for emptiness.
 * contains/containsAll to check for the presence of the passed element in the instance
 * add/addAll add the element/collections to the end of the list or to the specified position.
 * get an element by index.
 * set the element to the specified position.
 * remove/removeAll delete an item/collection by index or by value.
 * retainAll sets the common elements of the two instances.
 * indexOf/lastIndexOf search for the first/last occurrence of the passed element.
 * sublist extracts a sublist from an instance.
 * quickSort sorts the instance elements. Method that sorts in O(n*logn) time.
 * And also iterator and clear() method.
 */
public class MyArrayList<T> implements List<T> {
    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Multiplier for increasing the size of the array
     */
    private static final int MULTIPLIER = 2;

    /**
     * Number of elements in the array.
     */
    private int size = 0;

    /**
     * An array of objects for storing elements of the MyArrayList instance.
     */
    private Object[] elements;

    /**
     * Creates an empty MyArrayList with an initial capacity of ten.
     */
    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Creates an empty MyArrayList with the initial capacity of the passed value.
     * @param initialCapacity sets the capacity
     * @throws IllegalArgumentException if initialCapacity is negative
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity >= 0) {
            this.elements = new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    /**
     * Number of elements in MyArrayList.
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checking for the presence of elements.
     * @return false if MyArrayList eats one or more elements otherwise true
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checking for the presence of the passed element in MyArrayList.
     * @param o the desired element
     * @return true if the desired element was found otherwise false
     */
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Provides an iterator.
     * @return MyIterator type object
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    /**
     * Sorts the range of elements of the passed MyArrayList in natural order.
     * @param arrayList is the MyArrayList instance to be sorted by the quick sort method
     * @param first is the first index of the arrayList to be sorted
     * @param last is the last index of the arrayList to be sorted
     * @param <T> the object that should implement interface Comparable
     */
    public <T extends Comparable<? super T>> void quickSort(MyArrayList<T> arrayList, int first, int last) {
        if (first < last) {
            int divideIndex = partition(arrayList, first, last);
            quickSort(arrayList, first, divideIndex - 1);
            quickSort(arrayList, divideIndex, last);
        }
    }

    private <T extends Comparable<? super T>> int partition(MyArrayList<T> arrayList, int first, int last) {
        int rightIndex = last;
        int leftIndex = first;

        T pivot = arrayList.get(first + (last - first) / 2);
        while (leftIndex <= rightIndex) {

            while (arrayList.get(leftIndex).compareTo(pivot) < 0) {
                leftIndex++;
            }

            while (arrayList.get(rightIndex).compareTo(pivot) > 0) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                swap(arrayList, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    private <T extends Comparable<? super T>> void swap(MyArrayList<T> arrayList, int index1, int index2) {
        T tmp = arrayList.get(index1);
        arrayList.set(index1, arrayList.get(index2));
        arrayList.set(index2, tmp);
    }

    /**
     * Returns an array containing all of the elements in this instance of MyArrayList
     * in proper sequence (from first to last element).
     * @return an array Object type containing all of the elements in this list in proper sequence
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        System.arraycopy(elements, 0, array, 0, size);
        return array;
    }

    /**
     * Returns an array containing all of the elements in this instance of MyArrayList
     * in proper sequence (from first to last element); The runtime type of the returned
     * array is that of the specified array.
     * @param a the array into which the elements of the list are to be stored, if it is big enough;
     *          otherwise, a new array of the same runtime type is allocated for this purpose
     * @return an array Object type containing all of the elements in this list in proper sequence
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        return (T[]) this.toArray();
    }

    /**
     * Adding an item to the end of the list. When filling in, the size of the list will be increased
     * @param element the object to be added.
     * @return true after the operation is completed
     */
    @Override
    public boolean add(T element) {
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[size++] = element;
        return true;
    }

    /**
     * Inserting the specified element at the specified position.
     * If the specified position is occupied, the elements are shifted.
     * @param index the position to which the element will be added
     * @param element the object to be added
     * @throws IndexOutOfBoundsException if index's size more than size of MyArrayList instance
     * or index is negative
     */
    @Override
    public void add(int index, T element) {
        indexCheck(index);
        if (size == elements.length) {
            increaseCapacity();
        }
        System.arraycopy(elements, index,
                elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Adds all elements of the input collection to the end of the current instance of MyArrayList.
     * if necessary, the length of the list will be increased.
     * @param c collection whose elements will be added
     * @return true after the operation is completed
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T item : c) {
            add(item);
        }
        return true;
    }

    /**
     * Adds all elements of the input collection to the end of the current instance of MyArrayList
     * from the specified index.
     * If the specified position is occupied, the elements are shifted.
     * @param index the position from which to start adding new elements
     * @param c collection whose elements will be added
     * @throws IndexOutOfBoundsException if index's size more than size of MyArrayList instance
     * or index is negative
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        indexCheck(index);
        for (T item : c) {
            add(index++, item);
        }
        return true;
    }


    /**
     * Removes the first element from the MyArrayList instance equivalent to the one passed
     * @param element the element to be deleted.
     * @return true upon successful deletion
     */
    @Override
    public boolean remove(Object element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], element)) {
                System.arraycopy(elements, i + 1,
                        elements, i, size - i);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Deleting an item from the specified position.
     * @param index the position to which the element will be deleted
     * @return element the item that was deleted
     * @throws IndexOutOfBoundsException if index's size more than size of MyArrayList instance
     * or index is negative
     */
    @Override
    public T remove(int index) {
        indexCheck(index);
        T element = elements(index);
        System.arraycopy(elements, index + 1,
                elements, index, size - index);
        size--;
        return element;
    }

    /**
     * Deleting all items from the current instance of MyArrayList
     * that are contained in the incoming collection.
     * @param c collection whose elements will be deleted
     * @return true after the operation is completed
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object item : c) {
            remove(item);
        }
        return true;
    }

    /**
     * Сhecks whether the current MyArrayList object contains the passed collection in full volume.
     * @param c Collection type object
     * @return true if the current instance of MyArrayList contains the passed collection
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Will leave the elements in the current instance of MyArrayList that are present
     * in the current instance of MyArrayList and the input collection (intersection).
     * @param c input Collection
     * @return true after the operation is completed
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        int k = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(elements(i))) {
                elements[k++] = elements(i);
            }
        }
        Object[] temp = new Object[k];
        System.arraycopy(elements, 0, temp, 0, k);
        size = k;
        elements = temp;
        return true;
    }

    /**
     * Creates a new empty instance of MyArrayList with DEFAULT_CAPACITY = 10 in place of the current one.
     * Actually clearing the current instance of MyArrayList.
     */
    @Override
    public void clear() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Returns an element from the specified position from the current instance of MyArrayList.
     * @param index the position from which the element will be returned
     * @return T the element to be returned
     * @throws IndexOutOfBoundsException if index's size more than size of MyArrayList instance
     * or index is negative
     */
    @Override
    public T get(int index) {
        indexCheck(index);
        return elements(index);
    }

    /**
     * Changes the value of the element in the current MyArrayList at the specified position to a new one.
     * @param index the position to which the new value will be set
     * @param element the new value of the element at the specified position
     * @return the old value of the element
     * @throws IndexOutOfBoundsException if index's size more than size of MyArrayList instance
     * or index is negative
     */
    @Override
    public T set(int index, T element) {
        indexCheck(index);
        T oldValue = elements(index);
        elements[index] = element;
        return oldValue;
    }

    /**
     * Finding the position of the first occurrence of an incoming element.
     * @param element the element whose position we are looking for
     * @return the number of the first position if the incoming element is found, otherwise -1
     */
    @Override
    public int indexOf(Object element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finding the position of the last occurrence of an incoming element.
     * @param element the element whose position we are looking for
     * @return the number of the last position if the incoming element is found, otherwise -1
     */
    @Override
    public int lastIndexOf(Object element) {
        if (size == 0) {
            return -1;
        }
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(elements[i], element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a MyArrayList object consisting entirely of the elements of the current instance of MyArrayList,
     * keeping the order in the specified range.
     * @param fromIndex defines the starting position for the range
     * @param toIndex defines the final (not including) position for the range
     * @return an object of type MyArrayList<T>
     * @throws IndexOutOfBoundsException if toIndex size more than size of MyArrayList instance
     * or fromIndex is negative
     * @throws IllegalArgumentException if toIndex < fromIndex
     */
    @Override
    public MyArrayList<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("negative fromIndex: " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("Index: " + toIndex + ", Size: " + size);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex > toIndex: " +
                    "fromIndex " + fromIndex + ", toIndex " + toIndex);

        int length = toIndex - fromIndex;

        MyArrayList<T> sublist = length > DEFAULT_CAPACITY ?
                new MyArrayList<>(length) :
                new MyArrayList<>(10);
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.add(elements(i));
        }
        return sublist;
    }

    /**
     * String representation of the current instance of MyArrayList.
     * @return a string based on the current instance of MyArrayList
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    private class MyIterator implements Iterator<T> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return size > index;
        }

        @Override
        public T next() {
            return elements(index++);
        }
    }

    private void increaseCapacity() {
        int newSize = DEFAULT_CAPACITY + elements.length * MULTIPLIER;
        Object[] old = elements;
        elements = new Object[newSize];
        System.arraycopy(old, 0, elements, 0, size);
    }

    @SuppressWarnings("unchecked")
    private T elements(int index) {
        return (T) elements[index];
    }

    private void indexCheck(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * ListIterator with implementation from the ArrayList class
     */
    @Override
    public ListIterator<T> listIterator() {
        return new ArrayList<T>().listIterator();
    }

    /**
     * ListIterator with implementation from the ArrayList class
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        return new ArrayList<T>().listIterator(index);
    }

    public static void main(String[] args) {
        Map<Integer, Integer> in = new TreeMap<>();
        in.put(null,null);

    }
}

