import java.util.Random;

public class Test3 {
	private int pseudoNumber=20;
	private int packetLoss=0;
	public static void main(String[] args) {
		Test3 obj=new Test3();
		for(int i=0;i<98;i++){
			boolean check = false;
			check = obj.errorControl();
			System.out.println("sending packet:"+(i+1));
			if (!check) {
				//System.out.println("sending packet:"+i);
			}else{
				System.out.println("failed to sending packet:"+(i+1));
				--i;
			}
			
		}
		System.out.println("Number of packets lost:"+obj.packetLoss);
	}
	public boolean errorControl() {
		boolean condition = false;
		int random = (int )(Math.random() * 100 + 1);
		System.out.println("randomNumber::::::::" + random);

		if (pseudoNumber > random) {
			packetLoss++;

			return !condition;
		}
		return condition;

	}

}
