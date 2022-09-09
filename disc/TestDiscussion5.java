import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestDiscussion5 {
    public static boolean twoSum(int[] A, int k) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i : A) {
            if (set.contains(k - i)) {
                return true;
            }
            set.add(i);
        }
        return false;
    }

    @Test
    public void testTwoSum() {
        assertTrue(twoSum(new int []{0, 1, 2, 3, 6, 8}, 11));
    }

    public static PriorityQueue<String> topPopularWords(String[] words) {
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        for (String word : words) {
            if (stringIntegerMap.containsKey(word)) {
                stringIntegerMap.put(word, stringIntegerMap.get(word) + 1);
            } else {
                stringIntegerMap.put(word, 1);
            }
        }
        PriorityQueue<String> stringPriorityQueue = new PriorityQueue<String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return stringIntegerMap.get(o2) - stringIntegerMap.get(o1);
                    }
                });
        stringPriorityQueue.addAll(stringIntegerMap.keySet());
        return  stringPriorityQueue;
    }

    @Test
    public void testTopPopularWords() {
        String[] lists = new String[]{
                "one", "three", "two", "four",
                "two", "four", "three", "four",
                "four", "three"};
        PriorityQueue<String> order = topPopularWords(lists);
        assertEquals("four", order.poll());
        assertEquals("three", order.poll());
        assertEquals("two", order.poll());
        assertEquals("one", order.poll());
    }

    public static class MyQueue<T> {
        private final Stack<T> in;
        private final Stack<T> out;
        public MyQueue() {
            in = new Stack<>();
            out = new Stack<>();
        }
        public void push(T item) {
            in.push(item);
        }
        public T poll() {
            if (out.isEmpty()) {
                while (!in.isEmpty()) {
                    out.push(in.pop());
                }
            }
            return out.pop();
        }
    }

    @Test
    public void testMyQueue() {
        MyQueue<Integer> myQueue = new MyQueue<>();
        for (int i = 0; i < 10; i++) {
            myQueue.push(i);
        }
        for (int i = 0; i < 9; i++) {
            assertEquals((Integer)i, myQueue.poll());
        }
        myQueue.push(20);
        myQueue.push(30);
        assertEquals((Integer)9, myQueue.poll());
        assertEquals((Integer)20, myQueue.poll());
        myQueue.push(40);
        assertEquals((Integer)30, myQueue.poll());
        assertEquals((Integer)40, myQueue.poll());
    }

    public static class SortedStack<T extends Comparable<T>> {
        private final Stack<T> order;
        private final Stack<T> reverseOrder;
        public SortedStack() {
            order = new Stack<>();
            reverseOrder = new Stack<>();
        }
        public void push(T item) {
            while (!order.isEmpty() && item.compareTo(order.peek()) > 0) {
                reverseOrder.push(order.pop());
            }
            order.push(item);
            while (!reverseOrder.isEmpty()) {
                order.push(reverseOrder.pop());
            }
        }
        public T poll() {
            return order.pop();
        }
    }

    @Test
    public void testSortedStack() {
        SortedStack<Integer> integerSortedStack = new SortedStack<>();
        integerSortedStack.push(10);
        integerSortedStack.push(4);
        integerSortedStack.push(8);
        integerSortedStack.push(2);
        assertEquals(integerSortedStack.poll(), (Integer)2);
        assertEquals(integerSortedStack.poll(), (Integer)4);
        assertEquals(integerSortedStack.poll(), (Integer)8);
        assertEquals(integerSortedStack.poll(), (Integer)10);
    }
}
