
public class SLL<T extends Comparable<T>> extends DataStructure<T> {

    private class Node<T> {

        Node<T> next;
        T data;

        Node(T data) {
            this.data = data;
        }
    }

    public Node<T> head;
    public int size;

    public void insert(T e) {
        Node<T> node = new Node<>(e);
        node.next = head;
        head = node;
        size++;
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
        Node<T> previous = null;
        while (!current.data.equals(key)) {
            previous = current;
            current = current.next;
        }
        previous.next = current.next;
        size--;
        return temp;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
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
