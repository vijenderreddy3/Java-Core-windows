package Poly;

import java.io.*;

public class Poly {

	public static void main(String[] args) {

		String str[] = new String[50];       //To read all the lines or values of input file into string array
		int degree1 = 0;
		int degree2 = 0;
		int f = 0;


		String line = null;

		try {
//Opening the Input file
			File fname = new File(System.getProperty("user.dir")+"/input.txt");
			FileReader fread = new FileReader(fname);

			BufferedReader br = new BufferedReader(fread);
//Reading the input file using buffered reader
			while ((line = br.readLine()) != null) {

				str[f] = line;
//				System.out.println(str[f]);
				f++;

			}
			//Creating the output file to write the result
			File file = new File("output.txt");

			
			FileWriter fw = new FileWriter(file);
			
			BufferedWriter bw = new BufferedWriter(fw);
			degree1 = Integer.parseInt(str[0]);          //Degree of first polynomial
			degree2 = Integer.parseInt(str[2]);			//Degree of second polynomial

			//br.close();
			String poly1[] = null;
			if (degree1 >= degree2) {
				poly1 = new String[degree1 + 1];
			} else {
				poly1 = new String[degree2 + 1];
			}
			String temp1[] = str[1].split(" ");
			int polysize1 = temp1.length;            // finding the length of the array polynomial 1
			for (int i = 0; i < temp1.length; i++) {
				poly1[i] = temp1[polysize1 - 1];
				--polysize1;
			}

			if (temp1.length != poly1.length) {
				for (int i = temp1.length; i < poly1.length; i++) {

					poly1[i] = "0";

				}
			}

			

			String poly2[] = null;
			if (degree1 >= degree2) {
				poly2 = new String[degree1 + 1];
			} else {
				poly2 = new String[degree2 + 1];
			}

			String temp2[] = str[3].split(" ");
			int polysize2 = temp2.length;
			for (int i = 0; i < temp2.length; i++) {
				poly2[i] = temp2[polysize2 - 1];
				--polysize2;
			}

			if (temp2.length != poly2.length) {
				for (int i = temp2.length; i < poly2.length; i++) {

					poly2[i] = "0";

				}
			}


			int flag = 0;
			for (int i = poly2.length - 1; i >= 0; i--) {
				if (flag == 0
						&& Integer.parseInt(poly1[i]) + Integer.parseInt(poly2[i]) == 0) {
					continue;
				}
				flag = 1;
				
							
				if((Integer.parseInt(poly1[i]) + Integer.parseInt(poly2[i])!=0))//checks if the result is not zero
				{
				bw.write(Integer.parseInt(poly1[i]) + Integer.parseInt(poly2[i]) + " ");// addition logic that writes the addition of two polynomials
				}
				
			}
			bw.newLine();
			System.out.println();
			flag = 0;
			for (int i = poly2.length - 1; i >= 0; i--) {
				if (flag == 0
						&& Integer.valueOf(poly1[i]) - Integer.valueOf(poly2[i]) == 0) {
					continue;
				}
				flag = 1;
				if((Integer.valueOf(poly1[i]) - Integer.valueOf(poly2[i]) != 0))
						{
				
				bw.write(Integer.valueOf(poly1[i]) - Integer.valueOf(poly2[i]) + " ");//Substraction logic that writes the substraction of two polynomials
						}
				
			}
			bw.newLine();            //To write the contents of output file in a new line
			System.out.println();
			int prod[] = new int[poly1.length + poly2.length - 1];

			for (int i = 0; i < poly1.length + poly2.length - 1; i++)
				prod[i] = 0;

			for (int i = 0; i < poly1.length; i++) {

				for (int j = 0; j < poly2.length; j++)
					prod[i + j] += Integer.valueOf(poly1[i])//Multiplication of two polynomials logic
							* Integer.valueOf(poly2[j]);

			}

			int[] multresult = new int[prod.length];
			for (int i = 0; i < prod.length; i++) {
				multresult[i] = prod[prod.length - 1 - i];
			}

			flag = 0;
			for (int i = 0; i < multresult.length; i++) {
				if (flag == 0 && multresult[i] == 0) {
					continue;
				}
				flag = 1;

				bw.write(multresult[i] + " ");//writing multiplication result into file
			}

			bw.close();
			br.close();

		} catch (FileNotFoundException ex) {
			
			System.out.println("File has some problem in opening" +ex);
		} catch (IOException ex) {
			
			System.out.println("Error in reading the file"+ex);
		}
	}

}
