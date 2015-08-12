package calc;

import java.awt.Font;
import java.awt.Insets;
import java.math.BigDecimal;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Result extends JTextField {
	/**
	 * Constructs the input field that users will
	 * see their mathematical expressions displayed in
	 */
	Result() {
		setText("0");
		setHorizontalAlignment(SwingConstants.RIGHT);
		setEditable(false);
		setFont(new Font(Calculator.SYSTEM_FONT, Font.PLAIN, 20));
		setBackground(Calculator.BACKGROUND_COLOR);
	}
	
	public Constraints constraints() {
		Constraints gbc = new Constraints();
		gbc.gridwidth = 2;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 5, 5);
		return gbc;
	}
	
	public void replaceText(String text) {
		this.setText(text);
	}
	
	/**
	 * Adds the specified text to the current text of the field
	 * @param The text to add
	 */
	public void addText(String text) {
		this.setText(getText() + text);
	}

}
