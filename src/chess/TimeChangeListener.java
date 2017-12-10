package chess;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TimeChangeListener implements ChangeListener {
	
	ContentLayout contLayout;
	
	public TimeChangeListener(ContentLayout contLayout) {
		this.contLayout = contLayout;
	}
	
	@Override
		public void stateChanged(ChangeEvent arg0) {
			contLayout.timeRemaining = contLayout.getTimeSlider().getValue() * 60;
		}
}
