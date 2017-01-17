
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
///import sun.security.util.SecurityConstants;

public class Bigrams {
	URL url;
	int total_words;
	float frequency;
	Integer counter;
	static ArrayList<String> words;
	static ArrayList<String> twowords;
	static ArrayList<Float> counts;
	static ArrayList<String> twowords_tomatch;
	static ArrayList<Float> descending_order;
	static ArrayList<String> descending_bigrams;

	public Bigrams(String input) {
		url = getClass().getResource(input);
		total_words = 0;
	}

	public void File_read() throws FileNotFoundException, IOException {
		File fname = new File(System.getProperty("user.dir") + "/input.txt");
		// FileReader fread = new FileReader(fname);

		Scanner s = new Scanner(new FileReader(fname));
		words = new ArrayList<String>();
		twowords = new ArrayList<String>();
		twowords_tomatch = new ArrayList<String>();
		counts = new ArrayList<Float>();
		descending_order = new ArrayList<Float>();
		descending_bigrams = new ArrayList<String>();
		while (s.hasNext()) {
			String token = s.next();
			if (isAWord(token)) {
				if (token.contains(".")) {
					token = token.replace(".", "");
				}
				if (token.contains(",")) {
					token = token.replace(",", "");
				}

				words.add(token);
			}

		}
		total_words = words.size();

		for (int j = 0; j <= words.size() - 2; j++) {
			String bi = words.get(j) + " " + words.get(j + 1);
			twowords.add(bi);
			twowords_tomatch.add(bi);

		}
		Map<String, Integer> result = new HashMap<String, Integer>();

		for (int k = 0; k < words.size() - 2; k++) {
			counter = 0;
			for (int l = 0; l <= words.size() - 2; l++) {
				if (twowords.get(k).equalsIgnoreCase(twowords.get(l))) {
					counter++;
				} else {
				}
			}
			result.put(twowords.get(k), counter);
		}
		System.out.println("Bigrams       Number Of Repetitions ");
		System.out.println();

		TreeSet<Map.Entry<String, Integer>> sortedresult = (TreeSet) entriesSortedByValues(result);
		Iterator itr = sortedresult.descendingIterator();
		while (itr.hasNext()) {
			//HashMap<String,Integer> xy=itr.next();
			System.out.println(itr.next());
		}
	}

	static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				int res = e1.getValue().compareTo(e2.getValue());
				return res != 0 ? res : 1;
			}
		});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	private static boolean isAWord(String token) {
		if (token != " ") {
			return true;
		} else
			return false;
	}

	public static void main(String[] args) throws IOException {
		Bigrams b = new Bigrams("input.txt");
		b.File_read();
	}
}