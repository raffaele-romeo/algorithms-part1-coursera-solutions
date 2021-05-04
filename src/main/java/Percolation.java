import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int [ ] [ ] grid;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new int [n] [n] ;
        weightedQuickUnionUF = new WeightedQuickUnionUF((n * n) + 2);
        this.n = n ;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        this.validate(row, n);
        this.validate(col, n);
        if(grid[row - 1][col - 1] == 0) {
            grid[row - 1][col - 1] = 1;
            int oneD = xyTo1D(row, col);
            int topVirtualPoint = n * n;
            int downVirtualPoint = n * n + 1;
            if (row == 1 && weightedQuickUnionUF.find(oneD) !=  weightedQuickUnionUF.find(topVirtualPoint)){
                weightedQuickUnionUF.union(oneD, topVirtualPoint);
            }
            if (row == n && weightedQuickUnionUF.find(oneD) !=  weightedQuickUnionUF.find(downVirtualPoint)) {
                weightedQuickUnionUF.union(oneD, downVirtualPoint);
            }
            linkNeighbour(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        this.validate(row, n);
        this.validate(col, n);
        return grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        this.validate(row, n);
        this.validate(col, n);
        int topVirtualPoint = n * n;
        int oneD = xyTo1D(row, col);
        return weightedQuickUnionUF.find(oneD) == weightedQuickUnionUF.find(topVirtualPoint);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        int numberOfOpenSites = 0;
        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (grid[i][j] == 1){
                    numberOfOpenSites ++;
                }
            }
        }
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return isFull(n + 1, 2); //this represent the down virtual point, n * n + 1, in two dimensions
    }

    // test client (optional)
    public static void main(String[] args){
        int n = 20;
        Percolation percolation = new Percolation(n);
        while(!percolation.percolates()){
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            percolation.open(row, col);
        }
        System.out.println(percolation.numberOfOpenSites());
        System.out.println((double) percolation.numberOfOpenSites() / (n * n));
    }

    private void validate(int i, int n){
        if (i <= 0 || i > n) throw new IllegalArgumentException("row index i out of bounds");
    }

    private int xyTo1D(int row, int col) {
        return ((row - 1) * n) + (col -1);
    }

    private void linkNeighbour(int row, int col) {
        int oneD = xyTo1D(row, col);
        if (row > 1 && isOpen(row - 1, col)) weightedQuickUnionUF.union(oneD, xyTo1D(row - 1, col));
        if (col > 1 && isOpen(row, col - 1)) weightedQuickUnionUF.union(oneD, xyTo1D(row, col - 1));
        if (row < n && isOpen(row + 1, col)) weightedQuickUnionUF.union(oneD, xyTo1D(row + 1, col));
        if (col < n && isOpen(row, col + 1)) weightedQuickUnionUF.union(oneD, xyTo1D(row, col + 1));
    }
}