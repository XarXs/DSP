package Filtr;
import java.awt.*;

import javax.swing.*;

import SinWave.*;

public class SecondWaveForm extends JPanel {
	
	private static final long serialVersionUID = 1L;
	int width = 1000;
	int height = 200;
	
	SineWave sv;
	
	public SecondWaveForm(SineWave s) {
		sv = new SineWave(1000,50,.07f,1000,200);
		sv.setAmplitude(s.getAmplitude());
		this.setLayout(new BorderLayout());
		this.add(sv, BorderLayout.CENTER);
	}
		
	public void setSinWave(SineWave s) {
		sv.setAmplitude(s.getAmplitude());		
	}
	public void setAmplitude(double[]s) {
		sv.setAmplitude(s);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(this.width, this.height);
	}
	
	public void setSize(Dimension d) {
		this.width = d.width;
		this.height = d.height;
	}
	
}
