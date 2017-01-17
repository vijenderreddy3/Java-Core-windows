/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.frostburg.cosc610;

import java.util.List;
import java.util.Scanner;

/**
 *
 * Driver class to run the SpellChecker
 */
public class Driver {

    private static final String FILE_PATH = "lexicon.txt";

    public static void main(String[] args) {

        SpellCheck spellCheck = new SpellCheck(FILE_PATH);
        
        /* Check if SpellCheck is active, which means lexicon is loaded properly from the input file */
        if(!spellCheck.isActive()) {
            System.out.println("Program has exited.");
            System.exit(0);
        }
        
        // Initialize the scanner to read suer input from command line
        Scanner scan = new Scanner(System.in);
        
        // Ask for user input until 'q' for quit is pressed.
        while (true) {
            System.out.print("Enter a word (q to quit): ");
            String word = scan.nextLine().trim();
            if (word.equals("q")) {
                break;
            }
            
            // Check if the word is spelled correctly
            if (spellCheck.isSpelledCorrectly(word)) {
                System.out.println(word + " is spelled correctly");
            } else {
                System.out.println(word + " is not spelled correctly. ");
                
                // Get suggestions from the SpellCheck
                List<String> suggestions = spellCheck.makeSuggestions(word);
                if (!suggestions.isEmpty()) {
                    System.out.println("Below are suggestions for the correct spelling:");
                    for (String suggestion : suggestions) {
                        System.out.println(suggestion);
                    }
                } else {// No suggestions could be found. (May be we need more or improved SpellCheckProviders.)
                    System.out.println("Sorry, no suggestions found.");
                }
            }
            System.out.println();
        }

    }

}
