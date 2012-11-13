package RDS;

/**
 *Edge class is an extremely simple class that handles all of the routes between
 * 2 buildings. Edges can be thought of as the lines between the points of a graph.
 *
 * Since this is a directed graph, each edge will have only 1 associated building
 * it points to.
 *
 * @author Scott Stevenson
 */
public class Edge {

    public final Building target;   //the building this edge points to
    public final double weight;     //the weight (numerical value) of this edge

    /**
     * Simple constructor for the Edge class
     *
     * @param target The building this edge points at
     * @param weight The weight (value or cost) of this edge
     */
    public Edge(Building target, double weight) {
        this.target = target;
        this.weight = weight;
    }
}
