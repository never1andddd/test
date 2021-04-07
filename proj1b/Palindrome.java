public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque<>();
        for (int i=0; i < word.length(); i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        int length = word.length();
        for (int i = 0; i < length / 2; i++) {
            if (!(word.length() == 1 | word.length() == 0)) {
                if (wordDeque.removeFirst() != wordDeque.removeLast()) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> wordDeque = wordToDeque(word);
        int length = word.length();
        for (int i = 0; i < length / 2; i++) {
            if (!(word.length() == 1 | word.length() == 0)) {
                if (!cc.equalChars(wordDeque.removeFirst(), wordDeque.removeLast())) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }
}
