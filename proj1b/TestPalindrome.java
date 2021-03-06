import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /* You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.*/
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("raccar"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindrome2() {
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("cat", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("acfledb", cc));
        assertTrue(palindrome.isPalindrome("acfedb", cc));
        assertTrue(palindrome.isPalindrome("", cc));
    }
/**
    @Test
    public void testIsPalindrome3() {
        CharacterComparator cc = new OffByN(5);
        assertFalse(palindrome.isPalindrome("aba", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("afblgaf", cc));
        assertTrue(palindrome.isPalindrome("afbgaf", cc));
        assertTrue(palindrome.isPalindrome("", cc));
    }
    **/
}
