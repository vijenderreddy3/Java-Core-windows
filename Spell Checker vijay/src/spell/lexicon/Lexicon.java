package spell.lexicon;

import spell.collection.HashSet;

/**
 * 
 * An instance of this class represents a lexicon.
 *
 */
public class Lexicon implements ILexicon
{
    private final HashSet<String> words = new HashSet<>(5000);

    public HashSet<String> getWords()
    {
        return words;
    }

    @Override
    public boolean contains(String word)
    {
        return words.contains(word);
    }
}
