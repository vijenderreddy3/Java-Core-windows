import java.io.*;
import java.util.*;
public class GF3 {

	static int a,b,prime;
	static BufferedWriter bw;
	static BufferedWriter bw1;
	static int [] COEFF;
	static int [] REM;
	static{
		try{
			bw=new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt"));
			bw1=new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt",true));
		}
		catch(Exception e){
			System.out.println("problem with writing output to file");
		}
	}
	public static void main(String[] args) {
		int noLines=0;
		String[] input;
		int mxi[],fxi[],gxi[];
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
			prime=Integer.parseInt(input[0]);
			//System.out.println(prime);
			int p=Integer.parseInt(input[1]);
			String v[]=input[2].split(" ");
			String mx[]=new String[p+1];
		for(int i=0;i<mx.length;i++){
			mx[i]="0";
		}
		if (p>=v.length){
			    int p1=p;
				for(int i=(v.length-1);i>=0;i--){
					mx[p1]=v[i];
					p1=p1-1;
				}
		}
		else if(p==(v.length-1)){
			for(int i=(v.length-1);i>=0;i--){
				mx[i]=v[i];
			}
		}
		else if(p<(v.length-1)){
			//BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt"));

			bw.write("Please check the degree values of polynominals");
			bw.close();
		}
		mxi=new int[mx.length];
		for(int i=0;i<=(mxi.length-1);i++){
			mxi[i]=0;
			mxi[i]=Integer.parseInt(mx[(mx.length-1)-i]);
			//System.out.print(mxi[i]+" ");
		}
		
//		System.out.println();
		p=Integer.parseInt(input[3]);
		String u[]=input[4].split(" ");
		String fx[]=new String[p+1];
		for(int i=0;i<fx.length;i++){
			fx[i]="0";
		}
		if (p>=u.length){
			int z1=p;
			for(int i=(u.length-1);i>=0;i--){
				fx[z1]=u[i];
				z1=z1-1;
			}
		}
		else if(p==(u.length-1)){
			for(int i=(u.length-1);i>=0;i--){
				fx[i]=u[i];
			}
		}
		else if(p<(u.length-1)){
			

			bw.write("Please check the degree values of polynominals");
			
		}
		fxi=new int[fx.length];
		for(int i=0;i<fxi.length;i++){
			fxi[i]=Integer.parseInt(fx[(fx.length-1)-i]);
	//		System.out.print(fxi[i]+" ");
		}
		//System.out.println();
		p=Integer.parseInt(input[3]);
		u=input[6].split(" ");
		String gx[]=new String[p+1];
		for(int i=0;i<gx.length;i++){
			gx[i]="0";
		}
		if (p>=u.length){
			int z1=p;
			for(int i=(u.length-1);i>=0;i--){
				gx[z1]=u[i];
				z1=z1-1;
			}
		}
		else if(p==(u.length-1)){
			for(int i=(u.length-1);i>=0;i--){
				gx[i]=u[i];
			}
		}
		else if(p<(u.length-1)){
			///BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt"));

			bw.write("Please check the degree values of polynominals");
			//bw.close();
		}
		gxi=new int[gx.length];
		for(int i=0;i<gxi.length;i++){
			gxi[i]=Integer.parseInt(gx[(gx.length-1)-i]);
			//System.out.print(gxi[i]+" ");
		}
		//System.out.println();

			//ADDITION
		try{
			//BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt"));
			
			int add[];
			int x=fxi.length,y=gxi.length;
			if(x>y){
				add=new int[fxi.length];
				int i,j,k=0;
				for(i=0;i<=x-1;i++){
					add[i]=fxi[i];
					for(j=k;i==j&&j<=y-1;j++){
					add[i]=mod(fxi[i]+gxi[j]);
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
				add=new int[gxi.length];
				int i,j,k=0;
				for(i=0;i<=y-1;i++){
					add[i]=gxi[i];
					for(j=k;i==j&&j<=(x-1);j++){
					add[i]=mod(fxi[i]+gxi[j]);
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
				add=new int[fxi.length];
				int i,j,k=0;
				for(i=0;i<=x-1;i++){
					for(j=k;j<=y-1&&j==i;j++){
					add[i]=mod(fxi[i]+gxi[j]);
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
		//BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"output.txt",true));
		int sub[];
		System.out.println("Substraction");
		int x=fxi.length,y=gxi.length;
		if(x>y){
			sub=new int[fxi.length];
			int i,j,k=0;
			for(i=0;i<=x-1;i++){
				sub[i]=fxi[i];
				for(j=k;i==j&&j<=y-1;j++){
					sub[i]=mod(fxi[i]-gxi[j]);
				}
				k=k+1;
			}
			for(i=(sub.length-1);i>=0;i--){
				if(sub[i]!=0){
					bw1.write(Integer.toString(sub[i]));
					bw1.write(" ");
				}
				else if(sub[i]==0){
					for(int m=(sub.length-1);m>i;m--){
						if(sub[m]==0){
						}
						else if(sub[m]!=0){
							bw1.write(Integer.toString(sub[i]));
							bw1.write(" ");
							break;
						}
					}
				}
			}
		}
		else if(x<y){
			sub=new int[gxi.length];
			int i,j,k=0;
			for(i=0;i<=y-1;i++){
				sub[i]=-gxi[i];
				for(j=k;i==j&&j<=(x-1);j++){
				sub[i]=mod(fxi[i]-gxi[j]);
				}
				k=k+1;
			}
			for(i=(sub.length-1);i>=0;i--){
				if(sub[i]!=0){
					bw1.write(Integer.toString(sub[i]));
					bw1.write(" ");
				}
				else if(sub[i]==0){
					for(int m=(sub.length-1);m>i;m--){
						if(sub[m]==0){
						}
						else if(sub[m]!=0){
							bw1.write(Integer.toString(sub[i]));
							bw1.write(" ");
							break;
						}
					}
				}
			}	
		}
		else if(x==y){
			sub=new int[fxi.length];
			int i,j,k=0;
			for(i=0;i<=x-1;i++){
				for(j=k;j<=y-1&&j==i;j++){
					sub[i]=mod(fxi[i]-gxi[j]);
				}
				k=k+1;
			}
			for(i=(sub.length-1);i>=0;i--){
				if(sub[i]!=0){
					//System.out.println(sub[i]);
					bw1.write(Integer.toString(sub[i]));
					bw1.write(" ");
				}
				else if(sub[i]==0){
					for(int m=(sub.length-1);m>i;m--){
						if(sub[m]==0){
						}
						else if(sub[m]!=0){
						//	System.out.println(sub[i]);
							bw1.write(Integer.toString(sub[i]));
							bw1.write(" ");
							break;
						}
					}
				}
			}
		}
		bw1.newLine();
		//bw1.close();	
		}
		catch(Exception e){
		System.out.println("output.txt file not opened");	
		}			
		//Multiplication
		try{
		int mul[];
		int x=fxi.length,y=gxi.length;
			mul=new int[fxi.length+gxi.length-1];
			int i,j;
			for(i=0;i<mul.length;i++){				
				mul[i]=0;
			}
			for(i=0;i<x;i++){
				for(j=0;j<y;j++){
					mul[i+j]=mod(mul[i+j]+(fxi[i]*gxi[j]));	
				}
			}
			System.out.println("Mul size"+mul.length);
			System.out.println("Mxi size"+mxi.length);
			//Need to write PLDA here
			if((mul.length)>=mxi.length){
				mul=PLDA(mul,mxi)[0];
				System.out.println("After Mul PLDA");
				mul=modarr(mul);
				for(i=(mul.length-1);i>=0;i--){
					if(mul[i]!=0){
						System.out.println(mul[i]);
						bw1.write(Integer.toString(mul[i]));
						bw1.write(" ");
					}
					else if(mul[i]==0){
						for(int m=(mul.length-1);m>i;m--){
							if(mul[m]==0){
							}
							else if(mul[m]!=0){
								bw1.write(Integer.toString(mul[i]));
								bw1.write(" ");
								break;
							}
						}
					}
				}
			}
			else
			{
				for(i=(mul.length-1);i>=0;i--){
					if(mul[i]!=0){
						bw1.write(Integer.toString(mul[i]));
						bw1.write(" ");
					}
					else if(mul[i]==0){
						for(int m=(mul.length-1);m>i;m--){
							if(mul[m]==0){
							}
							else if(mul[m]!=0){
								bw1.write(Integer.toString(mul[i]));
								bw1.write(" ");
								break;
							}
						}
					}
				}
			}
		bw1.newLine();
		}
		catch(Exception e){
		System.out.println("output.txt file not opened");	
		}		
		//Division
		try{
		    int[] fxic1=new int[fxi.length];
		    int[] fxic2=new int[fxi.length];
		    int[] gxic1=new int[gxi.length];
		    int[] gxic2=new int[gxi.length];
		    int[] mxic=new int[mxi.length];
		    int[] temp;
			int[] Result;
		    int[] div;
		    if(CheckArr(gxi)){
		    	Result=new int[2];
		    	Result[0]=0;
		    	Result[1]=0;
		        System.out.printf("Divide by zero");
		      }
		    System.arraycopy(mxi, 0, mxic, 0, mxi.length);
		    System.arraycopy(fxi, 0, fxic1, 0, fxi.length);
		    System.arraycopy(fxi, 0, fxic2, 0, fxi.length);
		    System.arraycopy(gxi, 0, gxic1, 0, gxi.length);
		    System.arraycopy(gxi, 0, gxic2, 0, gxi.length);
		    if(fxi.length>=gxi.length){
		    	System.out.printf("Divide 1");
		        temp=EEAP(gxic1,mxic)[0];
		        System.out.printf("Divide 2");
		        gxi=new int[temp.length];
		        gxi=temp;
		    }
		    else{
		        temp=EEAP(fxic1,mxic)[0];
		         fxi=new int[temp.length];
		         fxi=temp;
		    }
		    Result=new int[fxi.length+gxi.length-1];
		    for(int i=0;i<gxi.length;i++)
		    for(int j=0; j<fxi.length;j++)
		        Result[i+j]=fxi[j]*gxi[i]+Result[i+j];
		    System.out.println("before PLDA");
		    div=PLDA(Result,mxic)[0];
		    System.out.println("after PLDA");
		    for(int i=(div.length-1);i>=0;i--){
				if(div[i]!=0){
					System.out.println(div[i]);
					bw1.write(Integer.toString(div[i]));
					bw1.write(" ");
				}
				else if(div[i]==0){
					for(int m=(div.length-1);m>i;m--){
						
						if(div[m]==0){
						}
						else if(div[m]!=0){
							System.out.println(div[m]);
							bw1.write(Integer.toString(div[i]));
							bw1.write(" ");
							break;
						}
					}
				}
			}
			
		bw1.close();	
		}
		catch(Exception e){
			System.out.println("Error while dividing");
		}
		}
		catch(Exception e){
		System.out.println("file not read");
		}
	}
	public static int mod(int c){
		if(c>=prime){
			b=c%prime;			
		}
		else if(c<prime&&c>=0){
			b=c;		
		}
		else if(c<0){
			b=c;
			while(b<0){
			b=b+prime;
			}
		}
		return b;
	}
	public static int[] modarr(int[] arr){
	    if(arr==null){
	        return null;
	    }
	   for(int i = 0 ; i<arr.length ; i++){
	       if(arr[i]>=prime){
	           arr[i]=arr[i]%prime;
	       }
	   }
	   for(int i=0 ; i<arr.length ;i++){
	       while(arr[i]<0){
	           arr[i]=arr[i]+prime;
	       }
	   }
	   return arr;
	}
	public static boolean CheckArr(int[] arr){
	    int i =0;
	    for(i=0;i<arr.length;i++){
	        if(arr[i]!=0){
	        	return false;
	        }
		}
        return true;
	}
	public static int EEA(int a, int b){        
		int s = 0, t = 1, old_s = 1, old_t = 0, r = prime, old_r = b, temp, q;
       
        if(b==0){
            return 0;
        }                
        while (r != 0)
        {
            q = old_r / r;
            temp=r;
            r = old_r - q*r;
            old_r = temp;
            temp=s;
            s = old_s - q*s;
            old_s = temp;
            temp = t;
            t = old_t - q*t;
            old_t = t;}
        old_s=old_s*a;
            

        return old_s;
		/*if(b == 0){             
            return new int[]{1,0};                        
        }
		else{
            int q = a/b; int r = a%b;
            int[] R = EEA(b,r);	    
            return new int[]{R[1], R[0]-(q*R[1])};
        }*/
    }
	public static int[][] PLDA(int[] r, int b[]){
	    r=modarr(r);
	    b=modarr(b);
	    int rMax=-1;
	    int rLead;
	    int mLead;
	    int[][] Result=new int[2][];
	    for(int i=r.length-1;i>=0;i--){
	        if(r[i]!=0){
	           rLead=r[i];
	           rMax=i;
	           break;
	            }
	    }
	    int rCnt=rMax;
	    int mMax=-1;
	        for(int i=b.length-1;i>=0;i--){
	        if(b[i]!=0){
	           mLead=b[i];
	           mMax=i;
	           break;
	            }
	    }
	        if(rMax<mMax){
	        Result[0]=r;
	        return Result; 
	    }
	    rLead=r[rMax];
	    mLead=b[mMax];
	    int[] midCal= new int[rMax+1];
	    int[] m=new int[rMax+1];
	    int[] q=new int[rMax-mMax+1];
	    int t;
	    for(int i=mMax;i>=0;i--){
	        m[i]=b[i];
	    }
	    for(int i=rMax-mMax;i>=0;i--){
	        q[i]=0;
	    }
	    while(!CheckArr(r) && rMax>=mMax){
	        t=EEA(rLead,mLead);
	        t=t%prime;
	        while(t<0)
	            t=t+prime;
	        q[rMax-mMax]=t;
	        for(int i =0 ; i<=rCnt ;i++)
	            midCal[i]=0;
	        for(int i=rMax ; i>=0 ; i--)
	            midCal[i]=m[i]*t;
	        int j=rMax;
	        while(midCal[j--]==0);
	        j++;
	        int k=j;
	        int c=rMax;
	        for(int i=0;i<=k;i++){
	            midCal[c--]=midCal[j--];
	        }
	        for(j=0;j<rMax-k;j++)
	            midCal[j]=0;
	        for(int i=rMax;i>=0;i--)
	            r[i]=r[i]-midCal[i];
	        r=modarr(r);
	        for(int i=rMax;i>=0;i--)
	            if(r[i]!=0){
	                rLead=r[i];
	                rMax=i;
	                break;
	            }
	    }   
	   	Result[0]=modarr(r);
	    Result[1]=modarr(q);
       
	    return Result;
        	
	}
	public static int[][] EEAP(int a[], int b[]){
		a=modarr(a);
	    b=modarr(b);
	    int[][] Q=new int[2][];
	    int[][] R=new int[2][];
	    int[][] Temp2;
	    int[] q;
	    int[] r;
	    int[] Temp;
	    int[] rCopy;
	    int[] copyA=new int[a.length];
	    int[] copyB=new int[b.length];
	    int i;
	    int aLead=0;
	    System.arraycopy(a, 0, copyA, 0, a.length);
	    System.arraycopy(b, 0, copyB, 0, b.length);
	    System.out.println("Entered EEAP");
	    for(i=a.length-1;i>=0;i--)
	        if(a[i]!=0)
	            break;
	    if(i>=0)
	       aLead=a[i];
	    if(CheckArr(b) || b==null){
	            r=new int[2];
	            r[0]=EEA(1,aLead);
	            r[0]=r[0]%prime;
	            while(r[0]<0)
	                r[0]=r[0]+prime;
	            r[1]=0;
	            R[0]=new int[r.length];
	            System.arraycopy(r, 0, R[0], 0, r.length);
	    }
	    else{
	        System.arraycopy(PLDA(copyA,copyB),0,Q,0,PLDA(copyA,copyB).length);
	        r=new int[Q[0].length];
	        System.arraycopy(Q[0],0,r,0, Q[0].length); 
	        rCopy=new int[r.length];
	        System.arraycopy(r, 0, rCopy, 0, r.length);
	        Temp2=EEAP(copyB,rCopy);
	        System.arraycopy(Temp2,0, R, 0,Temp2.length);
	        if(Q[1]!=null){
	            q=new int[Q[1].length];
	            System.arraycopy(Q[1],0,q,0,Q[1].length);
	            if(R[1]!=null && R[0]!=null){
	                Temp=new int[q.length+R[1].length-1];   
	                for(i=0 ; i<q.length;i++)
	                    for(int j=0; j<R[1].length ;j++)
	                        Temp[i+j]=q[i]*R[1][j]+Temp[i+j];
	                if(R[0].length>Temp.length){
	                    i=R[0].length-1;
	                    while(i>=Temp.length && i>=0){
	                          R[0][i]=R[0][i--];
	                    }
	                    while(i>=0)
	                         R[0][i]=R[0][i]-Temp[i--];
	                    Temp=new int[R[0].length];
	                    System.arraycopy(R[0], 0, Temp, 0, R[0].length);
	                }
	                else{
	                    i=Temp.length-1;
	                    while(i>=R[0].length && i>=0)
	                         Temp[i]=-Temp[i--];
	                    while(i>=0)
	                         Temp[i]=R[0][i]-Temp[i--];
	                }
	            R[0]=new int[R[1].length];
	            System.arraycopy(R[1],0,R[0],0,R[1].length);
	            R[1]=new int[Temp.length];
	            System.arraycopy(Temp, 0, R[1],0,Temp.length);
	            R[1]=modarr(R[1]);
	            R[0]=modarr(R[0]);
	            }
	            else
	                if(R[1]==null && R[0]!=null){
	                R[1]=new int[R[0].length];
	                System.arraycopy(R[0], 0, R[1], 0, R[0].length);
	                R[1]=modarr(R[1]);
	                R[0]=null;
	                }
	                else
	                    if(R[1]!=null && R[0]==null){
	                        Temp=new int[R[1].length+q.length-1];
	                        R[0]=new int[R[1].length];
	                        System.arraycopy(R[1], 0, R[0], 0, R[1].length);
	                        for(i=0;i<R[1].length;i++)
	                            for(int j=0;j<q.length;j++)
	                                Temp[i+j]=-R[1][i]*q[j]+Temp[i+j];
	                        R[1]=new int[Temp.length];
	                        System.arraycopy(Temp, 0, R[1], 0, Temp.length);  
	                        R[1]=modarr(R[1]);
	                        R[0]=modarr(R[0]);
	                    }                           
	}
	        else{
	        if(R[0]!=null && R[1]!=null){
	        Temp=new int[R[0].length];
	        System.arraycopy(R[0],0,Temp,0,R[0].length);
	        R[0]=new int[R[1].length];
	        System.arraycopy(R[1],0,R[0],0, R[1].length);
	        R[1]=new int[Temp.length];
	        System.arraycopy(Temp, 0, R[1], 0, Temp.length);
	        }
	            else
	            if(R[0]!=null && R[1]==null){
	                Temp=new int[R[0].length];
	                System.arraycopy(R[0],0,Temp,0,R[0].length);
	                R[0]=null;
	                R[1]=new int[Temp.length];
	                System.arraycopy(Temp, 0, R[1], 0, Temp.length);
	            }
	        else
	            if(R[0]==null && R[1]!=null){
	                Temp=new int[R[1].length];
	                System.arraycopy(R[1], 0, Temp, 0, R[1].length);
	                R[0]=new int[Temp.length];
	                System.arraycopy(Temp, 0,R[0], 0, Temp.length);
	                R[1]=null;
	                R[0]=modarr(R[0]);                
	            }
	        }   
	      }
	     return R;    
		}
	}
