package com.example.mazegeneratorandsolver;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class LazyPrimMST {
    private final boolean[] marked;
    private final List<Edge> mst;
    private final PriorityQueue<Edge> pq;
    private final EdgeWeightedGraph G;

    public LazyPrimMST(EdgeWeightedGraph G) {
        this.G = G;
        marked = new boolean[G.V()];
        mst = new ArrayList<>();
        pq = new PriorityQueue<>();
        visit(0);

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.remove();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.add(e);
            if (!marked[v]) visit(v);
            if (!marked[w]) visit(w);
        }

    }

    private void visit(int v) {
        marked[v] = true;
        for (Edge e : G.adjEdges(v))
            if (!marked[e.other(v)])
                pq.add(e);

    }

    public List<Edge> mst() { return mst; }
}
