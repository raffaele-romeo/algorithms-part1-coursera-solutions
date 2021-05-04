public class ExampleSelectionSort {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++)
                if (less(a[j], a[min]))
                    min = j;
            exch(a, i, min);
        }
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

/* Using a java Comparator
    public static void sort(Object[] a, Comparator comparator)
    {
     int N = a.length;
     for (int i = 0; i < N; i++)
     for (int j = i; j > 0 && less(comparator, a[j], a[j-1]); j--)
     exch(a, j, j-1);
    }
    private static boolean less(Comparator c, Object v, Object w)
    { return c.compare(v, w) < 0; }
    
    private static void exch(Object[] a, int i, int j)
    { Object swap = a[i]; a[i] = a[j]; a[j] = swap; }
*/

}
