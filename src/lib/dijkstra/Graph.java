package lib.dijkstra;

import java.util.List;

/**
 * Created by rafa93br on 28/11/16.
 */
public class Graph {
    private List<Vertex> vertexes;
    private List<Edge> edges;


    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
