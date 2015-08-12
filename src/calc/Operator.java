package calc;

public class Operator extends Button {
	String text;

	Operator(String t) {
		super(t);
		setText(t);
		this.text = t;
	}
	/**
	 * Constructor for buttons that have a different
	 * text displayed on the button than the text displayed
	 * when the button is clicked
	 * @param buttonText
	 * @param displayText
	 */
	Operator(String buttonText, String displayText) {
		super(buttonText, displayText);
		setText(buttonText);
		this.text = displayText;
	}
	
	/**
	 * Returns the text that we want to be displayed
	 * in the result when the button is clicked
	 * @return The display text of the operator
	 */
	public String getDisplayText() {
		return this.text;
	}
	
	/**
	 * Checks if the specified character is
	 * in the list of operators
	 * @param c - The character to check
	 * @return True if the character is an operator
	 */
	public static boolean isOperator(char c) {
		for(int i = 0; i < Calculator.operators.length; i++)
			if(c == Calculator.operators[i].getDisplayText().charAt(0))
				return true;
		return false;
	}
	
	/**
	 * Gets the last operator entered in the field
	 * @param t - The string to search
	 * @return The index position of the last entered operator,
	 * 		   or zero if the string doesn't contain an operator
	 */
	public static int getLastOperator(String t) {
		for(int k = t.length() - 1; k > 0; k--)
			for(int i = 0; i < Calculator.operators.length; i++)
				if(t.charAt(k) == Calculator.operators[i].getDisplayText().charAt(0))
					return k;
		return 0;
	}

}