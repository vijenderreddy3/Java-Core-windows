//package bearandfsih;
import java.util.*;
public class EcoSystem
{

	public static void main(String[] args) {
		int sizeOfTheRiver=Integer.parseInt(args[0]);
		int noOfAnimals=Integer.parseInt(args[1]);
		int rounds=Integer.parseInt(args[2]);
		if(args.length!=3){
			System.out.println("Enter 3 arguments");
			System.exit(0);
		}
		//EcoSystem ES=new EcoSystem();
		int[] count=new int[2];
		River r=new River();
		
		count=r.river(sizeOfTheRiver,noOfAnimals);
		int bc=count[0];
		int fc=count[1];
		System.out.println("bear and fish count:"+bc+","+fc);
		Random ran=new Random();
		int constant;
		for(int i=0;i<rounds;i++){
			System.out.println("Round "+i);
			for(int j=0;j<sizeOfTheRiver;j++){
				if(r.river[j]=="X"){
					r.river[j]=r.river[j];
				}
				else /*if(r.river[j]!="X")*/{
					constant=ran.nextInt(3);
					switch(constant){
					case 0:
						System.out.println(r.river[j]+" is not moved from it's location.");
						break;
					case 1:
						if(j==0){
							System.out.print(r.river[j]+" can not move to left. ");
							break;
						}
						else{
						System.out.print(r.river[j]+" moved to the left from position "+(j+1)+" ,");
						int loc=j;
						Bear bl=new Bear();
						Fish fl=new Fish();
						if(r.river[loc-1]=="X"){
							r.river[loc-1]=r.river[loc];
							r.river[loc]="X";
							System.out.println();
						}
						else if(r.river[loc-1].charAt(0)=='B'&&r.river[loc].charAt(0)=='B'){
							System.out.print(r.river[loc]+" is collide with "+r.river[loc-1]);
							r.river[loc-1]=bl.collide(bc);
							r.river[loc]="X";
							bc=bc+1;
						}
						else if(r.river[loc-1].charAt(0)=='F'&&r.river[loc].charAt(0)=='B'){
							r.river[loc-1]=bl.eat(r.river[loc],r.river[loc-1],bc);
							r.river[loc]="X";
							bc=bc+1;
						}
						else if(r.river[loc-1].charAt(0)=='B'&&r.river[loc].charAt(0)=='F'){
							r.river[loc-1]=bl.eat(r.river[loc-1],r.river[loc],bc);
							r.river[loc]="X";
							bc=bc+1;
						}
						else if(r.river[loc-1].charAt(0)=='F'&&r.river[loc].charAt(0)=='F'){
							r.river[loc-1]=fl.eat(r.river[loc],r.river[loc-1],fc);
							r.river[loc]="X";
							fc=fc+1;
						}
						}
						break;
						
					case 2:
						if(j==sizeOfTheRiver-1){
							System.out.print(r.river[j]+" can not move to the right from end of the river ");
						}
						else{
							System.out.print(r.river[j]+" moved to the right from position "+(j+1)+" ,");
							int loc=j;
							Bear br=new Bear();
							Fish fr=new Fish();
							if(r.river[loc+1]=="X"){
								//System.out.println("Entered if");
								r.river[loc+1]=r.river[loc];
								r.river[loc]="X";
								System.out.println();
							}else if(r.river[loc+1].charAt(0)=='B'&&r.river[loc].charAt(0)=='B'){
								System.out.print(r.river[loc]+" is collide with "+r.river[loc+1]);
								r.river[loc+1]=br.collide(bc);
								r.river[loc]="X";
								bc=bc+1;
							}
							else if(r.river[loc+1].charAt(0)=='F'&&r.river[loc].charAt(0)=='B'){
								r.river[loc+1]=br.eat(r.river[loc], r.river[loc+1],bc);
								r.river[loc]="X";
								bc=bc+1;
							}
							else if(r.river[loc+1].charAt(0)=='B'&&r.river[loc].charAt(0)=='F'){
								r.river[loc+1]=br.eat(r.river[loc+1], r.river[loc],bc);
								r.river[loc]="X";
								bc=bc+1;
							}
							else if(r.river[loc+1].charAt(0)=='F'&&r.river[loc].charAt(0)=='F'){
								r.river[loc+1]=fr.eat(r.river[loc], r.river[loc+1],fc);
								r.river[loc]="X";
								fc=fc+1;
							}
							
						}
						break;
					}
					
				}
			}
			System.out.println("");
			for(int m=0;m<sizeOfTheRiver;m++){
				System.out.print(r.river[m]+" ");
			}
			System.out.println("");
		}
		System.out.println("End of simulation for "+rounds+" rounds");
	}
}
