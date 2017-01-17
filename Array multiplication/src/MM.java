import java.io.*;
public class MM {
	static BufferedReader A1;
	static BufferedReader A2;
	static BufferedReader B1;
	static BufferedReader B2;
	public static void main(String[] args) throws Exception{
		A1=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "matrixA.txt"));
		A2=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "matrixA.txt"));
		B1=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "matrixB.txt"));
		B2=new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "matrixB.txt"));
		BufferedWriter bw= new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" +"matrixAnswer.txt"));
		MM ob=new MM();
		int MA[][]=ob.readMatrix(A1,A2);
		int MB[][]=ob.readMatrix(B1,B2);
		if(MA[0].length!=MB.length){
			bw.write("Multiplication can not be possible, First matrix number of columns does not match with second matrix number of rows.");
			bw.close();
			System.exit(0);
		}
		int Answer[][]=new int[MA.length][MB[0].length];
		for(int i=0;i<MA.length;i++){
			for(int k=0;k<MB[0].length;k++){
				int current=0;
				for (int j=0;j<MB.length;j++){
					current=current+(MA[i][j]*MB[j][k]);
				}
				Answer[i][k]=current;
				bw.write(Integer.toString(Answer[i][k]));
				bw.write(" ");
			}
			bw.newLine();
		}
		bw.close();
	}
	public int[][] readMatrix(BufferedReader br,BufferedReader br2)throws Exception{
		int count=0;
		while((br.readLine())!=null){
			count+=1;
		}
		br.close();
		String[] input=new String[count];
		for(int a=0;a<input.length;a++){
			input[a]=br2.readLine();		
		}
		br2.close();
		String M[][]=new String[input.length][];
		for(int x=0;x<input.length;x++){
		M[x]=input[x].split(" ");
		}
		int temp[][]=new int[M.length][M[0].length];
		for(int i=0;i<M.length;i++){
			for(int j=0;j<M[i].length;j++){
				temp[i][j]=Integer.parseInt(M[i][j]);
			}
		}
		return temp;
	}
}