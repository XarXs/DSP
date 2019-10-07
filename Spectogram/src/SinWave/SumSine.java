package SinWave;

public class SumSine {

	double[] sines;
	
	public void sum(double[] s1, double[] s2) {
		int size;
		size = s1.length;
		sines = new double[size];
		for(int i = 0; i < size ; i++) {
			sines[i] = s1[i] + s2[i];
		}
	}
	public void nextsum(double[]s) {
		for(int i =0; i< s.length; i++) {
			sines[i]+=s[i];
		}
	}
	
	public double[] getSines() {
		return sines;
	}
	
}
