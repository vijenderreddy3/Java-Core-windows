import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ComparePackages {

	public static void main(String[] args) {
		int length1 ;
		int length2 ;
		int[] array1;
		int[] array2;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter first array length:");
		length1 = sc.nextInt();
		System.out.println("Enter Second array length");
		length2 = sc.nextInt();

		if (length1 > 0 && length2 > 0) {
			array1 = new int[length1];
			array2 = new int[length2];
			for (int i = 0; i < length1; i++) {
				System.out.println("Enter index opsition of :" + i + " of array1:");
				array1[i] = sc.nextInt();
			}
			System.out.println("-------------------------------------------------------");
			for (int i = 0; i < length2; i++) {
				System.out.println("Enter index opsition of :" + i + " of array2:");
				array2[i] = sc.nextInt();
			}
			List<Integer> result=new ArrayList<Integer>();
			for(int i=0;i<length1;i++){
				for(int j=0;j<length2;j++){
					if(array1[i]==array2[j]){
						result.add(array1[i]);
					}
				}
			}
			System.out.println("-------------------------------------------------------");
			System.out.println("Common numbers of 2 arrays");
			Iterator<Integer> it=(Iterator) result.iterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
		} else {
			System.out.println("Entered wrong array sizes");
		}
		
	}

}
