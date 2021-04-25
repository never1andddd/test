package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        int N = oomages.size();
        for (Oomage o : oomages) {
            int hashManual = o.hashCode();
            int bucketNum = (hashManual & 0x7FFFFFFF) % M;
            //System.out.println("hashcode： " + hashManual + " bucketnum: " + bucketNum);
            buckets[bucketNum] += 1;
        }

        for (int i = 0; i < M; i ++) {
            if (buckets[i] < N / 50 | buckets[i] > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
