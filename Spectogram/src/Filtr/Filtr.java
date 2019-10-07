package Filtr;

import SinWave.SineWave;
import uk.me.berndporr.iirj.Butterworth;

public class Filtr {
/*
 * @filtrType:
 * 0 - lowpass
 * 1 - highpass
 * 2 - bandpass
 */
	public double[] filtr(SineWave s, int filtrType, int order, double sampleRate, double cutoffFreq, double widthFreq) {
		Butterworth bwt = new Butterworth();
		
		if(filtrType != 0) {
			if(filtrType == 1) {
				bwt.lowPass(order, sampleRate, cutoffFreq);
			}else if(filtrType == 3) {
				bwt.highPass(order, sampleRate, cutoffFreq);
			}else {
				bwt.bandPass(order, sampleRate, cutoffFreq, widthFreq);
			}
			double[] s2 = new double [s.getAmplitude().length];
			for(int i = 0; i < s.getAmplitude().length; i++) {
				s2[i]=bwt.filter(s.getAmplitude()[i]);
			}
			return s2;
		}
		return s.getAmplitude();
		
	}
}
