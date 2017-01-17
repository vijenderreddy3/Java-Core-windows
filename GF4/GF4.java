import java.io.*;
import java.util.*;
class GF4{
	public static void main(String args[])throws Exception{
		int p=7;
		int[] a={1,5,2};
		int[] b={4,0,9};
		int[] Q=new int[b.length];
		for(int i=0;i<Q.length;i++){
			Q[i]=0;
		}
		while(a.length>=b.length){
			for(int i=0;i<a.length;i++){
				for(int j=0;j==i&&j<b.length;j++){
					if((a[i]/b[j])==(int)(a[i]/b[j])){
						Q[a.length-b.length]=(a[i]/b[j]);
					}
					else{
					Q[(a.length-b.length)]=EEA(b[j],p);
					}
				}
			}
		}
	}
	public static int[] EEA(int a[int i], int b){        
		if(b == 0){             
            return new int[]{1,0};                        
        }
		else{
            int q = a/b; int r = a%b;
            int[] R = EEA(b,r);	    
            return new int[]{R[1], R[0]-(q*R[1])};
        }
    }
}
