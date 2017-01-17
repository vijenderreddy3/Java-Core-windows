import java.io.*;
import java.util.*;

public class Poly {

		public static void main(String[] args) throws Exception{
			int noLines=0;
			String[] input;
			int pfi1[],pfi2[];	
			try{
				BufferedReader br=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "input.txt"));
				BufferedReader br1=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "input.txt"));
				while((br.readLine())!=null){
					noLines=noLines+1;
					}
				input=new String[noLines];
				for(int a=0;a<input.length;a++){
				input[a]=br1.readLine();		
				}		
				br.close();
				br1.close();
				int z=Integer.parseInt(input[0]);
				String v[]=input[1].split(" ");
				String pf1[]=new String[z+1];
			for(int i=0;i<pf1.length;i++){
				pf1[i]="0";
			}
			if (z>=v.length){
				    int z1=z;
					for(int i=(v.length-1);i>=0;i--){
						pf1[z1]=v[i];
						z1=z1-1;
					}
			}
			else if(z==(v.length-1)){
				for(int i=(v.length-1);i>=0;i--){
					pf1[i]=v[i];
				}
			}
			else if(z<(v.length-1)){
				BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt"));
	
				bw.write("Please check the degree values of polynominals");
				bw.close();
			}
			pfi1=new int[pf1.length];
			for(int i=0;i<=(pfi1.length-1);i++){
				pfi1[i]=0;
				pfi1[i]=Integer.parseInt(pf1[(pf1.length-1)-i]);
			}
			z=Integer.parseInt(input[2]);
			String u[]=input[3].split(" ");
			String pf2[]=new String[z+1];
		for(int i=0;i<pf2.length;i++){
			pf2[i]="0";
		}
		if (z>=u.length){
			    int z1=z;
				for(int i=(u.length-1);i>=0;i--){
					pf2[z1]=u[i];
					z1=z1-1;
				}
		}
		else if(z==(u.length-1)){
			for(int i=(u.length-1);i>=0;i--){
				pf2[i]=u[i];
			}
		}
		else if(z<(u.length-1)){
			BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt"));

			bw.write("Please check the degree values of polynominals");
			bw.close();
		}
			pfi2=new int[pf2.length];
			for(int i=0;i<pfi2.length;i++){
				pfi2[i]=Integer.parseInt(pf2[(pf2.length-1)-i]);
			}
				//ADDITION
			try{
				BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt"));
				
				int add[];
				int x=pfi1.length,y=pfi2.length;
				if(x>y){
					add=new int[pfi1.length];
					int i,j,k=0;
					for(i=0;i<=x-1;i++){
						add[i]=pfi1[i];
						for(j=k;i==j&&j<=y-1;j++){
						add[i]=pfi1[i]+pfi2[j];
						}
						k=k+1;
					}
					for(i=(add.length-1);i>=0;i--){
						if(add[i]!=0){
							bw.write(Integer.toString(add[i]));
							bw.write(" ");
						}
						else if(add[i]==0){
							for(int m=(add.length-1);m>i;m--){
								if(add[m]==0){
								}
								else if(add[m]!=0){
									bw.write(Integer.toString(add[i]));
									bw.write(" ");
									break;
								}
							}
						}
					}
				}
				else if(x<y){
					add=new int[pfi2.length];
					int i,j,k=0;
					for(i=0;i<=y-1;i++){
						add[i]=pfi2[i];
						for(j=k;i==j&&j<=(x-1);j++){
						add[i]=pfi1[i]+pfi2[j];
						}
						k=k+1;
					}
					for(i=(add.length-1);i>=0;i--){
						if(add[i]!=0){
							bw.write(Integer.toString(add[i]));
							bw.write(" ");
						}
						else if(add[i]==0){
							for(int m=(add.length-1);m>i;m--){
								if(add[m]==0){
								}
								else if(add[m]!=0){
									bw.write(Integer.toString(add[i]));
									bw.write(" ");
									break;
								}
							}
						}
					}	
				}
				else {
					add=new int[pfi1.length];
					int i,j,k=0;
					for(i=0;i<=x-1;i++){
						for(j=k;j<=y-1&&j==i;j++){
						add[i]=pfi1[i]+pfi2[j];
						}
						k=k+1;
					}
					for(i=(add.length-1);i>=0;i--){
						if(add[i]!=0){
							bw.write(Integer.toString(add[i]));
							bw.write(" ");
						}
						else if(add[i]==0){
							for(int m=(add.length-1);m>i;m--){
								if(add[m]==0){
								}
								else if(add[m]!=0){
									bw.write(Integer.toString(add[i]));
									bw.write(" ");
									break;
								}
							}
						}
					}
				}
				bw.newLine();
				bw.close();	
				}
				catch(Exception e){
				System.out.println("output.txt file not opened");	
				}
			//SUBSTRACTION
			try{
			BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt",true));
			int sub[];
			int x=pfi1.length,y=pfi2.length;
			if(x>y){
				sub=new int[pfi1.length];
				int i,j,k=0;
				for(i=0;i<=x-1;i++){
					sub[i]=pfi1[i];
					for(j=k;i==j&&j<=y-1;j++){
						sub[i]=pfi1[i]-pfi2[j];
					}
					k=k+1;
				}
				for(i=(sub.length-1);i>=0;i--){
					if(sub[i]!=0){
						bw.write(Integer.toString(sub[i]));
						bw.write(" ");
					}
					else if(sub[i]==0){
						for(int m=(sub.length-1);m>i;m--){
							if(sub[m]==0){
							}
							else if(sub[m]!=0){
								bw.write(Integer.toString(sub[i]));
								bw.write(" ");
								break;
							}
						}
					}
				}
			}
			else if(x<y){
				sub=new int[pfi2.length];
				int i,j,k=0;
				for(i=0;i<=y-1;i++){
					sub[i]=-pfi2[i];
					for(j=k;i==j&&j<=(x-1);j++){
					sub[i]=pfi1[i]-pfi2[j];
					}
					k=k+1;
				}
				for(i=(sub.length-1);i>=0;i--){
					if(sub[i]!=0){
						bw.write(Integer.toString(sub[i]));
						bw.write(" ");
					}
					else if(sub[i]==0){
						for(int m=(sub.length-1);m>i;m--){
							if(sub[m]==0){
							}
							else if(sub[m]!=0){
								bw.write(Integer.toString(sub[i]));
								bw.write(" ");
								break;
							}
						}
					}
				}	
			}
			else {
				sub=new int[pfi1.length];
				int i,j,k=0;
				for(i=0;i<=x-1;i++){
					for(j=k;j<=y-1&&j==i;j++){
						sub[i]=pfi1[i]-pfi2[j];
					}
					k=k+1;
				}
				for(i=(sub.length-1);i>=0;i--){
					if(sub[i]!=0){
						bw.write(Integer.toString(sub[i]));
						bw.write(" ");
					}
					else if(sub[i]==0){
						for(int m=(sub.length-1);m>i;m--){
							if(sub[m]==0){
							}
							else if(sub[m]!=0){
								bw.write(Integer.toString(sub[i]));
								bw.write(" ");
								break;
							}
						}
					}
				}
			}
			bw.newLine();
			bw.close();	
			}
			catch(Exception e){
			System.out.println("output.txt file not opened");	
			}			
			//Multiplication
			try{
				BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt",true));
			int mul[];
			int x=pfi1.length,y=pfi2.length;
				mul=new int[pfi1.length+pfi2.length-1];
				int i,j;
				for(i=0;i<mul.length;i++){				
					mul[i]=0;
				}
				for(i=0;i<x;i++){
					for(j=0;j<y;j++){
							mul[i+j]=mul[i+j]+(pfi1[i]*pfi2[j]);
					}
				}
				for(i=(mul.length-1);i>=0;i--){
					if(mul[i]!=0){
						bw.write(Integer.toString(mul[i]));
						bw.write(" ");
					}
					else if(mul[i]==0){
						for(int m=(mul.length-1);m>i;m--){
							if(mul[m]==0){
							}
							else if(mul[m]!=0){
								bw.write(Integer.toString(mul[i]));
								bw.write(" ");
								break;
							}
						}
					}
				}
			bw.newLine();
			bw.close();	
			}
			catch(Exception e){
			System.out.println("output.txt file not opened");	
			}		

			}
			catch(Exception e){
			System.out.println("file not read");
			}
	}
}