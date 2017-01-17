package spell.check;

import spell.collection.HashSet;
import spell.lexicon.ILexicon;

/**
 * 
 * This spell checker uses the given sub checkers to check possible close spellings only if the word doesn't exist in the lexicon.
 *
 */
public class MisspellChecker implements ISpellChecker
{
    private final ILexicon lexicon;
    private final ISpellChecker[] checkers;
    
    public MisspellChecker(ILexicon lexicon, ISpellChecker[] checkers)
    {
        this.lexicon = lexicon;
        this.checkers = checkers;
    }

    @Override
    public HashSet<String> check(String word)
    {
        HashSet<String> words = new HashSet<>();
        if(lexicon.contains(word))
        {
            return null;
        }

        for(ISpellChecker checker : checkers)
        {
            words.addAll(checker.check(word));
        }
        
        return words;
    }
    
}
