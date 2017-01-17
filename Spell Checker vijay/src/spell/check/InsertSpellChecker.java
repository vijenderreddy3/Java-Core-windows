package spell.check;

import spell.IConstants;
import spell.collection.HashSet;
import spell.lexicon.ILexicon;

/**
 * 
 * This spell checker checks possible close spellings by inserting characters at each position.
 *
 */
public class InsertSpellChecker implements ISpellChecker
{
    private final ILexicon lexicon;
    
    public InsertSpellChecker(ILexicon lexicon)
    {
        this.lexicon = lexicon;
    }

    @Override
    public HashSet<String> check(String word)
    {
        HashSet<String> words = new HashSet<>();
        for(int i = 0;i <= word.length();i++)
        {
            for(char charr : IConstants.CHARS)
            {
                String newWord = word.substring(0, i) + charr + word.substring(i);
                if(lexicon.contains(newWord))
                {
                    words.add(newWord);
                }
            }
        }
        return words;
    }

}
