public class DataPoint {
    // Encapsulated fields to store the X and Y coordinate values privately
    private double x;
    private double y;

    // Constructor: Initializes a new DataPoint object with specific X and Y values
    public DataPoint(double x, double y) 
    {
        this.x = x;
        this.y = y;
    }

    // Getter method: Allows other classes (like Main) to safely read the X value
    public double getX() 
    {
        return x;
    }

    // Getter method: Allows other classes to safely read the Y value
    public double getY() 
    {
        return y;
    }
    
    // ToString Override: Dictates exactly how this object transforms into text
    // when printed or combined with strings (e.g., outputs "(8.5, 3.9)")
    @Override
    public String toString() 
    {
        String str;
        str = "(" + x + ", " + y + ")";
        return str;
    }
}