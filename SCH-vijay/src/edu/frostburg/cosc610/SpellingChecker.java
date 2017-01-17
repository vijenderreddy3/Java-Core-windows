package edu.frostburg.cosc610;

import java.io.*;
import java.util.*;

 
public class SpellingChecker
{
	
	 public static Map<String, Integer> dictionary = new HashMap<String, Integer>();
	 
  
	 public SpellingChecker()throws Exception
	 {
		 BufferedReader br=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "list.txt"));
	     String word;
		 while ((word=br.readLine())!=null) {
	    	 
	    	 	dictionary.put(word, 1);
				
	    	 /*System.out.print("please put text to directory (exit to exit):");
			Scanner sc = new Scanner(System.in);
			String text = sc.nextLine();
			if (text.equalsIgnoreCase("exit"))
				break ;
			dictionary.put(text, 1);
			System.out.println();
			*/
		  }	
		 System.out.println("Dictionary created");
	     br.close();
	     Scanner s = new Scanner(System.in);
	     while(true)
	     { 
		  System.out.println("put string need to ceck(exit to exit):");
		  String input = s.nextLine();
		  if (input.equalsIgnoreCase("exit"))
				break ;
		  String correct = correct(input);
		  System.out.println(); 
		  System.out.println("string after ceck:"+correct);
	     }
		  s.close();
	 }
 
	 public static String correct(String word) {
	   
	  if(dictionary.containsKey(word))
		  return word;
	   
	  List<String> edits = edits(word);
	  Map<Integer, String> candidates = new HashMap<Integer, String>();
	 
	  
	  for(String s : edits) {
		   if(dictionary.containsKey(s)) {
		    candidates.put(dictionary.get(s), s);
		   }
	  }
	   
  
	  if(candidates.size() > 0)
		  return candidates.get(Collections.max(candidates.keySet()));
   
   
	   
	  for(String s : edits) {
	    
	   List<String> newEdits = edits(s);
		   for(String ns : newEdits) {
			    if(dictionary.containsKey(ns)) {
			    	candidates.put(dictionary.get(ns), ns);
			    }
		   }
	  }
	  
	  if(candidates.size() > 0)
	   return candidates.get(Collections.max(candidates.keySet()));
	  else
	   return word;
 }
 
  
	 public static List<String> edits(String word) {
	   
		  if(word == null || word.isEmpty())
		   return null;
		   
		  List<String> list = new ArrayList<String>();
		   
		  String w = null;
		   
		  
		  for (int i = 0; i < word.length(); i++) {
		   w = word.substring(0, i) + word.substring(i + 1); 
		   list.add(w);
		  }
		   
		  
		  for (int i = 0; i < word.length() - 1; i++) { 
		   w = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2); 
		   list.add(w);
	  }
	   
  
	  for (int i = 0; i < word.length(); i++) {
	   for (char c = 'a'; c <= 'z'; c++) {
		    w = word.substring(0, i) + c + word.substring(i + 1); 
		    list.add(w);
		   }
	  }
   
  
  for (int i = 0; i <= word.length(); i++) { 
   for (char c = 'a'; c <= 'z'; c++) {
		    w = word.substring(0, i) + c + word.substring(i); 
		    list.add(w);
   }
  }
   
  return list;
 }
  
}
