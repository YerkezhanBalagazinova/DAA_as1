package DAA_asgn1;
import java.util.Arrays;
import java.util.Comparator;


public class ClosestPairSolver {
    private static long comparisons;
    private static int maxDepth;

    public static int getMaxDepth() {
        return maxDepth;
    }

    public static long getComparisons() {
        return comparisons;
    }

    public static double closestPair(Point[] points){
        comparisons=0;
        maxDepth=0;

        if(points==null||points.length<2){
            throw new IllegalArgumentException("Enter at least 2 points");
        }

        Point[] byX=points.clone();
        Point[] byY=points.clone();

        Arrays.sort(byX,Comparator.comparingDouble(p->p.x));
        Arrays.sort(byY,Comparator.comparingDouble(p->p.y));

        return closest(byX,byY,1);
    }

    private static double closest(Point[] byX,Point[] byY,int depth){
        if(depth>maxDepth){
            maxDepth=depth;
        }

        if(byX.length<=3){
            return bruteForce(byX);
        }

        int mid=byX.length/2;

        Point[] leftX=Arrays.copyOfRange(byX,0,mid);
        Point[] rightX=Arrays.copyOfRange(byX,mid,byX.length);

        Point[] leftY=new Point[leftX.length];
        Point[] rightY=new Point[rightX.length];

        double borderX=leftX[leftX.length-1].x;

        int leftSameX=0;

        for(Point p:leftX){
            if(p.x==borderX){
                leftSameX++;
            }
        }

        int leftIndex=0;
        int rightIndex=0;

        for(Point p:byY){
            if(p.x<borderX){
                leftY[leftIndex]=p;
                leftIndex++;
            }else if(p.x>borderX){
                rightY[rightIndex]=p;
                rightIndex++;
            }else{
                if(leftSameX>0){
                    leftY[leftIndex]=p;
                    leftIndex++;
                    leftSameX--;
                }else{
                    rightY[rightIndex]=p;
                    rightIndex++;
                }
            }
        }

        double leftDistance=closest(leftX,leftY,depth+1);
        double rightDistance=closest(rightX,rightY,depth+1);
        double d=Math.min(leftDistance,rightDistance);

        double middleX=byX[mid].x;

        Point[] strip=new Point[byY.length];
        int stripSize=0;

        for(Point p:byY){
            comparisons++;

            if(Math.abs(p.x-middleX)<d){
                strip[stripSize]=p;
                stripSize++;
            }
        }

        for(int i=0;i<stripSize;i++){
            for(int j=i+1;j<stripSize&&strip[j].y-strip[i].y<d;j++){
                comparisons++;

                double currentDistance=distance(strip[i],strip[j]);

                if(currentDistance<d){
                    d=currentDistance;
                }
            }
        }

        return d;
    }

    private static double bruteForce(Point[] points){
        double minDistance=distance(points[0],points[1]);

        for(int i=0;i<points.length;i++){
            for(int j=i+1;j<points.length;j++){
                comparisons++;

                double d=distance(points[i],points[j]);

                if(d<minDistance){
                    minDistance=d;
                }
            }
        }

        return minDistance;
    }

    private static double distance(Point a,Point b){
        double dx=a.x-b.x;
        double dy=a.y-b.y;

        return Math.sqrt(dx*dx+dy*dy);
    }
}
