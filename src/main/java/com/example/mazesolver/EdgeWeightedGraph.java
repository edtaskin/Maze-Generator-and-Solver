package com.example.mazesolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EdgeWeightedGraph {
    private final int V;
    private final LinkedList<Edge>[] adj; // Holds "edges" connected to vertex.
    private final ArrayList<Edge> edges;
    public EdgeWeightedGraph(int V) {
        this.V = V;
        edges = new ArrayList<>();
        adj = (LinkedList<Edge>[]) new LinkedList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new LinkedList<Edge>();
    }

    public int V() { return V; }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        edges.add(e);
    }

    public Iterable<Edge> adjEdges(int v) { return adj[v]; }

    public Iterable<Integer> adjVertices(int v) {
        List<Integer> adjVertices = new ArrayList<>();
        for (Edge e : adjEdges(v)) {
            adjVertices.add(e.other(v));
        }
        return adjVertices;
    }

    public Iterable<Edge> edges() { return edges; }
}
