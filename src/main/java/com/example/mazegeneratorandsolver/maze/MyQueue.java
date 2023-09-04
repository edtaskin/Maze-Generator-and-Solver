package com.example.mazegeneratorandsolver.maze;

import java.util.Iterator;

public class MyQueue<T> implements Iterable<T>{
    private Node first, last;
    private int n;

    private class Node {
        T item;
        Node next;
    }

    public MyQueue() {
        first = null;
        last = null;
        n = 0;
    }

    public int size() { return n; }

    public boolean isEmpty(){ return first == null; }

    public void enqueue(T item){
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }

    public T dequeue(){
        if (isEmpty()) return null;
        T item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        n--;
        return item;
    }

    public void clear() {
        first = null;
        last = null;
        n = 0;
    }

    public T tail() {
        return last.item;
    }

    @Override
    public Iterator<T> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<T>{
        private Node current = first;

        @Override
        public boolean hasNext() { return current != null; }

        @Override
        public T next() {
            T item = current.item;
            current = current.next;
            return item;
        }
    }
}