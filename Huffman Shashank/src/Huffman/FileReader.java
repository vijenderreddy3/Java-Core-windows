/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huffman;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {
    static String test ="";
    public static String files(){
    BufferedReader br = null;

		try {
                        
			String sCurrentLine;

			br = new BufferedReader(new java.io.FileReader(System.getProperty("user.dir") + "/" + "input.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				test+=sCurrentLine.toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
                    if (br != null)try {
                        br.close();
                    } catch (IOException ex) {
                      System.out.println(ex); 
                    }
		}
                return test;
    
}
}