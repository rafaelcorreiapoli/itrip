package lib.dijkstra;

/**
 * Created by rafa93br on 28/11/16.
 */
public class Edge {
    private String id;
    private Vertex source;
    private Vertex destination;
    private Double weight;

    public Edge(String id, Vertex source, Vertex destination, Double weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
