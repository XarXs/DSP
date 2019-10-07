package SinWave;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

/**
 * A simple utility class using Java swing to generate sine wave based on input params
 * @author raam
 *
 */
public class SineWave extends JPanel{

 private static final long serialVersionUID = 1L;
 

	private Color backgroundColor = new Color(0);
	private Color centerLineColor = new Color(0,240,100);
	private Color waveColor = new Color(100,200,200);
	
 //1d array to store the amplitude data, time data is represented or derived from the index of array
 double[] amplitude;
 
 int center;
 
 double H_STEP_SIZE = 1d;
 
 int t,amp;
 
 double omega;
 
 /**
  * Overloaded constructor
  * @param t
  *    Time part
  * @param amp
  *    Maximum amplitude of the generated sine wave
  * @param omega
  *    Frequency
  * @param hSize
  *    Horizontal size of the frame
  * @param vSize
  *    Vertical size of the frame
  */
 public SineWave(int t,int amp,float omega,int hSize,int vSize){
  this.t = t;
  this.amp = amp;
  this.omega = omega;
  //to check integer overflow when multiplied with horizontal step size
  amplitude = new double[t*(int)H_STEP_SIZE < t?Integer.MAX_VALUE:t*(int)H_STEP_SIZE]; 
  center = (vSize+81)/2;
  //calculate the co-ordinates based on input params to draw the wave
  calculateCoords(t,amp,omega);
 }
 

 private void calculateCoords(int t, int amp, double omega) {
  for(int i=0;i<amplitude.length;i++)
   amplitude[i] = amp * Math.sin(omega*(i/H_STEP_SIZE));  
 }

 public void calculate() {
	 this.calculateCoords(t, amp, omega);
 }
 
 public double[] getAmplitude() {
	 return amplitude;
 }
 
 public void setAmplitude(double[] a) {
	 amplitude = a;
	 this.repaint();
 }
 
 public void setOmega(double om) {
	 omega = om;
	 this.calculateCoords(t, amp, omega);
	 this.repaint();
 }
 
 public void setAmp(int am) {
	 amp = am;
	 this.calculateCoords(t, amp, omega);
	 this.repaint();	 
 }
 
 public int getTime() {
	 return this.t;
 }
 @Override
 public void paintComponent(Graphics g){
        Point2D prev,cur;
        double y;
        Line2D lin;
     // Draw Backgound
     		g.setColor(backgroundColor);
     		g.fillRect(0, 0, getWidth(), getHeight());

     		// g.setClip(paddingLeft, paddingRight, getWidth()-paddingLeft-paddingRight, getHeight()-paddingTop-paddingBottom);
     		
     		// Prepare positioning
     		int defX = getX();
     		int defY = getY();
     		int defWidth = getWidth();
     		int defHeight = getHeight();;
     		int half = defHeight/2;
     		

     		// Draw Half line
     		g.setColor(centerLineColor);
     		g.drawLine(defX, defY+half, defX+defWidth, defY+half);
     		

     		
        cur = new Point2D.Double(1,center);
        //iterate thru' the calculated co-ordinates and draw lines between adjacent points
        //to generate the sine wave
        g.setColor(waveColor);
        for(int i=0;i<amplitude.length;i++){
         prev = cur;
         y = getCoOrdsFromAmplitude(amplitude[i]);
         cur = new Point2D.Double(1+(i/H_STEP_SIZE), y);
         lin = new Line2D.Double(prev,cur);
         g.drawLine((int)lin.getX1(),(int)lin.getY1(),(int)lin.getX2(),(int)lin.getY2());
        }

        
 }
        
 private double getCoOrdsFromAmplitude(double amp){
  return center + amp;
 }
 
}