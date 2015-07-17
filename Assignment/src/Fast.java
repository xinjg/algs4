import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Fast {

    public static void main(String[] args) {
        String fileName = args[0];
        In fileInput = new In(fileName);
        int size = fileInput.readInt();
        Point[] points = new Point[size];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        int i = 0;
        while (!fileInput.isEmpty()) {
            int x = fileInput.readInt();
            int y = fileInput.readInt();
            Point point = new Point(x, y);
            points[i++] = point;
            point.draw();
        }
        Arrays.sort(points);
        List<Point> allPoints = new ArrayList<Point>(); // all points in line
        for (int j = 0; j < points.length; j++) {
            // copy array
            Point p = points[j];
            int auxIterator = 0;
            Point[] aux = new Point[size];
            for (int k = 0; k < size; k++) {
                if (p != points[k]) {
                    aux[auxIterator++] = points[k];
                }
            }
            Arrays.sort(aux, 0, size - 1, p.SLOPE_ORDER);
            aux[size - 1] = p;
            findLine(p, aux, allPoints);
        }
    }

    private static void findLine(Point p, Point[] slopeOrdered,
            List<Point> allPoints) {
        List<Point> line = new ArrayList<Point>(4);
        double[] slopes = new double[slopeOrdered.length];
        slopes[slopeOrdered.length - 1] = Double.POSITIVE_INFINITY;
        for (int i = 0; i < slopes.length; i++) {
            if (i < slopes.length - 1) {
                slopes[i] = p.slopeTo(slopeOrdered[i]);
            }
            if (i > 0 && slopes[i] == slopes[i - 1]) {
                if (!line.contains(slopeOrdered[i - 1])) {
                    line.add(slopeOrdered[i - 1]);
                }
                line.add(slopeOrdered[i]);
            } else {
                /* print line */
                if (line.size() >= 3) {
                    line.add(0, p);
                    if (!allPoints.containsAll(line)) {
                        printLine(line);
                        allPoints.addAll(line);
                    }
                }
                if (!line.isEmpty()) {
                    line.clear();
                }
            }
            if (line.size() >= 4 && i == slopeOrdered.length - 1
                    && isOrdered(line)) {
                printLine(line);
                line.clear();
            }
        }
    }

    private static void printLine(List<Point> line) {
        Point start = null;
        Point end = null;
        for (Iterator<Point> iterator = line.iterator(); iterator.hasNext();) {
            Point point = (Point) iterator.next();
            StdOut.print(point.toString());
            if (iterator.hasNext())
                StdOut.print("->");
            // find start point
            if (start == null) {
                start = point;
            }
            end = point;
        }
        StdOut.println();
        if (start != null && end != null)
            start.drawTo(end);
    }

    private static boolean isOrdered(List<Point> line) {
        Point prev = null;
        for (Iterator<Point> iterator = line.iterator(); iterator.hasNext();) {
            Point point = iterator.next();
            if (prev != null && prev.compareTo(point) > 0)
                return false;
            prev = point;
        }
        return true;
    }
}
