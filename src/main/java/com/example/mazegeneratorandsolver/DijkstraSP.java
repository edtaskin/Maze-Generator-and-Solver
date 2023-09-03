package com.example.mazegeneratorandsolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

// TODO Replace Dijkstra with simple DFS
public class DijkstraSP {
    private Edge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq; // Holds vertices in increasing distance from src
    private int src;

    public DijkstraSP(EdgeWeightedGraph G, int src) {
        this.src = src;
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[src] = 0;

        pq.insert(src, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adjEdges(v))
                relax(e, v);
        }
    }

    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.getWeight()) {
            distTo[w] = distTo[v] + e.getWeight();
            edgeTo[w] = e;

            if (pq.contains(w))
                pq.decreaseKey(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
    }

    // Returns the vertices on the shortest path to vertex with index v as an Iterable
    public Iterable<Integer> verticesOnPathTo(int v) {
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != src; x = edgeTo[x].other(x))
            path.push(x);
        path.push(src);
        Collections.reverse(path);
        return path;
    }

}
