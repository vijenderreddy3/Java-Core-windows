package madhu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GF1 {
	int x, y;

	public static void main(String[] args) {
		String[] input = new String[3];
		try {
			BufferedReader readFile = new BufferedReader(
					new FileReader(new File(System.getProperty("user.dir") + "/input.txt")));
			String nextLine = null;
			int i = 0;
			while ((nextLine = readFile.readLine()) != null) {
				input[i] = nextLine;
				i++;
			}
			readFile.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Input File not found in the following location:" + System.getProperty("/user.dir"));
		} catch (IOException ex) {
			System.out.println("Having problem with reading file");
		}
		int prime = Integer.parseInt(input[0]);
		int a = Integer.parseInt(input[1]);
		int b = Integer.parseInt(input[2]);
		GF1 obj = new GF1();
		int addition = obj.add(prime, a, b);
		int substraction = obj.sub(prime, a, b);
		int multiplication = obj.mul(prime, a, b);
		boolean bCheck = false;
		int division = 0;
		if (b == 0) {
			bCheck = true;
		} else {
			division = obj.div(prime, a, b);
		}
		try {
			BufferedWriter writeFile = new BufferedWriter(
					new FileWriter(new File(System.getProperty("user.dir") + "/output.txt")));
			writeFile.write(Integer.toString(addition));
			writeFile.newLine();
			writeFile.write(Integer.toString(substraction));
			writeFile.newLine();
			writeFile.write(Integer.toString(multiplication));
			writeFile.newLine();
			if (bCheck == true) {
				writeFile.write("Divide by Zero is not possible.");
			} else {
				writeFile.write(Integer.toString(division));
			}
			writeFile.close();
		} catch (IOException ex1) {
			System.out.println("Output is not printed to output.txt file");
		}
	}

	public int add(int p, int a, int b) {
		int addition = 0;
		addition = (a + b) % p;
		while (addition < 0) {
			addition = addition + p;
		}
		return addition;
	}

	public int sub(int p, int a, int b) {
		int substraction = 0;
		substraction = (a - b) % p;
		while (substraction < 0) {
			substraction = substraction + p;
		}
		return substraction;
	}

	public int mul(int p, int a, int b) {
		int multiplication;
		multiplication = (a * b) % p;
		while (multiplication < 0) {
			multiplication = multiplication + p;
		}
		return multiplication;
	}

	public int div(int p, int a, int b) {
		int division = 0;
		int mulInv = multiplicativeInverse(b, p);
		System.out.println(mulInv);
		division = (a * mulInv) % p;
		while ( division < 0 ){
			division = division + p;
		}
		return division;
	}

	public int multiplicativeInverse(int b, int prime) {
		if (prime != 0) {
			int q = b / prime;
			int r = b % prime;
			multiplicativeInverse(prime, r);
			y = (y - q * x);
			int temp;
			temp = x;
			x = y;
			y = temp;
			return y;
		} else {
			x = 0;
			y = 1;
			return 0;
		}
	}
}
