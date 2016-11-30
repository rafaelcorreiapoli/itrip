package lib.dijkstra.test;


import lib.dijkstra.Edge;
import lib.dijkstra.Engine;
import lib.dijkstra.Graph;
import lib.dijkstra.Vertex;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by rafa93br on 28/11/16.
 */
public class EngineTest {
    @Test
    public void testExecute() {
        ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
        ArrayList<Edge> edges = new ArrayList<Edge>();

        Vertex sp = new Vertex("SP", "sa");
        Vertex rj = new Vertex("RJ", "b");
        Vertex fo = new Vertex("FO", "c");
        Vertex sc = new Vertex("SC", "d");
        Vertex es = new Vertex("ES", "e");
        Vertex sa = new Vertex("SA", "e");

        Edge spes = new Edge("ab", sp, es, 77d);
        Edge spsa = new Edge("ac", sp, sa, 90d);
        Edge esrj = new Edge("ae", es, rj, 60d);
        Edge rjsp = new Edge("cd", rj, sp, 121d);
        Edge rjsc = new Edge("de", rj, sc, 31d);
        Edge safo = new Edge("be", sa, fo, 21d);
        Edge scsp = new Edge("bd", sc, sp, 121d);
        Edge scfo = new Edge("scfo", sc, fo, 21d);
        Edge fosp = new Edge("fosp", fo, sp, 121d);
        Edge forj = new Edge("forj", fo, rj, 1000d);


        vertexes.add(sp);
        vertexes.add(rj);
        vertexes.add(fo);
        vertexes.add(sc);
        vertexes.add(es);
        vertexes.add(sa);

        edges.add(spes);
        edges.add(spsa);
        edges.add(esrj);
        edges.add(rjsp);
        edges.add(rjsc);
        edges.add(safo);
        edges.add(scsp);
        edges.add(scfo);
        edges.add(fosp);
        edges.add(forj);


        Graph graph = new Graph(vertexes, edges);
        Engine engine = new Engine(graph);

        engine.execute(sa);


        LinkedList<Vertex> path = engine.getPath(rj);

        for (Vertex vertex : path) {
            System.out.println(vertex.getId());
        }

        assertEquals(path.size(), 5);
        assertEquals(path.get(0), sa);
        assertEquals(path.get(1), fo);
        assertEquals(path.get(2), sp);
        assertEquals(path.get(3), es);
        assertEquals(path.get(4), rj);
    }
}