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
    public static HashMap<String, Building> loadMap(String filePath) {
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
            System.out.println("Map at " + filePath + " has been loaded successfully.\n");
        } catch (Exception e) {
            System.out.println("There was an error reading from the file: "
                    + filePath + ".\nPlease check the file path and formatting and try again.");
        }
        return map;
    }
}
