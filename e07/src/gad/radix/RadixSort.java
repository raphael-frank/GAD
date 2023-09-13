package gad.radix;

import java.util.ArrayList;
import java.util.List;

public final class RadixSort {
    private static final int DIGITS = 10;

    private RadixSort() {
    }

    public static int key(int element, int decPlace) {
        for (int i = 0; i < decPlace; i++) {
            element /= 10;
        }
        return element % 10;
    }

    public static int getMaxDecimalPlaces(int[] elements) {
        if(elements == null || elements.length == 0) return 0;

        int longest = 1;
        for (int element : elements) {
            int currentElement = element;
            if (currentElement == 0) continue;

            int curL = 0;

            if (currentElement < 0) currentElement = -currentElement;

            while (currentElement > 0) {
                currentElement /= 10;
                curL++;
            }

            longest = Math.max(longest, curL);
        }
        return longest;
    }

    public static void concatenate(List<Integer>[] buckets, int[] elements) {
        int k = 0;
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                elements[k++] = buckets[i].get(j);
            }
        }
    }

    public static void kSort(int[] elements, int decPlace) {
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[DIGITS];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>(elements.length / DIGITS);
        }
        for (int i = 0; i < elements.length; i++) {
            buckets[key(elements[i], decPlace)].add(elements[i]);
        }
        concatenate(buckets, elements);
    }

    public static void sort(int[] elements, Result result) {
        int decPlaces = getMaxDecimalPlaces(elements);
        for (int decPlace = 0; decPlace < decPlaces; decPlace++) {
            kSort(elements, decPlace);
            result.logArray(elements);
        }
    }
}