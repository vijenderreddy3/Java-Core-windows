package spell.check;

import spell.collection.HashSet;
import spell.lexicon.ILexicon;

/**
 * 
 * This spell checker checks possible close spellings by removing characters at each position.
 *
 */
public class MissingSpellChecker implements ISpellChecker
{
    private final ILexicon lexicon;
    
    public MissingSpellChecker(ILexicon lexicon)
    {
        this.lexicon = lexicon;
    }

    @Override
    public HashSet<String> check(String word)
    {
        HashSet<String> words = new HashSet<>();
        for(int i = 0;i < word.length();i++)
        {
            String newWord = word.substring(0, i) + word.substring(i+1);
            if(lexicon.contains(newWord))
            {
                words.add(newWord);
            }
        }
        return words;
    }

}
