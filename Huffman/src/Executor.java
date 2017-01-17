import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Executor {

	public static void main(String[] args){
		
		if(args.length ==0){
			System.out.println("No file Selected!\nExiting...");
			return;
		}
		StringBuilder orignalData = loadData(args[0]);
		if(orignalData == null){
			return;
		}
		
		HuffmanCodedData codedData = HuffmanCoding.encode(orignalData);
		
		System.out.println("------------------------------  Original Data  ---------------------------");
		printStringBuilder(codedData.getOriginalData());
		System.out.println("--------------------------------------------------------------------------");
		
		System.out.println("------------------------------  Encoded Data  ----------------------------");
		printStringBuilder(codedData.getEncodedData());
		System.out.println("--------------------------------------------------------------------------\n");
		
		System.out.println("Total Size of Original Text:............. " + codedData.getOriginalData().length() * Character.SIZE);
		System.out.println("Total Size of Encoded Text:............. " + codedData.getEncodedDataSize());
		
		System.out.println("-----------------------------  Coding Info  ------------------------------");
		codedData.printEncodedInfo();
		System.out.println("--------------------------------------------------------------------------");
	}
	
	static StringBuilder loadData(String fileName){
		StringBuilder inputData = null;
		try {
			Scanner in = new Scanner(new FileInputStream(new File(fileName)));
			if(in != null){
				inputData = new StringBuilder();
				while(in.hasNextLine()){
					inputData.append(in.nextLine());
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		return inputData;
	}
	
	static void printStringBuilder(StringBuilder sb){
		
		int part = 300;
		int i = 0;
		for(i=0; i*part<sb.length(); i++){
			int temp = (i+1) * part;
			System.out.println(sb.substring(i*part, (temp < sb.length()? temp : sb.length())));
		}
		int temp1 = i*part;
		int temp2 = temp1 + part;
		if(temp1 < sb.length()){
			System.out.println(sb.substring(temp1, (temp2 < sb.length()? temp2 : sb.length())));
		}
	}
}
