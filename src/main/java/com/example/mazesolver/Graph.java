package com.example.mazesolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

// Adjacency-list representation of an "undirected" Graph
// Vertices are referenced via indices. If needed, add a symbol table to convert between indices and names.
public class Graph {
    private final int V;
    private LinkedList<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = (LinkedList<Integer>[]) new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<Integer>();
        }
    }

    public int V() { return V; }
    //Multi-edges and self-loops allowed
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    //Returns the adjacent vertices of v
    public Iterable<Integer> adj(int v) { return adj[v]; }

    public Iterable<Integer> adj(int v, boolean randomize) {
        if (randomize) {
            LinkedList<Integer> randomizedAdjV = (LinkedList<Integer>) adj[v].clone();
            Collections.shuffle(randomizedAdjV);
            return randomizedAdjV;
        }
        return null;
    }
}