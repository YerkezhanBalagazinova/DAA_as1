package DAA_asgn1;

public class MergeSort {
    private static final int CUTOFF=4;
    private static long comparisons;
    private static int maxDepth;
    public static long getComparisons() {
        return comparisons;
    }

    public static int getMaxDepth() {
        return maxDepth;
    }

    public static void msort(int[] array){
        comparisons=0;
        maxDepth=0;
        if (array==null || array.length<=1){
            return;
        }
        int[] buffer= new int[array.length];
        divide(array,buffer,0,array.length-1,1);

    }
    public static void divide(int[]array, int[] buffer, int left, int right,int depth){
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        if (left>=right) {
            return;
        }
        if(right-left+1<=CUTOFF){
            insertionSort(array,left,right);
            return;
        }

        int mid=(left+right)/2;
        divide(array,buffer,left,mid,depth+1);
        divide(array, buffer, mid+1, right,depth+1);
        combine(array,buffer,left,mid,right);
    }

    private static void combine(int[] array, int[] buffer, int left, int mid, int right) {
        for (int i=left;i<=right;i++) {
            buffer[i]=array[i];
        }

        int i=left;
        int j=mid + 1;
        int k=left;

        while (i<=mid && j<=right) {
            comparisons++;
            if (buffer[i]<= buffer[j]) {
                array[k]=buffer[i];
                i++;
            }
            else {
                array[k] = buffer[j];
                j++;
            }
            k++;
        }
        while (i<=mid) {
            array[k]=buffer[i];
            i++;
            k++;
        }
    }
    private static void insertionSort(int[]arr, int left,int right){
        for(int i=left+1;i<=right;i++){
            int key=arr[i];
            int j=i-1;
            while (j>=left) {
                comparisons++;
                if (arr[j]<=key) {
                    break;
                }
                arr[j+1]=arr[j];
                j--;
            }
            arr[j+1]=key;

        }
    }
}

