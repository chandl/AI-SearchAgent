import java.util.Comparator;

/**
 * Created by andres on 3/11/17.
 */
public class Point implements Comparable{
    //cartesian point for grid
    public int x, y;
    //change to individual classes if we end up using that, string for now
    public String object;

    //distance from starting Point (for A*)
    private int distFromSource;
    //Total cost of getting from the Starting Point to the goal Point by passing through this Point. (A*)
    private int costToDest;
    //Previous point in path (A*)
    private Point previous;

    //Getters & Setters
    public int getDistFromSource() {return distFromSource;}
    public void setDistFromSource(int distFromSource) {this.distFromSource = distFromSource;}
    public int getCostToDest() {return costToDest;}
    public void setCostToDest(int costToDest) {this.costToDest = costToDest;}
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