import static org.junit.Assert.*;
import org.junit.Test;
/** test the implement of class ArrayDeque. */
public class TestArrayDequeGold {
    /** randomly call method from StudentArrayDeque and ArrayDequeSolution */
    @Test
    public void testDequeWithSolution() {
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> answer = new StudentArrayDeque<>();
        String error = "";
        for (int i = 0; i < 1000; i++) {
            int random = StdRandom.uniform(4);
            switch (random) {
                case 0:
                    error = error + "addFirst(" + i + ")\n";
                    solution.addFirst(i);
                    answer.addFirst(i);
                    assertEquals(solution.get(0), answer.get(0));
                    break;
                case 1:
                    error = error + "addLast(" + i + ")\n";
                    solution.addLast(i);
                    answer.addLast(i);
                    assertEquals(solution.getLast(), answer.get(answer.size() - 1));
                    break;
                case 2:
                    error = error + "removeFirst()\n";
                    assertEquals(error, solution.removeFirst(), answer.removeFirst());
                    break;
                default:
                    error = error +  "removeLast()\n";
                    assertEquals(error, solution.removeLast() ,answer.removeLast());
            }
        }
    }
}
