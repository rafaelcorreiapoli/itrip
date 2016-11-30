package lib.dijkstra;

import java.util.HashMap;

/**
 * Created by rafa93br on 28/11/16.
 */
public class Vertex {
    private String id;
    private String name;
    private HashMap<String, Object> data = new HashMap<String, Object>();

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public Vertex(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
