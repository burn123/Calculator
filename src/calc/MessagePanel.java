package calc;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MessagePanel extends JLabel {
	public static boolean error;
	/**
	 * One of the various mathematical errors that can occur
	 */
	String squareRoot = "Cannot take square root of negative number",
		   divideByZero = "Dividing by zero is not possible",
		   undefinedLog = "Can't take log of a negative number",
		   logOf0 = "The log of zero doesn't exist",
		   decimalFactorial = "You cannot take the factorial of a non integer",
		   noOpenPar = "Syntax Error: No opening parenthesis",
		   unknownSymbol = "Syntax Error: Unknown Character ",
		   emptyExpression = "Syntax Error: Empty Expression",
		   infiniteRoot = "";

	/**
	 * Constructs the panel that displays various
	 * messages that are displayed to the user
	 */
	MessagePanel() {
		setText("");
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(new Font(Calculator.SYSTEM_FONT, Font.PLAIN, 15));
	}
	
	public void displayError(String s) {
		setText(s);
		error = true;
	}
	
	/**
	 * Clears the message panel
	 */
	public void reset() {
		this.setText("");
		error = false;
	}
	
}
