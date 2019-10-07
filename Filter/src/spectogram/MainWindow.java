package spectogram;

import java.awt.*;
import javax.swing.*;


public class MainWindow {

	WaveWindow ww ;
		
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new MainWindow().createGui();
            }
        });
		

	}	
	
	
	public void createGui() {
		ww = new WaveWindow();
		ww.setSize(new Dimension(1500,1000));
		ww.setLocation(150, 10);
		ww.setVisible(true);
		
	}
	
}
