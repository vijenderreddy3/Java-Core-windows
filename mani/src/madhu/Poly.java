package madhu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Poly {

	public static void main(String[] args) {
		String[] input = new String[4];
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
		int poly1Degree = Integer.parseInt(input[0]);
		int poly2Degree = Integer.parseInt(input[2]);
		String[] poly1String = input[1].split(" ");
		String[] poly2String = input[3].split(" ");
		int[] poly1 = new int[poly1Degree + 1];
		int[] poly2 = new int[poly2Degree + 1];
		int j = 0;
		for (int i = (poly1String.length - 1); i >= 0; i--) {
			poly1[j] = Integer.parseInt(poly1String[i]);
			j++;
		}
		j = 0;
		for (int i = (poly2String.length - 1); i >= 0; i--) {
			poly2[j] = Integer.parseInt(poly2String[i]);
			j++;
		}
		int[] add = addition(poly1, poly2);
		int[] sub = substraction(poly1, poly2);
		int[] mul = multiplication(poly1, poly2);
		try {
			BufferedWriter writeFile = new BufferedWriter(
					new FileWriter(new File(System.getProperty("user.dir") + "/output.txt")));
			writeFile.write(writeToFile(add));
			writeFile.newLine();
			writeFile.write(writeToFile(sub));
			writeFile.newLine();
			writeFile.write(writeToFile(mul));
			writeFile.newLine();
			writeFile.close();
		} catch (IOException ex1) {
			System.out.println("Output is not printed to output.txt file");
		}
	}

	public static int[] addition(int[] p1, int[] p2) {
		int[] add;
		int x = 0;
		if (p1.length >= p2.length) {
			add = new int[p1.length];
			for (int i = (p1.length - 1); i >= 0; i--) {
				
				if (i > (p2.length - 1)) {
					add[x] = p1[i];
					x++;
				} else {
					add[x] = p1[i] + p2[i];
					x++;
				}
			}
		} else {
			add = new int[p2.length];
			for (int i = (p2.length - 1); i >= 0; i--) {
				if (i > (p1.length - 1)) {
					add[x] = p2[i];
					x++;
				} else {
					add[x] = p1[i] + p2[i];
					x++;
				}
			}
		}
		return add;
	}

	public static int[] substraction(int[] p1, int[] p2) {
		int[] sub;
		int x = 0;
		if (p1.length >= p2.length) {
			sub = new int[p1.length];
			for (int i = (p1.length - 1); i >= 0; i--) {
				if (i > (p2.length - 1)) {
					sub[x] = p1[i];
					x++;
				} else {
					sub[x] = p1[i] - p2[i];
					x++;
				}
			}
		} else {
			sub = new int[p2.length];
			for (int i = (p2.length - 1); i >= 0; i--) {
				if (i > (p1.length - 1)) {
					sub[x] = -p2[i];
					x++;
				} else {
					sub[x] = p1[i] - p2[i];
					x++;
				}
			}
		}
		return sub;
	}

	public static int[] multiplication(int[] p1, int[] p2) {
		int[] mul;
		int x = 0;
		mul = new int[p1.length + p2.length - 1];
		for (int i = (p1.length - 1); i >= 0; i--) {
			int y = 0;
			for (int j = (p2.length - 1); j >= 0; j--) {
				mul[x + y] += p1[i] * p2[j];
				y++;
			}
			x++;
		}
		return mul;
	}

	public static String writeToFile(int[] array1) {
		String s = "";
		int check = 0, count = 0;
		for (int i = 0; i < array1.length; i++) {
			if (check == 0 && (array1[i] != 0)) {
				if (i != (array1.length - 1)) {
					s += Integer.toString(array1[i]) + " ";
				} else {
					s += Integer.toString(array1[i]);
				}
				count++;
			} else if (check == 0 && (array1[i]) == 0 && count == 0) {
				check++;
				continue;
			} else if (check != 0 && (array1[i] == 0 && count != 0)) {
				for (int y = 0; y < i; y++) {
					if (array1[y] != 0 && i < (array1.length - 1)) {
						s += Integer.toString(array1[i]) + " ";
					} else if (array1[y] != 0 && i == (array1.length - 1)) {
						s += Integer.toString(array1[i]);
						i++;
					} else {
						continue;
					}
				}
			} else {
				if (i != (array1.length - 1)) {
					s += Integer.toString(array1[i]) + " ";
				} else {
					s += Integer.toString(array1[i]);
				}
			}
		}
		return s;
	}
}
