
public class MinPriorityQueue<Node>{
    private Node[] dataArray;                    // data
    private int totalItems;                       

    @SuppressWarnings("unchecked")
	public MinPriorityQueue(int initCapacity) {
        dataArray = (Node[]) new Object[initCapacity + 1];
        totalItems = 0;
    }

    @SuppressWarnings("unchecked")
	public MinPriorityQueue(Node[] data) {
        totalItems = data.length;
        dataArray = (Node[]) new Object[data.length + 1];
        
        for (int i = 0; i < totalItems; i++)
            dataArray[i+1] = data[i];
        for (int k = totalItems/2; k >= 1; k--)
            restoring(k);
        assert isMinHeap();
    }

    public boolean isEmpty() {
        return totalItems == 0;
    }

    public int size() {
        return totalItems;
    }

    public Node min() {
        return isEmpty()? null : dataArray[1];
    }

    private void resize(int capacity) {
        assert capacity > totalItems;
        @SuppressWarnings("unchecked")
		Node[] temp = (Node[]) new Object[capacity];
        for (int i = 1; i <= totalItems; i++) {
            temp[i] = dataArray[i];
        }
        dataArray = temp;
    }

    public void insert(Node newNode) {
        // increase size of array if necessary
        if (totalItems == dataArray.length - 1) resize((int)(1.5 * dataArray.length));

        dataArray[++totalItems] = newNode;
        restore(totalItems);
        assert isMinHeap();
    }

    public Node remove() {
        
    	if(isEmpty()){
    		return null;
    	}
    	
        swap(1, totalItems);
        Node min = dataArray[totalItems--];
        restoring(1);
        dataArray[totalItems+1] = null;        
        if ((totalItems > 0) && (totalItems == (dataArray.length - 1) / 4)){
        	resize(dataArray.length  / 2);
        }
        assert isMinHeap();
        return min;
    }

    private void restore(int startIndex) {
        while (startIndex > 1 && greater(startIndex/2, startIndex)) {
            swap(startIndex, startIndex/2);
            startIndex = startIndex/2;
        }
    }

    private void restoring(int fromIndex) {
        
    	while (2*fromIndex <= totalItems) {
            int i = 2*fromIndex;
            if (i < totalItems && greater(i, i+1)){
            	i++;
            }
            if (!greater(fromIndex, i)){
            	break;
            }
            swap(fromIndex, i);
            fromIndex = i;
        }
    }

    private boolean greater(int i, int j) {
        HuffmanNode h1 = (HuffmanNode) dataArray[i];
        HuffmanNode h2 = (HuffmanNode) dataArray[j];
        
        return h1.compareTo(h2) == 1? true : false;
    }

    private void swap(int i, int j) {
        Node n = dataArray[i];
        dataArray[i] = dataArray[j];
        dataArray[j] = n;
    }

    private boolean isMinHeap() {
        return isMinHeap(1);
    }

    private boolean isMinHeap(int startFrom) {
        if (startFrom > totalItems){				//base case
        	return true;
        }
        int left = 2*startFrom, right = 2*startFrom + 1;
        if (right <= totalItems && greater(startFrom, right)){
        	return false;
        }
        if (left  <= totalItems && greater(startFrom, left)){
        	return false;
        }
        return isMinHeap(left) && isMinHeap(right);
    }
}