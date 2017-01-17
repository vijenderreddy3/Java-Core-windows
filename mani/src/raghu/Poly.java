package raghu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Poly {

	public static void main(String[] args) throws Exception{
		File inputFile=new File(System.getProperty("user.dir")+"/input.txt");
		FileReader fReader=new FileReader(inputFile);
		BufferedReader bReader1=new BufferedReader(fReader);
		FileReader fReader2=new FileReader(inputFile);
		BufferedReader bReader2=new BufferedReader(fReader2);
		
		File outputFile=new File(System.getProperty("user.dir")+"/output.txt");
		PrintWriter pWriter=new PrintWriter(outputFile);		
		int noOfLines=0;
		while(bReader1.readLine()!=null){
			noOfLines++;
		}
		bReader1.close();
		String[] input=new String[noOfLines];
		for(int x=0;x<noOfLines;x++){
			input[x]=bReader2.readLine();
		}
		bReader2.close();
		int dOfPoly1=Integer.parseInt(input[0]);
		int dOfPoly2=Integer.parseInt(input[2]);
		String[] poly1S=input[1].split(" ");
		String[] poly2S=input[3].split(" ");
		int[] poly1=new int[dOfPoly1+1];
		int[] poly2=new int[dOfPoly2+1];
		int i=0;
		for(int x=(poly1S.length-1);x>=0;x--){
			poly1[i]=Integer.parseInt(poly1S[x]);
			i++;
		}
		i=0;
		for(int x=(poly2S.length-1);x>=0;x--){
			poly2[i]=Integer.parseInt(poly2S[x]);
			i++;
		}
		int[] addition;
		if(poly1.length>=poly2.length){
			addition=poly1;
			addition=new int[poly1.length];
			for(int x=(poly1.length-1);x>=0;x--){
				if(x>poly2.length-1){
					addition[x]=poly2[x];
				}
				else{
					addition[x]=poly1[x]+poly2[x];
				}
			}
		}
		else{
			addition=new int[poly2.length];
			for(int x=(poly2.length-1);x>=0;x--){
				if(x>poly1.length-1){
					addition[x]=poly2[x];
				}
				else{
					addition[x]=poly1[x]+poly2[x];
				}
			}
			
		}
		for(int x=(addition.length-1);x>=0;x--){
			if(addition[x]!=0){
				pWriter.write(Integer.toString(addition[x])+" ");
			}
			else if(addition[x]==0){
				for(int y=(addition.length-1);y>x;y--){
					if(addition[y]==0){
					}
					else if(addition[y]!=0){
						pWriter.write(Integer.toString(addition[x])+" ");
						break;
					}
				}
			}
		}
		int[] substraction;
		if(poly1.length>=poly2.length){
			substraction=new int[poly1.length];
			for(int x=(poly1.length-1);x>=0;x--){
				if(x>poly2.length-1){
					substraction[x]=-poly2[x];
				}
				else{
				substraction[x]=poly1[x]-poly2[x];
				}
			}
		}
		else{
			substraction=new int[poly2.length];
			for(int x=(poly2.length-1);x>=0;x--){
				if(x>poly1.length-1){
					substraction[x]=-poly2[x];
				}
				else{
				substraction[x]=poly1[x]-poly2[x];
				}
			}
		}
		pWriter.println();
		for(int x=(substraction.length-1);x>=0;x--){
			if(substraction[x]!=0){
				pWriter.write(Integer.toString(substraction[x])+" ");
			}
			else if(substraction[x]==0){
				for(int y=(substraction.length-1);y>x;y--){
					if(substraction[y]==0){
					}
					else if(substraction[y]!=0){
						pWriter.write(Integer.toString(substraction[x])+" ");
						break;
					}
				}
			}
		}
		int[] multiplication=new int[poly1.length+poly2.length-1];
		i=0;
		for (int x = (poly1.length - 1); x >= 0; x--) {
			int j = 0;
			for (int y = (poly2.length - 1); y >= 0; y--) {
				multiplication[i + j] =multiplication[i + j]+( poly1[x] * poly2[y]);
				j++;
			}
			i++;
		}
		pWriter.println();
		for(int x=0;x<multiplication.length;x++){
			if(multiplication[x]!=0){
				pWriter.write(Integer.toString(multiplication[x])+" ");
			}
			else if(multiplication[x]==0){
				for(int y=0;y<x;y++){
					if(multiplication[y]==0){
					}
					else if(multiplication[y]!=0){
						pWriter.write(Integer.toString(multiplication[x])+" ");
						break;
					}
				}
			}
		}
		pWriter.close();
	}
}
