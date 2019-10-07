package spectogram;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import sun.audio.AudioStream;

public class SaveFile {
	protected static final String EXTENSION = ".wav";

	protected static final String FORMAT_NAME = "wav";

	protected static final LayoutFileFilter SAVE_AS_IMAGE = 
	        new LayoutFileFilter("WAV File Format", EXTENSION, true);

	public int chooseSaveFile(AudioStream ais) {
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
	            	
	               // AudioSystem.write(ais, AudioFileFormat.Type.WAVE, selectedFile);
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    return status;
	}
}
