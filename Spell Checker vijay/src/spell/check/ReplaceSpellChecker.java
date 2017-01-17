package spell.check;

import spell.IConstants;
import spell.collection.HashSet;
import spell.lexicon.ILexicon;

/**
 * 
 * This spell checker checks possible close spellings by replacing the characters at each position.
 *
 */
public class ReplaceSpellChecker implements ISpellChecker
{
    private final ILexicon lexicon;
    
    public ReplaceSpellChecker(ILexicon lexicon)
    {
        this.lexicon = lexicon;
    }

    @Override
    public HashSet<String> check(String word)
    {
        HashSet<String> words = new HashSet<>();
        for(int i = 0;i < word.length();i++)
        {
            char originalChar = word.charAt(i);
            for(char charr : IConstants.CHARS)
            {
                if(charr == originalChar)
                {
                    continue;
                }
                
                String newWord = word.substring(0, i) + charr + word.substring(i+1);
                if(lexicon.contains(newWord))
                {
                    words.add(newWord);
                }
            }
        }
        return words;
    }

}
