package gad.radix;

import java.util.Arrays;
import java.util.Random;

public final class BinaryRadixSort {

    private BinaryRadixSort() {
    }

    public static int key(int element, int binPlace) {
        element >>= binPlace;
        return element & 1;
    }

    public static void kSort(BinaryBucket from, BinaryBucket to, int binPlace) {
        from.resetPointer();
        for (int i = 0; i < from.getMid(); i++) {
            int currentNumber = from.readLeft();
            if (key(currentNumber, binPlace) == 0) {
                to.insertLeft(currentNumber);
            } else {
                to.insertRight(currentNumber);
            }
        }
        for (int i = 0; i < from.getSize() - from.getMid(); i++) {
            int currentNumber = from.readRight();
            if (key(currentNumber, binPlace) == 0) {
                to.insertLeft(currentNumber);
            } else {
                to.insertRight(currentNumber);
            }
        }
    }

    public static void lastSort(BinaryBucket from, int[] to) {
        int k = 0;
        from.resetPointer();
        for (int i = 0; i < from.getSize() - from.getMid(); i++) {
            to[k++] = from.readRight();
        }
        for (int i = 0; i < from.getMid(); i++) {
            to[k++] = from.readLeft();
        }
    }

    public static void sort(int[] elements, Result result) {
        BinaryBucket from = BinaryBucket.arrayAsUnsortedBucket(elements);
        BinaryBucket to = new BinaryBucket(elements.length);

        for (int i = 0; i < 32; i++) {
            to.clear();
            kSort(from, to, i);
            BinaryBucket a = from;
            from = to;
            to = a;
            to.clear();
            from.logArray(result);
        }

        lastSort(from,elements);
    }

    public static void main(String[] args) {
        /*int[] test = new int[10_000_000];
        Random random = new Random();
        for (int i = 0; i < test.length; i++) {
            test[i] = random.nextInt(Integer.MAX_VALUE);
        }
        int[] testTwo = Arrays.copyOf(test, test.length);

        long start = System.nanoTime();
        sort(test, ignored -> {
        });
        long binaryTime = System.nanoTime() - start;

        start = System.nanoTime();
        RadixSort.sort(testTwo, ignored -> {
        });
        long decimalTime = System.nanoTime() - start;

        System.out.println("Korrekt sortiert:" + Arrays.equals(test, testTwo));
        System.out.println("Binary: " + binaryTime / 1_000_000);
        System.out.println("Decimal: " + decimalTime / 1_000_000);
         */

        int[] nums = new int[]{-3,4,9,15,10,1,6};
        sort(nums, new StudentResult());
    }
}
