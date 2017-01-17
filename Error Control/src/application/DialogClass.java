package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogClass implements Initializable {

	public static final DialogClass instance = new DialogClass() ;
	private Stage stage = new Stage() ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

	}
	
	public void show() throws IOException{
		AnchorPane pane = FXMLLoader.load(getClass().getResource("dialog.fxml")) ;
		Scene scene = new Scene(pane) ;
		stage.setScene(scene);
		stage.show();
	}
	
	public void close(){
		stage.close(); 
	}
	
	@FXML public void openFile(ActionEvent e){
		ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "C://data//COSC635_2148_P2_DataReceived.txt");
		try {
			pb.start();
		} catch (IOException ev) {
			// TODO Auto-generated catch block
			ev.printStackTrace();
		}
		
		instance.close();
	}
}
