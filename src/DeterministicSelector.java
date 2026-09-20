package DAA_asgn1;

public class DeterministicSelector {
    private static long comparisons;
    private static int maxDepth;
    public static long getComparisons() {
        return comparisons;
    }

    public static int getMaxDepth() {
        return maxDepth;
    }

    public static int select(int[] array, int k) {
        comparisons=0;
        maxDepth=0;
        if (array==null || array.length==0) {
            throw new IllegalArgumentException("array cant be emtpy");
        }
        if (k < 1 || k > array.length) {
            throw new IllegalArgumentException("Invalid k");
        }
        return slct(array, 0, array.length - 1, k - 1, 1);
    }

    private static int slct(int[] array, int left, int right, int targetIndex, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        if (left == right) {
            return array[left];
        }
        int pivot = choosePivot(array, left, right, depth);
        int[] equalRange = partition(array, left, right, pivot);
        int equalStart = equalRange[0];
        int equalEnd = equalRange[1];
        if (targetIndex < equalStart) {

            return slct(array,left,equalStart - 1, targetIndex,depth + 1);
        } else if (targetIndex > equalEnd) {

            return slct(array, equalEnd + 1, right, targetIndex, depth + 1);
        } else {
            return pivot;
        }

    }

    private static int choosePivot(int[] array, int left, int right, int depth) {
        int size = right - left + 1;
        int medians = left;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        if (size <= 5) {
            insertionSort(array, left, right);
            return array[left + size / 2];
        }
        for (int groupStart = left; groupStart <= right; groupStart += 5) {
            int groupEnd = Math.min(groupStart + 4, right);
            insertionSort(array, groupStart, groupEnd);
            int medianIndex = groupStart + (groupEnd - groupStart) / 2;
            int temp = array[medians];
            array[medians] = array[medianIndex];
            array[medianIndex] = temp;
            medians++;
        }
        return choosePivot(array, left, medians - 1, depth + 1);
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                comparisons++;
                if (arr[j] <= key) {
                    break;
                }
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static int[] partition(int[] array, int left, int right, int pivot) {
        int pivotIndex=-1;
        for (int i=left; i<=right; i++) {
            comparisons++;
            if (array[i]==pivot) {
                pivotIndex= i;
                break;
            }
        }
        if (pivotIndex==-1) {
            throw new IllegalStateException("Pivot not found");
        }
        int temp=array[pivotIndex];
        array[pivotIndex]=array[right];
        array[right]=temp;
        int low=left;
        int current=left;
        int high=right;
        while (current<=high) {
            comparisons++;
            if (array[current]<pivot) {
                int tempo=array[low];
                array[low]=array[current];
                array[current]=tempo;
                low++;
                current++;
            } else {
                comparisons++;
                if (array[current]>pivot) {
                    int tempo=array[current];
                    array[current]=array[high];
                    array[high]=tempo;
                    high--;
                } else {
                    current++;
                }
            }
        }
        return new int[]{low, high};
    }
}

