package spell;

import java.io.File;
import java.util.Scanner;

import spell.collection.HashSet;
import spell.lexicon.ILexicon;
import spell.lexicon.Lexicon;

/**
 * 
 * A tool for IO related operations.
 *
 */
public class IOTool
{
    private IOTool()
    {
        
    }
    
    /**
     * Read the lexicon from the given path.
     * @param path
     * @return
     */
    public static ILexicon readLexicon(String path)
    {
        try(Scanner scanner = new Scanner(new File(path)))
        {
            Lexicon lexicon = new Lexicon();
            HashSet<String> words = lexicon.getWords();
            while(scanner.hasNextLine())
            {
                String word = scanner.nextLine().trim();
                words.add(word);
            }
            return lexicon;
        }catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
