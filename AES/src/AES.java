import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import java.util.*;
import java.math.BigInteger;
public class AES {
	public AES(){

    }
	public static void main(String[] args) {
        AES test = new AES();
        test.Input();
    }
	public String[] ReadFile(){
        String[] lines = new String[4];
        try{
            BufferedReader in = new BufferedReader(new FileReader("Input.txt"));
            for(int i=0; i<4;i++)
                lines[i]=in.readLine();
        }
        catch(IOException e)
        {
            System.err.println("Could not read the Input.txt file");    
        }
        return lines;
    }
	public void output(String cipher,String key){
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Output.txt")));
            out.println(cipher);
            out.println(key);
            out.close();
        }
        catch (IOException e) {
            System.out.println("error writing to the file: src");
        }
    }
    Object[][][] k1;
    Object[][][] k2;
    int[] inPoly= new int[9];
    //Reading inputfile
    public void Input()
    {
        String[] in=ReadFile();
        String s = in[0].replaceAll("\\s", "");
        for(int i=0;i<9;i++)
            inPoly[i]=Integer.parseInt(""+s.charAt(i));
        RountCount();
        String keyString = in[1];
        Object[][] key = ConvertStringToState(keyString);
        Object[][] keyD = ConvertStringToState(keyString);
        String input =in[2];
        Object[][] inputState = ConvertStringToState(input);
        k1 = KeyExpansion(key);
        k2 = KeyExpansion(key);
        String cipherText = Encryption(key,inputState);
        String plaintext = Decryption(in[3],keyD);
        output(cipherText,plaintext);	
    }
    String[] round= {"01","02","04","08","10","20","40","80","1b","36"};
    public void RountCount() {
        int[] a = ConvertHextToBinary("02");
        round[0] = "01";
        for (int j = 1; j < 10; j++) {
            int[] Rc = Multiply(a, ConvertHextToBinary(round[j-1]), false);
            round[j] = ConvertBinaryToHex(Rc);
        }   
     }
    public Object[][] SubBytes(Object[][] a){
        for(int row=0;row<4;row++){
            for(int col=0;col<4;col++){
                int[] bin = ConvertHextToBinary((String) a[row][col]);
                a[row][col]= SubBytesElement(bin);
            }
        }
        return a;
    }
    public String SubBytesElement(int[]a){
    	int[] constColEncrypt = {1,1,0,0,0,1,1,0};
    	int[][] sBoxEncrypt = {
                {1,0,0,0,1,1,1,1},
                {1,1,0,0,0,1,1,1},
                {1,1,1,0,0,0,1,1},
                {1,1,1,1,0,0,0,1},
                {1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,0,0},
                {0,0,1,1,1,1,1,0},
                {0,0,0,1,1,1,1,1}};
    	int[][] products = new int[8][8];
        int[] result = new int[8];
        a = EEAP(Trim(a), inPoly)[0];
        a = Standardize(a,8);
        a = ConRowToCol(a); 
        for(int i=0;i<8;i++){
            int[] temp= new int[8];

            for(int j=0;j<a.length;j++)
                temp[j]= sBoxEncrypt[i][j] * a[j];					
            products[i]=temp;
        }
        
        for(int i=0;i<8;i++){
            for(int j=1;j<8;j++)
                products[i][j]=products[i][j]^products[i][j-1];					
            result[i]=products[i][7]^constColEncrypt[i]; 					
        }
        
        String hexAnswer = ConvertBinaryToHex(ConRowToCol(result));
        return hexAnswer;
    }
    public Object[][] SR(Object[][]a){	
        Object t = a[0][1];
        for(int i=0;i<3;i++)
            a[i][1]=a[i+1][1];
        a[3][1]=t;
        for(int j=0;j<2;j++){
            Object t1 = a[0][2];
            for(int i=0;i<3;i++)
                a[i][2]=a[i+1][2];
            a[3][2]=t1;
        }
        for(int j=0;j<3;j++){
            Object t1 = a[0][3];
            for(int i=0;i<3;i++)
                a[i][3]=a[i+1][3];
            a[3][3]=t1;
        }
        return a;
    }public Object[][] MC(Object[][]a){
        Object[][] result = new Object[4][4];
        for(int j=0;j<4;j++){
            for(int k=0;k<4;k++){
                int[][] constantRow = new int[4][];
                int[][] bites = new int[4][];
                int[][] products = new int[4][];
                int[] sum = new int[8];
                String[][] EncSC = {
                		{"02","03","01","01"},
                		{"01","02","03","01"},
                		{"01","01","02","03"},
                		{"03","01","01","02"}};
                for(int i=0;i<4;i++){
                    constantRow[i]=ConvertHextToBinary((String)EncSC[k][i]);//turn the byte in the constant row to polynomial
                    bites[i]=(ConvertHextToBinary((String)a[j][i]));
                    products[i]= Standardize(Multiply(bites[i], constantRow[i], false),8);
                    sum=Standardize(Add(products[i], sum, false),8);
                }
                String s = ConvertBinaryToHex(sum);
                result[j][k]=s;
            }
        }
        return result;
    }
    public Object[][] ARK(Object[][]a,Object[][]b){
        Object[][] result = new Object[4][4];
        for(int col=0;col<4;col++){
            for(int i=0;i<4;i++){
                int[] x = Standardize(ConvertHextToBinary((String)a[col][i]),8);
                int[] y = Standardize(ConvertHextToBinary((String)b[col][i]),8);
                int[] sum = Standardize(Add(x, y, false),8);
                String s = ConvertBinaryToHex(sum);
                result[col][i]=s ;
            }
        }
        return result;
    }
    public String Encryption(Object[][] key, Object[][] inputState){
        Object[][] ActState = ARK(key, inputState);
        for(int i=0;i<9;i++){
            ActState = SubBytes(ActState);
            ActState = SR(ActState);
            ActState = MC(ActState);
            ActState = ARK(ActState,k1[i]);
        }
        ActState=SubBytes(ActState);
        ActState=SR(ActState);
        ActState=ARK(ActState,k1[9]);
        ActState = CorrectZeros(ActState);
        String s = ConvertStateToString(ActState);
        return s;
    }

    public Object[][] ISB(Object[][] a){
        for(int row=0;row<4;row++){
            for(int col=0;col<4;col++){
                a[row][col]= InvSubBytesElement(ConvertHextToBinary((String) a[row][col]));
            }
        }
        a= CorrectZeros(a);
        return a;
    }

    public String InvSubBytesElement(int[]a){
    	int[] DecrCCOL = {1,0,1,0,0,0,0,0};
    	int[][] products = new int[8][8];
        int[] result = new int[8];
        int[][] ISBox={
        		{0,0,1,0,0,1,0,1},
        		{1,0,0,1,0,0,1,0},
        		{0,1,0,0,1,0,0,1},
        		{1,0,1,0,0,1,0,0},
                {0,1,0,1,0,0,1,0},
                {0,0,1,0,1,0,0,1},
                {1,0,0,1,0,1,0,0},
                {0,1,0,0,1,0,1,0}};
        a = ConRowToCol(a);
        for(int i=0;i<8;i++){
            int[] temp= new int[8];
            for(int j=0;j<a.length;j++)
                temp[j]= ISBox[i][j] * a[j];
            products[i]=temp;
        }
        for(int i=0;i<8;i++){
            for(int j=1;j<8;j++)
                products[i][j]=products[i][j]^products[i][j-1];
            result[i]=products[i][7]^DecrCCOL[i]; 
        }
        result= ConRowToCol(result);
        result = EEAP(Trim(result), inPoly)[0];
        result = Standardize(result,8);
        String hexAnswer = ConvertBinaryToHex(result);
        return hexAnswer;
    }
    public Object[][] ISR(Object[][]a){
        Object t = a[3][1];
        for(int i=3;i>0;i--)
            a[i][1]=a[i-1][1];
        a[0][1]=t;
        for(int j=0;j<2;j++){
            Object t1 = a[3][2];
            for(int i=3;i>0;i--)
                a[i][2]=a[i-1][2];
            a[0][2]=t1;
        }
        for(int j=0;j<3;j++){
            Object t1 = a[3][3];
            for(int i=3;i>0;i--)
                a[i][3]=a[i-1][3];
            a[0][3]=t1;
        }
        return a;
    }
    public Object[][] IMC(Object[][]a){
        Object[][] result = new Object[4][4];
        for(int j=0;j<4;j++){
            for(int k=0;k<4;k++){
                int[][] constantRow = new int[4][];
                int[][] bites = new int[4][];
                int[][] products = new int[4][];
                int[] sum = new int[8];
                String[][] DecSC = {
                		{"0e","0b","0d","09"},
                		{"09","0e","0b","0d"},
                		{"0d","09","0e","0b"},
                		{"0b","0d","09","0e"}};

                for(int i=0;i<4;i++){
                    constantRow[i]=ConvertHextToBinary((String)DecSC[k][i]);//turn the byte in the constant row to polynomial
                    bites[i]=(ConvertHextToBinary((String)a[j][i])); //
                    products[i]= Standardize(Multiply(bites[i], constantRow[i], false),8);
                    sum=Standardize(Add(products[i], sum, false),8);
                }
                String s = ConvertBinaryToHex(sum);
                result[j][k]=s;
            }
        }
        result = CorrectZeros(result);
        return result;
    }
    public String Decryption(String ciphert,Object[][] keyD){
        Object[][] ActState = ConvertStringToState(ciphert);
        ActState = ARK(k1[9],ActState);
        for(int i=8;i>=0;i--){
            ActState=ISR(ActState);
            ActState=ISB(ActState);
            ActState=ARK(ActState,k2[i]);	
            ActState=IMC(ActState);
        }
        ActState = ISR(ActState);
        ActState = ISB(ActState);
        ActState = ARK(ActState,keyD);
        ActState = CorrectZeros(ActState);
        String s = ConvertStateToString(ActState); 
        return s;
    }
    public Object[][][] KeyExpansion(Object[][]a){
        Object[][] prevKey = a;
        Object[][][] result= new Object[10][][];
        for(int roundNo=0;roundNo<10;roundNo++){
            Object[][] currKey = new Object[4][];
            Object[] intermediate1 = prevKey[3];
            Object[] temp = intermediate1.clone();
            Object t = temp[0];
            for(int i=0;i<3;i++)
                temp[i]=temp[i+1];
            temp[3]=t;
            for(int i=0;i<4;i++)
                temp[i]=SubBytesElement(ConvertHextToBinary((String)temp[i])); //rotate
            int[] t0=ConvertHextToBinary((String)temp[0]);
            int[] RC=ConvertHextToBinary(round[roundNo]);
            for(int i=0;i<8;i++)
                t0[i]=t0[i]^RC[i];
            temp[0]= ConvertBinaryToHex(t0);		
            int[] res;
            int[] curtemp= ConvertHextToBinary((String)temp[0]);
            Object intermediate = prevKey[0][0];
            int[] pretemp= ConvertHextToBinary((String)intermediate);
            res = Add(curtemp, pretemp, false);
            String re = ConvertBinaryToHex(res);
            for(int i=0;i<4;i++){
                int[] currtemp= ConvertHextToBinary((String)temp[i]);
                int[] prevtemp= ConvertHextToBinary((String)prevKey[0][i]);
                for(int b=0;b<8;b++)
                    currtemp[b]= currtemp[b]^prevtemp[b];
                temp[i]= ConvertBinaryToHex(currtemp);
            }
            currKey[0]=temp;
            Object[] resultcol1= new Object[4];
            for(int i=0;i<4;i++)
                resultcol1[i]=ConvertBinaryToHex(Add(ConvertHextToBinary((String)prevKey[1][i]),ConvertHextToBinary((String)currKey[0][i]), false)); //xoring an entire column
            currKey[1]=resultcol1;
            Object[] resultcol2= new Object[4];
            for(int i=0;i<4;i++)
                resultcol2[i]=ConvertBinaryToHex(Add(ConvertHextToBinary((String)prevKey[2][i]),ConvertHextToBinary((String)currKey[1][i]), false)); //xoring an entire column
            currKey[2]=resultcol2;
            Object[] resultcol3= new Object[4];
            for(int i=0;i<4;i++)
                resultcol3[i]=ConvertBinaryToHex(Add(ConvertHextToBinary((String)prevKey[3][i]),ConvertHextToBinary((String)currKey[2][i]), false)); //xoring an entire column
            currKey[3]=resultcol3;

            result[roundNo]=currKey;
            prevKey=currKey;
        }
        return result;
    }
    public int[] Standardize(int[] a,int b){
        int[] temp=new int[b];
        int j=temp.length-1;
        for(int i=a.length-1;i>=0;i--)
        {temp[j]=a[i];j--;}
        return temp;
    }

    int pr=2;
    public int[][] EEAP(int[] a, int[] b){ //(the first return parameter of the EEAP return statement is the multiplicative inverse)
        int[][] UthenV = new int[2][];
        a = mod(a);
        b = mod(b);
        if(CheckArr(b)){
            a=Trim(a);
            int[] q = {((((EEA(a[0],pr)[0])%pr)+pr)%pr)};
            int[] z = {0};
            UthenV[0]=q;
            UthenV[1]=z;
            return UthenV;
        }else{
            int[][]Q = PLDA(a,b);
            int[] q = Q[0];
            int[] r = Q[1];
            int[][] R = EEAP(b,r);
            int[] firstp = mod(R[1]);
            int[] mu = Multiply(q,R[1],false);
            int[] sub = Subtract(R[0],mu,false);
            int[] secondp = mod(sub);
            R[0]=firstp;
            R[1]=secondp;
            return(R);
        }
    }
    public int[] EEA(int a, int b){ //Extended Euclidean Algorithm to find multiplicative inverse
        if(b == 0){
            return new int[]{1,0};
        }else{
            int q = a/b; int r = a%b;
            int[] R = EEA(b,r);
            return new int[]{R[1], R[0]-q*R[1]};
        }
    }
    public int[] Add(int[] a, int[] b, boolean fromPLDA){
        int[] result = new int[Math.max(a.length, b.length)];
        if(a.length>b.length){
            result = a.clone();
            int j = result.length-1;
            for(int i=0;i<b.length;i++){
                result[j] ^= b[b.length-1-i];
                j--;
            }
        }else{
            result = b.clone();
            int j = result.length-1;
            for(int i=0;i<a.length;i++){
                result[j] ^= a[a.length-1-i];
                j--;
            }
        }
        if(fromPLDA) return result;
        else return Trim(PLDA(result,inPoly)[1]);
    }

    public int[] Trim(int[] a){ 
        if(!CheckArr(a)){
            int front_zeros=0;
            for(int i=0;i<a.length;i++){
                if(a[i] == 0)
                    front_zeros++;
                else break;
            }
            int[] trimmed = new int[a.length-front_zeros];
            for(int i =0;i<a.length-front_zeros;i++){
                trimmed[i]=a[i+front_zeros];
            }
            return trimmed;
        }
        else
        {int[] trimzero = {0};  return trimzero;}
    }       
    public int[] mod(int[] a){
		for(int i=0;i<a.length;i++){
    		a[i]=a[i]%pr;			
			while(a[i]<0){
			a[i]+=pr;
			}
		}
		return a;
	}
    public boolean CheckArr(int[] a ){
        boolean CheckArr=true;
        for(int i=0;i<a.length;i++){
            if(a[i]!=0){ 
                CheckArr=false; break;}
        }
        return CheckArr;
    }
    public int[] Multiply(int[] a, int[]b, boolean FromPLDA){
        if(CheckArr(a) || CheckArr(b))
            return new int[]{0};
        else{
            int[] result = new int[(a.length+b.length)-1];
            for(int i=0;i<a.length;i++)
                for(int j=0;j<b.length;j++)
                    result[i+j] ^= a[i]*b[j];
            if(FromPLDA)
                return result;
            else return Trim(PLDA(result,inPoly)[1]);
        }
    }
    public int[][]  PLDA(int[] n, int[] d){ 
        n = mod(n);
        d = mod(d);
        int[] q = {0};
        int[] r = n;
        while(!CheckArr(r) && ( r.length-1 >= d.length-1)){
            int tco = (((r[0]*(EEA(d[0],pr)[0])%pr)+pr)%pr);
            int t_degree = (((r.length-1) - (d.length-1))+1);
            int[] t = new int[t_degree];
            t[0]=tco;
            int[] t_times_d = Multiply(t,d,true);
            r = Subtract(r,t_times_d,true);
            q = Add(q,t,true);
            r = mod(r);
            r = Trim(r);
            q = mod(q);
            q = Trim(q);
        }
        int[][] QthenR = new int[2][]; 
        QthenR[0]=q;
        QthenR[1]=r;
        return QthenR;
    }
    public int[] Subtract(int[] a, int[] b, boolean FromPLDA){
        int[] result = new int[Math.max(a.length, b.length)];
        if(a.length>b.length){
            result = a.clone();
            int j = result.length-1;
            for(int i=0;i<b.length;i++){
                result[j] ^= b[b.length-1-i];
                j--;
            }
        }else{
            result = b.clone();
            int j = result.length-1;
            for(int i=0;i<a.length;i++){
                result[j] ^= a[a.length-1-i];
                j--;
            }
        }
        if(FromPLDA) return Trim(result);
        else return Trim(PLDA(result,inPoly)[1]);
    }
    public static Object[][] ConvertStringToState(String a){
        Object[][] state = new Object[4][4];
        int count=0;
        for(int i=0;i<32;i+=2){
            String b =  a.substring(i, i+2);
            if(i<8){
                state[0][count]=b; if(count==3) count=-1;}
            if(i<16 &&i >7){
                state[1][count]=b;if(count==3) count=-1;}
            if(i<24 && i>15){
                state[2][count]=b;if(count==3) count=-1;}
            if(i<32 && i>23)
                state[3][count]=b;
            count++;
        }
        return state;
    }
    public static String ConvertStateToString(Object[][] a){
        String s="";
        for(int col=0;col<4;col++){
            for(int row=0;row<4;row++)
                s+=(String)a[col][row];
        }
        return s;
    }
    public static Object[][] CorrectZeros(Object[][]a){
        for(int col=0;col<4;col++){
            for(int row=0;row<4;row++){
                String s= (String)a[col][row];
                if(s.length()==1){
                    s="0"+s;
                    a[col][row]=s;
                }
            }
        }
        return a;
    }
    public static String ConvertBinaryToHex(int[] a){
        String bin="";
        for(int i=0;i<a.length;i++)
            bin += ""+a[i];
        String result = Long.toHexString(Long.parseLong(bin, 2));
        return result;
    }
    public static int[] ConRowToCol(int[] a){

        int[] reversed = a.clone();
        int j=0;
        for(int i=reversed.length-1;i>=0;i--)
        {reversed[i]=a[j]; j++;}
        return reversed;
    }
    public static int[] ConvertHextToBinary(String a){
        int[] result = new int[8];
        String s = new BigInteger(a, 16).toString(2);
        char[] arr = s.toCharArray();
        int j=result.length-1;
        for(int i=arr.length-1;i>=0;i--){
            result[j]=Integer.parseInt(""+arr[i]);
            j--;
        }
        return result;
    }
}