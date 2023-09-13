package gad.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class SelfOrganizingList<T> {
    private Node<T> head;
    private Node<T> tail;

    public Node<T> getHead() {
        return head;
    }
    public Node<T> getTail() {
        return tail;
    }

    public static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T d) {
            data = d;
            next = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private void printElements() {
        Node<T> next = head;
        while (next != null) {
            System.out.print(next.data.toString());
            if (next.next != null) {
                System.out.print(" -> ");
            }
            next = next.next;
        }
        System.out.println();
    }

    public void add(T data) {
        // TODO
        if(head == null) {
            head = new Node<>(data);
            tail = head;
        }
        else {
            Node<T> newNode = new Node<>(data);
            tail.next = newNode;
            tail = newNode;
        }
    }

    public Optional<T> findFirst(Predicate<T> p) {
        // TODO
        Node<T> lastNode = null;
        Node<T> nextNode = head;
        while(nextNode != null) {
            if(p.test(nextNode.data)) {
                if(lastNode != null) {
                    lastNode.next = nextNode.next;
                    nextNode.next = head;
                    head = nextNode;
                    if(nextNode == tail) tail = lastNode;
                }

                return Optional.of(nextNode.data);
            }
            lastNode = nextNode;
            nextNode = nextNode.next;
        }
        return Optional.empty();
    }

    public void removeDuplicates() {
        // TODO
        Node<T> current = head;
        while(current != null) {
            Node<T> lastV = current;
            Node<T> curV = current.next;
            while(curV != null) {
                if(curV.data.equals(current.data)) {
                    lastV.next = curV.next;
                    if(curV == tail) tail = lastV;
                }
                else {
                    lastV = curV;
                }
                curV = curV.next;
            }

            current = current.next;
        }
    }

    // Die folgenden Tests sind selbstverständlich nicht vollständig, sondern sollen nur
    // einen kurzen Überblick geben, sodass das Programmieren der Methoden schneller funktioniert.
    public static void main(String[] args) {
        SelfOrganizingList<Integer> myList = new SelfOrganizingList<>();
        System.out.println("add testen");
        for (int i = 0; i < 20; i++) {
            myList.add(i);
        }
        System.out.println("Enthaltene Elemente (sollte von 0 bis 19 aufsteigend sein): ");
        myList.printElements();

        System.out.println();
        System.out.println("findFirst testen");
        System.out.println("Sollte 11 zurückgeben: " + myList.findFirst(n -> n > 10).toString());
        System.out.println("Enthaltene Elemente (11 sollte am Index 0 stehen): ");
        myList.printElements();
        System.out.println("Sollte 12 zurückgeben: " + myList.findFirst(n -> n == 12).toString());
        System.out.println("Enthaltene Elemente (Liste sollte mit 12, 11, 0, 1, ... beginnen): ");
        myList.printElements();
        System.out.println("Sollte leeres Optional zurückgeben: " + myList.findFirst(n -> n > 30).toString());
        System.out.println("Enthaltene Elemente (sollten gleich bleiben): ");
        myList.printElements();

        System.out.println();
        System.out.println("removeDuplicates testen");
        for (int i = 0; i < 3; i++) {
            myList.add(3 * i % 4);
            myList.add(4 * i % 3);
        }
        myList.add(20);
        System.out.print("Enthaltene Elemente: ");
        myList.printElements();
        myList.removeDuplicates();
        System.out.print("Enthaltene Elemente: ");
        myList.printElements();
    }
}