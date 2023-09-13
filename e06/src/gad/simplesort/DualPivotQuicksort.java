package gad.simplesort;

public class DualPivotQuicksort extends SortAlgorithm {
	private DualPivotFinder pivotFinder;
	private int selectionSortSize;
	private Selectionsort selectionSort;

	public DualPivotQuicksort(DualPivotFinder pivotFinder, int selectionSortSize) {
		this.pivotFinder = pivotFinder;
		this.selectionSortSize = selectionSortSize;
		// TODO: Selectionsort Optimierung
		selectionSort = new Selectionsort();
	}

	@Override
	public void sort(int[] numbers, Result result, int from, int to) {
		// TODO
		if (from >= to) {
			return;
		}
		result.startDualPivotQuicksort(numbers, from, to);

		if(to - from + 1 <= selectionSortSize) {
			selectionSort.sort(numbers, result, from, to);
			return;
		}

		int[] selectedPivots = pivotFinder.findPivot(numbers, from, to);

		swap(numbers,selectedPivots[0],from);

		if(from == selectedPivots[1]) {
			swap(numbers, selectedPivots[0], to);
		}
		else {
			swap(numbers, selectedPivots[1], to);
		}

		if(numbers[from] > numbers[to]) {
			swap(numbers, from, to);
		}

		int pl = numbers[from];
		int pr = numbers[to];

		int pointerI = from + 1;

		int indexL = from + 1;
		int indexR = to - 1;

		while (pointerI <= indexR) {
			int curNum = numbers[pointerI];
			if(curNum < pl) {
				swap(numbers, pointerI, indexL);
				indexL++;
			}
			else if(curNum > pr) {
				while(numbers[indexR] > pr && pointerI < indexR) indexR--;
				swap(numbers, pointerI, indexR);
				indexR--;

				if(numbers[pointerI] < pl) {
					swap(numbers, pointerI, indexL);
					indexL++;
				}
			}

			pointerI++;
		}

		indexL--;
		indexR++;

		// Pivot an richtige Stelle verschieben
		swap(numbers, indexL, from);
		swap(numbers, indexR, to);


		result.logPartialArray(numbers, from, indexL - 1);
		result.logPartialArray(numbers, indexL + 1, indexR - 1);
		result.logPartialArray(numbers, indexR + 1, to);

		sort(numbers, result, from, indexL - 1);
		sort(numbers, result, indexL + 1, indexR - 1);
		sort(numbers, result, indexR + 1, to);
	}

	@Override
	public String toString() {
		return "DualPivotQuicksort (Pivot: " + pivotFinder + ", Selectionsort for: " + selectionSortSize + ")";
	}
}