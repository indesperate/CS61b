package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] array = new int[M];
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            array[bucketNum] += 1;
        }
        for (int arr : array) {
            if (arr > (oomages.size() / 2.5) || arr < (oomages.size() / 50)) {
                return false;
            }
        }
        return true;
    }
}
