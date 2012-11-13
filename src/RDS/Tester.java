package RDS;

import java.util.Scanner;
import java.util.List;
/**
 *
 * @author Nothing
 */
public class Tester {
    
    public static void main(String [] args)
    {
        Scanner scanner = new Scanner(System.in);

        Building james = new Building("James");
        Building robinson = new Building("Robinson");
        Building science = new Building("Science");
        Building reccenter = new Building("Rec Center");
        Building library = new Building("Library");
        Building studentcenter = new Building("Student Center");
        Building hawthorn = new Building("Hawthorn");
        Building bunce = new Building("Bunce");
        Building whitney = new Building("Whitney Center");
        Building bookstore = new Building("Barnes & Noble Bookstore");
        Building xwalk = new Building("Crosswalk on 322");
        Building tajmahal = new Building("Trump Taj Mahal");
        Building searstower = new Building("Sears Tower");

        james.adjacencies = new Edge[]{ new Edge(robinson,  262),
                                        new Edge(reccenter,  1056) };
        robinson.adjacencies = new Edge[]{ new Edge(james,  262),
                                           new Edge(science,  240) };
        science.adjacencies = new Edge[]{ new Edge(xwalk,  528),
                                          new Edge(robinson,  240) };
        reccenter.adjacencies = new Edge[]{ new Edge(library,  308)};
        library.adjacencies = new Edge[]{ new Edge(xwalk,  164),
                                          new Edge(studentcenter, 410),
                                          new Edge(science,  528) };
        studentcenter.adjacencies = new Edge[]{ new Edge(whitney,  1056)};
        hawthorn.adjacencies = new Edge[]{ new Edge(bunce,  528)};
        bunce.adjacencies = new Edge[]{ new Edge(xwalk,  528)};
        whitney.adjacencies = new Edge[]{ new Edge(xwalk,  1584),
                                          new Edge(bookstore,  528) };
        bookstore.adjacencies = new Edge[]{ new Edge(whitney,  528)};
        xwalk.adjacencies = new Edge[]{ new Edge(hawthorn,  482),
                                        new Edge(library, 164),
                                        new Edge(whitney,  1584) };
        Building[] map = {james, robinson, science, reccenter, library,
                            studentcenter, hawthorn, bunce, whitney, bookstore,
                            xwalk, tajmahal, searstower};

        System.out.println("Welcome to the Rowan University direction system!");
        System.out.println("The following buildings are supported:");
            for (Building b : map)
            {
                System.out.println(b);
            }
        System.out.println("Where are you located on campus?");
        String loc_string = scanner.nextLine();
        System.out.println("What is your desired destination?");
        String dest_string = scanner.nextLine();
        for (Building b : map)
        {
            if (b.toString().equals(loc_string))
                Dijkstras.computeRoutes(b);
        }
        for (Building b : map)
        {
            if (b.toString().equals(dest_string))
                Dijkstras.printRoute(Dijkstras.getShortestRouteTo(b));
        }
    }
}
    
     
