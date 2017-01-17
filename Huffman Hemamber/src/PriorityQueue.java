import java.util.*;

public class PriorityQueue
{
    private MinComparator minComparator = new MinComparator();
    private int totalNodes;
    private ArrayList<Object>  nodesList;

    public PriorityQueue(int size)
    {
        nodesList = new ArrayList<Object>(size);
        nodesList.add(null);             
        totalNodes = 0;
    }

    public boolean enQueue(Object o)
    {
        nodesList.add(o);        
        totalNodes++;           
        int k = totalNodes;    

        while (k > 1 && minComparator.compare(nodesList.get(k/2), o) > 0){
            nodesList.set(k, nodesList.get(k/2));
            k = k/2;
        }
        nodesList.set(k,o);
        
        return true;
    }

    public int getSize(){
        return totalNodes;
    }

    public boolean isEmpty(){
        return totalNodes == 0;
    }

    public Object deQueue(){
        
    	if (!isEmpty()){
            Object min = nodesList.get(1);			//get the first minimum one
            
            nodesList.set(1, nodesList.get(totalNodes));  
            nodesList.remove(totalNodes);             
            totalNodes--;
            if (totalNodes > 1){
                heapify(1);
            }
            return min;
        }
        return null;
    }
    
    private void heapify(int rootNode){
        
    	Object lastObj = nodesList.get(rootNode);
        int childNode, currentNode = rootNode;
        while (2*currentNode < totalNodes){
            childNode = 2*currentNode;
            int result = minComparator.compare(nodesList.get(childNode),nodesList.get(childNode+1));
            if (childNode < totalNodes && result > 0){
                childNode++;
            }
            if (minComparator.compare(lastObj, nodesList.get(childNode)) <= 0){
                break;
            }
            else{
                nodesList.set(currentNode, nodesList.get(childNode));
                currentNode = childNode;
            }
        }
        nodesList.set(currentNode, lastObj);
    }
}
