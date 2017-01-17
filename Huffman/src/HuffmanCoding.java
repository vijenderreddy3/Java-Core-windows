public class HuffmanCoding {

    private static final int TOTAL_CHARACTERS = 256;

    public static HuffmanCodedData encode(StringBuilder inputString) {

    	char[] inputChars = new char[inputString.length()];
    	inputString.getChars(0, inputString.length(), inputChars, 0);
    	StringBuilder outputSBuilder = new StringBuilder();

        // create Frequency Table
    	int[] frequencies = getFrequencyTable(inputChars);
        
        // Creates Huffman Tree
        HuffmanNode root = createHaffmanTree(frequencies);

        // Create Characters table
        String[] codes = new String[TOTAL_CHARACTERS];
        Long size = new Long(0);
        
        buildCode(codes, root, "");

        // Encoding Input String
        for (int i = 0; i < inputChars.length; i++) {
            String code = codes[inputChars[i]];
            for (int j = 0; j < code.length(); j++) {
            	size++;
                if (code.charAt(j) == '0') {
                    outputSBuilder.append('0');
                }
                else if (code.charAt(j) == '1') {
                	outputSBuilder.append('1');
                }
                else throw new IllegalStateException("Invalid Code Character!, Can't proceeded");
            }
        }
        
        HuffmanCodedData codedData =  new HuffmanCodedData(inputString, outputSBuilder, frequencies, codes, size);
        return codedData;
    }
    
    public static int[] getFrequencyTable(char inputChars[]){
    	
    	int[] freqs = new int[TOTAL_CHARACTERS];
        for (int i = 0; i < inputChars.length; i++){
        	if(inputChars[i] > 255)
        		System.out.println((int)inputChars[i]);
            freqs[inputChars[i]]++;
        }
        
        return freqs;
    }

    //createHaffmanTree using input frequencies
    private static HuffmanNode createHaffmanTree(int[] freqs) {

        MinPriorityQueue<HuffmanNode> minQueue = new MinPriorityQueue<HuffmanNode>(TOTAL_CHARACTERS);
        for (char i = 0; i < TOTAL_CHARACTERS; i++){
            if (freqs[i] > 0)
                minQueue.insert(new HuffmanNode(i, freqs[i], null, null));
        }

        if (minQueue.size() == 1) {
            if (freqs['\0'] == 0) minQueue.insert(new HuffmanNode('\0', 0, null, null));
            else                 minQueue.insert(new HuffmanNode('\1', 0, null, null));
        }

        //dequeue 2 smallest, merge them, and enqueue them
        while (minQueue.size() > 1) {
            HuffmanNode left  = minQueue.remove();
            HuffmanNode right = minQueue.remove();
            HuffmanNode parent = new HuffmanNode('\0', left.freq + right.freq, left, right);
            minQueue.insert(parent);
        }
        return minQueue.remove();
    }

    // make a lookup table from symbols and their encodings
    private static void buildCode(String[] codes, HuffmanNode startItem, String code) {
        if (!startItem.isLeaf()) {
            buildCode(codes, startItem.left,  code + '0');
            buildCode(codes, startItem.right, code + '1');
        }
        else {
            codes[startItem.ch] = code;
        }
    }
}

