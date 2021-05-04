import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;

public class FastCollinearPoints {

    private int numberOfSegments;
    private final LineSegment[] segments;

    /** finds all line segments containing 4 or more points */
    public FastCollinearPoints(Point[] points) {
        this.validatePoints(points);

        this.numberOfSegments = 0;

        Point[] p = new Point[points.length];
        System.arraycopy(points, 0, p, 0, points.length);
        this.segments = this.analyze(p);
    }

    private LineSegment[] analyze(Point[] points) {
        LineSegment[] tmpSegments = new LineSegment[points.length * 4];
        MergeX.sort(points);

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            int copySize = points.length - i - 1;
            Point[] copyPoints = new Point[copySize];
            System.arraycopy(points, i + 1, copyPoints, 0, copySize);
            MergeX.sort(copyPoints, p.slopeOrder());

            int lineCounter = 2;
            for (int j = 1; j < copySize; j++) {
                if (Double.compare(p.slopeTo(copyPoints[j - 1]), p.slopeTo(copyPoints[j])) == 0) {
                    lineCounter++;
                } else {
                    if (lineCounter >= 4) {
                        tmpSegments[numberOfSegments++] = new LineSegment(p, copyPoints[j - 1]);
                    }
                    lineCounter = 2;
                }
            }
            if (lineCounter >= 4) {
                tmpSegments[numberOfSegments++] = new LineSegment(p, copyPoints[copyPoints.length - 1]);
            }
        }

        LineSegment[] realSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            realSegments[i] = tmpSegments[i];
        }
        return realSegments;
    }

    /** the number of line segments */
    public int numberOfSegments() {
        return numberOfSegments;
    }

    /** the line segments */
    public LineSegment[] segments() {
        LineSegment[] returnSegments = new LineSegment[segments.length];
        System.arraycopy(segments, 0, returnSegments, 0, segments.length);
        return returnSegments;
    }

    private void validatePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int j = 0; j < points.length; j++) {
            for (int k = j + 1; k < points.length; k++) {
                if (points[j] == null || points[k].equals(points[j])) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}