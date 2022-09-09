package synthesizer;

import java.util.Iterator;

/** a interface which declares all the methods. */
public interface BoundedQueue<T> extends Iterable<T> {
    /** return size of the buffer. */
    int capacity();
    /** return number of items currently in the buffer. */
    int fillCount();
    /** add item x to the end. */
    void enqueue(T x);
    /** delete and return the first item. */
    T dequeue();
    /** return the first item. */
    T peek();
    /** is the buffer empty. */
    default boolean isEmpty() {
        return fillCount() <= 0;
    }
    /** is the buffer full. */
    default boolean isFull() {
        return fillCount() >= capacity();
    }
    @Override
    Iterator<T> iterator();
}
