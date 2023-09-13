package gad.simplesort;

import java.util.Arrays;
import java.util.Random;

public interface DualPivotFinder {

	int[] findPivot(int[] numbers, int from, int to);

	static DualPivotFinder getFirstLastPivot() {
		return new DualPivotFinder() {

			@Override
			public int[] findPivot(int[] numbers, int from, int to) {
				// TODO
				return new int[]{from,to};
			}

			@Override
			public String toString() {
				return "The first and last element";
			}
		};
	}

	static DualPivotFinder getRandomPivot(int seed) {
		Random random = new Random(seed);

		return new DualPivotFinder() {
			@Override
			public int[] findPivot(int[] numbers, int from, int to) {
				// TODO
				int first = from + random.nextInt(to - from + 1);
				int last;
				do {last = from + random.nextInt(to - from + 1);} while(last == first);
				return new int[]{first,last};
			}

			@Override
			public String toString() {
				return "Two random elements";
			}
		};
	}

	static DualPivotFinder getMedianPivotFront(int numberOfConsideredElements) {
		return new DualPivotFinder() {
			@Override
			public int[] findPivot(int[] numbers, int from, int to) {
				// TODO
				int[] pivots = new int[]{-1,-1};
				int rightBoundary = Math.min(from + numberOfConsideredElements - 1,to);
				int[] numberRange = Arrays.copyOfRange(numbers,from,rightBoundary+1);

				Arrays.sort(numberRange);
				int divideStep = (numberRange.length - 1) / 3;
				int median1 = numberRange[divideStep];
				int median2 = numberRange[numberRange.length - 1 - divideStep];

				for (int i = from; i <= rightBoundary; i++) {
					if(numbers[i] == median1) {
						pivots[0] = i;
						break;
					}
				}
				for (int i = rightBoundary; i >= from; i--) {
					if(numbers[i] == median2) {
						pivots[1] = i;
						break;
					}
				}
				return pivots;
			}

			@Override
			public String toString() {
				return "The thirds of the first " + numberOfConsideredElements + " elements";
			}
		};
	}

	static DualPivotFinder getMedianPivotDistributed(int numberOfConsideredElements) {
		return new DualPivotFinder() {
			@Override
			public int[] findPivot(int[] numbers, int from, int to) {
				// TODO
				int[] pivots = new int[]{-1,-1};
				int considered = Math.min(numberOfConsideredElements,to-from + 1);
				int step = (to - from)/(considered-1);

				while(from + step * (considered - 1) > to) step--;

				int[] numberRange = new int[considered];
				for (int i = 0; i < numberRange.length; i++) {
					numberRange[i] = numbers[from + i * step];
				}

				Arrays.sort(numberRange);

				int divideStep = (considered - 1) / 3;
				int median1 = numberRange[divideStep];
				int median2 = numberRange[considered - 1 - divideStep];

				for (int i = 0; i < numberRange.length; i++) {
					int index = from + i * step;
					if(numbers[index] == median1) {
						pivots[0] = index;
						break;
					}
				}
				for (int i = numberRange.length - 1; i >= 0; i--) {
					int index = from + i * step;
					if(numbers[index] == median2) {
						pivots[1] = index;
						break;
					}
				}

				return pivots;
			}

			@Override
			public String toString() {
				return "The thirds of " + numberOfConsideredElements + " elements distributed thoughout the array";
			}
		};
	}
}