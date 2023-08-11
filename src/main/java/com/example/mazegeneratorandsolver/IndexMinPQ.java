package com.example.mazegeneratorandsolver;

import java.util.Arrays;

// Throughout the code heap indices are named "i" and key indices are named "ki"
public class IndexMinPQ<Value extends Comparable<Value>> {
    private final int N; // capacity of PQ
    private int sz;

    // Keys: [0, N)
    private final Value[] valueMap;   // key index => value
    private final int[] positionMap;  // key index => heap position
    private final int[] indexMap;     // heap position => key index. Similar to the "pq" array from regular PQ implementation.

    public IndexMinPQ(int capacity) {
        N = capacity + 1; // Not using index 0
        valueMap = (Value[]) new Comparable[N];
        positionMap = new int[N];
        indexMap = new int[N];
        Arrays.fill(positionMap, -1);
    }

    public int size() { return sz; }

    public boolean isEmpty() { return sz == 0; }

    public boolean contains(int i) { return positionMap[i] != -1; }

    public void insert(Value val) {
        // key index = sz
        valueMap[sz] = val;
        positionMap[sz] = sz;
        indexMap[sz] = sz;
        swim(sz++);
    }

    // Deletes the node with given key index and preserves the heap properties.
    private Value delete(int ki) {
        int i = positionMap[ki];
        swap(i, sz);
        sink(i);
        swim(i);
        Value val = valueMap[ki];
        valueMap[ki] = null;
        positionMap[ki] = -1;
        indexMap[sz--] = -1;
        return val;
    }

    public Value delMin() {
        int ki = indexMap[1];
        Value minVal = valueMap[ki];
        swap(1, sz); // index 0 is not used
        sink(1);
        valueMap[ki] = null;
        positionMap[ki] = -1;
        indexMap[sz--] = -1;
        return minVal;
    }

    private Value changeKey(int ki, Value val) {
        Value oldVal = valueMap[ki];
        valueMap[ki] = val;
        swim(indexMap[ki]); // if val < oldVal
        sink(indexMap[ki]); // else if val > oldVal
        return oldVal;
    }

    // Decreases key and preserves heap constraints only if the input key is smaller than the current key.
    public void decreaseKey(int ki, Value val) {
        if (less(val, valueMap[ki])) {
            valueMap[ki] = val;
            swim(indexMap[ki]);
        }
    }

    // Move node at heap position i upwards in the heap until the heap constraints are satisfied.
    private void swim(int i) {
        while (i > 1 && less(i, i/2)) {
            swap(i, i/2);
            i = i/2;
        }
    }

    // Move node at heap position i downwards in the heap until the heap constraints are satisfied.
    private void sink(int i) {
        while (2*i < sz) {
            int left = 2*i + 1;
            int right = 2*i + 2;
            int smallest = left; // default is left child

            if (right < sz && less(right, left))
                smallest = right;
            if (less(i, smallest))
                break;
            swap(i, smallest);
            i = smallest;
        }
    }

    // Compares two nodes in the heap given their indices in the heap.
    private boolean less(int i, int j) {
        return valueMap[indexMap[i]].compareTo(valueMap[indexMap[j]]) < 0;
    }

    private boolean less(Value v1, Value v2) {
        return v1.compareTo(v2) < 0;
    }

    // Swaps two nodes in the heap given their heap indices.
    private void swap(int i, int j) {
        // valuesMap remains untouched
        positionMap[indexMap[i]] = j;
        positionMap[indexMap[j]] = i;
        int temp = indexMap[i];
        indexMap[i] = indexMap[j];
        indexMap[j] = temp;
    }
}
