import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by andres on 3/13/17.
 */
public class Sandbox {

    public static void main (String[] args) {
        String string = "abcdefghijklmnopqrstuvwxyz123456789!@#$%^&*(";
        StringCharacterIterator lists = new StringCharacterIterator(string);
        Point[][] view = new Point[7][5];
        System.out.println("___");
        Vector<Character> temp = new Vector<Character>();
        for (int x = 0; x< 7; x++) {
            for (int y = 0; y< 5; y++) {
                temp.add(lists.next());
                view[x][y] = new Point(x,y,temp);
                temp.clear();
            }
        }
        for (int x = 0; x< view.length; x++) {
            for (int y = 0; y< view[0].length; y++) {

                System.out.print(view[x][y].type + " ");

            }

            System.out.println("" );

        }
//                        int [][] rotate(int [][] input){
//
//                            int n =input.length();
//                            int m = input[0].length();
//                            int [][] output = new int [m][n];
//
//                            for (int i=0; i<n; i++)
//                                for (int j=0;j<m; j++)
//                                    output [j][n-1-i] = input[i][j];
//                            return output;
//                        }
        //System.out.println(view.length);
        Point[][] points2 = new Point[view[0].length][view.length];

        for (int i = 0; i < view.length; i++) {
            for (int j = 0; j < view[0].length; j++){
               // System.out.print(view[i][j] + "=>");

                points2[j][view.length-1-i] = new Point(i,j,view[i][j].type);
               // System.out.println("xy: " + j + " " + (7-1-i) + " " + points[j][7-1-i]);
            }
        }


        for (int x = 0; x< points2.length; x++) {
            for (int y = 0; y< points2[0].length; y++) {

                System.out.print(points2[x][y].type + " ");

            }

            System.out.println("" );

        }

        System.out.println("-----");
//
        Point[][] points3 = new Point[5][7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++){
                //System.out.print(view[i][j] + "=>");

                points3[5-1-j][i] = new Point(i,j,view[i][j].type);
                //System.out.println("xy: " + j + " " + (7-1-i) + " " + points[j][7-1-i]);
            }
        }


        for (int x = 0; x< points3.length; x++) {
            for (int y = 0; y< points3[0].length; y++) {

                System.out.print(points3[x][y].type + " ");

            }

            System.out.println("" );

        }
        System.out.println("-----");

//        for (int x = 0; x< points.length; x++) {
//            for (int y = 0; y< points[0].length; y++) {
//
//                System.out.print(points[x][y].type + " ");
//
//            }
//
//            System.out.println("" );
//
//        }
        Point[][] points4 = new Point[points2[0].length][points2.length];

        for (int i = 0; i < points2.length; i++) {
            for (int j = 0; j < points2[0].length; j++){
                // System.out.print(view[i][j] + "=>");

                points4[j][points2.length-1-i] = new Point(i,j,points2[i][j].type);
                // System.out.println("xy: " + j + " " + (7-1-i) + " " + points[j][7-1-i]);
            }
        }


        for (int x = 0; x< points4.length; x++) {
            for (int y = 0; y< points4[0].length; y++) {

                System.out.print(points4[x][y].type + " ");

            }

            System.out.println("" );

        }
        System.out.println("-----");
        Point[][] points5 = new Point[view.length][view[0].length];

        for (int i = 0; i < view.length; i++) {
            for (int j = 0; j < view[0].length; j++){
                // System.out.print(view[i][j] + "=>");

                points5[view.length-1-i][view[0].length-1-j] = new Point(i,j,view[i][j].type);
                // System.out.println("xy: " + j + " " + (7-1-i) + " " + points[j][7-1-i]);
            }
        }


        for (int x = 0; x< points5.length; x++) {
            for (int y = 0; y< points5[0].length; y++) {

                System.out.print(points5[x][y].type + " ");

            }

            System.out.println("" );

        }
        return;
    }
}
