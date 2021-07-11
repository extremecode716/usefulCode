public class QuickSort {
	public static <T extends Comparable<T>> void sort(T[] a) {
		quicksort(a, 0, a.length - 1);
	}

	private static <T extends Comparable<T>> void quicksort(T[] a, int low, int high) {
		if (low >= high)
			return;

		int pivot = partition(a, low, high);
		quicksort(a, low, pivot);
		quicksort(a, pivot + 1, high);
	}

	private static <T extends Comparable<T>> int partition(T[] a, int low, int high) {
		T x = a[low];
		int m = (low + high) / 2;
		if ((a[low].compareTo(a[m]) <= 0 && a[m].compareTo(a[high]) <= 0)
				|| (a[high].compareTo(a[m]) <= 0 && a[m].compareTo(a[low]) <= 0))
			x = a[m];
		if ((a[low].compareTo(a[high]) <= 0 && a[high].compareTo(a[m]) <= 0)
				|| (a[m].compareTo(a[high]) <= 0 && a[high].compareTo(a[low]) <= 0))
			x = a[high];
		int i = low - 1;
		int j = high + 1;
		while (true) {
			do {
				++i;
			} while (!(i > high || a[i].compareTo(x) >= 0));
			do {
				--j;
			} while (!(j < low || a[j].compareTo(x) <= 0));
			if (i < j)
				swap(a, i, j);
			else
				return j;
		}
	}

	private static <T extends Comparable<T>> void swap(T[] a, int left, int right) {
		T tmp;
		tmp = a[left];
		a[left] = a[right];
		a[right] = tmp;
	}
}
