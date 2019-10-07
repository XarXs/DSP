package spectogram;

import java.awt.*;

import javax.sound.sampled.AudioFormat;
import javax.swing.*;

import FFT.Fft;


public class Spektogram extends JPanel {

	private static final long serialVersionUID = 1L;
	
	byte[] data;
	int size;
	int windowSize = 2048;
	int windowing = 0;
	double overlap = 0.0;
	AudioFormat format;
	int width = 500;
	int height = 500;
	
	//methods
	public void setData(byte[] wave) {
		if(wave == null) {
			throw new NullPointerException();
		}
		
		data = wave;
		size = data.length;
		System.out.println("dlugosc data = "+size);
		System.out.println("rozmiar okna = "+windowSize);
	}
	
	public void setAudioFormat(AudioFormat format) {
		this.format = format;
		System.out.println(this.format.toString());
	}
	
	public void setProbe(String windowSize) {
		
		if(windowSize.equals("16")) {
			this.windowSize = 16;
		}else if(windowSize.equals("32")) {
			this.windowSize = 32;
		}else if(windowSize.equals("64")) {
			this.windowSize = 64;
		}else if(windowSize.equals("128")) {
			this.windowSize = 128;
		}else if(windowSize.equals("256")) {
			this.windowSize = 256;
		}else if(windowSize.equals("512")) {
			this.windowSize = 512;
		}else if(windowSize.equals("1024")) {
			this.windowSize = 1024;
		}else if(windowSize.equals("2048")) {
			this.windowSize = 2048;
		}else if(windowSize.equals("4096")) {
			this.windowSize = 4096;
		}
	}
	
	public void setWindowing(String win) {
		if(win.equals("Rectangular")) {
			this.windowing = 0;
		}
		else if(win.equals("Hann")) {
			this.windowing = 1;
		}
		else if(win.equals("Blackman")) {
			this.windowing = 2;
		}
		else if(win.equals("Hamming")) {
			this.windowing = 3;
		}
	}
	
	public void setOverlap(String over) {
		if(over.equals("0%")) {
			this.overlap = 0.0;
		}else if(over.equals("10%")) {
			this.overlap = 0.1;
		}else if(over.equals("20%")) {
			this.overlap = 0.2;
		}else if(over.equals("30%")) {
			this.overlap = 0.3;
		}else if(over.equals("40%")) {
			this.overlap = 0.4;
		}else if(over.equals("50%")) {
			this.overlap = 0.5;
		}
	}
	
	public int maxValue() {
		double naj = 0;
		int endingIndex = windowSize;
		
		double times = Math.round((double)Byte.SIZE/5.0);
		double[]real = new double[(int)Math.round((double)size/times)];
		Fft.bytes2doubles(format, data, real);
		//System.out.println("real size: " + real.length);
		//System.out.println("fore time: "+ (real.length/windowSnajize));
		for(int i = 0; i <real.length/windowSize;i++) {
			double[]realprobe;
			double[]imgprobe;
			if(i ==(real.length/windowSize)-1) {
				realprobe = new double[real.length-(endingIndex)];
				imgprobe = new double[real.length-(endingIndex)];
			}else {
				realprobe = new double[windowSize];
				imgprobe = new double[windowSize];
			}
						
			for(int j = 0; j<imgprobe.length; j++) {
				imgprobe[j]=0;
			}
			
			for(int j = 0; j<realprobe.length; j++) {
				realprobe[j] = real[endingIndex+j];
			}//end j for
			//beginingIndex = endingIndex;
			endingIndex+=windowSize;
			Fft.transform(realprobe, imgprobe);
			double[] module = new double[realprobe.length];
			
			for( int j = 0; j <realprobe.length; j++) {
				module[j] = Math.sqrt((Math.pow(realprobe[j], 2.0)+Math.pow(imgprobe[j], 2.0)));
				module[j] = Math.log(module[j])*44000;
				if(module[j] > naj) naj = module[j];
			}//end 2nd j for
			realprobe = null;
			imgprobe = null;
		}
		real = null;
		System.gc();
		return (int)naj;
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//malowanie wykresu linia po linii.
		int R=0;
		int G=0;
		int B = 10;
		//draw Background
		g.setColor(new Color(100,100,100));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//draw spectogram
		if(data != null) {
			int naj = this.maxValue();
			System.out.println("najwieksza wartosc: "+naj);
			int endingIndex = 0;

			//int times = Double.SIZE/Byte.SIZE;
			double times = Math.round((double)Byte.SIZE/5.0);
			double[]real = new double[(int)Math.round((double)size/times)];
			Fft.bytes2doubles(format, data, real);

			int lp=real.length/(windowSize - (int)(windowSize*overlap));
			for(int i = 0; i <lp;i++) {
				double[]realprobe;
				double[]imgprobe;
				if(i ==(real.length/(windowSize - (int)(windowSize*overlap)))-1) {
					realprobe = new double[real.length-(endingIndex)];
					imgprobe = new double[real.length-(endingIndex)];
				}else {
					realprobe = new double[windowSize];
					imgprobe = new double[windowSize];
				}
							
				for(int j = 0; j<imgprobe.length; j++) {
					imgprobe[j]=0;
				}
				
				for(int j = 0; j<realprobe.length; j++) {
					realprobe[j] = real[endingIndex+j];
				}//end j for
				//beginingIndex = endingIndex;
				//endingIndex+=windowSize;
				endingIndex = endingIndex +(windowSize - (int)(windowSize*overlap));
				
				//windowing
				if(this.windowing == 1) {
					/* Hanna*/
	                for(int j=0; j<realprobe.length; j++)
	                {
	                    realprobe[j] = (double)(realprobe[j] * 0.5 * (1.0 - Math.cos(2.0 * Math.PI * j / realprobe.length)));
	                }
				}else if (this.windowing == 2) {
					//Blackman
					for(int j=0; j<realprobe.length; j++)
	                {
						realprobe[j] = (double)(realprobe[j]*0.42 - 0.5 * Math.cos(2*Math.PI*i/(realprobe.length-1)) + 0.08 * Math.cos(4*Math.PI*i/(realprobe.length-1)));
	                }
				}else if (this.windowing == 3) {
					//Hamming
					for(int j=0; j<realprobe.length; j++)
	                {
						realprobe[j] = (double)(realprobe[j]*0.53836 - 0.46164 * (Math.cos(2.0 * Math.PI * i / realprobe.length - 1)));
	                }
				}
				
				Fft.transform(realprobe, imgprobe);
				double[] module = new double[realprobe.length];
				
				for( int j = 0; j <realprobe.length; j++) {
					module[j] = Math.sqrt((Math.pow(realprobe[j], 2.0)+Math.pow(imgprobe[j], 2.0)));
					module[j] = Math.log(module[j])*44000;
				}//end 2nd j for
				
				double xand = (double)this.getWidth()/(double)lp;
				double yand = (double)this.getHeight()/(double)windowSize;
				int yend = (int) Math.round(yand);
				int xend = (int) Math.round(xand);
				double p = (double)naj/6.0;
				int prog = (int) Math.round(p);
				//rysowanie jednego paska
				
				for(int j =0; j<module.length; j++) {
					
					if(module[j]< prog) {
						double pp = (double)(module[j]*235)/prog;
						B = 20 +(int) Math.round(pp);
						if(B >255) {
							B = 255;
						}else if (B < 20) B = 20;
						R = 0;
						G = 0;
					}else if(module[j]< (2*prog)) {
						B = 255;
						double pp = (double)(module[j]*255)/(2*prog);
						R = (int) Math.round(pp);
						if(R > 255) {
							R = 255;
						}else if (R < 0) R = 0;
						G = 0;
						
					}else if(module[j]<(3*prog)) {
						R = 255;
						G = 0;
						double pp = (double)(module[j]*235)/(3*prog);
						B = 235 - (int) Math.round(pp);
						if(B < 20) {
							B = 20;
						}
					}else if(module[j]<(5*prog)) {
						R = 255;
						B = 0;
						double pp = (double)(module[j]*255)/(5*prog);
						G = (int) Math.round(pp);
						if(G > 255) G = 255;
						else if (G< 0) G = 0;
					}else if(module[j]<(6*prog)) {
						R = 255;
						G = 255;
						double pp = (double)(module[j]*255)/(6*prog);
						B = (int) Math.round(pp);
						if(B > 255) B = 255;
						else if(B <20) B = 20;
					}else{
						R = 255;
						G = 255;
						B = 255;
					}
					g.setColor(new Color(R,G,B));
					
					if(windowSize<this.getHeight()){
						if(lp > this.getWidth()) {
							g.drawLine((int)Math.round(i*xand),(int) Math.round(j*yand),(int)Math.round(i*xand),(int) Math.round(j*yand)+(int) Math.round(yand));
							//System.out.println('1');
						}
						else {
							for(int k =0; k<yend;k++) g.drawLine((int)Math.round(i*xand), (j*yend)+k, (int)Math.round(i*xand)+xend,(j*yend)+k);
							//System.out.println('2');
						}
						/*
						for(int k = 0; k <yend; k++) {
							g.drawLine(i,j*yend,i,(j*yend)+k);
							//System.out.println("Pierwsza opcja");
						}
						*/
					}else {
						if(lp < this.getWidth()) {
							g.drawLine(((int)Math.round(i*xand)), j, (int)(i*xand)+xend,j);

							//System.out.println('3');
						}else {
							g.drawLine(((int)Math.round(i*xand)), j, ((int)Math.round((i)*xand)),j);
							//System.out.println('4');
						}
					}
										
				}
				realprobe = null;
				imgprobe = null;
			}//end i for
			real = null;
			
			System.gc();
		}
		
		
	}//end paint methode
	
	public Dimension getPreferredSize() {
		return new Dimension(this.height,this.width);
	}
	
	public void setSize(Dimension d) {
		this.width = d.width;
		this.height = d.height;
	}
}
