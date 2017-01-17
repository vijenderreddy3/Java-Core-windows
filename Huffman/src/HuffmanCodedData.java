public class HuffmanCodedData{
	
	private StringBuilder originalData = null;
	private StringBuilder encodedData = null;
	private int[] frequencies = null;
	private String[] codes;
	private Long binSize = null;
	
	public HuffmanCodedData(StringBuilder od, StringBuilder ed, int[] fr, String[] c, Long size){
		this.originalData = od;
		this.encodedData = ed;
		this.frequencies = fr;
		this.codes = c;
		this.binSize = size;
	}
	
	public Long getEncodedDataSize(){
		return binSize;
	}
	
	public StringBuilder getOriginalData(){
		return originalData;
	}
	
	public StringBuilder getEncodedData(){
		return encodedData;
	}
	
	public void printEncodedInfo(){
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("S.No\tChar\tFreq\t\t\tCode");
		System.out.println("--------------------------------------------------------------------------");
		for(int i=0; i<codes.length; i++){
			if(codes[i] != null){
				System.out.println(i +".\t'" + (char)i + "'\t" + frequencies[i] +"\t ...........\t" + codes[i]);
			}
		}
	}
	
	public void printCodes(){
		
		for(int i=0; i<codes.length; i++){
			if(codes[i] != null){
				System.out.println(i +". '" + (char)i + "' ...........\t" + codes[i]);
			}
		}
	}
	
	public void printFrequences(){
		
		for(int i=0; i<frequencies.length; i++){
			if(frequencies[i] > 0){
				System.out.println(i +". '" + (char)i + "' ..............\t" + frequencies[i]);
			}
		}
	}
}