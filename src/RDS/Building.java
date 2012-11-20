package RDS;

import java.util.ArrayList;
import java.util.List;

/**
 *The Building class is responsible for creation of and maintaining the various
 * buildings within the provided map. Not every building is an actual building
 * so in some cases it is more correct to think of them as points on the map.
 *
 * Building also implements the Comparable interface to allow for the use of a
 * priorityQueue within the Dijkstras class.
 *
 * @author Scott Stevenson
 */
public class Building implements Comparable<Building> {

    private final String name;   //name of the building
    private ArrayList<Edge> adjacencies = new ArrayList<Edge>();  //list of adjacent buildings
    //in order for the algorithm to work, each building must have a default
    //minDistance of infinity because EVERY route will be smaller than infinity.
    private double minDistance = Double.POSITIVE_INFINITY;
    private Building previous;   //the building before this one in the route

    /**
     * Simple constructor for the Building class
     *
     * @param name The name of the building
     */
    public Building(String name) {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public double getMinDistance()
    {
        return this.minDistance;
    }

    public void setMinDistance(double newMinDistance)
    {
        this.minDistance = newMinDistance;
    }

    public List<Edge> getAdjacencies()
    {
        return this.adjacencies;
    }

    public void addAdjacencie(Edge e)
    {
        adjacencies.add(e);
    }

    public Building getPrevious()
    {
        return this.previous;
    }

    public void setPrevious(Building newPrev)
    {
        this.previous = newPrev;
    }


    /**
     * toString() returns the name of the building as a string
     *
     * @return The name of the building
     */
    public String toString() {
        return this.name;
    }

    /**
     * compareTo determines if a given building is greater than, less than, or
     * equal to the current building. For our purposes the only pertinent data
     * is minDistance. This satisfies the Comparable interface and allows for
     * the use of a PriorityQueue
     * 
     * @param that The building to compare to
     * @return the value 0 if that.minDistance is numerically equal to this.minDistance;
     * a value less than 0 if this.minDistance is numerically less than that.minDistance;
     * and a value greater than 0 if this.minDistance is numerically greater than that.minDistance.
     */
    public int compareTo(Building that) {
        return Double.compare(this.minDistance, that.minDistance);
    }
}
