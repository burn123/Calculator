package calc;

import java.awt.Cursor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

public class MenuBar extends JMenuBar {

	MenuBar() {
		JMenu settings = new JMenu("Settings");
		JMenu colors = new JMenu("Color");
		JRadioButton red = new JRadioButton("Red");
		//red.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		colors.add(red);
		settings.add(colors);
		add(settings);
	}
	
}
