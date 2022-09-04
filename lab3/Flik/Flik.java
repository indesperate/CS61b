/** An Integer tester created by Flik Enterprises. */
public class Flik {
    /** when internal i is less than 127, java will use same cache object
     * otherwise, java will allocate a object in heap cause this error
     */
    public static boolean isSameNumber(Integer a, Integer b) {
        return a.intValue() == b.intValue();
    }
}
