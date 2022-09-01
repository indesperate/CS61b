/** test the arrayDeque */
public class ArrayDequeTest {
    public static void testInteger() {
        ArrayDeque<Integer> test = new ArrayDeque<>();
        for (int i = 0; i < 50; i++) {
            test.addFirst(i);
            test.addLast(i);
        }
        ArrayDeque<Integer> test1 = new ArrayDeque<>(test);
        for (int i = 0; i < 47; i++) {
            test.removeFirst();
        }
        test.printDeque();
        test1.printDeque();
        System.out.println(test.size());
    }
    public static void testString() {
        ArrayDeque<String> test = new ArrayDeque<>();
        test.addLast("one");
        test.addLast("two");
        test.addFirst("three");
        test.addFirst("four");
        test.addLast("five");
        test.addLast("hundred");
        ArrayDeque<String> test1 = new ArrayDeque<>(test);
        System.out.println(test.removeFirst());
        System.out.println(test.removeLast());
        test.printDeque();
        test1.printDeque();
    }
    public static void main(String[] args) {
        testInteger();
        testString();
    }
}
