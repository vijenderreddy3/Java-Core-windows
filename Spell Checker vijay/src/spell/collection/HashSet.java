package spell.collection;

/**
 * 
 * A simple implementation of hash set.
 *
 */
public class HashSet<E>
{
    private static final int INITIAL_ENTRIES = 10;
    
    private Node<E>[] entries;
    private int size;
    
    /**
     * Constructor.
     */
    public HashSet()
    {
        this(INITIAL_ENTRIES);
    }
    
    /**
     * Constructor.
     * @param size
     */
    @SuppressWarnings("unchecked")
    public HashSet(int size)
    {
        entries = new Node[size];
        for(int i = 0;i < entries.length;i++)
        {
            Node<E> entry = entries[i] = new Node<>();
            entry.next = entry.previous = entry;
        }
    }
    
    /**
     * Add all the elements in the given hash set into this.
     * @param set
     * @return
     */
    public boolean addAll(HashSet<E> set)
    {
        boolean added = false;
        for(Node<E> entry : set.entries)
        {
            for(Node<E> node = entry.next;node != entry;node = node.next)
            {
                added |= (this.add(node.element) != null);
            }
        }
        return added;
    }

    /**
     * Add the given element into this hash set.
     * @param element
     * @return
     */
    public E add(E element)
    {
        // Replace if exists.
        Node<E> existingNode = this.findNode(element);
        if(existingNode != null)
        {
            E oldElement = existingNode.element;
            existingNode.element = element;
            return oldElement;
        }
        
        // Append if doesn't exist.
        Node<E> entry = this.findEntry(element);
        Node<E> node = new Node<>();
        node.element = element;
        entry.previous.next = node;
        node.previous = entry.previous;
        entry.previous = node;
        node.next = entry;
        size++;
        
        return null;
    }

    /**
     * Remove the given element from this hash set.
     * @param element
     * @return
     */
    public boolean remove(E element)
    {
        Node<E> existingNode = this.findNode(element);
        if(existingNode != null)
        {
            existingNode.previous.next = existingNode.next;
            existingNode.next.previous = existingNode.previous;
            size--;
            return true;}
        
        return false;
    }

    /**
     * Check if the given element is contained in this hash set.
     * @param element
     * @return
     */
    public boolean contains(E element)
    {
        return this.findNode(element) != null;
    }
    
    /**
     * Find the node of the given element.
     * @param element
     * @return
     */
    private Node<E> findNode(E element)
    {
        Node<E> entry = this.findEntry(element);
        
        for(Node<E> node = entry.next;node != entry;node = node.next)
        {
            if(element.equals(node.element))
            {
                return node;
            }
        }
        
        return null;
    }
    
    /**
     * Find the entry of the given element.
     * @param element
     * @return
     */
    private Node<E> findEntry(E element)
    {
        int hashCode = element.hashCode();
        int entryIndex = hashCode % entries.length;
        if(entryIndex < 0)
        {
            entryIndex += entries.length;
        }
        
        return entries[entryIndex];
    }
    
    /**
     * Get the size of this hash set.
     * @return
     */
    public int size()
    {
        return size;
    }

    /**
     * Convert all elements in this hash set into an array.
     * @param elements
     * @return
     */
    public E[] toArray(E[] elements)
    {
        int index = 0;
        for(Node<E> entry : entries)
        {
            for(Node<E> node = entry.next;node != entry;node = node.next)
            {
                elements[index] = node.element;
                index++;
            }
        }
        return elements;
    }
    
    /**
     * 
     * An instance of this class represents an entry of a hash set.
     *
     */
    private static final class Node<E>
    {
        private Node<E> previous;
        private Node<E> next;
        private E element;
    }
}
