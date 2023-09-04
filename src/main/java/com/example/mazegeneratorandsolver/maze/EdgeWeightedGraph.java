package com.example.mazegeneratorandsolver.maze;

import com.example.mazegeneratorandsolver.maze.Edge;

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
        for (int i = 0; i < V; i++)
            adj[i] = new LinkedList<>();
    }

    public int V() { return V; }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        edges.add(e);
    }

    public void removeEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].remove(e);
        adj[w].remove(e);
        edges.remove(e);
    }

    public Iterable<Edge> adjEdges(int v) { return adj[v]; }

    public Iterable<Integer> adjVertices(int v) {
        List<Integer> adjVertices = new ArrayList<>();
        for (Edge e : adjEdges(v)) {
            adjVertices.add(e.other(v));
        }
        return adjVertices;
    }

    public Edge getEdgeBetween(int v, int w) {
        for (Edge e : this.adjEdges(v))
            if (e.other(v) == w)
                return e;
        return null;
    }

    public Iterable<Edge> edges() { return edges; }
}
