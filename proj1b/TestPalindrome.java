import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String test1 = "persiflage";
        assertFalse(palindrome.isPalindrome(test1));
        String test2 = "wow";
        assertTrue(palindrome.isPalindrome(test2));
    }

    @Test
    public void testIsPalindromeUseComparator() {
        String test1 = "flake";
        String test2 = "wow";
        CharacterComparator comparator = new OffByOne();
        assertTrue(palindrome.isPalindrome(test1, comparator));
        assertFalse(palindrome.isPalindrome(test2, comparator));
    }
}
