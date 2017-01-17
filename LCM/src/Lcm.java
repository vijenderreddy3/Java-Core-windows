import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Lcm {
	static int[] base = new int[2];

	static int[] setOfValues;
	static List<Integer> gcf = new ArrayList<Integer>();
	static List<Integer> lcm = new ArrayList<Integer>();

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the 2 numbers of an array:");
		base[0] = sc.nextInt();
		System.out.println("Enter next number :");
		base[1] = sc.nextInt();
		if (base[1] < base[0]) {
			System.out.println("Second number must be greater than first number");
			System.exit(0);
		}
		setOfValues = new int[base[1] - base[0] + 1];
		for (int i = 0; i < setOfValues.length; i++) {
			setOfValues[i] = base[0] + i;
		}

		int[] result = setOfValues;
		int[] tempgcf = new int[result.length];
		tempgcf = gcf(result);
		boolean check = false;
		for (int i = 0; i < result.length; i++) {
			if (result[i] != tempgcf[i]) {
				check = true;
				break;
			}
		}
		if (check) {
			result = tempgcf;
		}

		Iterator<Integer> ir = gcf.iterator();
		int gcfList = 1;
		while (ir.hasNext()) {
			gcfList *= ir.next();
		}
		System.out.println("GCF: " + gcfList);
		int[] templcm = new int[result.length];
		templcm = lcm(result);
		Iterator<Integer> ir2 = lcm.iterator();
		int lcmList = 1;
		while (ir2.hasNext()) {
			lcmList *= ir2.next();
		}
		int lcmArray = 1;
		for (int x : templcm) {
			lcmArray *= x;
		}
		System.out.println("LCM: " + gcfList * lcmList * lcmArray);
	}

	public static int[] gcf(int[] set) {
		int[] temp = new int[set.length];
		int[] result = set;
		if (set[0] != 1) {
			for (int j = 2; j <= set[0]; j++) {
				for (int i = 0; i < set.length; i++) {
					if ((set[i] % j) == 0) {
						temp[i] = set[i] / j;
					} else {
						for (int k = 0; k < temp.length; k++) {
							temp[k] = 0;
						}
						break;
					}
					if (i == (set.length - 1)) {
						gcf.add(j);
						result = temp;
						result = gcf(result);
						return result;
					}
				}
			}
			return result;
		} else {
			return result;
		}

	}

	public static int[] lcm(int[] set) {
		int[] temp = new int[set.length];
		int[] result = set;
		for (int j = 2; j <= Arrays.stream(result).max().getAsInt(); j++) {
			for (int i = 0; i < set.length; i++) {
				if ((set[i] % j) == 0) {
					temp[i] = set[i] / j;
				} else {
					temp[i] = set[i];
				}
				if (i == (set.length - 1)) {
					boolean check = false;
					for (int k = 0; k < temp.length; k++) {
						if (temp[k] != result[k]) {
							check = true;
							break;
						}
					}
					if (check) {
						result = temp;
						lcm.add(j);
						result = lcm(result);
						return result;
					}
				}
			}
		}
		return result;
	}
}
