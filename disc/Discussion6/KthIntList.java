package Discussion6;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class KthIntList implements Iterator<Integer> {
    public int k;
    private IntList curList;
    private boolean hasNext;

    public KthIntList(IntList I, int k) {
        this.k = k;
        this.curList = I;
        this.hasNext = true;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public Integer next() {
        if (this.curList == null) {
            throw new NoSuchElementException();
        }
        Integer item = this.curList.first;
        for (int i = 0; i < k && this.curList != null; i += 1) {
            this.curList = this.curList.rest;
        }
        hasNext = (this.curList != null);
        return item;
    }
}
