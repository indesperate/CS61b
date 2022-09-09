package synthesizer;
/** a abstract class. */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    /** the data store the fillCount. */
    protected int fillCount;
    /** the data store the volume. */
    protected int capacity;
    @Override
    public int capacity() {
        return capacity;
    }
    @Override
    public int fillCount() {
        return fillCount;
    }
}
