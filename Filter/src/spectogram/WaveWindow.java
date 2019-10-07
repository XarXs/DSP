package spectogram;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import WaveForm.Generator;
import WaveForm.Sample;
import WaveForm.WaveEditor;

import audio.recorder.*;


public class WaveWindow extends JFrame implements ActionListener, WindowListener {
    private static final long serialVersionUID = 1L;
        
    WaveEditor editor;
    Spektogram spek = new Spektogram();
    WavSound ws = new WavSound();
    Capture cap = new Capture();
    File filetmp = new File("output.wav");
    JButton play;
	JButton stop;
	JButton pause;
	JButton skip;
	JButton record;
	// menu bar objects
	JMenuBar menuBar;
	JMenu file; //kolejno pliki, edycja
	JMenuItem newFile, openWav, saveFile, print, exit;
	
	JTextArea testingKK;
	
	Boolean opened = false;
	
	JComboBox combobox;
	JComboBox windowingbox;
	JComboBox overlapsizebox;
	public WaveWindow()
	{
		this.setTitle("Spektogram");
		JPanel panel = new JPanel();	//pasek menu
		JPanel panel2 = new JPanel(new BorderLayout());	//wykres sonogramu
		
		spek.setSize(new Dimension (700, 2000));
				
		testingKK = new JTextArea("Window Size:");
		testingKK.setOpaque(false);
		testingKK.setEditable(false);
		
		
		play = new JButton("Play");
		play.setEnabled(false);
		stop = new JButton("Stop");
		stop.setEnabled(false);
		pause = new JButton("Pause");
		pause.setEnabled(false);
		skip = new JButton("Skip");
		skip.setEnabled(false);
		record = new JButton("Record");
		record.setEnabled(true);
		
		combobox = new JComboBox();
		combobox.addItem(16);
		combobox.addItem(32);
		combobox.addItem(64);
		combobox.addItem(128);
		combobox.addItem(256);
		combobox.addItem(512);
		combobox.addItem(1024);
		combobox.addItem(2048);
		combobox.addItem(4096);
		combobox.setSelectedItem(2048);
		
		windowingbox = new JComboBox();
		windowingbox.addItem("Rectangular");
		windowingbox.addItem("Hann");
		windowingbox.addItem("Blackman");
		windowingbox.addItem("Hamming");
		windowingbox.setSelectedItem("Rectangular");
		
		overlapsizebox = new JComboBox();
		overlapsizebox.addItem("0%");
		overlapsizebox.addItem("10%");
		overlapsizebox.addItem("20%");
		overlapsizebox.addItem("30%");
		overlapsizebox.addItem("40%");
		overlapsizebox.addItem("50%");
		
		//menu bar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		//adding tabs
		file = new JMenu("File");	//new, open, save, print, exit
		menuBar.add(file);
		//adding item to tabs
		//File tab
		newFile = new JMenuItem("New");
		openWav = new JMenuItem("Open WAV");
		saveFile = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		
		file.add(newFile);
		file.add(openWav);
		file.add(saveFile);
		file.add(exit);
		//adding listeners to items;
		newFile.addActionListener(this);
		openWav.addActionListener(this);
		saveFile.addActionListener(this);
		exit.addActionListener(this);
		//=====
		
		/*
		 * first ver of open file
		 * 
		JButton button = new JButton("Open Wav");
		button.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser("./");
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					try {
						ws.LoadAudio(fc.getSelectedFile().toString());
						Generator generator = new Generator();
						Sample wav = generator.loadStandardizedSample(file);

						editor.setWave(wav);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
				
			}
		
		});
		*/
		
		editor = new WaveEditor();
		//panel.add(button);
		panel.add(record);
		panel.add(play);
		panel.add(pause);
		panel.add(stop);
		panel.add(skip);
		panel.add(testingKK);
		panel.add(combobox);
		panel.add(windowingbox);
		panel.add(overlapsizebox);
		panel2.add(editor, BorderLayout.CENTER);
		panel2.add(spek, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().add(panel, BorderLayout.NORTH);
		this.getContentPane().add(panel2, BorderLayout.CENTER);
		this.setLocationByPlatform(true);
		
		play.addActionListener(this);
		stop.addActionListener(this);
		pause.addActionListener(this);
		skip.addActionListener(this);
		record.addActionListener(this);
		combobox.addActionListener(this);
		windowingbox.addActionListener(this);
		overlapsizebox.addActionListener(this);
		this.addWindowListener(this);
		
			
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==play) {
			ws.playAudio();
			play.setEnabled(false);
			
		}else if (arg0.getSource()==stop) {
			ws.stopAudio();
			play.setEnabled(true);
			pause.setEnabled(true);
		}
		else if (arg0.getSource()==pause){
			ws.pauseAudio();
			play.setEnabled(true);
			pause.setEnabled(false);
		}
		else if (arg0.getSource()==skip) {
			ws.skipAudio();
		}else if(arg0.getSource()==openWav) {
			JFileChooser fc = new JFileChooser("./");
			fc.setFileFilter(new FileNameExtensionFilter("wav","wav"));
			
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				filetmp = fc.getSelectedFile();
				ws.LoadAudio(fc.getSelectedFile().toString());
				this.openFile();
				this.setTitle("Spektogram - "+filetmp.getName());
			}
			
		}else if(arg0.getSource()==exit) {
			if(opened) {
				this.deleteTmpFile();
			}
			System.exit(0);
		}else if(arg0.getSource()==newFile) {
			record.setEnabled(true);
			play.setEnabled(false);
			stop.setEnabled(false);
			pause.setEnabled(false);
			skip.setEnabled(false);
			if(record.getText().startsWith("Record")) {
				cap.start(play, record);
				this.setTitle("Spektogram - Recording");
				record.setText("Stop");
			}else {
				cap.stop();
				play.setEnabled(true);
				stop.setEnabled(true);
				pause.setEnabled(true);
				skip.setEnabled(true);
				record.setEnabled(false);
				record.setText("Record");
				this.setTitle("Spektogram - Recorded");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ws.LoadAudio("output.wav");
				filetmp = new File("output.wav");
				openFile();

			}
		}else if(arg0.getSource()==record) {
			
			if(record.getText().startsWith("Record")) {
				cap.start(play, record);
				this.setTitle("Spektogram - Recording");
				record.setText("Stop");
			}else {
				cap.stop();
				play.setEnabled(true);
				stop.setEnabled(true);
				pause.setEnabled(true);
				skip.setEnabled(true);
				record.setEnabled(false);
				this.setTitle("Spektogram - Recorded");
				record.setText("Record");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ws.LoadAudio("output.wav");
				filetmp = new File("output.wav");
				openFile();
				this.setTitle("Spektogram - "+filetmp.getName()+" (not saved)");

			}
			
		}else if(arg0.getSource()==saveFile) {
			ws.saveFile();
			this.setTitle("Spektogram - "+ws.newFileName+" (saved)");
		}else if(arg0.getSource()==combobox) {
			spek.setProbe(combobox.getSelectedItem().toString());
			spek.repaint();
		}else if(arg0.getSource()==windowingbox) {
			spek.setWindowing(windowingbox.getSelectedItem().toString());
			spek.repaint();
		}else if(arg0.getSource()==overlapsizebox) {
			spek.setOverlap(overlapsizebox.getSelectedItem().toString());
			spek.repaint();
		}
		
	}//end of function
	
	public void deleteTmpFile() {
		filetmp = new File("output.wav");
		ws.killAudio();
		if(filetmp.exists()) {
			try {
				if(!filetmp.delete()) {
						System.out.println("sth goes wrong");
					}			
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("sth goes wrong2");
			}
		}else {
			System.out.println("file doesn't exist");
		}
		
	}//end of function
	
	public void openFile() {
		try {
			opened = true;
			Generator generator = new Generator();
			Sample wav = generator.loadStandardizedSample(filetmp);
			
			spek.setData(wav.getData().getData());
			spek.setAudioFormat(wav.getAudioFormat());
			spek.repaint();
			editor.setWave(wav);
			play.setEnabled(true);
			stop.setEnabled(true);
			pause.setEnabled(true);
			skip.setEnabled(true);
			record.setEnabled(false);
			
			generator = null;
			wav = null;
			System.gc();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		if(opened) {
			this.deleteTmpFile();
		}
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(opened) {
			this.deleteTmpFile();
		}
		System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
