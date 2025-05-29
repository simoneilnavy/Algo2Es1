package upo.algoritmi2.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;
import upo.graph.base.Graph;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class IncidListDirWeightTest {

    IncidListDirWeight graph;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        graph = new IncidListDirWeight();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test Costruttore: lista sia presente e vuota e grandezza 0")
    void testConstructor() {
        this.graph = new IncidListDirWeight();
        assertNotNull(this.graph.grafo);
        assertNotNull(this.graph.archi);
        assertNotNull(this.graph.pesi);
        assertEquals(0,this.graph.grafo.size());
        assertEquals(0,this.graph.archi.size());
        assertEquals(0,this.graph.pesi.size());
    }

    @Test
    void testaddEdge() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.addEdge(Edge.getEdgeByVertexes(0,1));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        assertEquals(0,this.graph.grafo.size());
        assertEquals(0,this.graph.archi.size());
        assertEquals(0,this.graph.pesi.size());

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1),4.1);

        assertEquals(1,this.graph.archi.size());
        assertEquals(1,this.graph.pesi.size());
        assertEquals(4.1,this.graph.pesi.get(0));

    }
    @Test
    void testcontainsEdge() {

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1),2.1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.addEdge(Edge.getEdgeByVertexes(1,2));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,1),3.1);

        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(1,2),4.1));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(2,1),3.1));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(0,1),2.1));
        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(1,2),1.1));
        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(2,1),5.1));
    }

    @Test
    void testremoveEdge() {

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.removeEdge(Edge.getEdgeByVertexes(12, 0));
        });

        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addVertex();

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            this.graph.removeEdge(Edge.getEdgeByVertexes(0, 1));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Arco non presente"));

        this.graph.addEdge(Edge.getEdgeByVertexes(0, 1), 4.1);

        int oldSizeE = this.graph.archi.size();
        int oldSizeP = this.graph.pesi.size();

        this.graph.removeEdge(Edge.getEdgeByVertexes(0, 1));

        assertEquals(oldSizeE - 1, this.graph.archi.size());
        assertEquals(oldSizeP - 1, this.graph.pesi.size());
    }

    @Test
    void testEquals() {
        Graph other = new IncidListDirWeight();
        //test con grafo/i vuoto/i
        //test con grafi uguali e con grafi diversi
        assertTrue(this.graph.equals(other));
        assertFalse(this.graph.equals(null));

        other.addVertex();
        other.addVertex();
        assertFalse(this.graph.equals(other));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0, 1), 4.1);
        ((IncidListDirWeight)(other)).addEdge(Edge.getEdgeByVertexes(0, 1),4.1);

        assertTrue(this.graph.equals(other));

        ((IncidListDirWeight)(other)).addEdge(Edge.getEdgeByVertexes(0, 1), 5.1);
        assertFalse(this.graph.equals(other));
    }
    
}
