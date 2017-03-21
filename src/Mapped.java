import java.util.*;

/**
 * Created by andres on 3/4/17.
 */

public class Mapped {
    static final int WIDTH = 100;
    static final int HEIGHT = 100;
    public boolean empty;
    public char facing;
    public Point origin;
    public Graph visited;
    public Graph known;

    //Get origin method, returns the char direction you facing presently: n w s e

    public char getFacing() {

        return facing;
    }

    //get any points neighbors as an array

    public List<Point> getNeighbors(Point p) {
        List<Point> list = Arrays.asList((Point[]) known.adjList.get(new Point(p.x,p.y,null)).toArray());
        return list;
    }


    //internal map of visited and seen parts of the grid
    //use graph to build out with adjacency list.

    class Graph {

        HashMap<Point, HashSet<Point>> adjList;
        public Graph(int x, int y) {

            adjList = new HashMap<Point, HashSet<Point>>();

            for (int count = 0; count < x; count++) {

                for (int count2 = 0; count2 < y; count2++) {
                    adjList.put(new Point(x,y,null), new HashSet<Point>());
                }

            }
            facing = 'w';
            origin = new Point(48, 45,null);
        }
        public Graph() {
            facing = 'w';

            origin = new Point(48,45,null);
            adjList = new HashMap<Point, HashSet<Point>>();


        }

        public HashSet<Point> changeNeighbor (Point find, Point newp ) {
            if (adjList.containsKey(find)) {
                if (adjList.get(find).contains(newp)) {
                    for (Point p :
                            adjList.get(find)) {
                        if (p.equals(newp)) {
                            p.setPointType(newp.type);
                        }
                    }
                }
            }
            return adjList.get(find);

        }
        public int addNeighbors(Point find, Point add) {
            try {
                if (adjList.containsKey(add)){
                    System.out.print("found");
                    adjList.get(find).add(add);
                    adjList.get(add).add(find);
                } else {

                    System.out.print("not found");

                    adjList.get(find).add(add);
                    adjList.get(add).add(find);
                }

                return 0;
            } catch (Exception e) {
                return 1;
            }

        }

        public HashSet<Point> getNeighbors(Point find) {
            return adjList.get(find);
        }

    }


    public Mapped() {

        this.empty = true;
        this.visited = new Graph();
        this.known = new Graph();

    }

    //constructor of singleton cause we only need 1 map
    private static class SingleMap {


        private static final Mapped SINGLETON = new Mapped();


    }

    public static Mapped getInstance() {

        return SingleMap.SINGLETON;
    }

    char getDirection (char to) {
        switch (facing) {
            case 'n' : {
                if (to == 'l') return 'w';
                if (to == 'r') return 'e';
                break;
            }
            case 'w' : {
                if (to == 'l') return 's';
                if (to == 'r') return 'n';

                break;
            }
            case 's' : {
                if (to == 'l') return 'e';
                if (to == 'r') return 'w';

                break;
            }
            case 'e' : {
                if (to == 'l') return 'n';
                if (to == 'r') return 's';

                break;
            }
            default: break;

        }
        return '\0';
    }
    public void move(char move) {
        if (move == 'l' || move == 'r') {
            facing = getDirection(move);

        } else {
            if (facing == 'n' || facing == 's') {
                if (facing == 'n') {
                    if (move == 'f') {
                        origin.x--;
                    } else {
                        origin.x++;
                    }
                } else {
                    if (move == 'f') {
                        origin.x++;
                    } else {
                        origin.x--;
                    }
                }
            } else {
                if (facing == 'w') {
                    if (move == 'f') {
                        origin.y--;
                    } else {
                        origin.y++;
                    }
                } else {
                    if (move == 'f') {
                        origin.y++;
                    } else {
                        origin.y--;
                    }
                }
            }
        }
    }
    public void addView(ArrayList<ArrayList<Vector<Character>>> view) {
        ArrayList<Point> grid = new ArrayList<Point>();
        //take in a field of view and insert/update accordingly
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 5; y++) {
                if (!view.get(x).get(y).contains('*')) {
                   // System.out.println(view.get(x).get(y).firstElement());
                    grid.add(new Point(x,y,view.get(x).get(y)));

                }
            }
        }


        if(empty) {
//

            switch (facing) {

                case 'n': {
//                    for (Point p :
//                            grid) {
//                        known.adjList.putIfAbsent(new Point(p.x, p.y, p.type), new HashSet<Point>());
//                    }
                    break;
                }
                case 'w': {
                    System.out.println("-----");
//
                    Point[][] points3 = new Point[5][7];

                    for (int i = 0; i < view.size(); i++) {
                        for (int j = 0; j < view.get(0).size(); j++){

                            //System.out.print(view[i][j] + "=>");
                            if (grid.contains(new Point(i,j,null))) {

                                points3[5-1-j][i] = new Point(i,j,view.get(i).get(j));

                            } else {

                                points3[5-1-j][i] = new Point(i,j,null);

                            }

                            //System.out.println("xy: " + j + " " + (7-1-i) + " " + points[j][7-1-i]);

                        }
                    }

                    for (int x = 0; x< points3.length; x++) {
                        for (int y = 0; y< points3[0].length; y++) {

                            if (!points3[x][y].type.contains('*')) {
                                System.out.println("found:" + points3[x][y].type.toString());
                                known.adjList.putIfAbsent(new Point(x+origin.x, y+origin.y, points3[x][y].type), new HashSet<Point>());

                            }
                        }

                        System.out.println("");

                    }
                    System.out.println(grid.size());



//                    for (Point p :
//                            grid) {
//                        known.adjList.putIfAbsent(new Point(p.x+origin.x, p.y+origin.y, p.type), new HashSet<Point>());
//                        ArrayList<ArrayList<Point>> grid90 = new ArrayList<ArrayList<Point>>();
//
//
////                        int [][] rotate(int [][] input){
////
////                            int n =input.length();
////                            int m = input[0].length();
////                            int [][] output = new int [m][n];
////
////                            for (int i=0; i<n; i++)
////                                for (int j=0;j<m; j++)
////                                    output [j][n-1-i] = input[i][j];
////                            return output;
////                        }
//                    }

                    break;
                }
                case 's': {

                    break;
                }
                case 'e': {
                    Point[][] points = new Point[5][7];
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 7; j++){
                            points[j][7-1-i] = new Point(i,j,view.get(i).get(j));
                        }
                    }

                    break;
                }
                default : break;
            }
            empty = false;

        } else {
            switch (facing) {

                case 'n': {
                    for (Point p :
                            grid) {
                        known.adjList.putIfAbsent(new Point(p.x+origin.x, p.y+origin.y, p.type), new HashSet<Point>());
                    }
                    break;

                }

                case 'w': {

                    System.out.println("-----");
//
                    Point[][] points3 = new Point[5][7];

                    for (int i = 0; i < view.size(); i++) {
                        for (int j = 0; j < view.get(0).size(); j++){

                            //System.out.print(view[i][j] + "=>");
                            if (grid.contains(new Point(i,j,null))) {
                                points3[5-1-j][i] = new Point(i,j,view.get(i).get(j));
                            }
//                            else {
//                                points3[5-1-j][i] = new Point(i,j,'*' );
//                            }
                            //System.out.println("xy: " + j + " " + (7-1-i) + " " + points[j][7-1-i]);
                        }
                    }

                    for (int x = 0; x< points3.length; x++) {
                        for (int y = 0; y< points3[0].length; y++) {

                            System.out.print(points3[x][y].type + " ");
                            if (!points3[x][y].type.contains('*')) {
                                known.adjList.putIfAbsent(new Point(x+origin.x, y+origin.y, points3[x][y].type), new HashSet<Point>());

                            }
                        }

                        System.out.println("");

                    }

                    System.out.println(grid.size());

//                    for (Point p :
//                            grid) {
//                        known.adjList.putIfAbsent(new Point(p.x+origin.x, p.y+origin.y, p.type), new HashSet<Point>());
//                        ArrayList<ArrayList<Point>> grid90 = new ArrayList<ArrayList<Point>>();
//
//
////                        int [][] rotate(int [][] input){
////
////                            int n =input.length();
////                            int m = input[0].length();
////                            int [][] output = new int [m][n];
////
////                            for (int i=0; i<n; i++)
////                                for (int j=0;j<m; j++)
////                                    output [j][n-1-i] = input[i][j];
////                            return output;
////                        }
//                    }

                    break;
                }
                case 's': {

                    break;
                }
                case 'e': {
                    Point[][] points = new Point[5][7];
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 7; j++){
                            points[j][7-1-i] = new Point(i,j,view.get(i).get(j));
                        }
                    }

                    break;
                }
                default : break;
            }
        }


    }

    //wishful classes
    public void setPoint(){

        //maybe set a flag on the map to return to

    }

    public AgentKnowledge agentFoodDirectionGuess() {
        return null;
    }
    public AgentKnowledge agentSeenHammer() {
        return null;

    }
    public AgentKnowledge agentSeenRobot() {
        //has the agent seen a robot
        return null;

    }
    public AgentKnowledge agentSeenKey() {
        //has the agent seen a key
        return null;

    }
    public AgentKnowledge agentSeenDoor() {
        //has the agent seen a door
        return null;

    }
    public AgentKnowledge agentInRoom() {
        //are we in a room right now?
        return null;

    }

    public AgentKnowledge agentSeenFood() {
        //has the agent seen food
        return null;

    }

    public class AgentKnowledge {

        public boolean affirmation;
        public Point location;

    }
    public static void main(String[] args) {

        Mapped map = new Mapped();
//        map.known.adjList.put(new Point(0,1,'b'), new HashSet<Point>());
//        map.known.adjList.put(new Point(0,0,'a'), new HashSet<Point>());
////
////        map.known.adjList.put(new Point(2,1,'a'), new HashSet<Point>());
//
//        map.known.addNeighbors(new Point(0,0,'a'), new Point(0,1,'b'));
//
//        map.known.addNeighbors(new Point(0,1,'b'), new Point(2,1,'c'));
////        for (Point hs:
////                map.known.adjList.keySet()) {
////            if (hs == new Point(0,0,' ')) {
////                System.out.println("found 0");
////                hs.setPointType('d');
////                hs.
////                hs = new Point(0,0,'d');
////            }
////        }
//        for (HashSet<Point> hs :
//                map.known.adjList.values()) {
//            if (hs.contains(new Point(0, 0, ' '))) {
//                System.out.println("found 0");
//                hs.remove(new Point(0, 0, ' '));
//                hs.add(new Point(0, 0, 'd'));
//            }
//        }
//
//        for (HashSet<Point> p:
//                map.known.adjList.values()) {
//            System.out.println(p);
//        }
        ArrayList<ArrayList<Vector<Character>>> newview = new ArrayList<ArrayList<Vector<Character>>>();
               char[][] viewedarray =  {
                        {' ', ' ', '*', ' ', 'X'},
                        {' ', '+', '*', '+', ' '},
                        {' ', ' ', '*', ' ', ' '},
                        {' ', ' ', '*', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '},
                        {' ', ' ', '1', ' ', ' '},
                        {' ', ' ', '*', ' ', 'X'},
                };
               Vector<Character> temp = new Vector<Character>();

        for (int row=0; row<7; row++){
            newview.add(row, new ArrayList<Vector<Character>>());
            for (int col=0; col<5; col++){
                newview.get(row).add(col, new Vector<Character>());
                newview.get(row).get(col).add(viewedarray[row][col]);
            }
        }

        for (int x = 0; x< newview.size(); x++) {
            for (int y = 0; y< newview.get(0).size(); y++) {

                System.out.print(newview.get(x).get(y).toString() + "_");

            }

            System.out.println("" );

        }


        map.addView(newview);
        System.out.println("####");

        for (Point p :
                map.known.adjList.keySet()) {
            System.out.println(p);
        }
        System.out.println(map.origin.x + " " + map.origin.y);
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                if (map.known.adjList.containsKey(new Point(x,y,null))) {
                    for(Point p: map.known.adjList.keySet()) {
                        if (p.equals(new Point(x,y,null))) {
                            System.out.print(p.type);
                        }
                    }
                } else {
                    System.out.print("_");
                }
            }
            System.out.println(" " );

        }
        Vector<Character> asdfkj = new Vector<Character>();
        asdfkj.add('a');asdfkj.add('a');
        System.out.println(asdfkj.toString());
        System.out.println(map.origin.x + " " + map.origin.y + " : " + map.facing);
        map.move('f');
        ArrayList<ArrayList<Vector<Character>>> newview2 = new ArrayList<ArrayList<Vector<Character>>>();
        char[][] newviewa =
                {
                        {'X', ' ', ' ', ' ', 'X'},
                        {' ', ' ', '*', ' ', 'X'},
                        {' ', '+', '*', '+', ' '},
                        {' ', ' ', '*', ' ', ' '},
                        {' ', ' ', '*', ' ', ' '},
                        {' ', ' ', '1', ' ', ' '},
                        {' ', ' ', ' ', ' ', ' '}
                };
        for (int row=0; row<7; row++){
            newview2.add(row, new ArrayList<Vector<Character>>());
            for (int col=0; col<5; col++){
                newview2.get(row).add(col, new Vector<Character>());
                newview2.get(row).get(col).add(newviewa[row][col]);
            }
        }
        map.addView(newview2);
        for (Point p :
                map.known.adjList.keySet()) {
            System.out.println(p);
        }
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                if (map.known.adjList.containsKey(new Point(x,y,null))) {
                    for(Point p: map.known.adjList.keySet()) {
                        if (p.equals(new Point(x,y,null))) {
                            System.out.print(p.type);
                        }
                    }
                } else {
                    System.out.print("_");
                }
            }
            System.out.println(" " );

        }
        return;
    }


}


