import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class CommonUtils {

	public static void printCompressionInfo(ArrayList<String> dataCodes, int fre[]){
		System.out.println("..........................................................................");
		System.out.println("S.No\tChar\tFreq\t\t\tCode");
		System.out.println("..........................................................................");
		for(int i=0; i<dataCodes.size(); i++){
			if(dataCodes.get(i) != null && fre[i] > 0){
				System.out.println(i +".\t'" + (char)i + "'\t" + fre[i] +"\t ...........\t" + dataCodes.get(i));
			}
		}
	}

	static void writeBooleans(OutputStream out, ArrayList<Boolean> ar) throws IOException {
	    for (int i = 0; i < ar.size(); i += 8) {
	        int b = 0;
	        for (int j = Math.min(i + 7, ar.size() - 1); j >= i; j--) {
	            b = (b << 1) | (ar.get(i) ? 1 : 0);
	        }
	        out.write(b);
	    }
	}
	
	public static Scanner getFileHandle(String fileName){
		
		Scanner in = null;
		try{
			in = new Scanner(new FileInputStream(new File(fileName)));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return in;
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
