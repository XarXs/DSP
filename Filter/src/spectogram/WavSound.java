package spectogram;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFileChooser;

import sun.audio.*;

public class WavSound {

	double real;
	double img;
	AudioStream as;
	private String audiodir;
	String newFileName;
	InputStream in;
	
	public void LoadAudio(String s) {
		try {
			in = new FileInputStream(s);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		audiodir = new String(s.toString());
		try {
			//as = new AudioStream(this.getClass().getResourceAsStream(s));
			as = new AudioStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playAudio() {
		AudioPlayer.player.start(as);
	}
	
	public void stopAudio() {
		AudioPlayer.player.stop(as);
		
		try {
			in.close();
			as.close();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		
		
		 try {
		 
			in = new FileInputStream(audiodir);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//as = new AudioStream(this.getClass().getResourceAsStream(s));
			as = new AudioStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void pauseAudio() {
		AudioPlayer.player.stop(as);
		
	}
	
	public void killAudio() {
		try {

			in.close();
			as.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot close AudioStream in WavSound");
		}
	}
	
	public void skipAudio() {
		/*
		 * koncept przewijania
		 * obliczyc zaznaczony czas w postaci roznicy zaznaczenia a calej dlugosci
		 * zresetowac strumien do zera
		 * przesunac, operacja skip, o wartosc obliczonej roznicy
		 * */
		try {
			as.skip((as.getLength())/10);
		} catch (IOException e) {
			System.out.println("Nie udalo sie skipnac");
			e.printStackTrace();
		}
	}
	
	public void saveFile() {
		final String EXTENSION = ".wav";

		final LayoutFileFilter SAVE_AS_IMAGE = 
		        new LayoutFileFilter("WAV File Format", EXTENSION, true);
		
		JFileChooser fileChooser = new JFileChooser();
	    ExtensionFileFilter pFilter = new ExtensionFileFilter(SAVE_AS_IMAGE);
	    fileChooser.setFileFilter(pFilter);
	   
	    int status = fileChooser.showOpenDialog(null);
	    if (status == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();

	        try {
	            String fileName = selectedFile.getCanonicalPath();
	            if (!fileName.endsWith(EXTENSION)) {
	                selectedFile = new File(fileName + EXTENSION);
	            }
	           	            
	            try {
	            	final File inputAudio = new File(audiodir);
	                final File outputAudio = new File(selectedFile.toString());
	                newFileName = selectedFile.getName();
	                // We get a stream for playing the input file.
	                AudioInputStream ais = AudioSystem.getAudioInputStream(inputAudio);
	                
	                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(outputAudio.toString()));
	                
	                ais.close();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	}
		
}
