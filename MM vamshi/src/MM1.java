import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
public class MM1 {
	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException {
		ArrayList<ArrayList<Integer>> matA = new ArrayList<ArrayList<Integer>>();
		int numOfColA = 0;
		int numOfRowA = 1;
		//this block is for reading matrixA and put it into a suitable format
		try {
			Scanner scA = new Scanner(new File(
					"matrixA.txt")); //this is the address where matrixA.txt is there.
			String firstLineA = scA.nextLine(); // this is for tokenizing a line with spaces and specify how many numbers are there in a line, which means number of columns
			StringTokenizer stA = new StringTokenizer(firstLineA, " ");
			ArrayList<Integer> firstNumbersA = new ArrayList<Integer>();
			while (stA.hasMoreTokens()) {
				firstNumbersA.add(Integer.parseInt(stA.nextToken()));
				numOfColA++;
			}
			matA.add(firstNumbersA);
			while (scA.hasNext()) {
				ArrayList<Integer> thisLine = new ArrayList<Integer>();
				for (int i = 0; i < numOfColA; i++) {
					thisLine.add(scA.nextInt());
				}
				numOfRowA++;
				matA.add(thisLine);
			}
		} catch (Exception e) {
		}
		int numOfColB = 0;
		int numOfRowB = 1;
		ArrayList<ArrayList<Integer>> matB = new ArrayList<ArrayList<Integer>>();
		//this block is for reading matrixB and put it into a suitable format
		try {
			Scanner scB = new Scanner(new File("matrixB.txt"));//this is the address where matrixB.txt is there.
			String firstLineB = scB.nextLine();
			StringTokenizer stB = new StringTokenizer(firstLineB, " ");
			ArrayList<Integer> firstNumbersB = new ArrayList<Integer>();
			while (stB.hasMoreTokens()) {
				firstNumbersB.add(Integer.parseInt(stB.nextToken()));
				numOfColB++;
			}
			matB.add(firstNumbersB);
			while (scB.hasNext()) {
				ArrayList<Integer> thisLineB = new ArrayList<Integer>();
				for (int i = 0; i < numOfColB; i++) {
					thisLineB.add(scB.nextInt());
				}
				numOfRowB++;
				matB.add(thisLineB);
			}
		} catch (Exception e) {
		}
		int[][] mA = new int[numOfRowA][numOfColA];
		int[][] mB = new int[numOfRowB][numOfColB];
		//putting the matrixes in int[][] makes us work easier
		for (int i = 0; i < numOfRowA; i++) {
			ArrayList<Integer> line = new ArrayList<Integer>();
			line = matA.get(i);
			for (int j = 0; j < numOfColA; j++) {
				mA[i][j] = line.get(j);
			}
		}
		for (int i = 0; i < numOfRowB; i++) {
			ArrayList<Integer> line = new ArrayList<Integer>();
			line = matB.get(i);
			for (int j = 0; j < numOfColB; j++) {
				mB[i][j] = line.get(j);
			}
		}
		PrintWriter writer = new PrintWriter("matrixAnswer.txt","UTF-8"); //this is for writing into a file named matrixAnswer.txt
		if (numOfColA == numOfRowB) {
			System.out.println("valid for multiplication");
			//this is how we calculate the multiplication and write it into the file as math algorithms
			for (int i = 0; i < numOfRowA; i++) {
				for (int j = 0; j < numOfColB; j++) {
					int result = 0;
					for(int k = 0 ; k < numOfColA; k++){
							result += mA[i][k] * mB[k][j];
					}
					writer.print(result + " ");
				}
				writer.println();
			}
			writer.close();
		} else {
			//we can't multiply these two matrixes
			System.out.println("unvalid matrixes");
		}
	}
}
