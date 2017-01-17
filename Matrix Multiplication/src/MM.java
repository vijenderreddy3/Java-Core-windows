import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class MM {
	public static void main(String args[]) throws IOException
	{
		BufferedReader read1 = new BufferedReader(new FileReader("matrixA.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("matrixAnswer.txt"));
		
	
		int[] Count = MM.count(read1);
		int OneR = Count[0];
		int OneC = Count[1];
		read1.close();
		BufferedReader read2 = new BufferedReader(new FileReader("matrixB.txt"));			
		int[] Count1 = MM.count(read2);
		int TwoR = Count1[0];
		int TwoC = Count1[1];
		read2.close();
		if(OneC != TwoR)
		{
			writer.write("Sorry Multiplication cant be performed because of no match of first matrix column and second matrix row");
		}
		else
		{
			BufferedReader read3 = new BufferedReader(new FileReader("matrixA.txt"));
			int[][] A = MM.readMatrix(read3, OneR, OneC);
		    BufferedReader read4 = new BufferedReader(new FileReader("matrixB.txt"));
			int[][] B = MM.readMatrix(read4, TwoR, TwoC);
			int[][] result = new int[OneR][TwoC];
			for(int i = 0;i < OneR;i++)
			{
				for(int k = 0; k < TwoC;k++)
				{
					for(int j = 0; j< TwoR; j++)
					{
						result[i][k] += A[i][j] * B[j][k];
					}
				}
			}
			read3.close();
			read4.close();
		for(int i = 0; i < result.length; i++)
		{
			for(int j = 0; j < result[0].length; j++)
			{
				writer.write(result[i][j]+" ");
			}
			writer.newLine();
		}
		writer.close();
		}
	}
	public static int[][] readMatrix(BufferedReader matrix, int rows, int columns) throws IOException
	{
		int[][] temp = new int [rows][columns];
		int x= 0, y = 0;
		String line;
		while ((line = matrix.readLine()) != null && x < temp.length)
		{
			String[] values = line.split(" ");
        	for (String str : values)
        	{
        		int str_double = Integer.parseInt(str);
        		temp[x][y]=str_double;
        		y++;
        	}
        	y=0;
        	x++;
		}
		matrix.close();
		return temp;
	}
	
	public static int[] count(BufferedReader r) throws IOException
	{
		int row =0;
		int columns = 0;
		int[] rc = new int[2];
		int total = 0;
		String matrix = r.readLine();
		while(matrix!=null)
		{
			row++;
			String[] M = matrix.split(" ");
			for(String parts: M)
			{
				total++;
			}
			matrix = r.readLine();
		}
		columns = total/row;
		rc[0] = row;
		rc[1] = columns;
		return rc;
	}
}