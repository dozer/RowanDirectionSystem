package RDS;

/**
 *The Driver class is the executable class of the RDS project. It creates a new
 * menu with the default map.
 *
 * @author Scott Stevenson
 */
public class Driver {
    
    public static void main(String [] args)
    {
        //Create a new Menu with the default Rowan University map loaded
        Menu RDS = new Menu(FileManager.generateDefaultMap());
    }
}
    
     
