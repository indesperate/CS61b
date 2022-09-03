/*
 * class Palindrome implement
 * */
public class Palindrome {
    /** convert word to character deque */
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> wordArray = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            wordArray.addLast(word.charAt(i));
        }
        return wordArray;
    }

    /** a helper function the help check the word deque */
    private boolean checkPalindrome(Deque<Character> wordDeque) {
        if (wordDeque.size() <= 1) {
            return true;
        }
        if (wordDeque.removeFirst() == wordDeque.removeLast()) {
            return checkPalindrome(wordDeque);
        } else {
            return false;
        }
    }

    /** check the string is palindrome */
    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return checkPalindrome(wordDeque);
    }
}
