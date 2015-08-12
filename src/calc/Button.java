package calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.math.BigDecimal;

import javax.swing.JButton;

public class Button extends JButton {
	String text;
	
	Button(String t) {
		this.text = t;
		setText(t);
		setFont(new Font(Calculator.SYSTEM_FONT, Font.PLAIN, 20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if(!(this instanceof Special))
			inputListener();
	}
	
	Button(String buttonText, String displayText) {
		this.text = displayText;
		setText(buttonText);
		setFont(new Font(Calculator.SYSTEM_FONT, Font.PLAIN, 20));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		inputListener();
	}
	
	/**
	 * Gets the text specified as the text that
	 * will show up in the input field
	 * @return The text set as the display text
	 */
	public String getDisplayText() {
		return this.text;
	}

	public void inputListener() {
		
		addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				MessagePanel messagePanel = Calculator.messagePanel;
				Result result = Calculator.lblResult;
				String currentText = result.getText(), buttonText = getDisplayText();
				int currentLength = currentText.length(), lastOperator = Operator.getLastOperator(currentText);
				
				if(!messagePanel.getText().equals("") && messagePanel.error == false) {
					messagePanel.reset();
				}

				// If the clear button is pressed
				if(buttonTextEquals(Calculator.clear)) {
					// Reset the input field
					messagePanel.reset();
					result.replaceText("0");
					return;
				}
				
				// If the backspace button is pressed
				if(buttonTextEquals(Calculator.backspace)) {
					
					// Removes functions
					// If the last char was a left parenthesis
					if(resultCharEquals(Calculator.leftPar, currentLength-1)) {
						// Remove the parenthesis
						currentText = currentText.substring(0, --currentLength);
						// While there are still letters in the input field
						while(currentLength > 0 && Character.isLetter(currentText.charAt(currentLength-1))) {
							// Remove them until the loop comes to a operator
							currentText = currentText.substring(0, --currentLength);
						}
					}
					// Otherwise remove the last character entered
					else
						currentText = currentText.substring(0, currentLength-1);
					
					// After this, if the input field is blank or only contains a negative
					if(currentText.equals("") || currentText.equals(Calculator.NEGATIVE_SIGN))
						// Set the field to 0
						currentText = "0";
					
					result.replaceText(currentText);
					return;
				}
				
				if(Operator.isOperator(buttonText.charAt(0)) && resultCharEquals(Calculator.leftPar, currentLength-1))
					return;
				
				// Adds the positive/negative button
				if(buttonTextEquals(Calculator.plusMinus)) {
					if(lastCharIsZero())
						return;
					if(resultCharEquals(Calculator.leftPar, currentLength-1) && currentLength>1 && !Character.isLetter(currentText.charAt(currentLength-2))) {
						if(currentText.charAt(currentLength-2) == Calculator.NEGATIVE_SIGN.charAt(0))
							result.replaceText(currentText.substring(0, currentLength-2) + textOf(Calculator.leftPar));
						else
							result.replaceText(currentText.substring(0, currentLength-1) + Calculator.NEGATIVE_SIGN + textOf(Calculator.leftPar));
						return;
					}
					int lastPar=-1;
					for(int i=currentLength-1; i>0; i--){
						if(currentText.charAt(i) == charOf(Calculator.leftPar)) {
							lastPar = i;
							break;
						}
					}
					if(lastOperator!=0 || lastPar!=-1) {
						String before, after;
						int biggerNum=0;
						if(lastOperator>lastPar) biggerNum=lastOperator;
						if(lastPar>lastOperator) biggerNum=lastPar;
						// Returns the part of the input field before the last operator entered
						before = currentText.substring(0, biggerNum+1);
						// Returns the part after the last operator entered
						after = currentText.substring(biggerNum+1, currentLength);
						// If the first character of the number since the last operator is already negative
						if(after.length() != 0 && after.charAt(0) == '-')
							result.replaceText(before + currentText.substring(biggerNum+2, currentLength));
						// Otherwise make it negative
						else
							result.replaceText(before + Calculator.NEGATIVE_SIGN + after);
						
					}
					else {
						if(!resultCharEquals(Calculator.NEGATIVE_SIGN.charAt(0), 0))
							result.replaceText(Calculator.NEGATIVE_SIGN + currentText);
						else
							result.replaceText(currentText.substring(1));
					}
					return;
				}

				if(buttonTextEquals(Calculator.leftPar)) {
					// If last entered char wasn't an operator or a left parenthesis
					if(!lastCharIsZero() && !lastCharOperatorOrPar() && !resultCharEquals(Calculator.NEGATIVE_SIGN.charAt(0), currentLength-1)) {
						// Assume they mean multiplication and add the symbol
						result.addText(textOf(Calculator.multiply) + buttonText);
						return;
					}
				}
				if(buttonTextEquals(Calculator.rightPar)) {
					if(lastCharIsOperator()) {
						result.replaceText(currentText.substring(0, currentLength-1) + textOf(Calculator.rightPar));
						return;
					}
				}
				
				if(resultCharEquals(Calculator.rightPar, currentLength-1)) {
					if(!Operator.isOperator(buttonText.charAt(0)) && !resultCharEquals(buttonText.charAt(0), currentLength-1)) {
						result.addText(textOf(Calculator.multiply));
					}
				}
				
				if(new Expression("").isVariable(currentText.charAt(currentLength-1))) {
					if(Character.isDigit(buttonText.charAt(0)) || buttonText.equals(textOf(Calculator.decimal))){
						result.addText(textOf(Calculator.multiply));
					}						
				}
				
				// Prevents more than one decimal
				if(buttonTextEquals(Calculator.decimal)) {
					// Only checks the number since the last operator for a decimal
					if(!currentText.substring(lastOperator, currentLength).contains(textOf(Calculator.decimal))) {
						if(!Character.isDigit(currentText.charAt(currentLength-1)))
							// If the last character was an operator, add a leading 0
							result.addText("0.");
						else
							result.addText(buttonText);
					}
					return;
				}
				
				if(buttonTextEquals(Calculator.log) || buttonTextEquals(Calculator.naturalLog) || buttonTextEquals(Calculator.squareRoot)
						|| buttonTextEquals(Calculator.inverse)) {
					if(currentText.equals("0"))
						result.replaceText(currentText.substring(0,currentLength-1) + buttonText + "(");
					else if(lastCharOperatorOrPar())
						result.addText(buttonText + textOf(Calculator.leftPar));
					else
						result.addText(textOf(Calculator.multiply) + buttonText + textOf(Calculator.leftPar));
					return;
				}
				
				if(buttonTextEquals(Calculator.factorial)) {
					String answ = evaluate("FACTORIAL("+currentText+")");
					result.replaceText(answ);
					return;
				}
				
				if(buttonTextEquals(Calculator.equals)) {
					if(lastCharIsOperator()) {
						currentText = currentText.substring(0, currentLength-1);
					}
					result.replaceText(evaluate(currentText));
					return;
				}
				
				// If the last character entered was an operator, and the user pressed an operator
				if(Operator.isOperator(buttonText.charAt(0)) && lastCharIsOperator()) {
					// Replace the old operator with the new operator
					result.replaceText(currentText.substring(0, currentLength-1) + buttonText);
					return;
				}

				// Checks if last character entered is zero
				if(lastCharIsZero() && !Operator.isOperator(buttonText.charAt(0))) {
					
					// If the current text is an operator followed by a 0,
					// if the user clicks on a number, change the 0 to the number clicked
					if(currentLength > 2 && (lastOperator == currentLength-2 || resultCharEquals(Calculator.leftPar, currentLength-2)))
						result.replaceText(currentText.substring(0, currentLength - 1) + buttonText);
					
					// If the 0 is the only number in the field
					// Change the 0 to the number clicked
					else if(currentLength == 1)
						result.replaceText(buttonText);
					return;
				}
				
				if(buttonText.equals("0")) {
					if(resultCharEquals(Calculator.NEGATIVE_SIGN.charAt(0), currentLength-1)) {
						result.replaceText(currentText.substring(0, currentLength-1) + buttonText);
						return;
					}
				}

				if(resultCharEquals(Calculator.decimal, currentLength-1) && !Character.isDigit(buttonText.charAt(0))) {
					result.replaceText(currentText.substring(0, currentLength-1)+buttonText);
					return;
				}
				
				// If none of the conditions above match
				// Add the value of the clicked button to the result
				result.addText(buttonText);
			}
			
			public void mouseEntered(MouseEvent e) {setForeground(Color.RED);}
			public void mouseExited(MouseEvent e)  {setForeground(Color.BLACK);}
			
		});
	}
	
	/**
	 * Evaluates the mathematical expression
	 * contained in the string
	 * @param s - The string to evaluate
	 * @return The answer to the string, or an error if the
	 * user tries to use an incorrect/impossible formula
	 */
	public String evaluate(String s) {
		// Attempts to match parenthesis
		int unmatched = 0;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == charOf(Calculator.leftPar)) 
				unmatched++;
			if(s.charAt(i) == charOf(Calculator.rightPar))
				unmatched--;
			if(unmatched<0) {
				Calculator.messagePanel.setText(Calculator.messagePanel.noOpenPar);
				return s;
			}
		}
		while(unmatched > 0) {
			s += textOf(Calculator.rightPar);
			unmatched--;
		}
		
		s = s.replace(textOf(Calculator.leftPar) + textOf(Calculator.rightPar), "0");
		
		Calculator.messagePanel.setText(s);
		Expression e = new Expression(s);
		BigDecimal answ = e.eval();

		if(answ != e.NULL_VAL)
			return answ.toPlainString();
		return s;
	}
	
	/**
	 * Checks if the button that was clicked is the button
	 * specified in the method
	 * @param b - The button to check the click against
	 * @return True, if the specified button matches the text of the button clicked
	 */
	public boolean buttonTextEquals(Button b) {
		if(this.getDisplayText().equals(b.getDisplayText()))
			return true;
		return false;
	}
	
	/**
	 * Gets the display text of a button
	 * @param b - A button
	 * @return The text of the button specified
	 */
	public String textOf(Button b) {
		String str = b.getDisplayText();
		return str;
	}
	
	/**
	 * Gets the display char of a button
	 * @param b - A button
	 * @return The character of the button specified
	 */
	public char charOf(Button b) {
		char ch = textOf(b).charAt(0);
		return ch;
	}
	
	/**
	 * Checks if the last character entered was a zero
	 */
	public boolean lastCharIsZero() {
		String text = Calculator.lblResult.getText();
		if(resultCharEquals('0', text.length()-1))
			return true;
		return false;
	}
	
	/**
	 * Checks if the last character entered was a operator provided
	 * that the string has an operator in it,
	 * or if the last character entered was a left parenthesis
	 */
	public boolean lastCharOperatorOrPar() {
		int currentLength = Calculator.lblResult.getText().length();
		if(lastCharIsOperator() || resultCharEquals(Calculator.leftPar, currentLength-1))
			return true;
		return false;
	}
	
	/**
	 * Checks whether the input field has an operator and if it does,
	 * checks if that was the last character entered
	 * @return True if the last entered character is an operator
	 */
	public boolean lastCharIsOperator() {
		int lastOperator = Operator.getLastOperator(Calculator.lblResult.getText()),
			currentLength = Calculator.lblResult.getText().length();
		if(lastOperator == currentLength-1 && lastOperator != 0)
			return true;
		return false;
	}
	
	/**
	 * Checks if the specified character equals the character
	 * at the specified index of the input field
	 * @param c - The character to search the input field for
	 * @param index - The position of the string
	 * @return True, if the character matches the character at the index
	 */
	public static boolean resultCharEquals(char c, int index) {
		if(Calculator.lblResult.getText().charAt(index) == c)
			return true;
		return false;
	}public static boolean resultCharEquals(Button b, int index) {
		if(Calculator.lblResult.getText().charAt(index) == b.getDisplayText().charAt(0))
			return true;
		return false;
	}
}
