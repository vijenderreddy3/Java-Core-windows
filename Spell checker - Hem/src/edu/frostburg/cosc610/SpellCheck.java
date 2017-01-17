package edu.frostburg.cosc610;

/**
 *
 * The main SpellCheck Class that uses a Lexicon and a list of
 * SpellCheckProviders to provide SpellCheck services.
 */
import edu.frostburg.cosc610.spellcheckprovider.InsertingLetterSpellCheckProvider;
import edu.frostburg.cosc610.spellcheckprovider.MissingLetterSpellCheckProvider;
import edu.frostburg.cosc610.structure.Lexicon;
import edu.frostburg.cosc610.spellcheckprovider.SpellCheckProvider;
import edu.frostburg.cosc610.spellcheckprovider.SwappingLetterSpellCheckProvider;
import edu.frostburg.cosc610.spellcheckprovider.WrongLetterSpellCheckProvider;
import java.util.ArrayList;
import java.util.List;

public class SpellCheck {

    private final Lexicon lexicon;
    private final List<SpellCheckProvider> spellCheckProviders = new ArrayList<>();
    private boolean isActive = true;

    SpellCheck(String filePath) {
        /* Create the lexicon from the input file */
        lexicon = new Lexicon(filePath);

        /* Check if lexicon is successgfully loaded from the file*/
        if (lexicon.isLoaded()) {
            /* Load spell check providers */
            spellCheckProviders.add(new SwappingLetterSpellCheckProvider(lexicon));
            spellCheckProviders.add(new InsertingLetterSpellCheckProvider(lexicon));
            spellCheckProviders.add(new MissingLetterSpellCheckProvider(lexicon));
            spellCheckProviders.add(new WrongLetterSpellCheckProvider(lexicon));
        } else {
            System.err.println("ERROR: Lexicon could not be loaded from the file! (wrong file path?)");
            isActive = false;
        }

    }

    public boolean isSpelledCorrectly(String word) {
        return lexicon.contains(word.toLowerCase());
    }

    public List<String> makeSuggestions(String word) {
        List<String> suggestions = new ArrayList<>();

        for (SpellCheckProvider scp : spellCheckProviders) {
            mergeLists(suggestions, scp.makeSuggestions(word.toLowerCase()));
        }

        return suggestions;
    }

    /*
    * Merge items of two lists without repetition
     */
    private void mergeLists(List<String> first, List<String> second) {
        for (String string : second) {
            if (!first.contains(string)) {
                first.add(string);
            }
        }
    }

    /**
     *
     * @return if SpellChecker is active, ie. Lexicon is loaded properly.
     */
    public boolean isActive() {
        return isActive;
    }
}
