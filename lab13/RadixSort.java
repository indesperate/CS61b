import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int max = Integer.MIN_VALUE;
        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            max = Math.max(max, asciis[i].length());
            sorted[i] = asciis[i];
        }
        for (int i = 1; i <= max; i += 1) {
            sortHelperLSD(sorted, max - i);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int[] counts = new int[128];
        int placeHolder = 0;
        String[] before = new String[asciis.length];
        System.arraycopy(asciis, 0, before, 0, asciis.length);
        for (String item: asciis) {
            if (index >= item.length()) {
                placeHolder += 1;
            } else {
                counts[item.charAt(index)] += 1;
            }
        }
        int[] starts = new int[128];
        int pos = placeHolder;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }
        int placeHolderIndex = 0;
        for (String item : before) {
            if (index >= item.length()) {
                asciis[placeHolderIndex] = item;
                placeHolderIndex += 1;
            }
            int place = starts[item.charAt(index)];
            asciis[place] = item;
            starts[item.charAt(index)] += 1;
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
