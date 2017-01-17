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
 * @author Checks for missing letters in the misspelled word and makes
 * suggestions from the lexicon
 */
public class MissingLetterSpellCheckProvider implements SpellCheckProvider {

    private final static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private Lexicon lexicon;

    public MissingLetterSpellCheckProvider(Lexicon lexicon) {
        this.lexicon = lexicon;
    }

    @Override
    public List<String> makeSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();
        StringBuilder alternative;

        for (char chr : alphabet) {
            for (int i = 0; i <= word.length(); i++) { // last character is included to be able to add to the end too.
                alternative = new StringBuilder();
                alternative.append(word.substring(0, i)); // Add up to i 

                // Add any character from alpahabet
                alternative.append(chr);

                alternative.append(word.substring(i)); // Add the rest

                // System.out.println(chr + ": MissingLetterSpellCheckProvider- Alternative: " + alternative.toString());
                if (lexicon.contains(alternative.toString())) {
                    suggestions.add(alternative.toString());
                }
            }
        }

        return suggestions;
    }

}
