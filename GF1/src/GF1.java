import java.io.*;
import java.util.*;
public class GF1 {

	public static void main(String[] args) throws Exception{
		BufferedReader br=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "input.txt"));
		BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt"));
		int input[]=new int[3];
		
		for(int a=0;a<input.length;a++){
			input[a]=Integer.parseInt(br.readLine());		
		}
		//Addition
		int add=input[1]+input[2];
		if(add>=input[0]){
			int madd=add%input[0];
			bw.write(Integer.toString(madd));
			bw.newLine();
		}
		else if(add<input[0]&&add>=0){
			bw.write(Integer.toString(add));
			bw.newLine();
		}
		else if(add<0){
			int madd=add;
			while(madd<0){
			madd=madd+input[0];
			}
			bw.write(Integer.toString(madd));
			bw.newLine();
		}
		//Multiplication
		int sub=input[1]-input[2];
		if(sub>=input[0]){
			int msub=sub%input[0];
			bw.write(Integer.toString(msub));
			bw.newLine();
		}
		else if(sub<input[0]&&sub>=0){
			bw.write(Integer.toString(sub));
			bw.newLine();
		}
		else if(sub<0){
			int msub=sub;
			while(msub<0){
			msub=msub+input[0];
			}
			bw.write(Integer.toString(msub));
			bw.newLine();
		}
		//Multiplication
		int mul=input[1]*input[2];
		if(mul>=input[0]){
			int mmul=mul%input[0];
			bw.write(Integer.toString(mmul));
			bw.newLine();
		}
		else if(mul<input[0]&&mul>=0){
			bw.write(Integer.toString(mul));
			bw.newLine();
		}
		else if(mul<0){
			int mmul=mul;
			while(mmul<0){
			mmul=mmul+input[0];
			}
			bw.write(Integer.toString(mmul));
			bw.newLine();
		}
		//Division
		int a,b;
		a=input[2];
		b=input[0];
		GF1 g=new GF1();
		int[] RE=g.EEA(a,b);
		System.out.println("Final EEA value:("+RE[0]+", "+RE[1]+")");
		while(RE[0]<0){
			RE[0]=RE[0]+input[0];
		}
        int dev=input[1]*RE[0];
        if(dev>=input[0]){
			int mdev=dev%input[0];
			bw.write(Integer.toString(mdev));
			bw.newLine();
		}
		else if(dev<input[0]&&dev>=0){
			bw.write(Integer.toString(dev));
			bw.newLine();
		}
		else if(dev<0){
			int mdev=dev;
			while(mdev<0){
			mdev=mdev+input[0];
			}
			bw.write(Integer.toString(mdev));
			bw.newLine();
		}
		br.close();
		bw.close();
	}
	public int[] EEA(int a, int b){        
		if(b == 0){             
            return new int[]{1,0};                        
        }
		else{
            int q = a/b; int r = a%b;
            System.out.print("q:"+q);
            System.out.println(", r:"+r);
            int[] R = EEA(b,r);	    
            int[] x=new int[]{R[1], R[0]-(q*R[1])};
            
            System.out.println("("+x[0]+","+x[1]+")");
            return x;
        }
    }
}
