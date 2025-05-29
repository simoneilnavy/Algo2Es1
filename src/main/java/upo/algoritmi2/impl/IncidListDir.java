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
    public void  addEdge(Edge edge) throws IllegalArgumentException {

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
        return this.grafo.size();    }

    @Override
    public boolean isDirected() {

        List<Integer> targetlist = new ArrayList<>();


        for(List<Integer> list : this.grafo) {
            for(Integer val : list) {
               targetlist.add(this.archi.get(val).getTarget());
            }
            for(Integer val : targetlist) {

                    for (Integer val1 : this.grafo.get(val)) {
                        if(this.archi.get(val1).getTarget()==this.grafo.indexOf(list))
                        {
                            return false;
                        }
                    }
            }
            targetlist.clear();
        }
        return true;


    }

    @Override
    public boolean isCyclic() {
        int i=0;
        int oi=0;
        int ooi =0;
        List<List<Integer>> adjIn = new ArrayList<>();
        List<Integer> supadj = new ArrayList<>();
        HashMap<Integer,Integer> from = new HashMap<>();
        for(List<Integer> list : this.grafo) {
            try {
                supadj.addAll(getAdjacent(this.grafo.indexOf(list)));
                adjIn.add(new ArrayList<>(supadj));
                supadj.clear();
            }catch (NoSuchElementException e)
            {
                return false;
            }
        }
        List<Integer> pass = new ArrayList<>();
        while (pass.size()<size()+1) {
            if(!pass.contains(i)) {
                pass.add(i);
                    while(adjIn.get(i).isEmpty()) {
                        if(pass.size()==size()){
                            return false;
                        }
                        oi=i;
                        i=from.get(i);
                        for(List<Integer> list : adjIn) {
                            if(list.contains(oi)) {
                                list.remove(list.indexOf(oi));
                            }
                        }
                    }
                    ooi=i;
                    i=adjIn.get(i).get(0);
                    from.put(i,ooi);
            }
            else{
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDAG() {
        return !isCyclic();
    }

    @Override
    public VisitResult getBFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {

        VisitResult visita = new VisitResult(this);
        List<Integer> coda = new ArrayList<>();
        List<Integer> adj = new ArrayList<>();


        if(!this.containsVertex(integer)) {
            throw new IllegalArgumentException("Vertice non presente");
        }
        coda.add(integer);

        while(!coda.isEmpty()){
            adj.addAll(getAdjacent(coda.getFirst()));
            for(Integer val1 : adj) {
                if(!visita.getColor(val1).equals(VisitResult.Color.GRAY) || !visita.getColor(val1).equals(VisitResult.Color.BLACK)) {
                    visita.setColor(val1, VisitResult.Color.GRAY);
                    coda.add(val1);
                }
            }
            adj.clear();
            visita.setColor(coda.getFirst(), VisitResult.Color.BLACK);
            coda.remove(0);
        }
        return visita;
    }

    @Override
    public VisitResult getDFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        VisitResult visita = new VisitResult(this);
        List<Integer> stack = new ArrayList<>();
        List<Integer> adj = new ArrayList<>();
        boolean flag=false;

        if(!this.containsVertex(integer)) {
            throw new IllegalArgumentException("Vertice non presente");
        }

        stack.add(integer);

        while(!stack.isEmpty()){
            flag=false;
            adj.addAll(getAdjacent(stack.getLast()));
            for(Integer val1 : adj) {
                if(visita.getColor(val1).equals(VisitResult.Color.WHITE)) {
                    visita.setColor(val1, VisitResult.Color.GRAY);
                    stack.add(val1);
                    flag=true;
                }
            }
            if(!flag) {
                visita.setColor(stack.getLast(), VisitResult.Color.BLACK);
                stack.remove(stack.getLast());
            }
            adj.clear();
        }

        return visita;
    }

    @Override
    public VisitResult getDFSTOTForest(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {

       VisitResult visita = getDFSTree(integer);
       VisitResult visita2;

       for(int val=0;val<size();val++) {
           if(visita.getColor(val).equals(VisitResult.Color.WHITE)) {
               visita2 = getDFSTree(val);
               for(int j=0;j<size();j++){
                   if(visita.getColor(j).equals(VisitResult.Color.WHITE)&&visita2.getColor(j).equals(VisitResult.Color.BLACK)) {
                       visita.setColor(j, VisitResult.Color.BLACK);
                   }
               }
           }
       }
       return visita;
    }

    @Override
    public VisitResult getDFSTOTForest(Integer[] integers) throws UnsupportedOperationException, IllegalArgumentException {
        VisitResult visita = getDFSTree(integers[0]);
        VisitResult visita2;

        for(Integer val : integers) {
            if(visita.getColor(val).equals(VisitResult.Color.WHITE)) {
                visita2 = getDFSTree(val);
                for(Integer j : integers){
                    if(visita.getColor(j).equals(VisitResult.Color.WHITE)&&visita2.getColor(j).equals(VisitResult.Color.BLACK)) {
                        visita.setColor(j, VisitResult.Color.BLACK);
                    }
                }
            }
        }
        return visita;
    }

    @Override
    public Integer[] topologicalSort() throws UnsupportedOperationException {
        List<Integer> sort= new ArrayList<>();
        boolean flag=false;
        int val =this.size()-1;
        Integer j=0;

        List<Integer> copy = new ArrayList<>();
        for(List<Integer> list : this.grafo) {
            copy.add(j);
            j++;
        }
        List<Edge> copyEdge = new ArrayList<>(this.archi);

        while(!copy.isEmpty()) {
            if (!sort.contains(val)) {
                for (Edge arco : copyEdge) {
                    if (arco.getTarget().intValue() == val) {
                        flag = true;
                    }
                }
                if (!flag) {
                    sort.add(val);
                    copy.remove(copy.indexOf(val));
                    for (Edge arco : copyEdge) {
                        if (arco.getSource().intValue() == val) {
                            copyEdge.set(copyEdge.indexOf(arco), Edge.getEdgeByVertexes(arco.getSource(), arco.getSource()));
                        }
                    }
                }
            }
                flag = false;
                if (val == 0) {
                    val = this.size() - 1;
                } else {
                    val--;
                }

        }
        return sort.toArray(new Integer[0]);
    }

    @Override
    public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
        Set<Set<Integer>> sccs = new HashSet<>();
        int n = this.size();
        if (n == 0) {
            return Set.of();
        }

        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {

                Stack<Integer> tempStack = new Stack<>();
                tempStack.push(i);
                while (!tempStack.isEmpty()) {
                    Integer current = tempStack.peek();
                    if (!visited[current]) {
                        visited[current] = true;
                        for (Integer neighbor : getAdjacent(current)) {
                            if (!visited[neighbor]) {
                                tempStack.push(neighbor);
                            }
                        }
                    } else {
                        tempStack.pop();
                        if (!stack.contains(current)) {
                            stack.push(current);
                        }
                    }
                }
            }
        }

        List<List<Integer>> trasposto = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            trasposto.add(new ArrayList<>());
        }
        for (Edge edge : archi) {
            trasposto.get(edge.getTarget()).add(edge.getSource());
        }

        visited = new boolean[n];
        while (!stack.isEmpty()) {
            Integer node = stack.pop();
            if (!visited[node]) {
                Set<Integer> scc = new HashSet<>();
                Stack<Integer> tempStack = new Stack<>();
                tempStack.push(node);
                while (!tempStack.isEmpty()) {
                    Integer current = tempStack.pop();
                    if (!visited[current]) {
                        visited[current] = true;
                        scc.add(current);
                        for (Integer neighbor : trasposto.get(current)) {
                            if (!visited[neighbor]) {
                                tempStack.push(neighbor);
                            }
                        }
                    }
                }
                sccs.add(scc);
            }
        }

        return sccs;
    }

    @Override
    public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {

        throw(new UnsupportedOperationException("Operazione non supportata")) ;
    }

    @Override
    public String toString() {
        String result = "graph{\n";
        result += "  nodes:0..." + (this.size() - 1) + "\n";
        for (Edge edge : this.archi) {
            result += "  " + edge.getSource() + "-" + edge.getTarget() + "\n";
        }
        result += "}";
        return result;
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
