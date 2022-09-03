/** class LinkedListDeque. */
public class LinkedListDeque<T> implements Deque<T> {
    /** a sentinel help reduce the special case. */
    private LinkedListNode sentinel;
    /** the size of the list. */
    private int size;

    /** internal implement of list node. */
    private class LinkedListNode {
        /** item of list. */
        private T item;
        /** record the node before. */
        private LinkedListNode before;
        /** record hte mode next. */
        private LinkedListNode next;

        /** one parameter constructor. */
        LinkedListNode(T i) {
            item = i;
            next = null;
            before = null;
        }

        /** three parameters constructor. */
        LinkedListNode(T i, LinkedListNode b, LinkedListNode n) {
            item = i;
            next = n;
            before = b;
        }
    }

    /** the constructor of class LinkedListDeque. */
    public LinkedListDeque() {
        sentinel = new LinkedListNode(null);
        sentinel.next = sentinel;
        sentinel.before = sentinel;
    }

    /** the deep copy of another LinkedListDeque. */
    LinkedListDeque(LinkedListDeque lld) {
        sentinel = new LinkedListNode(null);
        sentinel.next = sentinel;
        sentinel.before = sentinel;
        LinkedListNode iterator = lld.sentinel.next;
        size = lld.size;
        while (iterator != lld.sentinel) {
            addLast(iterator.item);
            iterator = iterator.next;
        }
    }

    /** add item to the first of the list. */
    @Override
    public void addFirst(T item) {
        LinkedListNode second = sentinel.next;
        sentinel.next = new LinkedListNode(item, sentinel, second);
        second.before = sentinel.next;
        size += 1;
    }

    /** add item to the end of the list. */
    @Override
    public void addLast(T item) {
        LinkedListNode before = sentinel.before;
        sentinel.before = new LinkedListNode(item, before, sentinel);
        before.next = sentinel.before;
        size += 1;
    }

    /** return the size of the list. */
    @Override
    public int size() {
        return size;
    }

    /** print the list. */
    @Override
    public void printDeque() {
        LinkedListNode iterator = sentinel.next;
        while (iterator != sentinel) {
            System.out.print(iterator.item + " ");
            iterator = iterator.next;
        }
        System.out.println();
    }

    /** remove the first item and return the value of the item. */
    @Override
    public T removeFirst() {
        if (size < 1) {
            return null;
        }
        LinkedListNode first = sentinel.next;
        sentinel.next = first.next;
        first.next.before = sentinel;
        size -= 1;
        return first.item;
    }

    /** remove the last item and return the value of the item. */
    @Override
    public T removeLast() {
        if (size < 1) {
            return null;
        }
        LinkedListNode last = sentinel.before;
        sentinel.before = last.before;
        last.before.next = sentinel;
        size -= 1;
        return last.item;
    }

    /** get the i index item of the list. */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        LinkedListNode iterator = sentinel.next;
        while (index > 0) {
            iterator = iterator.next;
            index -= 1;
        }
        return iterator.item;
    }

    /** a helper function help the list recursive get. */
    private T getNodeRecursive(LinkedListNode n, int index) {
        if (index < 1) {
            return n.item;
        } else {
            return getNodeRecursive(n.next, index - 1);
        }
    }

    /** get the i index item of the list recursively. */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getNodeRecursive(sentinel.next, index);
    }
}
