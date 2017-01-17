package spell;

import java.util.Scanner;

import spell.check.ISpellChecker;
import spell.check.InsertSpellChecker;
import spell.check.MissingSpellChecker;
import spell.check.MisspellChecker;
import spell.check.ReplaceSpellChecker;
import spell.check.SwapSpellChecker;
import spell.collection.HashSet;
import spell.lexicon.ILexicon;

/**
 * 
 * This is the entry of this program.
 *
 */
public class Startor
{
    /**
     * Entrance.
     * @param strings
     */
    public static void main(String[] strings)
    {
        Scanner scanner = new Scanner(System.in);
        
        // Read the lexicon.
        ILexicon lexicon = IOTool.readLexicon("lexicon.txt");
        
        // Build the spell checker.
        ISpellChecker[] checkers = {
                new InsertSpellChecker(lexicon),
                new MissingSpellChecker(lexicon),
                new ReplaceSpellChecker(lexicon),
                new SwapSpellChecker(lexicon)
                };
        MisspellChecker checker = new MisspellChecker(lexicon, checkers);
        
        // Read user input and check the spelling.
        while(true)
        {
            System.out.print("Please input the word you wanna check (or nothing to exit): ");
            String word = scanner.nextLine().trim();
            if(word.isEmpty())
            {
                System.out.println("Bye!");
                break;
            }
            
            HashSet<String> set = checker.check(word);
            if(set == null)
            {
                System.out.println("Correct spelling!");
            }else if(set.size() == 0)
            {
                System.out.println("No suggestion found!");
            }else
            {
                String[] words = set.toArray(new String[set.size()]);
                System.out.println("Do you mean:");
                for(String string : words)
                {
                    System.out.println(string);
                }
            }
            
            System.out.println();
        }
        
        scanner.close();
    }
}
