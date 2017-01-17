
public class DLL<T extends Comparable<T>> extends DataStructure<T> {

    private class Node<T> {

        Node<T> next;
        Node<T> previous;
        T data;

        Node(T data) {
            this.data = data;
        }
    }

    public Node<T> head;
    public Node<T> tail;
    public int size;

    public void insert(T e) {
        insertLast(e);
    }

    public void insertFirst(T e) {
        Node<T> node = new Node<>(e);
        if (isEmpty()) {
            head = tail = node;
            node.next = null;
            node.previous = null;
        }
        else {
            head.previous = node;
            node.next = head;
            head = node;
            node.previous = null;
        }
        size++;
    }

    public void insertLast(T e) {
        Node<T> node = new Node(e);
        if (isEmpty()) {
            head = tail = node;
            node.next = null;
            node.previous = null;
        }
        else {
            tail.next = node;
            node.previous = tail;
            tail = node;
            node.next = null;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty())
            return null;
        else if (size == 1) {
            Node<T> current = head;
            tail = head = null;
            size--;
            return current.data;
        }
        Node<T> current = head;
        head = head.next;
        head.previous = null;
        size--;
        return current.data;
    }

    public T removeLast() {
        if (isEmpty())
            return null;
        else if (size == 1) {
            Node<T> current = tail;
            tail = head = null;
            size--;
            return current.data;
        }
        Node<T> current = tail;
        tail = tail.previous;
        tail.next = null;
        size--;
        return current.data;
    }

    public T remove(T key) {
        if (isEmpty())
            return null;
        else if (size == 1) {
            Node<T> current = head;
            head = null;
            size--;
            return (T) current.data;
        }
        T temp = search(key);
        if(temp == null)
            return null;
        if (head.data.equals(key)) {
            head = head.next;
            return temp;
        }
        Node<T> current = head;
        while (!current.data.equals(key)) {
            current = current.next;
        }
        current.previous.next = current.next;
        current.next.previous = current.previous;
        size--;
        return temp;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public boolean contains(T key) {
        Node<T> current = head;
        while(current != null) {
            if (current.data.equals(key))
                return true;
            current = current.next;
        }
        return false;
    }

    public T search(T key) {
        Node<T> current = head;
        while(current != null) {
            if (current.data.equals(key))
                return current.data;
            current = current.next;
        }
        return null;
    }

    public String toString() {
        if (size == 0)
            return "";
        String output = "";
        Node<T> current = head;
        while(current != null) {
            output += current.data.toString() + ", ";
            current = current.next;
        }
        return output.substring(0, output.length() - 2);
    }

}
