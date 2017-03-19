import java.util.Vector;

/**
 * Created by andres on 3/11/17.
 */
public class Point {
    //cartesian point for grid
    public int x, y;
    //change to individual classes if we end up using that, string for now
    public Vector<Character> type;

    public Point (int x, int y, Vector<Character> object) {
        this.x = x; this.y = y; this.type = new Vector<Character>();
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

    public String toString(){
        return "xy: "+x+" " + y +"  type: "+type.toString();
    }
}