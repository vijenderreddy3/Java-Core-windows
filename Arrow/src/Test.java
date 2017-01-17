import java.io.*;
import java.util.*;
public class Test {

	public static void main(String[] args)throws Exception {
		BufferedReader br=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "list.txt"));
		int wordcount=0;
		String temp;
		LinkedList dictionary=new LinkedList();
		int hash;
		while(br.readLine()!=null){
			wordcount+=1;
		}
		BufferedReader br2=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "list.txt"));
		for(int i=0;i<wordcount;i++){
			temp=br2.readLine();
			hash=temp.hashCode();
			dictionary.add(hash,temp);
			System.out.println("No of words entered "+i);
		}
		//hash=name.hashCode()%1200;
		System.out.println("Linked List:"+dictionary.size());
	br.close();
	}
}
