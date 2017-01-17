import java.io.*;
import java.util.*;

public class GF2 {
	static BufferedWriter writeFile;
	static BufferedWriter writeFile1;
	static int a, b, prime;
	static {
		try {
			writeFile = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" + "output.txt"));
			writeFile1 = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" + "output.txt", true));
		} catch (Exception e) {
			System.out.println("Output not printed to output.txt file");
		}
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

	public static int[][] EEAP(int a[], int b[]) {
		int i;
		int aLead = 0;
		int[][] Q = new int[2][];
		int[][] R = new int[2][];
		int[] copyA = new int[a.length];
		int[] copyB = new int[b.length];
		int[][] Temp2;
		int[] q;
		int[] r;
		int[] Temp;
		int[] rCopy;

		a = arrayMod(a);
		b = arrayMod(b);
		System.arraycopy(a, 0, copyA, 0, a.length);
		System.arraycopy(b, 0, copyB, 0, b.length);
		for (i = a.length - 1; i >= 0; i--)
			if (a[i] != 0)
				break;
		if (i >= 0)
			aLead = a[i];
		if (CheckArr(b) || b == null) {
			r = new int[2];
			r[0] = EEA(1, aLead);
			r[0] = r[0] % prime;
			while (r[0] < 0)
				r[0] += prime;
			r[1] = 0;
			R[0] = new int[r.length];
			System.arraycopy(r, 0, R[0], 0, r.length);
		} else {
			System.arraycopy(PLDA(copyA, copyB), 0, Q, 0, PLDA(copyA, copyB).length);
			r = new int[Q[0].length];
			System.arraycopy(Q[0], 0, r, 0, Q[0].length);
			rCopy = new int[r.length];
			System.arraycopy(r, 0, rCopy, 0, r.length);
			Temp2 = EEAP(copyB, rCopy);
			System.arraycopy(Temp2, 0, R, 0, Temp2.length);
			if (Q[1] != null) {
				q = new int[Q[1].length];
				System.arraycopy(Q[1], 0, q, 0, Q[1].length);
				if (R[1] != null && R[0] != null) {
					Temp = new int[q.length + R[1].length - 1];
					for (i = 0; i < q.length; i++)
						for (int j = 0; j < R[1].length; j++)
							Temp[i + j] = q[i] * R[1][j] + Temp[i + j];
					if (R[0].length > Temp.length) {
						i = R[0].length - 1;
						while (i >= Temp.length && i >= 0) {
							R[0][i] = R[0][i--];
						}
						while (i >= 0)
							R[0][i] = R[0][i] - Temp[i--];
						Temp = new int[R[0].length];
						System.arraycopy(R[0], 0, Temp, 0, R[0].length);
					} else {
						i = Temp.length - 1;
						while (i >= R[0].length && i >= 0)
							Temp[i] = -Temp[i--];
						while (i >= 0)
							Temp[i] = R[0][i] - Temp[i--];
					}
					R[0] = new int[R[1].length];
					System.arraycopy(R[1], 0, R[0], 0, R[1].length);
					R[1] = new int[Temp.length];
					System.arraycopy(Temp, 0, R[1], 0, Temp.length);
					R[1] = arrayMod(R[1]);
					R[0] = arrayMod(R[0]);
				} else if (R[1] == null && R[0] != null) {
					R[1] = new int[R[0].length];
					System.arraycopy(R[0], 0, R[1], 0, R[0].length);
					R[1] = arrayMod(R[1]);
					R[0] = null;
				} else if (R[1] != null && R[0] == null) {
					Temp = new int[R[1].length + q.length - 1];
					R[0] = new int[R[1].length];
					System.arraycopy(R[1], 0, R[0], 0, R[1].length);
					for (i = 0; i < R[1].length; i++)
						for (int j = 0; j < q.length; j++)
							Temp[i + j] = -R[1][i] * q[j] + Temp[i + j];
					R[1] = new int[Temp.length];
					System.arraycopy(Temp, 0, R[1], 0, Temp.length);
					R[1] = arrayMod(R[1]);
					R[0] = arrayMod(R[0]);
				}
			} else {
				if (R[0] != null && R[1] != null) {
					Temp = new int[R[0].length];
					System.arraycopy(R[0], 0, Temp, 0, R[0].length);
					R[0] = new int[R[1].length];
					System.arraycopy(R[1], 0, R[0], 0, R[1].length);
					R[1] = new int[Temp.length];
					System.arraycopy(Temp, 0, R[1], 0, Temp.length);
				} else if (R[0] != null && R[1] == null) {
					Temp = new int[R[0].length];
					System.arraycopy(R[0], 0, Temp, 0, R[0].length);
					R[0] = null;
					R[1] = new int[Temp.length];
					System.arraycopy(Temp, 0, R[1], 0, Temp.length);
				} else if (R[0] == null && R[1] != null) {
					Temp = new int[R[1].length];
					System.arraycopy(R[1], 0, Temp, 0, R[1].length);
					R[0] = new int[Temp.length];
					System.arraycopy(Temp, 0, R[0], 0, Temp.length);
					R[1] = null;
					R[0] = arrayMod(R[0]);
				}
			}
		}
		return R;
	}

	public static int[][] PLDA(int[] r, int b[]) {
		r = arrayMod(r);
		b = arrayMod(b);
		int rMax = -1;
		int rLead;
		int mLead;
		int[][] Result = new int[2][];
		for (int i = r.length - 1; i >= 0; i--) {
			if (r[i] != 0) {
				rLead = r[i];
				rMax = i;
				break;
			}
		}
		int rCnt = rMax;
		int mMax = -1;
		for (int i = b.length - 1; i >= 0; i--) {
			if (b[i] != 0) {
				mLead = b[i];
				mMax = i;
				break;
			}
		}
		if (rMax < mMax) {
			Result[0] = r;
			return Result;
		}
		rLead = r[rMax];
		mLead = b[mMax];
		int[] midCal = new int[rMax + 1];
		int[] m = new int[rMax + 1];
		int[] q = new int[rMax - mMax + 1];
		int t;
		for (int i = mMax; i >= 0; i--) {
			m[i] = b[i];
		}
		for (int i = rMax - mMax; i >= 0; i--) {
			q[i] = 0;
		}
		while (!CheckArr(r) && rMax >= mMax) {
			t = EEA(rLead, mLead);
			t = t % prime;
			while (t < 0)
				t += prime;
			q[rMax - mMax] = t;
			for (int i = 0; i <= rCnt; i++)
				midCal[i] = 0;
			for (int i = rMax; i >= 0; i--)
				midCal[i] = m[i] * t;
			int j = rMax;
			while (midCal[j--] == 0)
				;
			j++;
			int k = j;
			int c = rMax;
			for (int i = 0; i <= k; i++) {
				midCal[c--] = midCal[j--];
			}
			for (j = 0; j < rMax - k; j++)
				midCal[j] = 0;
			for (int i = rMax; i >= 0; i--)
				r[i] = r[i] - midCal[i];
			r = arrayMod(r);
			for (int i = rMax; i >= 0; i--)
				if (r[i] != 0) {
					rLead = r[i];
					rMax = i;
					break;
				}
		}
		Result[0] = arrayMod(r);
		Result[1] = arrayMod(q);
		return Result;
	}

	public static int EEA(int a, int b) {
		int s = 0;
		int t = 1;
		int old_s = 1;
		int old_t = 0;
		int r = prime;
		int old_r = b;
		int temp;
		int q;
		if (b == 0) {
			return 0;
		}
		while (r != 0) {
			q = old_r / r;
			temp = r;
			r = old_r - q * r;
			old_r = temp;
			temp = s;
			s = old_s - q * s;
			old_s = temp;
			temp = t;
			t = old_t - q * t;
			old_t = t;
		}
		old_s = old_s * a;
		return old_s;
	}

	public static int mod(int c) {
		if (c >= prime) {
			b = c % prime;
		} else if (c < prime && c >= 0) {
			b = c;
		} else if (c < 0) {
			b = c;
			while (b < 0) {
				b += prime;
			}
		}
		return b;
	}

	public static boolean CheckArr(int[] arr) {
		int i = 0;
		for (i = 0; i < arr.length; i++) {
			if (arr[i] != 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean writeToFile(int[] resultArray) {
		boolean check = false;
		try {
			for (int i = (resultArray.length - 1); i >= 0; i--) {
				if (resultArray[i] != 0) {
					writeFile.write(Integer.toString(resultArray[i]));
					writeFile.write(" ");
				} else if (resultArray[i] == 0) {
					for (int m = (resultArray.length - 1); m > i; m--) {
						if (resultArray[m] == 0) {
						} else if (resultArray[m] != 0) {
							writeFile.write(Integer.toString(resultArray[i]));
							writeFile.write(" ");
							break;
						}
					}
				}
			}
			check = true;
		} catch (IOException e) {
			System.out.println("Writing to output file has failed.");
		}
		return check;
	}

	public static boolean writeToFile2(int[] resultArray) {
		boolean check = false;
		try {
			for (int i = (resultArray.length - 1); i >= 0; i--) {
				if (resultArray[i] != 0) {
					writeFile1.write(Integer.toString(resultArray[i]));
					writeFile1.write(" ");
				} else if (resultArray[i] == 0) {
					for (int m = (resultArray.length - 1); m > i; m--) {
						if (resultArray[m] == 0) {
						} else if (resultArray[m] != 0) {
							writeFile1.write(Integer.toString(resultArray[i]));
							writeFile1.write(" ");
							break;
						}
					}
				}
			}
			check = true;
		} catch (IOException e) {
			System.out.println("Writing to output file has failed.");
		}
		return check;
	}

	public static void main(String[] args) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "input.txt"));
			BufferedReader br1 = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "input.txt"));
			int numberOfLines = 0;
			while ((br.readLine()) != null) {
				numberOfLines += 1;
			}
			String[] input = new String[numberOfLines];
			for (int i = 0; i < input.length; i++) {
				input[i] = br1.readLine();
			}
			br.close();
			br1.close();
			prime = Integer.parseInt(input[0]);
			int mDegree = Integer.parseInt(input[1]);
			String x1[] = input[2].split(" ");
			String mx[] = new String[mDegree + 1];
			for (String value : mx) {
				value = "0";
			}
			if (mDegree >= x1.length) {
				int p1 = mDegree;
				for (int i = (x1.length - 1); i >= 0; i--) {
					mx[p1] = x1[i];
					p1 = p1 - 1;
				}
			} else if (mDegree == (x1.length - 1)) {
				for (int i = (x1.length - 1); i >= 0; i--) {
					mx[i] = x1[i];
				}
			} else if (mDegree < (x1.length - 1)) {
				writeFile.write("Please check the degree values of polynominals");
				writeFile.close();
			}
			int mxint[] = new int[mx.length];
			for (int i = 0; i <= (mxint.length - 1); i++) {
				mxint[i] = 0;
				mxint[i] = Integer.parseInt(mx[(mx.length - 1) - i]);
			}
			int fDegree = Integer.parseInt(input[3]);
			String u[] = input[4].split(" ");
			String fx[] = new String[fDegree + 1];
			for (String value : fx) {
				value = "0";
			}
			if (fDegree >= u.length) {
				int fDegree2 = fDegree;
				for (int i = (u.length - 1); i >= 0; i--) {
					fx[fDegree2] = u[i];
					fDegree2 = fDegree2 - 1;
				}
			} else if (fDegree == (u.length - 1)) {
				for (int i = (u.length - 1); i >= 0; i--) {
					fx[i] = u[i];
				}
			} else if (fDegree < (u.length - 1)) {
				writeFile.write("Please check the degree values of polynominals");
			}
			int fxint[] = new int[fx.length];
			for (int i = 0; i < fxint.length; i++) {
				fxint[i] = Integer.parseInt(fx[(fx.length - 1) - i]);
			}
			int gDegree = Integer.parseInt(input[3]);
			u = input[6].split(" ");
			String gx[] = new String[gDegree + 1];
			for (int i = 0; i < gx.length; i++) {
				gx[i] = "0";
			}
			if (gDegree >= u.length) {
				int gDegree2 = gDegree;
				for (int i = (u.length - 1); i >= 0; i--) {
					gx[gDegree2] = u[i];
					gDegree2 = gDegree2 - 1;
				}
			} else if (gDegree == (u.length - 1)) {
				for (int i = (u.length - 1); i >= 0; i--) {
					gx[i] = u[i];
				}
			} else if (gDegree < (u.length - 1)) {
				writeFile.write("Degree values of polynominal is not correct");
			}
			int gxint[] = new int[gx.length];
			for (int i = 0; i < gxint.length; i++) {
				gxint[i] = Integer.parseInt(gx[(gx.length - 1) - i]);
			}
			/* ADDITION */
			int x = fxint.length, y = gxint.length;
			int additionResult[];
			if (x >= y) {
				additionResult = new int[fxint.length];
				int i, j, k = 0;
				for (i = 0; i <= x - 1; i++) {
					additionResult[i] = fxint[i];
					for (j = k; i == j && j <= y - 1; j++) {
						additionResult[i] = mod(fxint[i] + gxint[j]);
					}
					k += 1;
				}
				boolean writeCheck = writeToFile(additionResult);
				if (!writeCheck) {
					System.out.println("Failed to write addition result to output.txt file ");
				}
			} else {
				additionResult = new int[gxint.length];
				int i, j, k = 0;
				for (i = 0; i <= y - 1; i++) {
					additionResult[i] = gxint[i];
					for (j = k; i == j && j <= (x - 1); j++) {
						additionResult[i] = mod(fxint[i] + gxint[j]);
					}
					k += 1;
				}

				boolean writeCheck = writeToFile(additionResult);
				if (!writeCheck) {
					System.out.println("Failed to write addition result to output.txt file ");
				}
			}
			writeFile.newLine();
			writeFile.close();
			/* SUBSTRACTION */
			int substractionResult[];
			x = fxint.length;
			y = gxint.length;
			if (x >= y) {
				substractionResult = new int[fxint.length];
				int i, j, k = 0;
				for (i = 0; i <= x - 1; i++) {
					substractionResult[i] = fxint[i];
					for (j = k; i == j && j <= y - 1; j++) {
						substractionResult[i] = mod(fxint[i] - gxint[j]);

					}
					k += 1;
				}
				boolean writeCheck = writeToFile2(substractionResult);
				if (!writeCheck) {
					System.out.println("Failed to write substraction result to output.txt file ");
				}
			} else {
				substractionResult = new int[gxint.length];
				int i, j, k = 0;
				for (i = 0; i <= y - 1; i++) {
					substractionResult[i] = -gxint[i];
					for (j = k; i == j && j <= (x - 1); j++) {
						substractionResult[i] = mod(fxint[i] - gxint[j]);
					}
					k += 1;
				}
				boolean writeCheck = writeToFile2(substractionResult);
				if (!writeCheck) {
					System.out.println("Failed to write substraction result to output.txt file ");
				}
			}
			writeFile1.newLine();
			/* Multiplication */
			x = fxint.length;
			y = gxint.length;
			int[] multiplicationResult = new int[fxint.length + gxint.length - 1];
			for (int value : multiplicationResult) {
				value = 0;
			}
			int i, j;
			for (i = 0; i < x; i++) {
				for (j = 0; j < y; j++) {
					multiplicationResult[i + j] = mod(multiplicationResult[i + j] + (fxint[i] * gxint[j]));
				}
			}
			if ((multiplicationResult.length) >= mxint.length) {
				multiplicationResult = PLDA(multiplicationResult, mxint)[0];
				multiplicationResult = arrayMod(multiplicationResult);
				boolean writeCheck = writeToFile2(multiplicationResult);
				if (!writeCheck) {
					System.out.println("Failed to write multiplication result to output.txt file ");
				}
			} else {
				boolean writeCheck = writeToFile2(multiplicationResult);
				if (!writeCheck) {
					System.out.println("Failed to write multiplication result to output.txt file ");
				}
			}
			writeFile1.newLine();
			/* division */
			int[] Result;
			if (CheckArr(gxint)) {
				Result = new int[2];
				Result[0] = 0;
				Result[1] = 0;
			}
			int[] mxic = new int[mxint.length];
			System.arraycopy(mxint, 0, mxic, 0, mxint.length);
			int[] fxiCoeff1 = new int[fxint.length];
			System.arraycopy(fxint, 0, fxiCoeff1, 0, fxint.length);
			int[] gxiCoeff1 = new int[gxint.length];
			System.arraycopy(gxint, 0, gxiCoeff1, 0, gxint.length);
			int[] temp;
			if (fxint.length >= gxint.length) {
				temp = EEAP(gxiCoeff1, mxic)[0];
				gxint = new int[temp.length];
				gxint = temp;
			} else {
				temp = EEAP(fxiCoeff1, mxic)[0];
				fxint = new int[temp.length];
				fxint = temp;
			}
			Result = new int[fxint.length + gxint.length - 1];
			for (i = 0; i < gxint.length; i++)
				for (j = 0; j < fxint.length; j++)
					Result[i + j] = fxint[j] * gxint[i] + Result[i + j];
			int[] divResult = PLDA(Result, mxic)[0];
			boolean writeCheck = writeToFile2(divResult);
			if (!writeCheck) {
				System.out.println("Failed to write division result to output.txt file ");
			}
			writeFile1.close();
		} catch (IOException e) {
			System.out.println("Error while reading input file");
		}
	}
}
