import java.util.Vector;
import java.util.Comparator;


/**
 * Created by andres on 3/11/17.
 */
public class Point implements Comparable{
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //cartesian point for grid
    public int x, y;
    //change to individual classes if we end up using that, string for now
    public Vector<Character> type;

    public Point (int x, int y, Vector<Character> object) {
        this.x = x; this.y = y;
        this.type = object==null ? new Vector<>() : object;
    }

    @Override
    public int hashCode(){
        //System.out.println("In hashcode");
        int hashcode = 0;
        hashcode = ((x*101)+(y*101));
        return hashcode;
    }

    @Override
    public boolean equals(Object obj){
        //System.out.println("In equals");
        if (obj instanceof Point) {
            Point pp = (Point) obj;
            return (pp.x == this.x && pp.y == this.y);
        } else {
            return false;
        }
    }

    public Vector<Character> getPointType() {
        return type;
    }
    public void setPointType(Vector<Character> type) {
        this.type = type;
    }


//    public String toString(){
//        return "xy: "+x+" " + y +"  type: "+type.toString();
//    }

    public String toString(){
        return "Point ("+x+", "+y+") ";
    }
    //distance from starting Point (for A*)
    private int distFromSource;
    //Total cost of getting from the Starting Point to the goal Point by passing through this Point. (A*)
    private double costToDest;
    //Previous point in path (A*)
    private Point previous;
    //for A*
    public char facing;

    //Getters & Setters
    public int getDistFromSource() {return distFromSource;}
    public void setDistFromSource(int distFromSource) {this.distFromSource = distFromSource;}
    public double getCostToDest() {return costToDest;}
    public void setCostToDest(double costToDest) {this.costToDest = costToDest;}
    public Point getPrevious() {return previous;}
    public void setPrevious(Point previous) {this.previous = previous;}


    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Point)){throw new ClassCastException("Invalid Point!");}

        Point compare = (Point)o;

        if(costToDest == compare.costToDest){return 0;}
        return costToDest > compare.costToDest ? 1 : -1;
    }

    static class PointComparator implements Comparator{
        @Override
        public int compare(Object o1, Object o2) {
            if(!(o1 instanceof Point) || !(o2 instanceof Point)){
                throw new ClassCastException("Invalid Point!");
            }
            Point p1 = (Point)o1, p2 = (Point)o2;
            if(p1.costToDest == p2.costToDest) return 0;

            return p1.costToDest > p2.costToDest ? 1 : -1;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }

    }
}