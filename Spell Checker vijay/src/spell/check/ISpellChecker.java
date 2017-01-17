package spell.check;

import spell.collection.HashSet;

/**
 * 
 * An interface for spell checkers.
 *
 */
public interface ISpellChecker
{
    HashSet<String> check(String word);
}
