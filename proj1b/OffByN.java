public class OffByN implements CharacterComparator {
    private int count;

    public OffByN(int N) {
        count = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (java.lang.Math.abs(y - x) == count) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
