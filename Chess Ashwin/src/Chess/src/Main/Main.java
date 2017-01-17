package Main;

import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

import view.MainMenu;


public class Main {

	static{
		/*try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try 
	    {
	      UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
	    } 
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		new MainMenu();
	}

}
