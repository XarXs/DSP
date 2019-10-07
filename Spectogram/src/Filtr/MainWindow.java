package Filtr;

import java.awt.*;
import java.awt.Window.Type;

import javax.swing.*;


public class MainWindow {

	FirstWaveForm ww ;
		
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new MainWindow().createGui();
            }
        });
		

	}	
	
	
	public void createGui() {
		ww = new FirstWaveForm();
		Type type = null;
		ww.setType(type.UTILITY);
		ww.setSize(new Dimension(1000,600));
		ww.setLocation(150, 10);
		ww.setVisible(true);
		
	}
	
}
