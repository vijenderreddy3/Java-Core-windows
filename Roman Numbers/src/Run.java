import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Run {

	public static void main(String[] args) {
		System.out.println("Welcome to CONVERT TO ROMAN NUMBERS");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter your number(enter 'exit' to terminate):");
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			System.out.println("Failed to read your input");
		}
		while (!(input.equals("exit"))) {
			if (input.equals("")) {
				System.out.println("Invalid input:");
				System.out.println("Enter your number(enter 'exit' to terminate):");
				try {
					input = br.readLine();
				} catch (IOException e) {
					System.out.println("Failed to read your input");
				}
			} else {
				int intNo = Integer.parseInt(input);
				if (intNo >= 4000) {
					System.out.println("Invalid Input: Please enter value below 4000.");
				} else {
					Roman r = new Roman();
					String romanNo = r.converToRoman(intNo);
					System.out.println("Equal roman number for your number:" + intNo + " is " + romanNo);
				}
				System.out.println("Enter your next number(enter 'exit' to terminate):");
				try {
					input = br.readLine();
				} catch (IOException e) {
					System.out.println("Failed to read your input");
				}
			}
		}
	}

}
