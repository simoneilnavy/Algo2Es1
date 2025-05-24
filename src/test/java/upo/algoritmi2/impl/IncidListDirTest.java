package upo.algoritmi2.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;
import upo.graph.base.Graph;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class IncidListDirTest {

    IncidListDir graph;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        graph = new IncidListDir();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test Costruttore: lista sia presente e vuota e grandezza 0")
    void testConstructor() {
        this.graph = new IncidListDir();
        assertNotNull(this.graph.grafo);
        assertNotNull(this.graph.archi);
        assertEquals(0,this.graph.grafo.size());
        assertEquals(0,this.graph.archi.size());
    }

    @Test
    @DisplayName("Test per il toString")
    void testToString() {
        String actual = this.graph.toString().trim();

        String expected = "Graph {\n\tnodes: \n\t}";
        assertEquals(expected,actual);

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        actual = this.graph.toString().trim();
        expected = "Graph {\n\tnodes: 0..1\n\t0-1}";

        assertEquals(expected,actual);
    }

    @Test
    void testEquals() {
        Graph other = new IncidListDir();
        //test con grafo/i vuoto/i
        //test con grafi uguali e con grafi diversi
        assertTrue(this.graph.equals(other));
        assertFalse(this.graph.equals(null));
        other.addVertex();
        assertFalse(this.graph.equals(other));


    }

    @Test
    void testAddVertex() {
        int oldsize=this.graph.grafo.size();
        this.graph.addVertex();
        int newsize = this.graph.grafo.size();
        //io controllo se la lista che contiene i vertici sia aumentata senza interessarmi se aumenta anche la lista degli edge
        assertNotEquals(oldsize,newsize);

    }

    @Test
    void testGetVertex() {
        assertTrue(this.graph.getVertices().isEmpty());
        this.graph.addVertex();
        assertFalse(this.graph.getVertices().isEmpty());
        assertEquals(1,this.graph.getVertices().size());
    }

    @Test
    void testGetEdge() {

        assertTrue(this.graph.getEdges().isEmpty());
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));
        assertFalse(this.graph.archi.isEmpty());
        assertEquals(1,this.graph.archi.size());
    }

    @Test
    void testaddEdge() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.addEdge(Edge.getEdgeByVertexes(0,1));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        assertEquals(0,this.graph.grafo.size());

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        assertEquals(1,this.graph.archi.size());
    }

    @Test
    void testcontainsEdge() {

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.addEdge(Edge.getEdgeByVertexes(1,2));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,1));

        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(1,2)));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(2,1)));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(0,1)));
    }

    @Test
    void testgetEdges() {

        assertTrue(this.graph.getEdges().isEmpty());
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        assertTrue(this.graph.getEdges().contains(Edge.getEdgeByVertexes(0,1)));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,2));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,3));
        assertTrue(this.graph.getEdges().contains(Edge.getEdgeByVertexes(0,1))
                &&this.graph.getEdges().contains(Edge.getEdgeByVertexes(0,2))
                &&this.graph.getEdges().contains(Edge.getEdgeByVertexes(1,3)));
    }

    @Test
    void testcontainsVertex() {

        assertFalse(this.graph.containsVertex(0));
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();

        assertTrue(this.graph.containsVertex(0));
        assertTrue(this.graph.containsVertex(3));
    }

    @Test
    void testremoveVertex() {

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            this.graph.removeVertex(0);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,2));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(3,4));

        assertTrue(this.graph.containsVertex(0)&&this.graph.containsVertex(1));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(0,1)));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(0,2)));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(3,4)));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(1,2)));
        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(1,3)));

        this.graph.removeVertex(0);
        assertTrue(this.graph.containsVertex(0)&&this.graph.containsVertex(1));
        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(0,2)));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(0,1)));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(2,3)));

    }
}