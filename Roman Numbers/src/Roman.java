
public class Roman {
	private int number;
	private String one="I";
	private String five="V";
	private String ten="X";
	private String fifty="L";
	private String hundred="C";
	private String fivehundred="D";
	private String thousand="M";
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumber() {
		return number;
	}
	public String converToRoman(int intNo){
		this.setNumber(intNo);
		String roman="This is Roman";
		int enteredNo=this.number;
		
		System.out.println("Entered Integer:"+enteredNo);
		
		int thousandsValue=(enteredNo/1000);
		int hundredsValue=((enteredNo%1000)/100);
		int tensValue=(((enteredNo%1000)%100)/10);
		int onesValue=(((enteredNo%1000)%100)%10);
		
		roman=thousandsPlace(thousandsValue)+hundredsPlace(hundredsValue)+tensPlace(tensValue)+onesPlace(onesValue,enteredNo);
		return roman;
	}
	private String onesPlace(int onesValue,int enteredNo){
		String value="";
		if(onesValue==0 && enteredNo==0){
			value="nulla";
		}
		else if(onesValue>=1 && onesValue<=3){
			for(int i=1;i<=onesValue;i++){
				value+=one;
			}
		}
		else if(onesValue>=4 && onesValue<=8){
			value=fivesPlace(onesValue);	
		}
		else if(onesValue==9){
			value="IX";
		}
		return value;
	}
	private String fivesPlace(int fivesValue){
		String value="";
		if(fivesValue==4){
			value="IV";
		}
		else if(fivesValue>=5 && fivesValue<=8){
			if(fivesValue==5){
				value=five;
			}else{
				value=five;
				for(int i=1;i<=fivesValue-5;i++){
					value+=one;
				}
			}	
		}
		return value;
	}
	private String tensPlace(int tensValue){
		String value="";
		if(tensValue==0){
			value="";
		}
		else if(tensValue>=1 && tensValue<=3){
			for(int i=1;i<=tensValue;i++){
				value+=ten;
			}
		}
		else if(tensValue>=4 && tensValue<=8){
			value=fiftysPlace(tensValue);	
		}
		else if(tensValue==9){
			value="XC";
		}
		
		return value;
	}
	private String fiftysPlace(int fiftysValue){
		String value="";
		if(fiftysValue==4){
			value="XL";
		}
		else if(fiftysValue>=5 && fiftysValue<=8){
			if(fiftysValue==5){
				value=fifty;
			}else{
				value=fifty;
				for(int i=1;i<=fiftysValue-5;i++){
					value+=ten;
				}
			}	
		}		
		return value;
	}
	private String hundredsPlace(int hundredsValue){
		String value="";
		if(hundredsValue==0){
			value="";
		}
		else if(hundredsValue>=1 && hundredsValue<=3){
			for(int i=1;i<=hundredsValue;i++){
				value+=hundred;
			}
		}
		else if(hundredsValue>=4 && hundredsValue<=8){
			value=fiveHundredsPlace(hundredsValue);	
		}
		else if(hundredsValue==9){
			value="CM";
		}
		return value;
	}
	private String fiveHundredsPlace(int fiveHundreadsValue){
		String value="fiveHundredsPlace";
		if(fiveHundreadsValue==4){
			value="CD";
		}
		else if(fiveHundreadsValue>=5 && fiveHundreadsValue<=8){
			if(fiveHundreadsValue==5){
				value=fivehundred;
			}else{
				value=fivehundred;
				for(int i=1;i<=fiveHundreadsValue-5;i++){
					value+=hundred;
				}
			}	
		}
		return value;
	}
	private String thousandsPlace(int thousandsValue){
		String value="";
		if(thousandsValue==0){
			value="";
		}
		else if(thousandsValue>=1 && thousandsValue<=3){
			for(int i=1;i<=thousandsValue;i++){
				value+=thousand;
			}
		}
		return value;
	}
		
}
