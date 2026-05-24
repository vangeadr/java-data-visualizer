import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class GraphGenerator 
{
    // Added 'double intercept' and 'double slope' parameters to the method definition
    public static void createScatterPlot(ArrayList<DataPoint> data, String outputPath, double intercept, double slope) 
    {
        // 1. Image dimensions
        int width = 450;
        int height = 450;
        int padding = 50; 

        // 2. Create canvas
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 3. Background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        // 4. Calculate corner positions
        int cornerX = padding;  
        int cornerY = height - padding; 
        int graphWidth = width - (2 * padding);
        int graphHeight = height - (2 * padding);

        // 5. Draw White Axes Lines
        g2d.setColor(Color.WHITE);
        g2d.drawLine(cornerX, padding, cornerX, cornerY); // Y Axis
        g2d.drawLine(cornerX, cornerY, width - padding, cornerY); // X Axis

        // 6. Set up fonts for labels
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        // 7. DRAW SCALE NUMBERS AND TICKS
        // X-Axis Numbers (Screen Time: 0 to 12)
        g2d.drawString("0", cornerX, cornerY + 15);
        g2d.drawString("6", cornerX + (graphWidth / 2) - 3, cornerY + 15);
        g2d.drawString("12", width - padding - 10, cornerY + 15);

        // Y-Axis Numbers (Sleep: 0 to 10)
        g2d.drawString("10", cornerX - 20, padding + 5);
        g2d.drawString("5", cornerX - 13, padding + (graphHeight / 2) + 5);
        g2d.drawString("0", cornerX - 13, cornerY + 5);

        // 8. DRAW AXIS TITLE LABELS
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        // X-Axis Title
        g2d.drawString("Daily Social Media (Hours)", cornerX + (graphWidth / 4) + 20, height - 12);
        
        // Y-Axis Title
        g2d.drawString("Sleep (Hours)", cornerX - 25, padding - 15);

        // 9. Data boundaries (0 to max)
        double minX = 0.0;
        double maxX = 12.0; 
        double minY = 0.0;
        double maxY = 10.0; 

        // 10. Loop through every DataPoint and plot them
        g2d.setColor(Color.WHITE); 
        for (DataPoint pt : data) 
        {
            int pixelX = cornerX + (int) ((pt.getX() / maxX) * graphWidth);
            int pixelY = cornerY - (int) ((pt.getY() / maxY) * graphHeight);

            // Safety bounds check
            if (pixelX >= cornerX && pixelX <= (width - padding) && pixelY >= padding && pixelY <= cornerY) 
            {
                g2d.fillRect(pixelX, pixelY, 1, 1);
            }
        }

        // ==========================================================
        // STEP 11: DRAW LINE OF BEST FIT (ŷ = a + bx)
        // ==========================================================
        // Point 1: Find predicted Y when X = 0.0 (Left edge)
        double x1Data = 0.0;
        double y1Data = intercept + (slope * x1Data);

        // Point 2: Find predicted Y when X = 12.0 (Right edge)
        double x2Data = 12.0;
        double y2Data = intercept + (slope * x2Data);

        // Convert data points into layout pixel coordinates
        int lineX1 = cornerX + (int) ((x1Data / maxX) * graphWidth);
        int lineY1 = cornerY - (int) ((y1Data / maxY) * graphHeight);

        int lineX2 = cornerX + (int) ((x2Data / maxX) * graphWidth);
        int lineY2 = cornerY - (int) ((y2Data / maxY) * graphHeight);

        // Render the line in bright red
        g2d.setColor(Color.RED);
        g2d.drawLine(lineX1, lineY1, lineX2, lineY2);

        // 12. Clean up and save
        g2d.dispose();
        try 
        {
            File outputFile = new File(outputPath);
            ImageIO.write(image, "png", outputFile);
            System.out.println("<<< Success: Labeled graph with line of best fit saved to " + outputPath + " >>>");
        } 
        
        catch (IOException e)
        {
            System.out.println("<< Error: Could not save the image. >>");
        }
    }
}