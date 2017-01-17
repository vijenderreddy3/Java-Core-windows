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
 * @author Checks for inserting letters in the misspelled word and makes
 * suggestions from the lexicon
 */
public class InsertingLetterSpellCheckProvider implements SpellCheckProvider {

    private Lexicon lexicon;

    public InsertingLetterSpellCheckProvider(Lexicon lexicon) {
        this.lexicon = lexicon;
    }

    @Override
    public List<String> makeSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();
        StringBuilder alternative;

        for (int i = 0; i < word.length(); i++) {
            alternative = new StringBuilder();
            alternative.append(word.substring(0, i)); // Add up to i 
            // character at i is removed
            alternative.append(word.substring(i + 1)); // Add the rest

            //System.out.println(i + ": InsertingLetterSpellCheckProvider- Alternative: " + alternative.toString());
            if (lexicon.contains(alternative.toString())) {
                suggestions.add(alternative.toString());
            }
        }

        return suggestions;
    }

}
