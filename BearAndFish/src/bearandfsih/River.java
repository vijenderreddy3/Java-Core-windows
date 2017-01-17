package bearandfsih;
import java.util.*;
public class River {
public String river[];
public int[] count={0,0};
	public int[] river(int size,int animals){
		river=new String[size];
		for(int i=0;i<size;i++){
			river[i]="X";
		}
		animal(size,animals);
	return count;
	}
	private void animal(int size,int NOA){
		Bear bear=new Bear();
		Fish fish=new Fish();
		for(int i=0;i<NOA;i++){
			Random random = new Random();
			Random random2 = new Random();
			int set=random2.nextInt(size);
			int animal=random.nextInt(2);
			if(animal==0){
				if(river[set]=="X"){
					river[set]=bear.creat();
					count[0]=count[0]+1;
				}
				else if(river[set]!="X"){
					river[set]=river[set];
				}
			}
			else if(animal==1){
				if(river[set]=="X"){
					river[set]=fish.creat();	
					count[1]=count[1]+1;
				}
				else if(river[set]!="X"){
					river[set]=river[set];
				}
			}
		}
		for(int i=0;i<size;i++){
			System.out.print(river[i]+" ");
		}
		System.out.println("");
	}

}

