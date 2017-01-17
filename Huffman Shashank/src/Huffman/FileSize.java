/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huffman;

import java.util.ArrayList;
import java.util.Collections;


public class FileSize {
    
    public static void  files(ArrayList<Integer> arrayList,int freq){
    
    System.out.println("Input size= " +freq*2 +" byte");
          Collections.sort(arrayList);
          int outputSize= arrayList.get(0)*1 ;
          for (int i=1; i<arrayList.size();i++)
          {
              outputSize+=arrayList.get(i)*2 ;
          }
          
         
         System.out.println("Output size= " +outputSize +" byte");
         
         
    }
    
}
