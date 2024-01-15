package oy.interact.tira.student;

import org.w3c.dom.Node;

import oy.interact.tira.util.QueueInterface;

public class QueueImplementation<E> implements QueueInterface<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    private class Node<T> {
        T data;
        Node<T> next;
        Node<T> previous;

        public Node(T data) {
            this.data = data;
            next = null;
            previous = null;
        }
    }

    
    @Override
    public int capacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void enqueue(E element) throws OutOfMemoryError, NullPointerException {
        if(element == null) {
            throw new NullPointerException();
        }
        Node<E> new_node = new Node<E>(element);
        if (isEmpty()) {
            head = new_node;
            tail = new_node;
            
        }
        else {
            tail.next = new_node;
            new_node.previous = tail;
            tail = new_node;
            
        }
        size++;
        
        
    }

    @Override
    public E dequeue() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        E toReturn = element();
        head = head.next;
        if(head != null) {
            head.previous = null;
        } else {
            tail = null;
        }
        
        size--;
        return toReturn;
    }
    @Override
    public E element() throws IllegalStateException {
       if(head == null) {
        throw new IllegalStateException();
       } 
       return head.data;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;      
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        builder.append("[");

        if (head != null) {
             Node<E> temp = head;
             while (temp != null) {
                builder.append(temp.data);
                if(temp.next != null) {
                    builder.append(", ");
                }
                temp = temp.next;
             }
        }
       
        builder.append("]");

        return builder.toString();
    }
}
