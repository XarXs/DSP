package WaveForm;
import java.util.EventListener;


public interface ResolutionChangeListener extends EventListener {
	public void change(ResolutionChangeEvent e);
}
