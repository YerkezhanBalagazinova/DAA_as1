package DAA_asgn1;
import java.util.Random;

public class QuickSorter {
    private static final Random rndm = new Random();
    private static long comparisons;
    private static int maxDepth;

    public static int getMaxDepth() {
        return maxDepth;
    }

    public static long getComparisons() {
        return comparisons;
    }
    public static void qsort(int[] array){
        comparisons=0;
        maxDepth=0;

        if (array==null || array.length<=1){
            return;
        }
        quickSort(array,0,array.length-1,1);
    }
    public static void quickSort(int[]array,int left,int right,int depth){
        if (depth>maxDepth){
            maxDepth=depth;
        }
        while (left<right) {
            int pivotIndex=partition(array,left,right);
            if (pivotIndex-left<right-pivotIndex) {
                quickSort(array, left, pivotIndex - 1,depth+1);
                left = pivotIndex + 1;
            }
            else {
                quickSort(array, pivotIndex + 1, right,depth+1);
                right = pivotIndex - 1;
            }
        }
    }
    private static int partition(int[] array,int left, int right){
        int randomIdx=left+rndm.nextInt(right-left+1);
        int temp=array[randomIdx];
        array[randomIdx]=array[right];
        array[right]=temp;
        int pivot=array[right];
        int i=left-1;
        for(int j=left;j<right;j++){
            comparisons++;
            if (array[j]<=pivot) {
                i++;
                int tempo=array[i];
                array[i]=array[j];
                array[j]=tempo;
            }
        }
        temp=array[i+1];
        array[i+1]=array[right];
        array[right]=temp;
        return i+1;
    }
}
