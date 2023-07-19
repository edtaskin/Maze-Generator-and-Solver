package com.example.mazesolver;

public class ConnectedComponents {
    private final boolean[] marked;
    private final int[] id;
    private int highestId;

    public ConnectedComponents(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int i = 0; i < G.V(); i++)
            if (!marked[i]) {
                dfs(G, i);
                highestId++;
            }
    }

    public ConnectedComponents(EdgeWeightedGraph G, boolean stopWhenNotConnected) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int i = 0; i < G.V(); i++)
            if (!marked[i]) {
                dfs(G, i);
                highestId++;
                if (stopWhenNotConnected && highestId > 2)
                    break;
            }
    }


    private void dfs(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        id[v] = highestId;
        for (int w : G.adjVertices(v)) {
            if (!marked[w])
                dfs(G, w);
        }
    }

    public boolean isConnected() { return highestId == 2;}

}
