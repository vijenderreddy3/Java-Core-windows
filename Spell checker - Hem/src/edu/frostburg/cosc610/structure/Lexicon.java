package edu.frostburg.cosc610.structure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * Class that reads words from file and stores them in the HashSet
 */
public class Lexicon {

    private HashSet words;

    public Lexicon(String filePath) {
        readWordsFromFile(filePath);
    }

    private void readWordsFromFile(String filePath) {
        words = new HashSet();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(filePath)));
            String word;
            while ((word = reader.readLine()) != null) {
                words.add(word);
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
        }
    }

    /**
     * @return true if lexicon is successfully loaded, false otherwise. It may
     * be false if the file path supplied is wrong, or an Exception occured when
     * loading.
     */
    public boolean isLoaded() {
        return (words != null && words.size() > 0);
    }

    public boolean contains(String word) {
        return words.contains(word);
    }
}
