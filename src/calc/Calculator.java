package calc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Calculator {

	static JFrame frame;
	
	// Initializes the basic numbers involved
	static Button[] numbers = {new Button("0"), new Button("1"), new Button("2"), new Button("3"), new Button("4"),
							   new Button("5"), new Button("6"), new Button("7"), new Button("8"), new Button("9")};

	// Initializes the odd functions of a calculator
	// Not inserted into array for readability
	static Button inverse = new Button("x\u207B\u00B9", "inv"), backspace = new Button("\u2190"), decimal = new Button("."),
			 	  clear = new Button("C"), equals = new Button("\u003D"), plusMinus = new Button("\u00B1"), squareRoot = new Button("\u221A", "sqrt"),
			 	  factorial = new Button("!"), log = new Button("log"), naturalLog = new Button("ln"), leftPar = new Button("("),
			 	  rightPar = new Button(")");
	/**
	 * The standard operators
	 */
	static Operator divide = new Operator("\u00F7", "\u2215"), multiply = new Operator("\u00D7", "\u00B7"),
					subtract = new Operator("\u2212"), add = new Operator("+"), power = new Operator("^");
	/**
	 * Array of the standard operators
	 */
	static Operator[] operators = {divide, multiply, subtract, add, power};
	
	// Declares the special constants
	static final Special pi = new Special("\u03C0"),  e = new Special("e");
	
	static Result lblResult = new Result();
	static JPanel numberPanel = new JPanel(), functionPanel = new JPanel(), panel;
	static MessagePanel messagePanel = new MessagePanel();
	
	/**
	 * The font to use for the program
	 */
	static final String SYSTEM_FONT = "Cambria Math";
	/**
	 * The character to use as the negative sign
	 */
	static final String NEGATIVE_SIGN = "-";
	
	/**
	 * The background color to use for the program
	 */
	static final Color BACKGROUND_COLOR = Color.decode("#95a6fd");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Calculator();
					Calculator.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Calculator() {
		initializeFrame();
		initializePanel();
		addNumbers();
		addFunctions();
		addOperators();
	}
	
	private void initializeFrame() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(475, 400));
		frame.setBounds(100, 100, 378, 378);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Calculator");
		frame.setJMenuBar(new MenuBar());

		/**
		 * Sets the icon image of the frame
		 */
		try {
			ArrayList<Image> icons = new ArrayList<Image>();
			final String ICON_FILE_PATH = "D:\\Cody\\My Documents\\Coding\\Calculator\\src\\calc\\";
			icons.add(ImageIO.read(new FileInputStream(ICON_FILE_PATH + "imageIcon64.gif")));
			icons.add(ImageIO.read(new FileInputStream(ICON_FILE_PATH + "imageIcon32.gif")));
			frame.setIconImages(icons);
		}
		catch (FileNotFoundException e) {messagePanel.displayError("Can't find the file for the icon");}
		catch (IOException e) {e.printStackTrace();}
	}
	
	private void initializePanel() {
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.add(panel);
		panel.setBackground(BACKGROUND_COLOR);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{187, 187, 0};
		gbl_panel.rowHeights = new int[]{20, 60, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		Constraints gbc_messagePanel = new Constraints();
		gbc_messagePanel.gridwidth = 2;
		messagePanel.setBounds(0, 0, 0, 10);
		panel.add(messagePanel, gbc_messagePanel);
		
		lblResult.setBorder(BorderFactory.createLineBorder(BACKGROUND_COLOR));
		panel.add(lblResult, lblResult.constraints());
	}
	
	private void addNumbers() {
		numberPanel.setBackground(BACKGROUND_COLOR);
		numberPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		Constraints gbc_numberPanel = new Constraints();
		gbc_numberPanel.gridy = 2;
		panel.add(numberPanel, gbc_numberPanel);
		numberPanel.setLayout(new GridBagLayout());
		
		decimal.setToolTipText("Decimal");
		backspace.setToolTipText("Backspace");
		clear.setToolTipText("Clear");
		
		numberPanel.add(backspace, 	buttonConstraints(1, 1, 2, 1));
		numberPanel.add(clear, 		buttonConstraints(3, 1));
		numberPanel.add(numbers[9], buttonConstraints(3, 2));
		numberPanel.add(numbers[8], buttonConstraints(2, 2));
		numberPanel.add(numbers[7], buttonConstraints(1, 2));
		numberPanel.add(numbers[6], buttonConstraints(3, 3));
		numberPanel.add(numbers[5], buttonConstraints(2, 3));
		numberPanel.add(numbers[4], buttonConstraints(1, 3));
		numberPanel.add(numbers[3], buttonConstraints(3, 4));
		numberPanel.add(numbers[2], buttonConstraints(2, 4));
		numberPanel.add(numbers[1], buttonConstraints(1, 4));
		numberPanel.add(numbers[0], buttonConstraints(1, 5, 2, 1));
		numberPanel.add(decimal, 	buttonConstraints(3, 5));
	}
	
	private void addOperators() {
		divide.setToolTipText("Divide");
		multiply.setToolTipText("Multiply");
		subtract.setToolTipText("Subtract");
		add.setToolTipText("Add");
		power.setToolTipText("Power");
		
		for(int i = 0; i < operators.length; i++)
			functionPanel.add(operators[i], buttonConstraints(1, i+2));
		
		functionPanel.add(power, buttonConstraints(2, 3));
	}
	
	private void addFunctions() {
		factorial.setToolTipText("Factorial");
		naturalLog.setToolTipText("Natural Log");
		inverse.setToolTipText("Inverse");
		squareRoot.setToolTipText("Square Root");
		pi.setToolTipText("Pi");
		plusMinus.setToolTipText("Negative");
		
		functionPanel.setBackground(BACKGROUND_COLOR);
		functionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		Constraints gbc_functionPanel = new Constraints();
		gbc_functionPanel.gridy = 2;
		panel.add(functionPanel, gbc_functionPanel);
		functionPanel.setLayout(new GridBagLayout());
		
		functionPanel.add(plusMinus, 	buttonConstraints(1, 1));

		functionPanel.add(leftPar,		buttonConstraints(2, 1));
		functionPanel.add(factorial,	buttonConstraints(2, 2));
		functionPanel.add(log,			buttonConstraints(2, 4));
		functionPanel.add(naturalLog,	buttonConstraints(2, 5));

		functionPanel.add(rightPar, 	buttonConstraints(3, 1, 1, 1));
		functionPanel.add(squareRoot, 	buttonConstraints(3, 2, 1, 1));
		functionPanel.add(inverse,		buttonConstraints(3, 3, 1, 1));
		functionPanel.add(equals,		buttonConstraints(3, 4, 1, 2));

		functionPanel.add(pi, 			buttonConstraints(4, 1));
		functionPanel.add(e,			buttonConstraints(4, 2));
	}
	
	public static Constraints buttonConstraints(int x, int y) {
		Constraints gbc = new Constraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 0, 5, 5);
		return gbc;
	}
	public static Constraints buttonConstraints(int x, int y, int gridw, int gridh) {
		Constraints gbc = new Constraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = gridw;
		gbc.gridheight = gridh;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 0, 5, 5);
		return gbc;
	}

}
