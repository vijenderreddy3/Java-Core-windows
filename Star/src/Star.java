import java.io.*;
//import java.util.*;
public class Star {
public static void main(String args[])throws IOException{
	int i,j;
for (i=0;i<4;i++){
	for (j=0;j<4;j++){
		if(i==1||i==2){
			if(j==1||j==2){
				System.out.print("  ");
			}
			else{
				System.out.print("* ");
			}
		}
		else{
			System.out.print("* ");
		}
		
	}
	System.out.println();
	
}
}
}
