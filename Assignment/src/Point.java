/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {

        @Override
        public int compare(Point o1, Point o2) {
            double slopeToO1 = slopeTo(o1);
            double slopeToO2 = slopeTo(o2);
            if (slopeToO1 == slopeToO2) {
                return 0;
            } else if (slopeToO1 < slopeToO2) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    private final int x; // x coordinate
    private final int y; // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that.x == this.x && that.y == this.y)
            // degenerate line segment (between a point and itself)
            return Double.NEGATIVE_INFINITY;
        else if (that.y == this.y) {
            // horizontal line
            return 0.0;
        } else if (that.x == this.x) {
            // vertical line
            return Double.POSITIVE_INFINITY;
        }
        return (0.0 + that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return 0;
        } else if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        } else
            return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point pointA = new Point(3, 17);
        Point pointB = new Point(3, 27);
        Point pointC = new Point(3, 32);
        Point pointD = new Point(1, 1);
        // double x1 = pointA.slopeTo(pointB);
        // double x2 = pointA.slopeTo(pointC);
        // double x3 = pointA.slopeTo(pointA);
        Point[] points = { pointA, pointB, pointC, pointD };
        Arrays.sort(points, pointA.SLOPE_ORDER);
        StdOut.println(Arrays.toString(points));
    }

}