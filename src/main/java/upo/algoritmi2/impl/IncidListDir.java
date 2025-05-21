package upo.algoritmi2.impl;

import upo.graph.base.Edge;
import upo.graph.base.Graph;
import upo.graph.base.VisitResult;

import java.util.*;

/**
 * Destro Simone Implementazione Lista Di incidenza orientate
 */

public class IncidListDir implements Graph {

    protected List<HashMap<Integer,List<Integer>>> grafo;
    protected List<HashMap<Integer,Edge>> archi;
    protected int vertexN=0,edgeN=0;


    public IncidListDir()
    {
        //vertices
        this.grafo = new ArrayList<HashMap<Integer,List<Integer>>>();
        //Edge
        this.archi = new ArrayList<HashMap<Integer,Edge>>();
    }

    @Override
    public int addVertex() {
        HashMap<Integer,List<Integer>> mappa = new HashMap<Integer,List<Integer>>();
        mappa.put(vertexN,new ArrayList<Integer>());
        this.grafo.add(mappa);
        vertexN++;
        return 0;
    }

    @Override
    public Set<Integer> getVertices() {

        Set<Integer> val = new HashSet<>();

        for(HashMap<Integer,List<Integer>> mappa : this.grafo){
            val.addAll(mappa.keySet());
        }
        return val;
    }

    @Override
    public Set<Edge> getEdges() {
        return Set.of();
    }

    @Override
    public boolean containsVertex(Integer integer) {
        return false;
    }

    @Override
    public void removeVertex(Integer integer) throws NoSuchElementException {

    }

    @Override
    public void addEdge(Edge edge) throws IllegalArgumentException {

        if(edge.getSource()>=this.vertexN || edge.getTarget()>=this.vertexN) {
            throw new IllegalArgumentException("Vertice non presente");
        }

        HashMap<Integer,Edge> mappa = new HashMap<>();

        mappa.put(edgeN,edge);
        edgeN++;
        this.archi.add(mappa);
    }

    @Override
    public boolean containsEdge(Edge edge) throws IllegalArgumentException {

        if(edge.getSource()>=vertexN || edge.getTarget()>=vertexN) {
            throw new IllegalArgumentException("Vertice non presente");
        }

        for(HashMap<Integer,Edge> mappa : this.archi) {
            for(Edge entry : mappa.values()){
                if(entry.getSource().equals(edge.getSource()) && entry.getTarget().equals(edge.getTarget())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {

    }

    @Override
    public Set<Integer> getAdjacent(Integer integer) throws NoSuchElementException {
        return Set.of();
    }

    @Override
    public boolean isAdjacent(Integer integer, Integer integer1) throws IllegalArgumentException {
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
