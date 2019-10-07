package Filtr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;

import javax.swing.*;

import SinWave.SineWave;
import SinWave.SumSine;

public class FirstWaveForm extends JFrame  implements ActionListener, WindowListener{
	private static final long serialVersionUID = 1L;
	
	SineWave sv;
	SineWave sv2;
	SineWave backup_;
	SumSine ss;
	SecondWaveForm editor2;
	JFormattedTextField test, amp, test2, amp2;
	JFormattedTextField order, cutofFreq, widthFreq;
	JButton repainting, filtring;
	
	JComboBox passType;
	
	public FirstWaveForm()
	{
		JPanel panel = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel(new BorderLayout());
		JPanel panel2 = new JPanel(new BorderLayout());
		JPanel panel5 = new JPanel();
		
		repainting = new JButton("Reset");
		repainting.addActionListener(this);
		
		filtring = new JButton("Filtering");
		filtring.addActionListener(this);
		
		passType = new JComboBox();
		passType.addItem("None");
		passType.addItem("Low Pass");
		passType.addItem("Band Pass");
		passType.addItem("High Pass");
		passType.addActionListener(this);
		
		DecimalFormat dc = new DecimalFormat("0.00");
		
		test = new JFormattedTextField(dc);
		test.setColumns(4);
		test.setValue(new Double(0.07f));
		test.addActionListener(this);
		
		amp = new JFormattedTextField();
		amp.setColumns(3);
		amp.setValue(new Integer(50));
		amp.addActionListener(this);
		
		test2 = new JFormattedTextField(dc);
		test2.setColumns(4);
		test2.setValue(new Double(0.07f));
		test2.addActionListener(this);
		
		amp2 = new JFormattedTextField();
		amp2.setColumns(3);
		amp2.setValue(new Integer(0));
		amp2.addActionListener(this);
		
		order = new JFormattedTextField();
		order.setValue("Order");
		order.setColumns(4);
		order.setEnabled(false);
		order.addActionListener(this);
		
		cutofFreq = new JFormattedTextField();
		cutofFreq.setValue("cutFreq");
		cutofFreq.setColumns(5);
		cutofFreq.setEnabled(false);
		cutofFreq.addActionListener(this);
		
		widthFreq = new JFormattedTextField();
		widthFreq.setValue("widthFreq");
		widthFreq.setColumns(5);
		widthFreq.setEnabled(false);
		widthFreq.addActionListener(this);
		
		sv = new SineWave(1000,50,.07f,1000,107);
		ss = new SumSine();
		sv2= new SineWave(1000,0,.07f,1000,200);
		ss.sum(sv.getAmplitude(), sv2.getAmplitude());
		sv.setAmplitude(ss.getSines());
		backup_ = new SineWave(1000, 0, .07f,1000,200);
		backup_.setAmplitude(sv2.getAmplitude());
		editor2 = new SecondWaveForm(sv);
		editor2.setSize(new Dimension(1000,280));
		

		JTextArea ampli, freq, ampli2, freq2;
		ampli = new JTextArea("Amplitude 1:");
		ampli.setOpaque(false);
		ampli.setEditable(false);
		

		freq = new JTextArea("Frequency 1:");
		freq.setOpaque(false);
		freq.setEditable(false);
		
		ampli2 = new JTextArea("Amplitude 2:");
		ampli2.setOpaque(false);
		ampli2.setEditable(false);
		

		freq2 = new JTextArea("Frequency 2:");
		freq2.setOpaque(false);
		freq2.setEditable(false);
		
		panel.add(freq);
		panel.add(test);
		panel.add(ampli);
		panel.add(amp);
		
		panel3.add(freq2);
		panel3.add(test2);
		panel3.add(ampli2);
		panel3.add(amp2);
		
		panel5.add(passType);
		panel5.add(order);
		panel5.add(cutofFreq);
		panel5.add(widthFreq);
		panel5.add(filtring);
		panel5.add(repainting);
		
		panel2.add(sv, BorderLayout.CENTER);
		panel2.add(editor2,BorderLayout.SOUTH);
		
		panel4.add(panel, BorderLayout.NORTH);
		panel4.add(panel3, BorderLayout.CENTER);
		panel4.add(panel5, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(FirstWaveForm.EXIT_ON_CLOSE);
		
		this.getContentPane().add(panel4, BorderLayout.NORTH);
		this.getContentPane().add(panel2, BorderLayout.CENTER);
		this.addWindowListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == test) {
			sv.setOmega((double)test.getValue());
			ss.sum(sv.getAmplitude(), sv2.getAmplitude());
			sv.setAmplitude(ss.getSines());
			editor2.setSinWave(sv);
			sv.repaint();
		}else if(arg0.getSource() == amp) {
			sv.setAmp((int)amp.getValue());
			ss.sum(sv.getAmplitude(), sv2.getAmplitude());
			sv.setAmplitude(ss.getSines());
			editor2.setSinWave(sv);
			sv.repaint();
		}else if(arg0.getSource() == test2) {
			sv2.setOmega((double)test2.getValue());
			sv.calculate();
			ss.sum(sv.getAmplitude(), sv2.getAmplitude());
			sv.setAmplitude(ss.getSines());
			editor2.setSinWave(sv);
			sv.repaint();
		}else if(arg0.getSource() == amp2) {
			sv2.setAmp((int)amp2.getValue());
			sv.calculate();
			ss.sum(sv.getAmplitude(), sv2.getAmplitude());
			sv.setAmplitude(ss.getSines());
			editor2.setSinWave(sv);
			sv.repaint();
		}else if(arg0.getSource() == passType) {
			String pom = passType.getSelectedItem().toString();
			if(pom.equals("None")){
				order.setValue("Order");
				order.setEnabled(false);
				
				cutofFreq.setValue("cutFreq");
				cutofFreq.setEnabled(false);
				
				widthFreq.setValue("widthFreq");
				widthFreq.setEnabled(false);
			}else if(pom.equals("Low Pass")) {
				order.setValue(new Integer(0));
				order.setEnabled(true);
				
				cutofFreq.setValue(new Double(.00f));
				cutofFreq.setEnabled(true);
				
				widthFreq.setValue("widthFreq");
				widthFreq.setEnabled(false);
			}else if(pom.equals("High Pass")) {
				order.setValue(new Integer(0));
				order.setEnabled(true);
				
				cutofFreq.setValue(new Double(.00f));
				cutofFreq.setEnabled(true);
				
				widthFreq.setValue("widthFreq");
				widthFreq.setEnabled(false);
			}else if(pom.equals("Band Pass")) {
				order.setValue(new Integer(0));
				order.setEnabled(true);
				
				cutofFreq.setValue(new Double(.00f));
				cutofFreq.setEnabled(true);
				
				widthFreq.setValue(new Double(.00f));
				widthFreq.setEnabled(true);
			}
			
		}else if(arg0.getSource() == repainting) {
			editor2.setSinWave(sv);
			editor2.repaint();
			
		}else if(arg0.getSource() == filtring) {
			if(passType.getSelectedIndex()!= 0) {

				Filtr f = new Filtr();
				if(passType.getSelectedIndex()!=2) {
					backup_.setAmplitude(f.filtr(sv, passType.getSelectedIndex(), (int)order.getValue(), sv.getTime(), (double)cutofFreq.getValue(), 0));
				}else {
					backup_.setAmplitude(f.filtr(sv, passType.getSelectedIndex(), (int)order.getValue(), sv.getTime(), (double)cutofFreq.getValue(), (double)widthFreq.getValue()));
				}
				//ss.sum(sv.getAmplitude(), backup_.getAmplitude());
				//editor2.setAmplitude(ss.getSines());
				editor2.setSinWave(backup_);
			
			}else
				editor2.setSinWave(sv);
			editor2.repaint();
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
