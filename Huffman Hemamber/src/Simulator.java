import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Simulator {

	public static void main(String[] args){
	/*	
		if(args.length == 0){
			System.out.println("Enter fileName as Parameter 'FileName.txt'\nExiting...");
			return;
		}
		else if(args.length != 1){
			System.out.println("Invalid parameters found, can't proceed!");
			return;
		}*/
		
		simulate("input.txt");
	}
	
	static void simulate(String fileName){
		StringBuilder orgData = loadInputData(fileName);
		if(orgData == null){
			return;
		}
		
		System.out.println("Compressing...");
		DataDTO compressedData = HuffmanCompressor.compress(orgData);
		System.out.println("Compressed!\n");
		
		System.out.println("Total Size of Original Uncompressed Data:.......... " + compressedData.getUnCompressedData().length() * Character.SIZE);
		System.out.println("Total Size of Compressed Text:..................... " + compressedData.getCompressedDataSize());
		
		System.out.println(".......................  Original Uncompressed Data  .....................");
		CommonUtils.printStringBuilder(compressedData.getUnCompressedData());
		System.out.println("..........................................................................");
		
		System.out.println("...........................  Compressed Data  ............................");
		CommonUtils.printStringBuilder(compressedData.getCompressedData());
		System.out.println("..........................................................................\n");
		
		
		System.out.println(".................... Characters Coding Info  ......................");
		CommonUtils.printCompressionInfo(compressedData.getCharCodes(), compressedData.getFreqs());
		System.out.println("..........................................................................");
		
		System.out.println("Writing Binary...");
		if(writeBinaryData(compressedData))
			System.out.println("Binary file created! 'Binary.dat'");

	}
	
	static StringBuilder loadInputData(String fileName){
		StringBuilder inputData = null;
		Scanner inFile = CommonUtils.getFileHandle(fileName);
		if(inFile != null){
			inputData = new StringBuilder();
			while(inFile.hasNextLine()){
				inputData.append(inFile.nextLine());
			}
			inFile.close();
		}
		else{
			System.out.println("Can't load data!!\nExiting...");
		}
		
		return inputData;
	}
	
	static boolean writeBinaryData(DataDTO data){
		
		File binFile = new File("Binary.dat");
		try {
			if(binFile.createNewFile()){
				FileOutputStream outStream = new FileOutputStream(binFile);
				ArrayList<Boolean> binaryData = data.getBinaryData();
				CommonUtils.writeBooleans(outStream, binaryData);
				outStream.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
