package upo.algoritmi2.impl;

import upo.graph.base.Edge;
import upo.graph.base.Graph;
import upo.graph.base.VisitResult;

import java.util.*;

/**
 * Destro Simone Implementazione Lista Di incidenza orientate
 */

public class IncidListDir implements Graph {

    protected List<List<Integer>> grafo;
    protected List<Edge> archi;


    public IncidListDir()
    {
        //vertices
        this.grafo = new ArrayList<List<Integer>>();
        //Edge
        this.archi = new ArrayList<Edge>();
    }

    @Override
    public int addVertex() {
        List<Integer> list = new ArrayList<>();
        this.grafo.add(list);
        return this.grafo.indexOf(list);
    }

    @Override
    public Set<Integer> getVertices() {
        List<Integer> sup = new ArrayList<>();
        for(List<Integer> list : this.grafo) {
            sup.add(this.grafo.indexOf(list));
        }
        return Set.copyOf(sup);
    }

    @Override
    public Set<Edge> getEdges() {
        return Set.copyOf(this.archi);
    }

    @Override
    public boolean containsVertex(Integer integer) {

        if(integer>=this.grafo.size()) {
            return false;
        }
        return true;
    }

    @Override
    public void removeVertex(Integer integer) throws NoSuchElementException {


        if(!this.containsVertex(integer)) {
            throw new NoSuchElementException("Vertice non presente");
        }

        this.archi.removeIf(arco -> arco.getSource().intValue() == integer.intValue() || arco.getTarget().intValue() == integer.intValue());
        for(Edge arco : this.archi)
        {


            if(arco.getSource().intValue() > integer.intValue()) {

                Edge newEdge = Edge.getEdgeByVertexes(arco.getSource()-1, arco.getTarget());
                this.archi.set(this.archi.indexOf(arco), newEdge);
                arco = newEdge;
            }
            if(arco.getTarget().intValue() > integer.intValue()) {
                Edge newEdge = Edge.getEdgeByVertexes(arco.getSource(), arco.getTarget()-1);
                this.archi.set(this.archi.indexOf(arco), newEdge);
            }
        }
        this.grafo.remove(integer.intValue());
    }

    @Override
    public void addEdge(Edge edge) throws IllegalArgumentException {

        if(edge.getSource()>=this.grafo.size() || edge.getTarget()>=this.grafo.size()) {
            throw new IllegalArgumentException("Vertice non presente");
        }
        this.archi.add(edge);
        this.grafo.get(edge.getSource()).add(this.archi.indexOf(edge));
    }

    @Override
    public boolean containsEdge(Edge edge) throws IllegalArgumentException {

        if(!containsVertex(edge.getSource()) || !containsVertex(edge.getTarget())) {
            throw new IllegalArgumentException("Vertice non presente");
        }


        return this.archi.contains(edge);
    }

    @Override
    public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {

        //non ho determinato necessario inserire un try catch perchè la funzione è throwable
        //inoltre non devo nemmeno aggiungere il throw dell'IllegalArgumentException
        //perchè il controllo è effettuato da containsEdge che nel caso fà il throw e removeEdge lo gira alla funzione chiamante

            if(!containsEdge(edge)) {
                throw new NoSuchElementException("Arco non presente");
            }

            Integer index = this.archi.indexOf(edge);

            this.grafo.get(edge.getSource()).remove(index);
            this.archi.remove(edge);
    }

    @Override
    public Set<Integer> getAdjacent(Integer integer) throws NoSuchElementException {

        if(!containsVertex(integer)) {
            throw new NoSuchElementException("Vertice non presente");
        }

        List<Integer> vertex = this.grafo.get(integer);
        List<Integer> sup = new ArrayList<>();

        for(Integer val : vertex) {
            sup.add(this.archi.get(val).getTarget());
        }

        return Set.copyOf(sup);
    }

    @Override
    public boolean isAdjacent(Integer integer, Integer integer1) throws IllegalArgumentException {
        if(!containsVertex(integer) || !containsVertex(integer1)) {
            throw new IllegalArgumentException("Vertice non presente");
        }
        for(Edge arco : this.archi) {
            if(arco.getSource().intValue() == integer.intValue() && arco.getTarget().intValue() == integer1.intValue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public boolean isCyclic() {
        return false;
    }

    @Override
    public boolean isDAG() {
        return false;
    }

    @Override
    public VisitResult getBFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        return null;
    }

    @Override
    public VisitResult getDFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        return null;
    }

    @Override
    public VisitResult getDFSTOTForest(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        return null;
    }

    @Override
    public VisitResult getDFSTOTForest(Integer[] integers) throws UnsupportedOperationException, IllegalArgumentException {
        return null;
    }

    @Override
    public Integer[] topologicalSort() throws UnsupportedOperationException {
        return new Integer[0];
    }

    @Override
    public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
        return Set.of();
    }

    @Override
    public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
        return Set.of();
    }

    @Override
    public String toString(){
        //quali sono i vertici e quali sono gli archi
        /*
        * graph{
        *   nodes:0...6
        *   0-1
        *   0-3
        * }
        * */
        return null;
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Graph other = (Graph) o;
        if(!this.getVertices().equals(other.getVertices())) return false;
        if(!this.getEdges().equals(other.getEdges())) return false;
        return true;
    }

}
