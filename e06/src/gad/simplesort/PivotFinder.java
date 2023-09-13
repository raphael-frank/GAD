package gad.simplesort;

import java.util.Arrays;
import java.util.Random;

public interface PivotFinder {

	int findPivot(int[] numbers, int from, int to);

	static PivotFinder getLastPivot() {
		return new PivotFinder() {

			@Override
			public int findPivot(int[] numbers, int from, int to) {
				return to;
			}

			@Override
			public String toString() {
				return "The last element";
			}
		};
	}

	static PivotFinder getMidPivot() {
		return new PivotFinder() {

			@Override
			public int findPivot(int[] numbers, int from, int to) {
				// TODO
				return (from + to)/2;
			}

			@Override
			public String toString() {
				return "The middle element";
			}
		};
	}

	static PivotFinder getRandomPivot(int seed) {
		Random random = new Random(seed);

		return new PivotFinder() {
			@Override
			public int findPivot(int[] numbers, int from, int to) {
				// TODO
				return from + random.nextInt(to - from + 1);
			}

			@Override
			public String toString() {
				return "A random element";
			}
		};
	}

	static PivotFinder getMedianPivotFront(int numberOfConsideredElements) {
		return new PivotFinder() {
			@Override
			public int findPivot(int[] numbers, int from, int to) {
				// TODO
				int rightBoundary = Math.min(from + numberOfConsideredElements - 1,to);
				int[] numberRange = Arrays.copyOfRange(numbers,from,rightBoundary+1);
				Arrays.sort(numberRange);
				int median = numberRange[numberRange.length / 2];
				for (int i = from; i <= rightBoundary; i++) {
					if(numbers[i] == median) return i;
				}
				return -1;
			}

			@Override
			public String toString() {
				return "The median of the first " + numberOfConsideredElements + " elements";
			}
		};
	}

	static PivotFinder getMedianPivotDistributed(int numberOfConsideredElements) {
		return new PivotFinder() {
			@Override
			public int findPivot(int[] numbers, int from, int to) {
				// TODO
				int considered = Math.min(numberOfConsideredElements,to-from + 1);
				int step = (to - from)/(considered-1);

				while(from + step * (considered - 1) >= to + 1) step--;

				int[] numberRange = new int[considered];
				for (int i = 0; i < numberRange.length; i++) {
					numberRange[i] = numbers[from + i * step];
				}

				Arrays.sort(numberRange);
				int median = numberRange[numberRange.length / 2];
				for (int i = 0; i < numberRange.length; i++) {
					if(numbers[from + i * step] == median) return from + i * step;
				}
				return -1;
			}

			@Override
			public String toString() {
				return "The median of " + numberOfConsideredElements + " elements distributed throughout the array";
			}
		};
	}
}