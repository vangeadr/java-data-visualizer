import java.util.ArrayList;

public class Main 
{
    public static void main(String[] args) 
    {
        System.out.println("Starting Java Data Visualizer...");
        
        // 1. Specify the exact name of the file to be read
        String csvPath = "social_media_mental_health.csv"; 
        
        // 2. Call the parser method to read the file and return a collection of data coordinates
        ArrayList<DataPoint> data = FileParser.parseCSV(csvPath);
        
        // 3. Safety check: If the file is missing or unreadable, stop the program to avoid crashing
        if (data.isEmpty()) 
        {
            System.out.println("Error: No valid data points found. Exiting math engine.");
            return;
        }

        // 4. Print clean headers to describe the dataset variables (Updated to match graph exactly)
        System.out.println("\n<<< social media's impact on mental health >>>\n    << x =  Daily_Social_Media_Hours >>\n    << y =  Sleep_Duration_Hours    >>\n");
        int n = data.size(); // Collect total sample size (number of rows parsed)
        System.out.println("--------------------------------------------");
        System.out.println("Total data points (n): " + n);

        // ==========================================================
        // STEP 1: CALCULATE MEANS (AVERAGES) FOR X AND Y
        // Formula: x̄ = (sum of all x) / n   and   ȳ = (sum of all y) / n
        // ==========================================================
        double sumX = 0;
        double sumY = 0;
        
        // Accumulate the totals for both columns
        for (DataPoint pt : data) 
        {
            sumX += pt.getX();
            sumY += pt.getY();
        }
        
        // Divide totals by the count to get the statistical averages
        double meanX = sumX / n;
        double meanY = sumY / n;
        
        System.out.println("--------------------------------------------");
        System.out.printf("Mean of X (Social Media):   %.4f hours\n", meanX);
        System.out.printf("Mean of Y (Sleep Duration):  %.4f hours\n", meanY);
        System.out.println("--------------------------------------------");

        // ==========================================================
        // STEP 2: CALCULATE SAMPLE STANDARD DEVIATIONS (sx and sy)
        // Formula: s = sqrt( sum(point - mean)^2 / (n - 1) )
        // ==========================================================
        double sumSquaredDiffX = 0;
        double sumSquaredDiffY = 0;
        
        // Accumulate squared distances from the mean for every individual point
        for (DataPoint pt : data) 
        {
            sumSquaredDiffX += Math.pow(pt.getX() - meanX, 2);
            sumSquaredDiffY += Math.pow(pt.getY() - meanY, 2);
        }
        
        // Divide by degrees of freedom (n - 1) and calculate square root for sample deviation
        double stdDevX = Math.sqrt(sumSquaredDiffX / (n - 1));
        double stdDevY = Math.sqrt(sumSquaredDiffY / (n - 1));
        
        System.out.printf("Std Dev of X (sx):          %.4f\n", stdDevX);
        System.out.printf("Std Dev of Y (sy):          %.4f\n", stdDevY);
        System.out.println("--------------------------------------------");

        // ==========================================================
        // STEP 3: CALCULATE CORRELATION COEFFICIENT (r)
        // Formula: r = sum( zX * zY ) / (n - 1)
        // ==========================================================
        double sumProductOfZScores = 0;
        
        // Determine the standard score (z-score) for each pair and multiply them together
        for (DataPoint pt : data) 
        {
            double zX = (pt.getX() - meanX) / stdDevX;
            double zY = (pt.getY() - meanY) / stdDevY;
            sumProductOfZScores += (zX * zY);
        }
        
        // Finalize correlation by dividing the accumulated products by (n - 1)
        double r = sumProductOfZScores / (n - 1);
        double rSquared = r * r;
        System.out.printf("Correlation Coeff. (r):     %.4f\n", r);
        System.out.printf("Coeff. of Determ. (r²):     %.4f\n", rSquared);
        System.out.println("--------------------------------------------");

        // ==========================================================
        // STEP 4: COMPUTE LINEAR REGRESSION COMPONENTS (ŷ = a + bx)
        // Formulas: Slope (b) = r * (sy / sx)
        //           Y-Intercept (a) = ȳ - b(x̄)
        // ==========================================================
        double b = r * (stdDevY / stdDevX); // Calculate rate of change (slope)
        double a = meanY - (b * meanX);     // Calculate base value where x = 0 (y-intercept)
        
        System.out.println("\n<<< Linear Regression Model >>>\n");
        
        // Dynamically checks the slope sign to format the equation elegantly
        if (b < 0) 
        {
            System.out.printf("y = %.4f - %.4fx\n", a, Math.abs(b));
        } 
        else 
        {
            System.out.printf("y = %.4f + %.4fx\n", a, b);
        }
        System.out.println();

        // ==========================================================
        // GENERATE VISUAL SCATTER PLOT WITH RED BEST FIT LINE
        // ==========================================================
        // Pass your dynamically calculated intercept (a) and slope (b) to the graph engine
        GraphGenerator.createScatterPlot(data, "scatterplot.png", a, b);

        // TODO: Implement JavaFX GUI visualization pipeline for dynamic plotting
        // TODO: Add support for multivariate polynomial regression algorithms
    }
}
