package com.example.mazesolver;

import java.util.ArrayList;
import java.util.List;

public class RandomizedDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private int startVertex;
    private List<Integer> visitedVertices;

    public RandomizedDFS(Graph G, int startVertex) {
        this.startVertex = startVertex;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        visitedVertices = new ArrayList<>();
        dfs(G, startVertex);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        visitedVertices.add(v);
        for (int w : G.adj(v, true))
            if (!marked[w]) {
                dfs(G, w);
                edgeTo[w] = v;
            }
    }

    //public boolean hasPathTo(int v) { return marked[v]; }

    //public Iterable<Integer> pathTo(int)

    public List<Integer> getVisitedVertices() { return visitedVertices; }

}
