import java.io.*;
public class Arrow {
public static void main(String args[])throws Exception{
	int i,j;
	for (i=0;i<10;i++){
		for (j=0;j<5;j++){
			if(i==1){
				if(j==1||j==3){
					System.out.print("* ");
				}
				else if(j==2){
					System.out.print("* ");
				}
				else{
					System.out.print("  ");
				}
			}
			else if(i==2){
				if(j==0||j==4){
					System.out.print("* ");
				}
				else if(j==2){
					System.out.print("* ");
				}
				else{
					System.out.print("  ");
				}
			}
			if((i==0||i>2)&&j==2){
				System.out.print("* ");
			}
			else if((i==0||i>2)&&j!=2){
				System.out.print("  ");
			}
		}
		System.out.println();
	}
}
}
