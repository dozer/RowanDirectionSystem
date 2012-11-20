package RDS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author an0maly
 */
public class FileManager {

    public static HashMap<String, Building> loadMap(String filePath) {
        //might ahve to make this null
        HashMap<String, Building> map = new HashMap<String, Building>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean building = true;

            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) == '#') {
                    building = false;
                    continue;
                }
                //get buildings
                if (building) {
                    //store buildings
                    int endOfKey = line.indexOf(' ');   //returns first space in line
                    String key = line.substring(0, endOfKey);   //returns up to first space
                    String name = line.substring(endOfKey + 1); //returns everything after first space
                    Building b = new Building(name);
                    map.put(key, b);
                } else {
                    String[] edge = line.split(" ");    //split into space-delimited segments
                    String from = edge[0];
                    String to = edge[1];
                    double weight = Double.parseDouble(edge[2]);
                    Building b = map.get(from);
                    b.addAdjacencie(new Edge(map.get(to), weight));
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
