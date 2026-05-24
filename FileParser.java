import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileParser 
{
    // Method that reads a CSV file path and returns a list of populated DataPoint coordinates
    public static ArrayList<DataPoint> parseCSV(String filePath) 
    {
        // Initialize an empty dynamic array to hold valid coordinates extracted from the file
        ArrayList<DataPoint> list = new ArrayList<>();
        
        try 
        {
            // Create a File object link to locate the file asset on your hard drive
            File file = new File(filePath);
            // Open a Scanner stream to scan through the file contents text line by text line
            Scanner fileScanner = new Scanner(file);
            
            // Outer check: If the file contains data, pull the first line 
            // This skips the header row (column labels) so we do not try to parse words into math decimals
            if (fileScanner.hasNextLine()) 
            {
                fileScanner.nextLine();
            }
            
            // Run a loop that continues until the Scanner hits the absolute end of the file
            while (fileScanner.hasNextLine()) 
            {
                // Read the entire current row of data as a single continuous text String
                String line = fileScanner.nextLine();
                // Split the row into an array of smaller strings using the comma as the boundary marker
                String[] values = line.split(",");
                
                try 
                {
                    // Validation: Ensure the array split yielded enough columns to prevent missing data errors
                    if (values.length >= 11) 
                    {
                        // Extract index 5 (Daily_Screen_Time_Hours), clean off any invisible spacing, and convert to double
                        double x = Double.parseDouble(values[5].trim());
                        // Extract index 10 (Sleep_Duration_Hours), clean off any invisible spacing, and convert to double
                        double y = Double.parseDouble(values[10].trim());
                        
                        // Construct a new custom DataPoint instance with the coordinates and append it to our list
                        list.add(new DataPoint(x, y));
                    }
                } 
                catch (NumberFormatException | ArrayIndexOutOfBoundsException e) 
                {
                    // Inner Catch Block: If a row has text descriptions instead of numbers, or columns are completely blank,
                    // intercept the crash here. The empty block safely discards the bad row and goes right to the next loop iteration.
                }
            }
            
            // Explicitly close the file streaming connection to free up computer system memory resources
            fileScanner.close();
            
        } 
        catch (FileNotFoundException e) 
        {
            // Outer Catch Block: Triggers exclusively if the path provided is broken or the file does not exist in the project directory
            System.out.println("Error: Could not find the file at " + filePath);
        }
        
        // Return the clean, loaded list of coordinate pairs back to the Main calling program
        return list;
    }
}