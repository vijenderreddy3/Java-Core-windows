/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.frostburg.cosc610.spellcheckprovider;

import java.util.List;

/**
 *
 * Class that provides a strategy for making sugggestions for a word that is misspelled
 */
public interface SpellCheckProvider {
    
    /**
     * Makes suggestions for the misspelled word.
     * @param word The misspelled word to get suggestions for.
     * @return List of alternative words
     */
    public List<String> makeSuggestions(String word);
    
}
