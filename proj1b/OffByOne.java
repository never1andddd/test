public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        if (java.lang.Math.abs(y - x) == 1) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }
}
