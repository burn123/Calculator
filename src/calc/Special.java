package calc;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Special extends Button {

	Special(String text) {
		super(text);
		this.specialListener();
	}
	
	private void specialListener() {
		addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				Result result = Calculator.lblResult;
				String currentText = result.getText(), buttonText = getText();

				if(currentText.equals("0")) {
					result.replaceText(buttonText);
					return;
				}
				
				// If the user clicked the special number and the
				// last character entered isn't an operator
				if(!lastCharOperatorOrPar())
					// Assume they mean multiplication, and add the symbol
					result.addText(Calculator.multiply.getDisplayText() + buttonText);
				
				// Otherwise just add the special number
				else
					result.addText(buttonText);
				
			}
			public void mouseEntered(MouseEvent e) {setForeground(Color.RED);}
			public void mouseExited(MouseEvent e)  {setForeground(Color.BLACK);}
			
		});
	}
}
