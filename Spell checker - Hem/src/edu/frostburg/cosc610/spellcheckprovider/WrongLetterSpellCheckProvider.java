/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.frostburg.cosc610.spellcheckprovider;

import edu.frostburg.cosc610.structure.Lexicon;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Checks for wrong letters in the misspelled word and makes suggestions
 * from the lexicon
 */
public class WrongLetterSpellCheckProvider implements SpellCheckProvider {

    private final static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private Lexicon lexicon;

    public WrongLetterSpellCheckProvider(Lexicon lexicon) {
        this.lexicon = lexicon;
    }

    @Override
    public List<String> makeSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();
        StringBuilder alternative;

        for (char chr : alphabet) {
            for (int i = 0; i < word.length(); i++) {
                alternative = new StringBuilder();
                alternative.append(word.substring(0, i)); // Add up to i 

                // Add any character from alpahabet
                alternative.append(chr);

                alternative.append(word.substring(i + 1)); // Add the rest

                if (!alternative.toString().equals(word)) {
                    //System.out.println(chr + ": WrongLetterSpellCheckProvider- Alternative: " + alternative.toString());
                    if (lexicon.contains(alternative.toString())) {
                        suggestions.add(alternative.toString());
                    }
                }
            }
        }

        return suggestions;
    }

}
