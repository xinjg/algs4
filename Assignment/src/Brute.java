public class Brute {

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
            point.draw();
            points[i++] = point;
            // StdOut.println(x + "     " + y);
        }

        for (int j = 0; j < points.length; j++) {
            Point pointJ = points[j];
            for (int k = 0; k < points.length; k++) {
                Point pointK = points[k];
                for (int l = 0; l < points.length; l++) {
                    Point pointL = points[l];
                    for (int m = 0; m < points.length; m++) {
                        Point pointM = points[m];
                        if (pointJ.compareTo(pointK) < 0
                                && pointK.compareTo(pointL) < 0
                                && pointL.compareTo(pointM) < 0) {
                            double slope1 = pointJ.slopeTo(pointK);
                            double slope2 = pointJ.slopeTo(pointL);
                            double slope3 = pointJ.slopeTo(pointM);
                            if (slope1 == slope2 && slope2 == slope3) {
                                StdOut.println(pointJ.toString() + "->"
                                        + pointK.toString() + "->"
                                        + pointL.toString() + "->"
                                        + pointM.toString());
                                /*
                                 * call drawTo() once for each line segment
                                 * discovered
                                 */
                                if (pointJ.compareTo(pointK) < 0
                                        && pointK.compareTo(pointL) < 0
                                        && pointL.compareTo(pointM) < 0) {
                                    pointJ.drawTo(pointM);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}