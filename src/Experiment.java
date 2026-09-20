package DAA_asgn1;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
public class Experiment {
    private static final Random random=new Random();
    private static final int RUNS=5;
    private static final int[] SIZES={1000,10000,100000};

    public static void run()throws IOException{
        FileWriter writer=new FileWriter("results.csv");
        writer.write("algorithm,size,inputType,timeNs,maxDepth,comparisons\n");

        for(int size:SIZES){
            int[] randomArray=generateRandom(size);
            int[] sortedArray=generateSorted(size);
            int[] reverseArray=generateReverse(size);
            int[] duplicateArray=generateDuplicates(size);

            runAll(writer,size,"Random",randomArray);
            runAll(writer,size,"Sorted",sortedArray);
            runAll(writer,size,"Reverse",reverseArray);
            runAll(writer,size,"Duplicate-heavy",duplicateArray);

            Point[] points=generatePoints(size);
            runClosestPair(writer,size,points);
        }

        writer.close();
        System.out.println("Experiment finished. Results saved to results.csv");
    }

    private static void runAll(FileWriter writer,int size,String type,int[] original)throws IOException{
        runMergeSort(writer,size,type,original);
        runQuickSort(writer,size,type,original);
        runSelection(writer,size,type,original);
    }

    private static void runMergeSort(FileWriter writer,int size,String type,int[] original)throws IOException{
        long[] times=new long[RUNS];
        long[] comparisons=new long[RUNS];
        int[] depths=new int[RUNS];

        for(int i=0;i<RUNS;i++){
            int[] array=original.clone();

            long start=System.nanoTime();
            MergeSort.msort(array);
            long end=System.nanoTime();

            times[i]=end-start;
            comparisons[i]=MergeSort.getComparisons();
            depths[i]=MergeSort.getMaxDepth();
        }

        writer.write("MergeSort,"+size+","+type+","+median(times)+","+median(depths)+","+median(comparisons)+"\n");
    }

    private static void runQuickSort(FileWriter writer,int size,String type,int[] original)throws IOException{
        long[] times=new long[RUNS];
        long[] comparisons=new long[RUNS];
        int[] depths=new int[RUNS];

        for(int i=0;i<RUNS;i++){
            int[] array=original.clone();

            long start=System.nanoTime();
            QuickSorter.qsort(array);
            long end=System.nanoTime();

            times[i]=end-start;
            comparisons[i]=QuickSorter.getComparisons();
            depths[i]=QuickSorter.getMaxDepth();
        }

        writer.write("QuickSort,"+size+","+type+","+median(times)+","+median(depths)+","+median(comparisons)+"\n");
    }

    private static void runSelection(FileWriter writer,int size,String type,int[] original)throws IOException{
        long[] times=new long[RUNS];
        long[] comparisons=new long[RUNS];
        int[] depths=new int[RUNS];

        int k=size/2+1;

        for(int i=0;i<RUNS;i++){
            int[] array=original.clone();

            long start=System.nanoTime();
            DeterministicSelector.select(array,k);
            long end=System.nanoTime();

            times[i]=end-start;
            comparisons[i]=DeterministicSelector.getComparisons();
            depths[i]=DeterministicSelector.getMaxDepth();
        }

        writer.write("DeterministicSelect,"+size+","+type+","+median(times)+","+median(depths)+","+median(comparisons)+"\n");
    }

    private static void runClosestPair(FileWriter writer,int size,Point[] original)throws IOException{
        long[] times=new long[RUNS];
        long[] comparisons=new long[RUNS];
        int[] depths=new int[RUNS];

        for(int i=0;i<RUNS;i++){
            Point[] points=original.clone();

            long start=System.nanoTime();
            ClosestPairSolver.closestPair(points);
            long end=System.nanoTime();

            times[i]=end-start;
            comparisons[i]=ClosestPairSolver.getComparisons();
            depths[i]=ClosestPairSolver.getMaxDepth();
        }

        writer.write("ClosestPair,"+size+",Random,"+median(times)+","+median(depths)+","+median(comparisons)+"\n");
    }

    private static int[] generateRandom(int size){
        int[] array=new int[size];

        for(int i=0;i<size;i++){
            array[i]=random.nextInt(size*10+1);
        }

        return array;
    }

    private static int[] generateSorted(int size){
        int[] array=new int[size];

        for(int i=0;i<size;i++){
            array[i]=i;
        }

        return array;
    }

    private static int[] generateReverse(int size){
        int[] array=new int[size];

        for(int i=0;i<size;i++){
            array[i]=size-i;
        }

        return array;
    }

    private static int[] generateDuplicates(int size){
        int[] array=new int[size];

        for(int i=0;i<size;i++){
            array[i]=random.nextInt(10);
        }

        return array;
    }

    private static Point[] generatePoints(int size){
        Point[] points=new Point[size];

        for(int i=0;i<size;i++){
            double x=random.nextDouble()*size;
            double y=random.nextDouble()*size;
            points[i]=new Point(x,y);
        }

        return points;
    }

    private static long median(long[] times){
        long[] copy=times.clone();
        Arrays.sort(copy);
        return copy[copy.length/2];
    }
    private static int median(int[] values){
        int[] copy=values.clone();
        Arrays.sort(copy);
        return copy[copy.length/2];
    }
}
