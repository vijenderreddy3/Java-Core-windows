/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huffman;

import Huffman.FileReader;
import static Huffman.HuffmanCode.*;
import java.io.BufferedReader;
import Huffman.FileSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author shashank
 */
public class Driver {
   public static void main(String[] args) {
       
       String test;
       test = FileReader.files();
       
  
        
        int[] charFreqs = new int[512];
   
        for (char c : test.toCharArray())
            charFreqs[c]++;
 
        // build huffman tree
        HuffmanTree tree = createTree(charFreqs);
 
        
        System.out.println("SYMBOL FREQUENCY HUFFMAN CODE");
        print(tree, new StringBuffer());
         System.out.println("String = "+sym + "\n" +"Total freqency = "+ freq+ "\n" +"Total huffmancode = "+ huff);
         FileSize.files(arrayList, freq);
    }   
}
