package RDS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 *The FileManager class is responsible for loading user-created maps into the system.
 * The maps are based off of a pre-determined text file syntax and allow the user
 * to enter any kind of map they want into the system. The format for the file is:
 *      //Buildings:
 *      (key value) (building name)
 *      # //End buildings, start edges:
 *      (from key) (to key) (weight)
 * If this format is not followed or there is an error in reading from the file
 * (due to incorrect filepath etc), the user will be told nothing was loaded and
 * to please try again.
 * 
 * @author Scott Stevenson
 */
public class FileManager {

    /**
     * The loadMap() method is responsible for reading the user-created map from
     * a file and returning it in a usable format (HashMap).
     *
     * @param filePath The path to the text file containing the map
     * @return The HashMap representation of the map
     */
    public static HashMap<String, Building> loadMap(String filePath, boolean inFn) {
        HashMap<String, Building> map = new HashMap<String, Building>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean building = true;    //all maps start with buildings

            //The '#' char seperates the buildings from the edges, so until we see
            //that char, we are still in the buildings section
            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) == '#') {
                    building = false;   //we found the '#' so we are now in edges
                    continue;   //go to the next section of code
                }
                //we have a building, so add it to the map
                if (building) {
                    int endOfKey = line.indexOf(' ');   //returns first space in line
                    String key = line.substring(0, endOfKey);   //returns up to first space (the key)
                    String name = line.substring(endOfKey + 1); //returns everything after first space (the name)
                    Building b = new Building(name);
                    map.put(key, b);
                } else {
                    String[] edge = line.split(" ");    //split into space-delimited segments
                    String from = edge[0];              //from key
                    String to = edge[1];                //to key
                    double weight = Double.parseDouble(edge[2]);    //weight
                    Building b = map.get(from);         //get the correct building
                    b.addAdjacencie(new Edge(map.get(to), weight)); //add the edge to the building's list
                }
            }
            if (!inFn) {    //we are in the main menu, print successful load
                System.out.println("Map at " + filePath + " has been loaded successfully.\n");
            }
        } catch (Exception e) {
            System.out.println("There was an error reading from the file: "
                    + filePath + ".\nPlease check the file path and formatting and try again.");
        }
        Menu.setFP(filePath);   //set the Menu's filepath variable to the current path
        return map;             //return the newly loaded map
    }

    /**
     * The generateDefaultMap() method creates all the buildings and edges associated
     * with the buildings for the default Rowan University map and returns it in
     * the form of a HashMap.
     *
     * @return A HashMap representation of Rowan University
     */
    public static HashMap<String, Building> generateDefaultMap() {
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

        james.addAdjacencie(new Edge(robinson, 262));
        james.addAdjacencie(new Edge(reccenter, 1056));
        robinson.addAdjacencie(new Edge(james, 262));
        robinson.addAdjacencie(new Edge(science, 240));
        science.addAdjacencie(new Edge(xwalk, 528));
        science.addAdjacencie(new Edge(robinson, 240));
        reccenter.addAdjacencie(new Edge(library, 308));
        library.addAdjacencie(new Edge(xwalk, 164));
        library.addAdjacencie(new Edge(studentcenter, 410));
        library.addAdjacencie(new Edge(science, 528));
        studentcenter.addAdjacencie(new Edge(whitney, 1056));
        hawthorn.addAdjacencie(new Edge(bunce, 528));
        bunce.addAdjacencie(new Edge(xwalk, 528));
        whitney.addAdjacencie(new Edge(xwalk, 1584));
        whitney.addAdjacencie(new Edge(bookstore, 528));
        bookstore.addAdjacencie(new Edge(whitney, 528));
        xwalk.addAdjacencie(new Edge(hawthorn, 482));
        xwalk.addAdjacencie(new Edge(library, 164));
        xwalk.addAdjacencie(new Edge(whitney, 1584));

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
