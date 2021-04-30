import java.util.Arrays;

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
        // TODO: Implement LSD Sort
        int max = Integer.MIN_VALUE;
        for (String i : asciis) {
            max = max > i.length() ? max : i.length();
        }
        String[] returnString = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            returnString[i] = asciis[i];
            while (returnString[i].length()<max) {
                returnString[i] = returnString[i] + "_";
            }
        }

        for (int d = max - 1; d >= 0; d--) {
            sortHelperLSD(returnString, d);
        }
        return returnString;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int N = asciis.length;
        int R = 256;
        String[] aux = new String[asciis.length];
        int[] count = new int[R + 1]; // Compute frequency counts.
        for (int i = 0; i < N; i++) {
            int asciisCode = (int)asciis[i].charAt(index);
            count[asciisCode + 1] += 1;
        }
        for (int r = 0; r < R; r++) {
            // Transform counts to indices.
            count[r+1] += count[r];
        }

        for (int i = 0; i < N; i++) {
            // Distribute.
            int auxCode = (int)asciis[i].charAt(index);
            aux[count[auxCode]++] = asciis[i];
        }

        for (int i = 0; i < N; i++) {
            // Copy back.
            asciis[i] = aux[i];
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
/**
    public static void main(String[] args) {
        String[] student = new String[] {"vincent", "alice","ben", "ann",  "liz", "zoey","mary"};
        System.out.println(Arrays.toString(sort(student)));
    } */
}
