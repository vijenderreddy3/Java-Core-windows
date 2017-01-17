import java.util.ArrayList;

public class HuffmanCompressor {

	 final static int MAX_CHARACTERS_SUPPORTED = 512;
	 
	 public static DataDTO compress(StringBuilder inputString) {

		char[] inputChars = new char[inputString.length()];
	    inputString.getChars(0, inputString.length(), inputChars, 0);
	    StringBuilder outputSBuilder = new StringBuilder();

	    int[] frequencies = getFrequencies(inputChars);
	    Node root = createHaffmanTree(frequencies);

	    ArrayList<String> codes = getCodes(root);
	    ArrayList<Boolean> bin = new ArrayList<Boolean>();
	        
	    for (int i = 0; i < inputChars.length; i++) {
	         String code = codes.get(inputChars[i]);
	         for (int j = 0; j < code.length(); j++) {
	             if (code.charAt(j) == '0') {
	                 outputSBuilder.append('0');
	                 bin.add(false);
	             }
	             else if (code.charAt(j) == '1') {
	              	outputSBuilder.append('1');
	              	bin.add(true);
	             }
	             else throw new IllegalStateException("Invalid Code Character!, Can't proceeded");
	         }
	    }
	        
	    DataDTO codedData =  new DataDTO(inputString, outputSBuilder, frequencies, codes, bin);
	    return codedData;
	 }
	 
	 private static ArrayList<String> getCodes(Node root) {
	    if (root == null){
	    	return null;    
	    }
	    
	    ArrayList<String> codes = new ArrayList<>(MAX_CHARACTERS_SUPPORTED);
	    for(int i=0; i<MAX_CHARACTERS_SUPPORTED; i++){
	    	codes.add("");
	    }
	    createCodes(root, codes);
	    return codes;
	  }
	  
	  private static void createCodes(Node root, ArrayList<String> codes) {
	    
		if(root.isLeaf()){
			codes.set((int)root.ch, root.code);
		}
	    else {
	    	root.leftChild.code = root.code + "0";
	    	createCodes(root.leftChild, codes);
	    	
	    	root.rightChild.code += root.code + "1";
	    	createCodes(root.rightChild, codes);
	    }
	  }
	  
	  private static Node createHaffmanTree(int[] freqs) {

	      PriorityQueue minQueue = new PriorityQueue(MAX_CHARACTERS_SUPPORTED);
	      for (char i = 0; i < MAX_CHARACTERS_SUPPORTED; i++){
	          if (freqs[i] > 0)
	              minQueue.enQueue(new Node(i, freqs[i], null, null));
	      }

	      if (minQueue.getSize() == 1) {
	          if (freqs['\0'] == 0){
	        	  minQueue.enQueue(new Node('\0', 0, null, null));
	          }
	          else{
	        	  minQueue.enQueue(new Node('\1', 0, null, null));
	          }
	      }
	      
	      while (minQueue.getSize() > 1) {
	          Node left  = (Node) minQueue.deQueue();
	          Node right = (Node) minQueue.deQueue();
	          Node parent = new Node('\0', left.freq + right.freq, left, right);
	          minQueue.enQueue(parent);
	      }
	      return (Node) minQueue.deQueue();
	    }
	  
	    private static int[] getFrequencies(char inputChars[]){
	    	
	    	int[] freqs = new int[MAX_CHARACTERS_SUPPORTED];
	        for (int i = 0; i < inputChars.length; i++){
	        	if(inputChars[i] > MAX_CHARACTERS_SUPPORTED){
	        		System.out.println("Invalid Character! '" + inputChars[i] + "': " + inputChars[i]);
	        	}
	        	else{
	        		freqs[inputChars[i]]++;
	        	}
	        }
	        return freqs;
	    }
}