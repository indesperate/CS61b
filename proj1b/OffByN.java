/** class OffByN */
public class OffByN implements CharacterComparator {
    private final int offset;
    /** the N off set equal */
    public OffByN(int N) {
        offset = N;
    }

    /** the function */
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == offset;
    }
}
