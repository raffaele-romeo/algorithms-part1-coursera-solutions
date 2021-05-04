import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;

public class BruteCollinearPoints {
    private final Point[] p;
    private final LineSegment[] segments;
    private int numberOfSegments = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);
        p = points;
        segments = new LineSegment[points.length];
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        Point[] bruteCollinearPoints = new Point[p.length];
        System.arraycopy(p, 0, bruteCollinearPoints, 0, p.length);

        MergeX.sort(bruteCollinearPoints);
        for (int i = 0; i < bruteCollinearPoints.length; i++) {
            for (int j = i + 1; j < bruteCollinearPoints.length; j++) {
                for (int k = j + 1; k < bruteCollinearPoints.length; k++) {
                    if (bruteCollinearPoints[i].slopeTo(bruteCollinearPoints[j]) == bruteCollinearPoints[i].slopeTo(bruteCollinearPoints[k])) {
                        for (int z = k + 1; z < bruteCollinearPoints.length; z++) {
                            if (bruteCollinearPoints[i].slopeTo(bruteCollinearPoints[j]) == bruteCollinearPoints[i].slopeTo(bruteCollinearPoints[z])) {
                                segments[numberOfSegments++] = new LineSegment(bruteCollinearPoints[i], bruteCollinearPoints[z]);
                            }
                        }
                    }
                }
            }
        }
        LineSegment[] segmentsOutput = new LineSegment[numberOfSegments];
        if (numberOfSegments >= 0) System.arraycopy(segments, 0, segmentsOutput, 0, numberOfSegments);
        return segmentsOutput;
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
        //In in = new In(args[0]);
        In in = new In(new File("C:\\Users\\RaffaeleRomeo\\Downloads\\collinear\\input40.txt"));
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdOut.println(points.length);
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}