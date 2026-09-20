package DAA_asgn1;
import java.util.Arrays;
import java.util.Random;


public class Main {
    private static final Random random=new Random();
    public static void main(String[] args) {
        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPair();

        try {
            Experiment.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testMergeSort(){
        System.out.println("MERGE SORT");

        int[][] tests={
                {8,3,5,1,7,2,6,4},
                {1,2,3,4,5},
                {5,4,3,2,1},
                {4,2,4,1,2,4},
                {},
                {7}
        };

        for(int i=0;i<tests.length;i++){
            int[] actual=tests[i].clone();
            int[] expected=tests[i].clone();

            MergeSort.msort(actual);
            Arrays.sort(expected);

            if(Arrays.equals(actual,expected)){
                System.out.println("Test "+(i+1)+": PASS");
            }else{
                System.out.println("Test "+(i+1)+": FAIL");
            }
        }
    }

    private static void testQuickSort(){
        System.out.println("\nQUICK SORT");

        int[][] tests={
                {8,3,5,1,7,2,6,4},
                {1,2,3,4,5},
                {5,4,3,2,1},
                {4,2,4,1,2,4},
                {},
                {7}
        };

        for(int i=0;i<tests.length;i++){
            int[] actual=tests[i].clone();
            int[] expected=tests[i].clone();

            QuickSorter.qsort(actual);
            Arrays.sort(expected);

            if(Arrays.equals(actual,expected)){
                System.out.println("Test "+(i+1)+": PASS");
            }else{
                System.out.println("Test "+(i+1)+": FAIL");
            }
        }
    }

    private static void testDeterministicSelect(){
        System.out.println("\nDETERMINISTIC SELECT");

        boolean passed=true;

        for(int test=0;test<100;test++){
            int size=random.nextInt(100)+1;
            int[] array=new int[size];

            for(int i=0;i<size;i++){
                array[i]=random.nextInt(100);
            }

            int k=random.nextInt(size)+1;

            int[] sorted=array.clone();
            Arrays.sort(sorted);

            int expected=sorted[k-1];
            int actual=DeterministicSelector.select(array.clone(),k);

            if(actual!=expected){
                passed=false;
                System.out.println("Test "+(test+1)+": FAIL");
            }
        }

        if(passed){
            System.out.println("100 random tests: PASS");
        }
    }

    private static void testClosestPair(){
        System.out.println("\nCLOSEST PAIR");

        int[] sizes={10,100,500,1000,2000};
        boolean passed=true;

        for(int size:sizes){
            Point[] points=new Point[size];

            for(int i=0;i<size;i++){
                points[i]=new Point(
                        random.nextDouble()*10000,
                        random.nextDouble()*10000
                );
            }

            double actual=ClosestPairSolver.closestPair(points);
            double expected=bruteForceClosest(points);

            if(Math.abs(actual-expected)<0.000001){
                System.out.println("n="+size+": PASS");
            }else{
                System.out.println("n="+size+": FAIL");
                passed=false;
            }
        }

        if(passed){
            System.out.println("All Closest Pair tests: PASS");
        }
    }

    private static double bruteForceClosest(Point[] points){
        double min=Double.POSITIVE_INFINITY;

        for(int i=0;i<points.length;i++){
            for(int j=i+1;j<points.length;j++){
                double dx=points[i].x-points[j].x;
                double dy=points[i].y-points[j].y;
                double distance=Math.sqrt(dx*dx+dy*dy);

                if(distance<min){
                    min=distance;
                }
            }
        }

        return min;
    }
}


