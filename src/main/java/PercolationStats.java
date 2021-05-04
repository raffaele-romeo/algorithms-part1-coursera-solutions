import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] stats ;
    private final int trials;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        this.trials = trials;
        stats = new double[trials];
        if(n <= 0 || trials <= 0){
            throw new IllegalArgumentException("n or trials negative");
        }
        for(int i = 0; i < trials; i ++){
            Percolation percolation = new Percolation(n);
            while(!percolation.percolates()){
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                percolation.open(row, col);
            }
            stats[i] = ((double) percolation.numberOfOpenSites() / (n * n));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stats);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(stats);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return this.mean() - ( (1.96 * this.stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return this.mean() - ( (1.96 * this.stddev()) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args){
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("Mean= " + percolationStats.mean());
        System.out.println("Stddev= " + percolationStats.stddev());
        System.out.println("95% confidence interval= [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

}