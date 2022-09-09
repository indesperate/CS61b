package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0 ; i < 10; i += 1) {
            arb.enqueue(i);
        }
        assertTrue(arb.isFull());
        for (int i = 0; i < 10; i += 1) {
            assertEquals((Integer) i, arb.dequeue());
        }
        assertTrue(arb.isEmpty());
        assertNull(arb.peek());
        arb.enqueue(10);
        arb.enqueue(10000);
        assertEquals((Integer)10, arb.peek());
        assertEquals(2, arb.fillCount);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
