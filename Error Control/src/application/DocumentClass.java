package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class DocumentClass implements Initializable {
	
	@FXML private TextField sendTo ;
	@FXML private TextField port ;
	@FXML private TextField packetLoss ;
	@FXML private Label totalPacket ;
	@FXML private Label sent ;
	@FXML private Label loss ;
	@FXML private RadioButton GBN ;
	@FXML private RadioButton SAW ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			new Thread(new Runnable(){
				@Override
				public void run() {
					SAWServer server = null;
					try {
						server = new SAWServer(7650);
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						server.receive() ;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start(); 
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					GBNServer server = null;
					try {
						server = new GBNServer(7653);
					} catch (SocketException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						server.receive() ;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
	}
	
	@FXML public void send(ActionEvent e) throws Exception{
		if(SAW.isSelected()){
			SAWClient client = new SAWClient(sendTo.getText().trim() , Integer.parseInt(port.getText().trim()) ,
					 Integer.parseInt(packetLoss.getText().trim()) , totalPacket , loss , sent) ;
			client.send() ;
		}else{
			GBNClient client = new GBNClient(sendTo.getText().trim() , Integer.parseInt(port.getText().trim()) ,
					 Integer.parseInt(packetLoss.getText().trim()) , totalPacket , loss , sent) ;
			client.send() ;
		}
	}
	
	@FXML public void receive(ActionEvent e){
		ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "C://data//COSC635_2148_P2_DataReceived.txt");
		try {
			pb.start();
		} catch (IOException ev) {
			// TODO Auto-generated catch block
			ev.printStackTrace();
		}
	}

}
