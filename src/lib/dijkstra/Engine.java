package lib.dijkstra;

import java.util.*;

/**
 * Created by rafa93br on 28/11/16.
 */
public class Engine {
    private Graph graph;
    private Set<Vertex> visitedVertexes;
    private Set<Vertex> unvisittedVertexes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Double> distance;

    public Engine(Graph graph) {
        this.graph = graph;
    }

    private Double getDistance(Vertex source, Vertex destination) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getDestination().equals(destination) && edge.getSource().equals(source)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Não conseguiu encontrar distância");
    }

    public void execute(Vertex source) {
        visitedVertexes = new HashSet<Vertex>();
        unvisittedVertexes = new HashSet<Vertex>();
        predecessors = new HashMap<Vertex, Vertex>();
        distance = new HashMap<Vertex, Double>();

        for (Vertex vertex : graph.getVertexes()) {
            unvisittedVertexes.add(vertex);
            distance.put(vertex, Double.POSITIVE_INFINITY);
        }

        distance.put(source, 0d);


        while(!unvisittedVertexes.isEmpty()) {
            Vertex nextToVisit = getClosest();
            unvisittedVertexes.remove(nextToVisit);
            visitedVertexes.add(nextToVisit);
            updateDistanceList(nextToVisit);
        }
    }

    private void updateDistanceList(Vertex source) {
        List<Vertex> adjacentVertexes = getNeighbors(source);

        for (Vertex neighbour : adjacentVertexes) {
            Double newDistance = distance.get(source) + getDistance(source, neighbour);
            if (newDistance < distance.get(neighbour)) {
                distance.put(neighbour, newDistance);
                predecessors.put(neighbour, source);
            }
        }
    }

    private Vertex getClosest() {
        Vertex closestVertex = null;
        for (Vertex unvisitedVertex : unvisittedVertexes) {
            if (closestVertex == null) {
                closestVertex = unvisitedVertex;
            } else {
                if (distance.get(unvisitedVertex) < distance.get(closestVertex)) {
                    closestVertex = unvisitedVertex;
                }
            }
        }

        return closestVertex;
    }

    private List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbours = new ArrayList<Vertex>();

        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(vertex)) {
                neighbours.add(edge.getDestination());
            }
        }

        return neighbours;
    }

    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        if (predecessors.get(target) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }
}
