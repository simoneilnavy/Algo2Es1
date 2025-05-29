package upo.algoritmi2.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import upo.graph.base.Edge;
import upo.graph.base.Graph;
import upo.graph.base.VisitResult;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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
        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(1,2)));
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

    @Test
    void testremoveEdge() {

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.removeEdge(Edge.getEdgeByVertexes(12,0));
        });

        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addVertex();

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            this.graph.removeEdge(Edge.getEdgeByVertexes(0,1));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Arco non presente"));

        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        int oldSize= this.graph.archi.size();

        this.graph.removeEdge(Edge.getEdgeByVertexes(0,1));

        assertEquals(oldSize-1,this.graph.archi.size());
    }

    @Test
    void testgetAdjacent() {

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            this.graph.getAdjacent(1);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addVertex();
        assertTrue(this.graph.getAdjacent(0).isEmpty());
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,2));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(3,4));

        assertTrue(this.graph.getAdjacent(0).contains(1)&&this.graph.getAdjacent(0).contains(2));
        assertTrue(this.graph.getAdjacent(1).contains(2));
        assertFalse(this.graph.getAdjacent(1).contains(3));

    }

    @Test
    void testisAdj() {

        this.graph.addVertex();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.isAdjacent(0,1);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));


        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        assertTrue(this.graph.isAdjacent(0,1));
        assertFalse(this.graph.isAdjacent(1,0));
        assertFalse(this.graph.isAdjacent(0,2));
    }

    @Test
    void testSize() {
        assertEquals(0,this.graph.size());

        this.graph.addVertex();
        this.graph.addVertex();

        assertEquals(2,this.graph.size());
    }

    @Test
    void testisDirected() {
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,3));

        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));

        assertTrue(this.graph.isDirected());
        this.graph.addEdge(Edge.getEdgeByVertexes(3,0));
        assertTrue(this.graph.isDirected());
        this.graph.addEdge(Edge.getEdgeByVertexes(0,3));
        assertFalse(this.graph.isDirected());
    }

    @Test
    void testisCyclic() {
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,3));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));
        assertFalse(this.graph.isCyclic());
        this.graph.addEdge(Edge.getEdgeByVertexes(3,0));
        assertTrue(this.graph.isCyclic());
    }

    @Test
    void testisDAG() {

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,3));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));
        assertTrue(this.graph.isDAG());
        this.graph.addEdge(Edge.getEdgeByVertexes(3,0));
        assertFalse(this.graph.isDAG());
    }

    @Test
    void testgetBFSTree() {

        VisitResult visita;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.getBFSTree(0);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,3));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));


        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,4));
        this.graph.addEdge(Edge.getEdgeByVertexes(4,5));

        visita=this.graph.getBFSTree(0);

        for(Integer val : this.graph.getVertices()){
            assertEquals(VisitResult.Color.BLACK, visita.getColor(val));
        }
    }

    @Test
    void testgetDFSTree() {
        VisitResult visita;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.getDFSTree(0);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,3));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));


        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,4));
        this.graph.addEdge(Edge.getEdgeByVertexes(4,5));


        visita=this.graph.getDFSTree(0);

        for(Integer val : this.graph.getVertices()){
            assertEquals(VisitResult.Color.BLACK, visita.getColor(val));
        }

        Boolean flag=false;

        this.graph.addVertex();
        this.graph.addVertex();

        this.graph.addEdge(Edge.getEdgeByVertexes(6,7));

        visita=this.graph.getDFSTree(0);

        for(Integer val : this.graph.getVertices()){
            if(VisitResult.Color.WHITE.equals(visita.getColor(val))){
                flag=true;
                break;
            }
        }

        assertTrue(flag);

    }

    @Test
    void testgetDFSTOTForest1() {
        VisitResult visita;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.getDFSTree(0);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,3));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));


        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,4));
        this.graph.addEdge(Edge.getEdgeByVertexes(4,5));


        visita=this.graph.getDFSTOTForest(0);

        for(Integer val : this.graph.getVertices()){
            assertEquals(VisitResult.Color.BLACK, visita.getColor(val));
        }

        boolean flag=false;

        this.graph.addVertex();
        this.graph.addVertex();

        this.graph.addEdge(Edge.getEdgeByVertexes(6,7));

        visita=this.graph.getDFSTOTForest(0);

        for(Integer val : this.graph.getVertices()){
            if(VisitResult.Color.WHITE.equals(visita.getColor(val))){
                flag=true;
                break;
            }
        }

        assertFalse(flag);
    }

    @Test
    void testgetDFSTOTForest2() {
        VisitResult visita;

        Integer[] visitOrder ={0,4,3,1,5,2};

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.graph.getDFSTOTForest(visitOrder);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("Vertice non presente"));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,3));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));


        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,4));
        this.graph.addEdge(Edge.getEdgeByVertexes(4,5));


        visita=this.graph.getDFSTOTForest(visitOrder);

        for(Integer val : this.graph.getVertices()){
            assertEquals(VisitResult.Color.BLACK, visita.getColor(val));
        }

        boolean flag=false;

        Integer[] visitOrder2 ={0,4,3,1,5,2,7,6};

        this.graph.addVertex();
        this.graph.addVertex();

        this.graph.addEdge(Edge.getEdgeByVertexes(6,7));

        visita=this.graph.getDFSTOTForest(visitOrder2);

        for(Integer val : this.graph.getVertices()){
            if(VisitResult.Color.WHITE.equals(visita.getColor(val))){
                flag=true;
                break;
            }
        }

        assertFalse(flag);
    }

    @Test
    void testtopologicalSort() {

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0,1));

        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,3));
        this.graph.addEdge(Edge.getEdgeByVertexes(1,2));


        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(2,4));
        this.graph.addEdge(Edge.getEdgeByVertexes(4,5));


        this.graph.addVertex();
        this.graph.addVertex();

        this.graph.addEdge(Edge.getEdgeByVertexes(6,7));

        Integer[] topoSort= this.graph.topologicalSort();

        Integer[] expected={6,0,7,1,2,4,3,5};

        assertArrayEquals(expected,topoSort);

    }


    @Test
    void teststronglyConnectedComponents() {
        Set<Set<Integer>> scc;


        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();

        this.graph.addEdge(Edge.getEdgeByVertexes(0, 1));
        this.graph.addEdge(Edge.getEdgeByVertexes(1, 2));
        this.graph.addEdge(Edge.getEdgeByVertexes(2, 0));
        this.graph.addEdge(Edge.getEdgeByVertexes(3, 4));
        this.graph.addEdge(Edge.getEdgeByVertexes(4, 3));

        scc = this.graph.stronglyConnectedComponents();

        assertEquals(2, scc.size());
        assertTrue(scc.contains(Set.of(0, 1, 2)));
        assertTrue(scc.contains(Set.of(3, 4)));
    }


    @Test
    void testRemoveVertexWithMultipleEdges() {
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();

        this.graph.addEdge(Edge.getEdgeByVertexes(0, 1));
        this.graph.addEdge(Edge.getEdgeByVertexes(1, 2));
        this.graph.addEdge(Edge.getEdgeByVertexes(2, 0));

        assertTrue(this.graph.containsVertex(1));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(0, 1)));
        assertTrue(this.graph.containsEdge(Edge.getEdgeByVertexes(1, 2)));

        this.graph.removeVertex(1);

        assertFalse(this.graph.containsVertex(1));
        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(0, 1)));
        assertFalse(this.graph.containsEdge(Edge.getEdgeByVertexes(1, 2)));
    }

    @Test
    void testRobustTopologicalSort() {
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();

        this.graph.addEdge(Edge.getEdgeByVertexes(0, 1));
        this.graph.addEdge(Edge.getEdgeByVertexes(1, 2));
        this.graph.addEdge(Edge.getEdgeByVertexes(2, 3));

        Integer[] topoSort = this.graph.topologicalSort();
        Integer[] expected = {0, 1, 2, 3};

        assertArrayEquals(expected, topoSort);

        this.graph.addEdge(Edge.getEdgeByVertexes(3, 1)); // introducing a cycle
        assertThrows(UnsupportedOperationException.class, () -> this.graph.topologicalSort());
    }

    @Test
    void testIsDirectedWithEmptyGraph() {
        assertTrue(this.graph.isDirected());

        this.graph.addVertex();
        assertTrue(this.graph.isDirected());

        this.graph.addVertex();
        this.graph.addEdge(Edge.getEdgeByVertexes(0, 1));
        assertTrue(this.graph.isDirected());
    }

    @Test
    void testStronglyConnectedComponentsNoEdges() {
        this.graph.addVertex();
        this.graph.addVertex();
        this.graph.addVertex();

        Set<Set<Integer>> scc = this.graph.stronglyConnectedComponents();

        assertEquals(3, scc.size());
        assertTrue(scc.contains(Set.of(0)));
        assertTrue(scc.contains(Set.of(1)));
        assertTrue(scc.contains(Set.of(2)));
    }
}