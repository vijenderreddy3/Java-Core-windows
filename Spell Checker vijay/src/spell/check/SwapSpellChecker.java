package spell.check;

import spell.collection.HashSet;
import spell.lexicon.ILexicon;

/**
 * 
 * This spell checker checks possible close spellings by swaping characters at each two positions.
 *
 */
public class SwapSpellChecker implements ISpellChecker
{
    private final ILexicon lexicon;
    
    public SwapSpellChecker(ILexicon lexicon)
    {
        this.lexicon = lexicon;
    }

    @Override
    public HashSet<String> check(String word)
    {
        HashSet<String> words = new HashSet<>();
        for(int i = 0;i < word.length();i++)
        {
            char char1 = word.charAt(i);
            for(int j = i + 1;j < word.length();j++)
            {
                char char2 = word.charAt(j);
                String newWord = word.substring(0, i) + char2 + word.substring(i+1, j) + char1 + word.substring(j+1);
                if(lexicon.contains(newWord))
                {
                    words.add(newWord);
                }
            }
        }
        return words;
    }

}
