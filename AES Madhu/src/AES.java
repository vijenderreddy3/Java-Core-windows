import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class AES {
	Object[][][] k1;
	Object[][][] k2;
	String[] round = { "01", "02", "04", "08", "10", "20", "40", "80", "1b", "36" };
	int[][] encryptMatrix = { { 1, 0, 0, 0, 1, 1, 1, 1 }, { 1, 1, 0, 0, 0, 1, 1, 1 }, { 1, 1, 1, 0, 0, 0, 1, 1 },
			{ 1, 1, 1, 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 0, 0, 0 }, { 0, 1, 1, 1, 1, 1, 0, 0 },
			{ 0, 0, 1, 1, 1, 1, 1, 0 }, { 0, 0, 0, 1, 1, 1, 1, 1 } };
	int[][] decryptionMatrix = { { 0, 0, 1, 0, 0, 1, 0, 1 }, { 1, 0, 0, 1, 0, 0, 1, 0 }, { 0, 1, 0, 0, 1, 0, 0, 1 },
			{ 1, 0, 1, 0, 0, 1, 0, 0 }, { 0, 1, 0, 1, 0, 0, 1, 0 }, { 0, 0, 1, 0, 1, 0, 0, 1 },
			{ 1, 0, 0, 1, 0, 1, 0, 0 }, { 0, 1, 0, 0, 1, 0, 1, 0 } };
	String[][] ShiftRowMatrix = { { "02", "03", "01", "01" }, { "01", "02", "03", "01" }, { "01", "01", "02", "03" },
			{ "03", "01", "01", "02" } };
	String[][] inverseShiftRowMatrix = { { "0e", "0b", "0d", "09" }, { "09", "0e", "0b", "0d" }, { "0d", "09", "0e", "0b" },
			{ "0b", "0d", "09", "0e" } };
	int pr = 2;
	int[] mDegree = new int[9];
	static PrintWriter pw;
	static{
		try{
			pw = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
		}catch (IOException e) {
			System.out.println("error writing to the file: src");
		}
	}
	public static void main(String[] args) {
		AES obj = new AES();
		obj.input();
		pw.close();
	}

	public void input() {
		String[] fileInput = readFile();
		String s = fileInput[0].replaceAll("\\s", "");
		for (int i = 0; i < 9; i++)
			mDegree[i] = Integer.parseInt("" + s.charAt(i));
		roundCount();
		String keyString = fileInput[1];
		Object[][] keyState1 = convertToState(keyString);
		Object[][] keyState2 = keyState1;
		k1 = keyExpansion(keyState1);
		k2 = keyExpansion(keyState1);
		String plainString = fileInput[2];
		Object[][] plainState = convertToState(plainString);
		String cipherText = encryption(keyState1, plainState);
		String plaintext = decryption(fileInput[3], keyState2);
		output(cipherText);
		output(plaintext);
	}

	public String[] readFile() {
		String[] lines = new String[4];
		try {
			BufferedReader in = new BufferedReader(new FileReader("input.txt"));
			for (int i = 0; i < 4; i++)
				lines[i] = in.readLine();
		} catch (IOException e) {
			System.err.println("Could not read the input.txt file");
		}
		return lines;
	}

	public void roundCount() {
		int[] a = convertToBinary("02");
		round[0] = "01";
		for (int j = 1; j < 10; j++) {
			int[] Rc = multiply(a, convertToBinary(round[j - 1]), false);
			round[j] = convertToHex(Rc);
		}
	}

	public static Object[][] convertToState(String a) {
		Object[][] state = new Object[4][4];
		int count = 0;
		for (int i = 0; i < 32; i += 2) {
			String b = a.substring(i, i + 2);
			if (i < 8) {
				state[0][count] = b;
				if (count == 3)
					count = -1;
			}
			if (i < 16 && i > 7) {
				state[1][count] = b;
				if (count == 3)
					count = -1;
			}
			if (i < 24 && i > 15) {
				state[2][count] = b;
				if (count == 3)
					count = -1;
			}
			if (i < 32 && i > 23)
				state[3][count] = b;
			count++;
		}
		return state;
	}

	public Object[][][] keyExpansion(Object[][] a) {
		Object[][] prevKey = a;
		Object[][][] result = new Object[10][][];
		for (int roundNo = 0; roundNo < 10; roundNo++) {
			Object[][] currKey = new Object[4][];
			Object[] intermediate1 = prevKey[3];
			Object[] temp = intermediate1.clone();
			Object t = temp[0];
			for (int i = 0; i < 3; i++)
				temp[i] = temp[i + 1];
			temp[3] = t;
			for (int i = 0; i < 4; i++)
				temp[i] = subBytesElement(convertToBinary((String) temp[i])); // rotate
			int[] t0 = convertToBinary((String) temp[0]);
			int[] RC = convertToBinary(round[roundNo]);
			for (int i = 0; i < 8; i++)
				t0[i] = t0[i] ^ RC[i];
			temp[0] = convertToHex(t0);
			int[] res;
			int[] curtemp = convertToBinary((String) temp[0]);
			Object intermediate = prevKey[0][0];
			int[] pretemp = convertToBinary((String) intermediate);
			res = add(curtemp, pretemp, false);
			String re = convertToHex(res);
			for (int i = 0; i < 4; i++) {
				int[] currtemp = convertToBinary((String) temp[i]);
				int[] prevtemp = convertToBinary((String) prevKey[0][i]);
				for (int b = 0; b < 8; b++)
					currtemp[b] = currtemp[b] ^ prevtemp[b];
				temp[i] = convertToHex(currtemp);
			}
			currKey[0] = temp;
			Object[] resultcol1 = new Object[4];
			for (int i = 0; i < 4; i++)
				resultcol1[i] = convertToHex(
						add(convertToBinary((String) prevKey[1][i]), convertToBinary((String) currKey[0][i]), false)); 
			currKey[1] = resultcol1;
			Object[] resultcol2 = new Object[4];
			for (int i = 0; i < 4; i++)
				resultcol2[i] = convertToHex(
						add(convertToBinary((String) prevKey[2][i]), convertToBinary((String) currKey[1][i]), false)); 
			currKey[2] = resultcol2;
			Object[] resultcol3 = new Object[4];
			for (int i = 0; i < 4; i++)
				resultcol3[i] = convertToHex(
						add(convertToBinary((String) prevKey[3][i]), convertToBinary((String) currKey[2][i]), false)); 
			currKey[3] = resultcol3;

			result[roundNo] = currKey;
			prevKey = currKey;
		}
		return result;
	}

	public String encryption(Object[][] key, Object[][] inputState) {
		Object[][] ActState = addRoundKey(key, inputState);
		for (int i = 0; i < 9; i++) {
			ActState = subBytes(ActState);
			ActState = shiftRows(ActState);
			ActState = mixedColumns(ActState);
			ActState = addRoundKey(ActState, k1[i]);
		}
		ActState = subBytes(ActState);
		ActState = shiftRows(ActState);
		ActState = addRoundKey(ActState, k1[9]);
		ActState = correctZeros(ActState);
		String s = convertStateToString(ActState);
		return s;
	}

	public String decryption(String ciphert, Object[][] keyD) {
		Object[][] ActState = convertToState(ciphert);
		ActState = addRoundKey(k1[9], ActState);
		for (int i = 8; i >= 0; i--) {
			ActState = inverseShiftRows(ActState);
			ActState = inverseSubByte(ActState);
			ActState = addRoundKey(ActState, k2[i]);
			ActState = inverseMixedColumns(ActState);
		}
		ActState = inverseShiftRows(ActState);
		ActState = inverseSubByte(ActState);
		ActState = addRoundKey(ActState, keyD);
		ActState = correctZeros(ActState);
		String s = convertStateToString(ActState);
		return s;
	}

	public static int[] convertToBinary(String a) {
		int[] result = new int[8];
		String s = new BigInteger(a, 16).toString(2);
		char[] arr = s.toCharArray();
		int j = result.length - 1;
		for (int i = arr.length - 1; i >= 0; i--) {
			result[j] = Integer.parseInt("" + arr[i]);
			j--;
		}
		return result;
	}

	public static String convertToHex(int[] a) {
		String bin = "";
		for (int i = 0; i < a.length; i++)
			bin += "" + a[i];
		String result = Long.toHexString(Long.parseLong(bin, 2));
		return result;
	}

	public int[] multiply(int[] a, int[] b, boolean FromPLDA) {
		if (checkArr(a) || checkArr(b))
			return new int[] { 0 };
		else {
			int[] result = new int[(a.length + b.length) - 1];
			for (int i = 0; i < a.length; i++)
				for (int j = 0; j < b.length; j++)
					result[i + j] ^= a[i] * b[j];
			if (FromPLDA)
				return result;
			else
				return checkStartZeros(PLDA(result, mDegree)[1]);
		}
	}

	public Object[][] subBytes(Object[][] a) {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				int[] bin = convertToBinary((String) a[row][col]);
				a[row][col] = subBytesElement(bin);
			}
		}
		return a;
	}

	public String subBytesElement(int[] a) {
		int[] constColEncrypt = { 1, 1, 0, 0, 0, 1, 1, 0 };
		int[][] products = new int[8][8];
		int[] result = new int[8];
		a = EEAP(checkStartZeros(a), mDegree)[0];
		a = convert(a, 8);
		a = convertToCol(a);
		for (int i = 0; i < 8; i++) {
			int[] temp = new int[8];
			for (int j = 0; j < a.length; j++)
				temp[j] = encryptMatrix[i][j] * a[j];
			products[i] = temp;
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 1; j < 8; j++)
				products[i][j] = products[i][j] ^ products[i][j - 1];
			result[i] = products[i][7] ^ constColEncrypt[i];
		}

		String hexAnswer = convertToHex(convertToCol(result));
		return hexAnswer;
	}

	public Object[][] shiftRows(Object[][] a) {
		Object t = a[0][1];
		for (int i = 0; i < 3; i++)
			a[i][1] = a[i + 1][1];
		a[3][1] = t;
		for (int j = 0; j < 2; j++) {
			Object t1 = a[0][2];
			for (int i = 0; i < 3; i++)
				a[i][2] = a[i + 1][2];
			a[3][2] = t1;
		}
		for (int j = 0; j < 3; j++) {
			Object t1 = a[0][3];
			for (int i = 0; i < 3; i++)
				a[i][3] = a[i + 1][3];
			a[3][3] = t1;
		}
		return a;
	}

	public Object[][] mixedColumns(Object[][] a) {
		Object[][] result = new Object[4][4];
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				int[][] constantRow = new int[4][];
				int[][] bites = new int[4][];
				int[][] products = new int[4][];
				int[] sum = new int[8];
				for (int i = 0; i < 4; i++) {
					constantRow[i] = convertToBinary((String) ShiftRowMatrix[k][i]);
					bites[i] = (convertToBinary((String) a[j][i]));
					products[i] = convert(multiply(bites[i], constantRow[i], false), 8);
					sum = convert(add(products[i], sum, false), 8);
				}
				String s = convertToHex(sum);
				result[j][k] = s;
			}
		}
		return result;
	}

	public Object[][] addRoundKey(Object[][] a, Object[][] b) {
		Object[][] result = new Object[4][4];
		for (int col = 0; col < 4; col++) {
			for (int i = 0; i < 4; i++) {
				int[] x = convert(convertToBinary((String) a[col][i]), 8);
				int[] y = convert(convertToBinary((String) b[col][i]), 8);
				int[] sum = convert(add(x, y, false), 8);
				String s = convertToHex(sum);
				result[col][i] = s;
			}
		}
		return result;
	}

	public Object[][] inverseSubByte(Object[][] a) {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				a[row][col] = invSubBytesElement(convertToBinary((String) a[row][col]));
			}
		}
		a = correctZeros(a);
		return a;
	}

	public String invSubBytesElement(int[] a) {
		int[] DecrCCOL = { 1, 0, 1, 0, 0, 0, 0, 0 };
		int[][] products = new int[8][8];
		int[] result = new int[8];
		
		a = convertToCol(a);
		for (int i = 0; i < 8; i++) {
			int[] temp = new int[8];
			for (int j = 0; j < a.length; j++)
				temp[j] = decryptionMatrix[i][j] * a[j];
			products[i] = temp;
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 1; j < 8; j++)
				products[i][j] = products[i][j] ^ products[i][j - 1];
			result[i] = products[i][7] ^ DecrCCOL[i];
		}
		result = convertToCol(result);
		result = EEAP(checkStartZeros(result), mDegree)[0];
		result = convert(result, 8);
		String hexAnswer = convertToHex(result);
		return hexAnswer;
	}

	public Object[][] inverseShiftRows(Object[][] a) {
		Object t = a[3][1];
		for (int i = 3; i > 0; i--)
			a[i][1] = a[i - 1][1];
		a[0][1] = t;
		for (int j = 0; j < 2; j++) {
			Object t1 = a[3][2];
			for (int i = 3; i > 0; i--)
				a[i][2] = a[i - 1][2];
			a[0][2] = t1;
		}
		for (int j = 0; j < 3; j++) {
			Object t1 = a[3][3];
			for (int i = 3; i > 0; i--)
				a[i][3] = a[i - 1][3];
			a[0][3] = t1;
		}
		return a;
	}

	public Object[][] inverseMixedColumns(Object[][] a) {
		Object[][] result = new Object[4][4];
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				int[][] constantRow = new int[4][];
				int[][] bites = new int[4][];
				int[][] products = new int[4][];
				int[] sum = new int[8];
				for (int i = 0; i < 4; i++) {
					constantRow[i] = convertToBinary((String) inverseShiftRowMatrix[k][i]);
					bites[i] = (convertToBinary((String) a[j][i])); //
					products[i] = convert(multiply(bites[i], constantRow[i], false), 8);
					sum = convert(add(products[i], sum, false), 8);
				}
				String s = convertToHex(sum);
				result[j][k] = s;
			}
		}
		result = correctZeros(result);
		return result;
	}

	public int[] convert(int[] a, int b) {
		int[] temp = new int[b];
		int j = temp.length - 1;
		for (int i = a.length - 1; i >= 0; i--) {
			temp[j] = a[i];
			j--;
		}
		return temp;
	}

	public int[][] EEAP(int[] a, int[] b) { 
		int[][] UthenV = new int[2][];
		a = mod(a);
		b = mod(b);
		if (checkArr(b)) {
			a = checkStartZeros(a);
			int[] q = { ((((EEA(a[0], pr)[0]) % pr) + pr) % pr) };
			int[] z = { 0 };
			UthenV[0] = q;
			UthenV[1] = z;
			return UthenV;
		} else {
			int[][] Q = PLDA(a, b);
			int[] q = Q[0];
			int[] r = Q[1];
			int[][] R = EEAP(b, r);
			int[] firstp = mod(R[1]);
			int[] mu = multiply(q, R[1], false);
			int[] sub = subtract(R[0], mu, false);
			int[] secondp = mod(sub);
			R[0] = firstp;
			R[1] = secondp;
			return (R);
		}
	}

	public int[] EEA(int a, int b) { 
		if (b == 0) {
			return new int[] { 1, 0 };
		} else {
			int q = a / b;
			int r = a % b;
			int[] R = EEA(b, r);
			return new int[] { R[1], R[0] - q * R[1] };
		}
	}

	public int[] add(int[] a, int[] b, boolean fromPLDA) {
		int[] result = new int[Math.max(a.length, b.length)];
		if (a.length > b.length) {
			result = a.clone();
			int j = result.length - 1;
			for (int i = 0; i < b.length; i++) {
				result[j] ^= b[b.length - 1 - i];
				j--;
			}
		} else {
			result = b.clone();
			int j = result.length - 1;
			for (int i = 0; i < a.length; i++) {
				result[j] ^= a[a.length - 1 - i];
				j--;
			}
		}
		if (fromPLDA)
			return result;
		else
			return checkStartZeros(PLDA(result, mDegree)[1]);
	}

	public int[] checkStartZeros(int[] array) {
		if (!checkArr(array)) {
			int noZeros = 0;
			for (int i = 0; i < array.length; i++) {
				if (array[i] == 0)
					noZeros++;
				else
					break;
			}
			int newArrayLength = array.length - noZeros;
			int[] newA = new int[newArrayLength];
			for (int i = 0; i < newArrayLength; i++) {
				newA[i] = array[i + noZeros];
			}
			return newA;
		} else {
			int[] arrayZero = { 0 };
			return arrayZero;
		}
	}

	public int[] mod(int[] a) {
		for (int i = 0; i < a.length; i++) {
			a[i] = a[i] % pr;
			while (a[i] < 0) {
				a[i] += pr;
			}
		}
		return a;
	}

	public boolean checkArr(int[] a) {
		boolean checkArr = true;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0) {
				checkArr = false;
				break;
			}
		}
		return checkArr;
	}

	public int[][] PLDA(int[] n, int[] d) {
		n = mod(n);
		d = mod(d);
		int[] q = { 0 };
		int[] r = n;
		while (!checkArr(r) && (r.length - 1 >= d.length - 1)) {
			int tco = (((r[0] * (EEA(d[0], pr)[0]) % pr) + pr) % pr);
			int t_degree = (((r.length - 1) - (d.length - 1)) + 1);
			int[] t = new int[t_degree];
			t[0] = tco;
			int[] t_times_d = multiply(t, d, true);
			r = subtract(r, t_times_d, true);
			q = add(q, t, true);
			r = mod(r);
			r = checkStartZeros(r);
			q = mod(q);
			q = checkStartZeros(q);
		}
		int[][] QthenR = new int[2][];
		QthenR[0] = q;
		QthenR[1] = r;
		return QthenR;
	}

	public int[] subtract(int[] a, int[] b, boolean FromPLDA) {
		int[] result = new int[Math.max(a.length, b.length)];
		if (a.length > b.length) {
			result = a.clone();
			int j = result.length - 1;
			for (int i = 0; i < b.length; i++) {
				result[j] ^= b[b.length - 1 - i];
				j--;
			}
		} else {
			result = b.clone();
			int j = result.length - 1;
			for (int i = 0; i < a.length; i++) {
				result[j] ^= a[a.length - 1 - i];
				j--;
			}
		}
		if (FromPLDA)
			return checkStartZeros(result);
		else
			return checkStartZeros(PLDA(result, mDegree)[1]);
	}

	public static String convertStateToString(Object[][] a) {
		String s = "";
		for (int col = 0; col < 4; col++) {
			for (int row = 0; row < 4; row++)
				s += (String) a[col][row];
		}
		return s;
	}

	public static Object[][] correctZeros(Object[][] a) {
		for (int col = 0; col < 4; col++) {
			for (int row = 0; row < 4; row++) {
				String s = (String) a[col][row];
				if (s.length() == 1) {
					s = "0" + s;
					a[col][row] = s;
				}
			}
		}
		return a;
	}

	public static int[] convertToCol(int[] a) {

		int[] reversed = a.clone();
		int j = 0;
		for (int i = reversed.length - 1; i >= 0; i--) {
			reversed[i] = a[j];
			j++;
		}
		return reversed;
	}

	public void output(String data) {
		
			pw.println(data);
	}
}