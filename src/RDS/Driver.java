package RDS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
/**
 *
 * @author Nothing
 */
public class Driver {
    
    public static void main(String [] args)
    {
        Menu RDS = new Menu(generateDefaultMap());
    }

    public static HashMap<String, Building> generateDefaultMap()
    {
        HashMap<String, Building> map = new HashMap<String, Building>();

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

        james.addAdjacencie(new Edge(robinson,  262));
        james.addAdjacencie(new Edge(reccenter,  1056));
        robinson.addAdjacencie(new Edge(james,  262));
        robinson.addAdjacencie(new Edge(science,  240));
        science.addAdjacencie(new Edge(xwalk,  528));
        science.addAdjacencie(new Edge(robinson,  240));
        reccenter.addAdjacencie(new Edge(library,  308));
        library.addAdjacencie(new Edge(xwalk,  164));
        library.addAdjacencie(new Edge(studentcenter, 410));
        library.addAdjacencie(new Edge(science,  528));
        studentcenter.addAdjacencie(new Edge(whitney,  1056));
        hawthorn.addAdjacencie(new Edge(bunce,  528));
        bunce.addAdjacencie(new Edge(xwalk,  528));
        whitney.addAdjacencie(new Edge(xwalk,  1584));
        whitney.addAdjacencie(new Edge(bookstore,  528));
        bookstore.addAdjacencie(new Edge(whitney,  528));
        xwalk.addAdjacencie(new Edge(hawthorn,  482));
        xwalk.addAdjacencie(new Edge(library, 164));
        xwalk.addAdjacencie(new Edge(whitney,  1584));

        map.put("b0", james);
        map.put("b1", robinson);
        map.put("b2", science);
        map.put("b3", reccenter);
        map.put("b4", library);
        map.put("b5", studentcenter);
        map.put("b6", hawthorn);
        map.put("b7", bunce);
        map.put("b8", whitney);
        map.put("b9", bookstore);
        map.put("b10", xwalk);
        map.put("b11", tajmahal);
        map.put("b12", searstower);

        return map;
    }
}
    
     
