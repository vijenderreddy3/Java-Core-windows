import java.util.ArrayList;

public class DataDTO{
	
	private StringBuilder unCompressedData = null;
	private StringBuilder compressedData = null;
	private int[] freqs = null;
	private int compressedDataSize = 0;
	private ArrayList<String> charCodes;
	private ArrayList<Boolean> binaryData;
	
	public DataDTO(StringBuilder od, StringBuilder ed, int[] fr, ArrayList<String> c, ArrayList<Boolean> bin){
		this.unCompressedData = od;
		this.compressedData = ed;
		this.freqs = fr;
		this.charCodes = c;
		this.binaryData = bin;
		compressedDataSize = this.compressedData.length();
	}

	public StringBuilder getUnCompressedData() {
		return unCompressedData;
	}

	public StringBuilder getCompressedData() {
		return compressedData;
	}

	public int[] getFreqs() {
		return freqs;
	}

	public ArrayList<String> getCharCodes() {
		return charCodes;
	}

	public ArrayList<Boolean> getBinaryData() {
		return binaryData;
	}
	
	public int getCompressedDataSize(){
		return this.compressedDataSize;
	}
}