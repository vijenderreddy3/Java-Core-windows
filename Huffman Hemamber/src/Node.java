
	public class Node{
        char ch;
        int freq;
        Node leftChild, rightChild;
        String code = "";

        Node(char ch, int freq, Node leftChild, Node rightChild) {
            this.ch    = ch;
            this.freq  = freq;
            this.leftChild  = leftChild;
            this.rightChild = rightChild;
        }

        public boolean isLeaf() {
            return (leftChild == null) && (rightChild == null);
        }
    }