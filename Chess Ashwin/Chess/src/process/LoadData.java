package process;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class LoadData {
	
	public static BufferedImage[] getBlackChess(){
		BufferedImage blackChessPiece[] = new BufferedImage[6];
		String url = "./res/";
		
		try{
			blackChessPiece[0] = ImageIO.read(new FileInputStream(url + "vua den.png"));
	    	blackChessPiece[1] = ImageIO.read(new FileInputStream(url + "hau den.png"));
	    	blackChessPiece[2] = ImageIO.read(new FileInputStream(url + "tuong den.png"));
	    	blackChessPiece[3] = ImageIO.read(new FileInputStream(url + "ma den.png"));
	    	blackChessPiece[4] = ImageIO.read(new FileInputStream(url + "xe den.png"));
	    	blackChessPiece[5] = ImageIO.read(new FileInputStream(url + "tot den.png"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return blackChessPiece;
	}
	
	public static BufferedImage[] getWhiteChess(){
		BufferedImage whiteChessPiece[] = new BufferedImage[6];
		String url = "./res/";
		
		try{
			whiteChessPiece[0] = ImageIO.read(new FileInputStream(url + "vua trang.png"));
			whiteChessPiece[1] = ImageIO.read(new FileInputStream(url + "hau trang.png"));
			whiteChessPiece[2] = ImageIO.read(new FileInputStream(url + "tuong trang.png"));
			whiteChessPiece[3] = ImageIO.read(new FileInputStream(url + "ma trang.png"));
			whiteChessPiece[4] = ImageIO.read(new FileInputStream(url + "xe trang.png"));
			whiteChessPiece[5] = ImageIO.read(new FileInputStream(url + "tot trang.png"));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return whiteChessPiece;
	}
	
	public static BufferedImage getImage(String name){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new FileInputStream(name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return image;
	}
}
