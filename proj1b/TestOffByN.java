import org.junit.Test;
import static org.junit.Assert.*;

/** test offByN. */
public class TestOffByN {
    static CharacterComparator offByN = new OffByN(5);
    @Test
    public void testEqualChars() {
        assertTrue(offByN.equalChars('a', 'f'));
        assertTrue(offByN.equalChars('v', 'q'));
        assertFalse(offByN.equalChars('z', 'a'));
        assertFalse(offByN.equalChars('a', 'z'));
        assertFalse(offByN.equalChars('a', 'a'));
    }
}
