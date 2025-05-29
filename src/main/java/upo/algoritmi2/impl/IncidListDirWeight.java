package upo.algoritmi2.impl;

import upo.graph.base.Edge;
import upo.graph.base.Graph;
import upo.graph.base.WeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class IncidListDirWeight extends IncidListDir implements WeightedGraph
{
    List<Double> pesi;

    public IncidListDirWeight()
    {
        super();
        this.pesi= new ArrayList<>();
    }

    public void addEdge(Edge edge,Double peso) throws IllegalArgumentException {
        if(edge.getSource()>=this.grafo.size() || edge.getTarget()>=this.grafo.size()) {
            throw new IllegalArgumentException("Vertice non presente");
        }
        this.archi.add(edge);
        this.pesi.add(peso);
        this.grafo.get(edge.getSource()).add(this.archi.indexOf(edge));

    }

    
    public boolean containsEdge(Edge edge,Double peso) throws IllegalArgumentException {


        if(!containsVertex(edge.getSource()) || !containsVertex(edge.getTarget())) {
            throw new IllegalArgumentException("Vertice non presente");
        }


        return  this.archi.contains(edge) && this.pesi.get(this.archi.indexOf(edge)).equals(peso);
    }

    @Override
    public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        if(!containsEdge(edge)) {
            throw new NoSuchElementException("Arco non presente");
        }

        Integer index = this.archi.indexOf(edge);

        this.grafo.get(edge.getSource()).remove(index);
        this.pesi.remove(this.pesi.get(index));
        this.archi.remove(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph other = (Graph) o;
        if (!this.getVertices().equals(other.getVertices())) return false;
        if (!this.getEdges().equals(other.getEdges())) return false;
        IncidListDirWeight otherWeighted = (IncidListDirWeight) o;
        if (!this.pesi.equals(otherWeighted.pesi)) return false;
        return true;
    }



    @Override
    public double getEdgeWeight(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        return this.pesi.get(this.archi.indexOf(edge));
    }

    @Override
    public void setEdgeWeight(Edge edge, double v) throws IllegalArgumentException, NoSuchElementException {
        this.pesi.set(this.archi.indexOf(edge),v);
    }

    @Override
    public WeightedGraph getBellmanFordShortestPaths(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WeightedGraph getDijkstraShortestPaths(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WeightedGraph getPrimMST(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
