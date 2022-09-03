/* class ArrayDeque implement
 * the index begin at the middle of the array
 * and end at the left item of the middle of the array
 * */
public class ArrayDeque<T> implements Deque<T> {
    /** the raw data of the deque. */
    private T[] array;
    /** the size of the array. */
    private int size;
    /** the begin index of the deque. */
    private int beginIndex;
    /** the end index of the deque. */
    private  int endIndex;

    /** the default constructor of deque. */
    public ArrayDeque() {
        array = (T[]) new Object[5];
        size = 0;
        endIndex = array.length / 2;
        beginIndex = endIndex + 1;
    }

    /** the copy constructor of deque. */
    ArrayDeque(ArrayDeque other) {
        array = (T[]) new Object[other.array.length];
        System.arraycopy(other.array, 0, array, 0, array.length);
        size = other.size;
        beginIndex = other.beginIndex;
        endIndex = other.endIndex;
    }

    /** a helper function compute the index before. */
    private int minusOne(int index) {
        index = index - 1;
        if (index < 0) {
            index = index + array.length;
        }
        return index;
    }

    /** a helper function compute the index before. */
    private int plusOne(int index) {
        index = index + 1;
        if (index >= array.length) {
            index = index - array.length;
        }
        return index;
    }

    /* helper function that copy a deque raw data to another
     * first copy begin at begin index, end at the end.
     * second copy begin at font, end at the end index.
     * if begin index less than end index, just copy
     * else copy the two part
     * */
    private void copyDeque(int destArrayBeginIndex, T[] arrayNew) {
        int arraySize = array.length;
        if (beginIndex < endIndex) {
            System.arraycopy(array, beginIndex, arrayNew, destArrayBeginIndex, size);
        } else {
            int firstCopyNum = arraySize - beginIndex;
            int secondCopyNum = endIndex + 1;
            System.arraycopy(array, beginIndex, arrayNew, destArrayBeginIndex, firstCopyNum);
            System.arraycopy(array, 0, arrayNew, destArrayBeginIndex + firstCopyNum, secondCopyNum);
        }
        beginIndex = destArrayBeginIndex;
        endIndex = destArrayBeginIndex + size - 1;
        array = arrayNew;
    }

    /*
     * resize the internal data structure.
     * copy dest begin at the arraySize / 2 index.
     * */
    private void resize() {
        int arraySize = array.length;
        if (size == array.length) {
            T[] arrayNew = (T[]) new Object[arraySize * 2];
            int destArrayBeginIndex = arraySize / 2;
            copyDeque(destArrayBeginIndex, arrayNew);
        }
    }

    /* reduce size of deque when usage ratio is below 25%
     * copy begin at index 0
     * */
    private void reduce() {
        int arraySize = array.length;
        if (arraySize > 16 && (1.0 * size / arraySize < 0.25)) {
            T[] arrayNew = (T[]) new Object[arraySize / 2];
            int destArrayBeginIndex = 0;
            copyDeque(destArrayBeginIndex, arrayNew);
        }
    }

    /** return the size of the deque. */
    @Override
    public int size() {
        return size;
    }

    /** add item to the font of the deque. */
    @Override
    public void addFirst(T item) {
        resize();
        beginIndex = minusOne(beginIndex);
        array[beginIndex] = item;
        size = size + 1;
    }

    /** add item to the back of the deque. */
    @Override
    public void addLast(T item) {
        resize();
        endIndex = plusOne(endIndex);
        array[endIndex] = item;
        size = size + 1;
    }

    /** print he items in the deque. */
    @Override
    public void printDeque() {
        int loopBegin = beginIndex;
        int loopEnd = endIndex;
        while (loopBegin != loopEnd) {
            System.out.print(array[loopBegin] + " ");
            loopBegin = plusOne(loopBegin);
        }
        System.out.print(array[loopEnd]);
        System.out.println();
    }

    /** remove the first item in the deque. */
    @Override
    public T removeFirst() {
        if (size <= 0) {
            return null;
        }
        reduce();
        T item = array[beginIndex];
        array[beginIndex] = null;
        beginIndex = plusOne(beginIndex);
        size = size - 1;
        return item;
    }

    /** remove the last item in the deque. */
    @Override
    public T removeLast() {
        if (size <= 0) {
            return null;
        }
        reduce();
        T item = array[endIndex];
        array[endIndex] = null;
        endIndex = minusOne(endIndex);
        size = size - 1;
        return item;
    }

    /** get the i index item in the deque. */
    @Override
    public T get(int index) {
        return array[(index + beginIndex) % array.length];
    }
}
