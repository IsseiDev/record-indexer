package client.indexer;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel{
	
	DrawingComponent component;
	JSlider slider;

	public ImagePanel(){
		this.setBackground(Color.GRAY);
		
		component = new DrawingComponent();

		this.add(component, BorderLayout.CENTER);
	}
	
	private ChangeListener sliderChangeListener = new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			component.setScale(slider.getValue() * 0.05);
		}
	};
}
