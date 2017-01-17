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
 * @author Checks for swapping letters in the misspelled word
 * and makes suggestions from the lexicon
 */
public class SwappingLetterSpellCheckProvider implements SpellCheckProvider{
    
    private Lexicon lexicon;
    
    public SwappingLetterSpellCheckProvider(Lexicon lexicon) {
        this.lexicon = lexicon;
    }

    @Override
    public List<String> makeSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();
        StringBuilder alternative;
        
        for (int i = 0; i < word.length() - 1; i++) {
            alternative = new StringBuilder();
            alternative.append(word.substring(0, i)); // Add up to i 
            alternative.append(word.charAt(i + 1)); // Swap (i+1)'th 
            alternative.append(word.charAt(i));     // and i'th characters  
            alternative.append(word.substring(i + 2)); // Add the rest
           
            //System.out.println(i + "- Alternative: " + alternative.toString());
            if (lexicon.contains(alternative.toString())) {
                suggestions.add(alternative.toString());
            }
        }
        
        return suggestions;
    }
    
}
