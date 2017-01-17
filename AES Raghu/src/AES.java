import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class AES {
	static int prime = 2;
	static int[] mPolyDegree;
	static BufferedReader reader;
	static PrintWriter writer;
	static {
		try {
			reader = new BufferedReader(new FileReader("input.txt"));
		} catch (IOException e) {
			System.out.println("Failed to read data from input.txt");
		}
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
		} catch (IOException e) {
			System.out.println("Failed to write to output.txt file");
		}
	}
	static Object[][][] key1;
	static Object[][][] key2;
	static String[] round;

	public static Object[][] addRoundKey(Object[][] a, Object[][] b) {
		Object[][] result = new Object[4][4];
		for (int col = 0; col < 4; col++) {
			for (int i = 0; i < 4; i++) {
				int[] x = convertTo8bits(toBinary((String) a[col][i]));
				int[] y = convertTo8bits(toBinary((String) b[col][i]));
				int[] sum = convertTo8bits(polyAddition(x, y, false));
				String s = toHex(sum);
				result[col][i] = s;
			}
		}
		return result;
	}

	public static boolean arrayCheckForAllZero(int[] a) {

		for (int i = 0; i < a.length; i++) {
			if (a[i] != 0) {
				return false;
			}
		}
		return true;
	}

	public static int[] arrayMod(int[] passArray) {
		if (passArray == null) {
			return null;
		}
		for (int i = 0; i < passArray.length; i++) {
			while (passArray[i] < 0) {
				passArray[i] += prime;
			}
		}
		for (int i = 0; i < passArray.length; i++) {
			if (passArray[i] >= prime) {
				passArray[i] = passArray[i] % prime;
			}
		}
		return passArray;
	}

	public static int[] checkStartZeros(int[] array) {
		if (!arrayCheckForAllZero(array)) {
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

	public static String convertStateToString(Object[][] a) {
		String s = "";
		for (int col = 0; col < 4; col++) {
			for (int row = 0; row < 4; row++)
				s += (String) a[col][row];
		}
		return s;
	}

	public static int[] convertTo8bits(int[] a) {
		int[] temp = new int[8];
		int j = temp.length - 1;
		for (int i = a.length - 1; i >= 0; i--) {
			temp[j] = a[i];
			j--;
		}
		return temp;
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

	public static int[] EEA(int a, int b) {
		if (b == 0) {
			return new int[] { 1, 0 };
		} else {
			int q = a / b;
			int r = a % b;
			int[] R = EEA(b, r);
			return new int[] { R[1], R[0] - q * R[1] };
		}
	}

	public static int[][] EEAP(int[] a, int[] b) {
		int[][] UthenV = new int[2][];
		a = arrayMod(a);
		b = arrayMod(b);
		if (arrayCheckForAllZero(b)) {
			a = checkStartZeros(a);
			int[] q = { ((((EEA(a[0], prime)[0]) % prime) + prime) % prime) };
			int[] z = { 0 };
			UthenV[0] = q;
			UthenV[1] = z;
			return UthenV;
		} else {
			int[][] Q = PLDA(a, b);
			int[] q = Q[0];
			int[] r = Q[1];
			int[][] R = EEAP(b, r);
			int[] firstp = arrayMod(R[1]);
			int[] mu = polyMultiplication(q, R[1], false);
			int[] sub = polySubtract(R[0], mu, false);
			int[] secondp = arrayMod(sub);
			R[0] = firstp;
			R[1] = secondp;
			return (R);
		}
	}

	public static Object[][] inverseMixedColumns(Object[][] a) {
		Object[][] result = new Object[4][4];
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				String[][] inverseShiftRowMatrix = { { "0e", "0b", "0d", "09" }, { "09", "0e", "0b", "0d" },
						{ "0d", "09", "0e", "0b" }, { "0b", "0d", "09", "0e" } };
				int[][] constantRow = new int[4][];
				int[][] bites = new int[4][];
				int[][] products = new int[4][];
				int[] sum = new int[8];
				for (int i = 0; i < 4; i++) {
					constantRow[i] = toBinary((String) inverseShiftRowMatrix[k][i]);
					bites[i] = (toBinary((String) a[j][i])); //
					products[i] = convertTo8bits(polyMultiplication(bites[i], constantRow[i], false));
					sum = convertTo8bits(polyAddition(products[i], sum, false));
				}
				String s = toHex(sum);
				result[j][k] = s;
			}
		}
		result = correctZeros(result);
		return result;
	}

	public static Object[][] inverseShiftRows(Object[][] a) {
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

	public static Object[][] inverseSubByte(Object[][] a) {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				a[row][col] = invSubBytesElement(toBinary((String) a[row][col]));
			}
		}
		a = correctZeros(a);
		return a;
	}

	public static String invSubBytesElement(int[] a) {
		int[] DecrCCOL = { 1, 0, 1, 0, 0, 0, 0, 0 };
		int[][] products = new int[8][8];
		int[] result = new int[8];

		a = toColumnMatrix(a);
		int[][] inverseSubByteMatrix = { { 0, 0, 1, 0, 0, 1, 0, 1 }, { 1, 0, 0, 1, 0, 0, 1, 0 },
				{ 0, 1, 0, 0, 1, 0, 0, 1 }, { 1, 0, 1, 0, 0, 1, 0, 0 }, { 0, 1, 0, 1, 0, 0, 1, 0 },
				{ 0, 0, 1, 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 1, 0, 0 }, { 0, 1, 0, 0, 1, 0, 1, 0 } };
		for (int i = 0; i < 8; i++) {
			int[] temp = new int[8];
			for (int j = 0; j < a.length; j++)
				temp[j] = inverseSubByteMatrix[i][j] * a[j];
			products[i] = temp;
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 1; j < 8; j++)
				products[i][j] = products[i][j] ^ products[i][j - 1];
			result[i] = products[i][7] ^ DecrCCOL[i];
		}
		result = toColumnMatrix(result);
		result = EEAP(checkStartZeros(result), mPolyDegree)[0];
		result = convertTo8bits(result);
		String hexAnswer = toHex(result);
		return hexAnswer;
	}

	public static void main(String[] args) {
		String[] fileInput = new String[4];
		for (int i = 0; i < 4; i++)
			try {
				fileInput[i] = reader.readLine();
			} catch (IOException e) {
				System.out.println("Error while reading data from input.txt");
			}
		mPolyDegree = new int[9];
		String s = fileInput[0].replaceAll("\\s", "");
		for (int i = 0; i < 9; i++)
			mPolyDegree[i] = Integer.parseInt("" + s.charAt(i));
		round = new String[] { "01", "02", "04", "08", "10", "20", "40", "80", "1b", "36" };
		int[] a = toBinary("02");
		round[0] = "01";
		for (int j = 1; j < 10; j++) {
			int[] Rc = polyMultiplication(a, toBinary(round[j - 1]), false);
			round[j] = toHex(Rc);
		}
		String keyString = fileInput[1];
		Object[][] keyState1 = toStateMatrix(keyString);
		Object[][] keyState2 = keyState1;
		// key expansion
		Object[][] prevKey = keyState1;
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
				temp[i] = subBytesElement(toBinary((String) temp[i])); // rotate
			int[] t0 = toBinary((String) temp[0]);
			int[] RC = toBinary(round[roundNo]);
			for (int i = 0; i < 8; i++)
				t0[i] = t0[i] ^ RC[i];
			temp[0] = toHex(t0);
			for (int i = 0; i < 4; i++) {
				int[] currtemp = toBinary((String) temp[i]);
				int[] prevtemp = toBinary((String) prevKey[0][i]);
				for (int b = 0; b < 8; b++)
					currtemp[b] = currtemp[b] ^ prevtemp[b];
				temp[i] = toHex(currtemp);
			}
			currKey[0] = temp;
			Object[] resultcol1 = new Object[4];
			for (int i = 0; i < 4; i++)
				resultcol1[i] = toHex(
						polyAddition(toBinary((String) prevKey[1][i]), toBinary((String) currKey[0][i]), false));
			currKey[1] = resultcol1;
			Object[] resultcol2 = new Object[4];
			for (int i = 0; i < 4; i++)
				resultcol2[i] = toHex(
						polyAddition(toBinary((String) prevKey[2][i]), toBinary((String) currKey[1][i]), false));
			currKey[2] = resultcol2;
			Object[] resultcol3 = new Object[4];
			for (int i = 0; i < 4; i++)
				resultcol3[i] = toHex(
						polyAddition(toBinary((String) prevKey[3][i]), toBinary((String) currKey[2][i]), false));
			currKey[3] = resultcol3;

			result[roundNo] = currKey;
			prevKey = currKey;
		}
		// end of key expansion
		key1 = result;
		key2 = key1;
		String plainString = fileInput[2];
		String cipherString = fileInput[3];
		Object[][] plainState = toStateMatrix(plainString);
		// Encryption
		Object[][] ActStateEncry = addRoundKey(keyState1, plainState);
		for (int i = 0; i <= 9; i++) {
			ActStateEncry = subBytes(ActStateEncry);
			ActStateEncry = shiftRows(ActStateEncry);
			if (i != 9) {
				ActStateEncry = mixedColumns(ActStateEncry);
			}
			ActStateEncry = addRoundKey(ActStateEncry, key1[i]);
		}
		String cipherText = convertStateToString(correctZeros(ActStateEncry));

		// End of Encryption
		writer.println(cipherText);
		// Decryption
		Object[][] ActStateDecry = toStateMatrix(cipherString);
		ActStateDecry = addRoundKey(key1[9], ActStateDecry);
		for (int i = 8; i >= 0; i--) {
			ActStateDecry = inverseShiftRows(ActStateDecry);
			ActStateDecry = inverseSubByte(ActStateDecry);
			ActStateDecry = addRoundKey(ActStateDecry, key2[i]);
			ActStateDecry = inverseMixedColumns(ActStateDecry);
		}
		ActStateDecry = inverseShiftRows(ActStateDecry);
		ActStateDecry = inverseSubByte(ActStateDecry);
		ActStateDecry = addRoundKey(ActStateDecry, keyState2);
		ActStateDecry = correctZeros(ActStateDecry);
		String plaintext = convertStateToString(ActStateDecry);
		// End of Decryption
		writer.println(plaintext);
		writer.close();
	}

	public static Object[][] mixedColumns(Object[][] a) {
		Object[][] result = new Object[4][4];
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {

				String[][] shiftRowMatrix = { { "02", "03", "01", "01" }, { "01", "02", "03", "01" },
						{ "01", "01", "02", "03" }, { "03", "01", "01", "02" } };
				int[][] constantRow = new int[4][];
				int[][] bites = new int[4][];
				int[][] products = new int[4][];
				int[] sum = new int[8];
				for (int i = 0; i < 4; i++) {
					constantRow[i] = toBinary((String) shiftRowMatrix[k][i]);
					bites[i] = (toBinary((String) a[j][i]));
					products[i] = convertTo8bits(polyMultiplication(bites[i], constantRow[i], false));
					sum = convertTo8bits(polyAddition(products[i], sum, false));
				}
				String s = toHex(sum);
				result[j][k] = s;
			}
		}
		return result;
	}

	public static int[][] PLDA(int[] nPoly, int[] dPoly) {
		nPoly = arrayMod(nPoly);
		dPoly = arrayMod(dPoly);
		int[] r = nPoly;
		int[] q = { 0 };
		while ((r.length - 1 >= dPoly.length - 1) && !arrayCheckForAllZero(r)) {
			int temp_degree = (((r.length - 1) - (dPoly.length - 1)) + 1);
			int[] temp = new int[temp_degree];
			int tempCoeff = (((r[0] * (EEA(dPoly[0], prime)[0]) % prime) + prime) % prime);
			temp[0] = tempCoeff;
			q = polyAddition(q, temp, true);
			q = arrayMod(q);
			q = checkStartZeros(q);
			int[] temp_multiply_d = polyMultiplication(temp, dPoly, true);
			r = polySubtract(r, temp_multiply_d, true);
			r = arrayMod(r);
			r = checkStartZeros(r);
		}
		int[][] QthenR = new int[2][];
		QthenR[0] = q;
		QthenR[1] = r;
		return QthenR;
	}

	public static int[] polyAddition(int[] polyA, int[] polyB, boolean condition) {
		int[] result = new int[Math.max(polyA.length, polyB.length)];
		if (polyA.length > polyB.length) {
			result = polyA.clone();
			int j = result.length - 1;
			for (int i = 0; i < polyB.length; i++) {
				result[j] ^= polyB[polyB.length - 1 - i];
				j--;
			}
		} else {
			result = polyB.clone();
			int j = result.length - 1;
			for (int i = 0; i < polyA.length; i++) {
				result[j] ^= polyA[polyA.length - 1 - i];
				j--;
			}
		}
		if (condition)
			return result;
		else
			return checkStartZeros(PLDA(result, mPolyDegree)[1]);
	}

	public static int[] polyMultiplication(int[] a, int[] b, boolean FromPLDA) {
		if (arrayCheckForAllZero(a) || arrayCheckForAllZero(b))
			return new int[] { 0 };
		else {
			int[] result = new int[(a.length + b.length) - 1];
			for (int i = 0; i < a.length; i++)
				for (int j = 0; j < b.length; j++)
					result[i + j] ^= a[i] * b[j];
			if (FromPLDA)
				return result;
			else
				return checkStartZeros(PLDA(result, mPolyDegree)[1]);
		}
	}

	public static int[] polySubtract(int[] a, int[] b, boolean condition) {
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
		if (condition)
			return checkStartZeros(result);
		else
			return checkStartZeros(PLDA(result, mPolyDegree)[1]);
	}

	public static Object[][] shiftRows(Object[][] a) {
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

	public static Object[][] subBytes(Object[][] a) {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				int[] bin = toBinary((String) a[row][col]);
				a[row][col] = subBytesElement(bin);
			}
		}
		return a;
	}

	public static String subBytesElement(int[] a) {
		int[] constColEncrypt = { 1, 1, 0, 0, 0, 1, 1, 0 };
		int[][] products = new int[8][8];
		int[] result = new int[8];
		a = EEAP(checkStartZeros(a), mPolyDegree)[0];
		a = convertTo8bits(a);
		a = toColumnMatrix(a);
		int[][] subByteMatrix = { { 1, 0, 0, 0, 1, 1, 1, 1 }, { 1, 1, 0, 0, 0, 1, 1, 1 }, { 1, 1, 1, 0, 0, 0, 1, 1 },
				{ 1, 1, 1, 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 0, 0, 0 }, { 0, 1, 1, 1, 1, 1, 0, 0 },
				{ 0, 0, 1, 1, 1, 1, 1, 0 }, { 0, 0, 0, 1, 1, 1, 1, 1 } };
		for (int i = 0; i < 8; i++) {
			int[] temp = new int[8];
			for (int j = 0; j < a.length; j++)
				temp[j] = subByteMatrix[i][j] * a[j];
			products[i] = temp;
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 1; j < 8; j++)
				products[i][j] = products[i][j] ^ products[i][j - 1];
			result[i] = products[i][7] ^ constColEncrypt[i];
		}
		String hexAnswer = toHex(toColumnMatrix(result));
		return hexAnswer;
	}

	public static int[] toBinary(String a) {
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

	public static int[] toColumnMatrix(int[] a) {

		int[] reversed = a.clone();
		int j = 0;
		for (int i = reversed.length - 1; i >= 0; i--) {
			reversed[i] = a[j];
			j++;
		}
		return reversed;
	}

	public static String toHex(int[] a) {
		String bin = "";
		for (int i = 0; i < a.length; i++)
			bin += "" + a[i];
		String result = Long.toHexString(Long.parseLong(bin, 2));
		return result;
	}

	public static Object[][] toStateMatrix(String a) {
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

}