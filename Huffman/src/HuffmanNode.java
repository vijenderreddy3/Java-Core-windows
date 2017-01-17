
	public class HuffmanNode implements Comparable<HuffmanNode> {
        final char ch;
        final int freq;
        final HuffmanNode left, right;

        HuffmanNode(char ch, int freq, HuffmanNode left, HuffmanNode right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        public boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(HuffmanNode that) {
            return (this.freq - that.freq) > 0? 1 : -1;
        }
    }