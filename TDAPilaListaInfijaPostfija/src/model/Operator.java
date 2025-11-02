package model;
// Model class to represent a mathematical operator
public class Operator {
	private char symbol;
	private int infixPriority;
	private int stackPriority;
	
	public Operator(char symbol) {
		this.symbol = symbol;
		assignPriorities();
	}
	
	private void assignPriorities() {
		switch (symbol) {
			case '^':
				infixPriority = 4;
				stackPriority = 3;
				break;
			case '*':
			case '/':
				infixPriority = 2;
				stackPriority = 2;
				break;
			case '+':
			case '-':
				infixPriority = 1;
				stackPriority = 1;
				break;
			case '(':
				infixPriority = 5;
				stackPriority = 0;
				break;
			default:
				infixPriority = 0;
				stackPriority = 0;
		}
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public int getInfixPriority() {
		return infixPriority;
	}
	
	public int getStackPriority() {
		return stackPriority;
	}
	
	public static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}
	
	// Performs the operation between two numbers
	public double operate(double operand1, double operand2) {
		switch (symbol) {
			case '+': return operand1 + operand2;
			case '-': return operand1 - operand2;
			case '*': return operand1 * operand2;
			case '/': 
				if (operand2 == 0) {
					throw new ArithmeticException("Division by zero");
				}
				return operand1 / operand2;
			case '^': return Math.pow(operand1, operand2);
			default: return 0;
		}
	}
	
	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
}
