/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huffman;

/**
 *
 * @author saikrishna
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // the frequency of this tree
    public HuffmanTree(int freq) { frequency = freq; }
 
    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}
 
class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents
 
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}
 
class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // subtrees
 
    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}
 
public class HuffmanCode {
       static int freq;
        static String sym="",huff="";
        static ArrayList<Integer> arrayList = new ArrayList<Integer>();
       static Integer i=0;
    // input is an array of frequencies, indexed by character code
    public static HuffmanTree createTree(int[] Freqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < Freqs.length; i++)
            if (Freqs[i] > 0)
                trees.offer(new HuffmanLeaf(Freqs[i], (char)i));
 
        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
 
            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }
 
    public static void print(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leafnode = (HuffmanLeaf)tree;
            
            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(leafnode.value + "\t" + leafnode.frequency + "\t" + prefix);
            sym+=String.valueOf(leafnode.value );
            huff+=String.valueOf(prefix);
            freq+=leafnode.frequency;
           
             
            arrayList.add(leafnode.frequency); 

             
                     
               
            
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
 
            // left traverse
            prefix.append('0');
            print(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // right traverse 
            prefix.append('1');
            print(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
 
   
   
}