package ashwin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Poly {
	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader(new File(System.getProperty("user.dir") + "/input.txt"));
			BufferedReader br = new BufferedReader(fr);
			String s = null;
			s = br.readLine();
			int fDegree = Integer.parseInt(s);
			s = br.readLine();
			String[] coeff1 = s.split(" ");
			s = br.readLine();
			int gDegree = Integer.parseInt(s);
			s = br.readLine();
			String[] coeff2 = s.split(" ");
			String[] fCoeffs = new String[fDegree + 1];
			String[] gCoeffs = new String[gDegree + 1];
			if (gDegree + 1 == coeff2.length) {
				for (int i = coeff2.length - 1; i >= 0; i--) {
					gCoeffs[i] = coeff2[i];
				}
			}
			if (fDegree + 1 == coeff1.length) {
				for (int i = (coeff1.length - 1); i >= 0; i--) {
					fCoeffs[i] = coeff1[i];
				}
			}
			int x = fDegree, y = gDegree;
			if (fDegree + 1 > coeff1.length) {
				for (int i = coeff1.length - 1; i >= 0; i--) {
					if (i == 0) {
						for (int j = 0; j <= x; j++) {
							if (j < x) {
								fCoeffs[j] = "0";
							} else {
								fCoeffs[x] = coeff1[i];
							}
						}
					} else {
						fCoeffs[x] = coeff1[i];
						x--;
					}
				}
			}
			if (gDegree + 1 > coeff2.length) {
				for (int i = coeff2.length - 1; i >= 0; i--) {
					if (i == 0) {
						for (int j = 0; j <= y; j++) {
							if (j < y) {
								gCoeffs[j] = "0";
							} else {
								gCoeffs[y] = coeff2[i];
							}
						}
					} else {
						gCoeffs[y] = coeff2[i];
						y--;
					}
				}
			}
			String addResult = "", subtractResult = "", multiplyResult = "";
			int[] result = add(fDegree, fCoeffs, gDegree, gCoeffs);
			for (int k = 0; k < result.length; k++) {
				addResult = addResult + result[k] + " ";
			}
			result = subtract(fDegree, fCoeffs, gDegree, gCoeffs);
			for (int k = 0; k < result.length; k++) {
				subtractResult = subtractResult + result[k] + " ";
			}
			Object[] res = multiply(fDegree, fCoeffs, gDegree, gCoeffs);
			for (int k = 0; k < fDegree + gDegree + 1; k++) {
				multiplyResult = multiplyResult + res[k] + " ";
			}
			while (addResult.startsWith("0 "))
				addResult = addResult.substring(2);
			while (subtractResult.startsWith("0 "))
				subtractResult = subtractResult.substring(2);
			while (multiplyResult.startsWith("0 "))
				multiplyResult = multiplyResult.substring(2);
			FileWriter fw;
			try {
				fw = new FileWriter(new File(System.getProperty("user.dir") + "/output.txt"));
				fw.write(addResult);
				fw.write(System.lineSeparator()); // new line
				fw.write(subtractResult);
				fw.write(System.lineSeparator()); // new line
				fw.write(multiplyResult);
				fw.write(System.lineSeparator()); // new line
				fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static int[] add(int fDegree, String[] fCoeffs, int gDegree, String[] gCoeffs) {
		int resultDegree = fDegree > gDegree ? fDegree : gDegree;
		int result[] = new int[resultDegree + 1];
		if (fDegree >= gDegree) {
			for (int i = 0; i <= fDegree; i++) {
				String gCoeff = null;
				if (fDegree - gDegree > i) {
					gCoeff = "0";
				} else {
					gCoeff = gCoeffs[i - fDegree + gDegree];
				}
				result[i] = Integer.parseInt(fCoeffs[i]) + Integer.parseInt(gCoeff);
			}
		}
		else {
			for (int i = 0; i <= gDegree; i++) {
				String fCoeff = null;
				if (gDegree - fDegree > i) {
					fCoeff = "0";
				} else {
					fCoeff = fCoeffs[i - gDegree + fDegree];
				}
				result[i] = Integer.parseInt(fCoeff) + Integer.parseInt(gCoeffs[i]);
			}
		}
		return result;
	}
	private static int[] subtract(int fDegree, String[] fCoeffs, int gDegree, String[] gCoeffs) {
		int resultDegree = fDegree > gDegree ? fDegree : gDegree;
		int result[] = new int[resultDegree + 1];
		if (fDegree >= gDegree) {
			for (int i = 0; i <= fDegree; i++) {
				String gCoeff = null;
				if (fDegree - gDegree > i) {
					gCoeff = "0";
				} else {
					gCoeff = gCoeffs[i - fDegree + gDegree];
				}
				result[i] = Integer.parseInt(fCoeffs[i]) - Integer.parseInt(gCoeff);
			}
		} 
		else {
			for (int i = 0; i <= gDegree; i++) {
				String fCoeff = null;
				if (gDegree - fDegree > i) {
					fCoeff = "0";
				} else {
					fCoeff = fCoeffs[i - gDegree + fDegree];
				}
				result[i] = Integer.parseInt(fCoeff) - Integer.parseInt(gCoeffs[i]);
			}
		}
		return result;
	}
	private static Object[] multiply(int fDegree, String[] fCoeffs, int gDegree, String[] gCoeffs) {
		int resultDegree = fDegree + gDegree;
		List<String> list1 = Arrays.asList(fCoeffs);
		List<String> list2 = Arrays.asList(gCoeffs);
		ArrayList<Integer> array = new ArrayList<Integer>(list1.size() + list2.size() - 1);
		for (int i = 0; i < list1.size() + list2.size() - 1; i++)
			array.add(0);
		for (int i = 0; i < list1.size(); i++)
			for (int j = 0; j < list2.size(); j++)
				array.set(i + j,
						((Integer.parseInt(list1.get(i)) * Integer.parseInt(list2.get(j))) + array.get(i + j)));
		return array.toArray();
	}
}