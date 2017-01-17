
import java.util.*;
import java.io.*;

public class WordFrequency {

	Hashtable tab;
	String bigram;
	BufferedReader br;
	Enumeration words, keys;
	CountBiagram[] ResultCount;

	public static void main(String[] args) throws IOException {

		WordFrequency wfreq = new WordFrequency();
		wfreq.run();

	}

	void run() throws IOException {

		fileOpen();
		collectWords();
		collectResults();
		showFrequencyResults();

	}

	void fileOpen() {
		try {
			br = new BufferedReader(new FileReader("input.txt"));

		} catch (Exception e) {
			System.out.println("Problem in reading the file" + e);
		}
	}

	void collectWords() throws IOException {

		tab = new Hashtable();
		String line = "";
		StringTokenizer strtoken;
		String tok1 = "";
		String tok2 = "";

		try {
			line = br.readLine();
			while (line != null) {
				strtoken = new StringTokenizer(line);
				if (strtoken.hasMoreTokens()) {
					tok1 = strtoken.nextToken();
				}
				while (strtoken.hasMoreTokens()) {
					tok2 = strtoken.nextToken();
					bigram = tok1 + " " + tok2;
					Object obj = tab.get(bigram);
					if (obj != null)
						((int[]) obj)[0]++;
					else {
						int[] count = { 1 };
						tab.put(bigram, count);
					}

					tok1 = tok2;
				}
				line = br.readLine();
			}
		} catch (Exception e) {
		}

	}

	class CountBiagram {
		int sum;
		String bigram;

		CountBiagram(int count, String bigram) {
			this.sum = count;
			this.bigram = bigram;
		}
	}

	void collectResults() {

		keys = tab.keys();
		words = tab.elements();
		int index = 0;
		ResultCount = new CountBiagram[tab.size()];

		while (keys.hasMoreElements()) {
			words.hasMoreElements();
			ResultCount[index++] = new CountBiagram(
					((int[]) words.nextElement())[0],
					(String) keys.nextElement());
		}
	}

	void showFrequencyResults() throws IOException {

		Comparator comp = new Comparator() {
			public int compare(Object obj1, Object obj2) {
				int i1 = ((CountBiagram) obj1).sum;
				int i2 = ((CountBiagram) obj2).sum;
				if (i1 == i2)
					return 0;
				return ((i1 > i2) ? -1 : +1);
			}
		};
		PrintWriter print = new PrintWriter(new FileWriter("output.txt"));

		Arrays.sort(ResultCount, comp);
		System.out.println("Results sorted by bigram count:");
		print.write("Total count of bigram:" + tab.size());
		print.write(System.lineSeparator());
		print.write("Results sorted by bigram in descending order are:");
		print.write(System.lineSeparator());
		for (int i = 0; i < ResultCount.length; i++) {
			System.out.println(ResultCount[i].sum + " "
					+ ResultCount[i].bigram);
			print.write(ResultCount[i].sum + " " + ResultCount[i].bigram);
			print.write(System.lineSeparator());
			print.flush();
		}

	}

}