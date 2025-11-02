package model;

// Model class for input validation
public class Validator {
	
	// Validates if expression has balanced parentheses
	public static boolean hasBalancedParentheses(String expression) {
		if (expression == null || expression.isEmpty()) {
			return false;
		}
		
		int count = 0;
		for (char c : expression.toCharArray()) {
			if (c == '(') {
				count++;
			} else if (c == ')') {
				count--;
				if (count < 0) {
					return false; // More closing than opening
				}
			}
		}
		return count == 0; // Same number of opening and closing
	}
	
	// Validates if expression contains only valid characters
	public static boolean hasValidCharacters(String expression) {
		if (expression == null || expression.isEmpty()) {
			return false;
		}
		
		for (char c : expression.toCharArray()) {
			if (!Character.isLetterOrDigit(c) && 
			    !Operator.isOperator(c) && 
			    c != '(' && c != ')' && c != ' ') {
				return false;
			}
		}
		return true;
	}
	
	// Validates if expression has at least one operand
	public static boolean hasOperands(String expression) {
		if (expression == null || expression.isEmpty()) {
			return false;
		}
		
		for (char c : expression.toCharArray()) {
			if (Character.isLetterOrDigit(c)) {
				return true;
			}
		}
		return false;
	}
	
	// Validates if expression doesn't have consecutive operators
	public static boolean hasNoConsecutiveOperators(String expression) {
		if (expression == null || expression.isEmpty()) {
			return false;
		}
		
		boolean lastWasOperator = false;
		for (char c : expression.toCharArray()) {
			if (c == ' ') continue;
			
			if (Operator.isOperator(c)) {
				if (lastWasOperator) {
					return false; // Two consecutive operators
				}
				lastWasOperator = true;
			} else if (Character.isLetterOrDigit(c) || c == '(' || c == ')') {
				lastWasOperator = false;
			}
		}
		return true;
	}
	
	// Validates if expression contains only digits (for evaluation)
	public static boolean containsOnlyDigits(String expression) {
		if (expression == null || expression.isEmpty()) {
			return false;
		}
		
		for (char c : expression.toCharArray()) {
			if (c == ' ') continue;
			if (Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}
	
	// Complete validation for infix expression
	public static ValidationResult validateInfixExpression(String expression) {
		if (expression == null || expression.trim().isEmpty()) {
			return new ValidationResult(false, "Expression cannot be empty");
		}
		
		String trimmed = expression.trim();
		
		if (!hasValidCharacters(trimmed)) {
			return new ValidationResult(false, "Expression contains invalid characters.\nOnly letters, numbers, operators (+, -, *, /, ^) and parentheses are allowed");
		}
		
		if (!hasOperands(trimmed)) {
			return new ValidationResult(false, "Expression must contain at least one operand (letter or number)");
		}
		
		if (!hasBalancedParentheses(trimmed)) {
			return new ValidationResult(false, "Expression has unbalanced parentheses.\nCheck that every '(' has a matching ')'");
		}
		
		if (!hasNoConsecutiveOperators(trimmed)) {
			return new ValidationResult(false, "Expression cannot have consecutive operators.\nExample: 'A++B' is invalid");
		}
		
		return new ValidationResult(true, "Valid expression");
	}
	
	// Validates if postfix expression is ready for evaluation
	public static ValidationResult validateForEvaluation(String postfixExpression) {
		if (postfixExpression == null || postfixExpression.trim().isEmpty()) {
			return new ValidationResult(false, "First convert an expression to postfix");
		}
		
		if (!containsOnlyDigits(postfixExpression)) {
			return new ValidationResult(false, 
				"⚠️ NUMERICAL EVALUATION REQUIRED\n\n" +
				"The postfix expression '" + postfixExpression + "' contains letters.\n\n" +
				"• CONVERSION: ✓ Works with letters (A, B, C, etc.)\n" +
				"• EVALUATION: ✗ Requires only numbers (0-9)\n\n" +
				"To evaluate, enter a numeric infix expression:\n" +
				"Examples: (2+3)*4, 5+6*2, 4*(5+6-(8/2^3)-7)-11");
		}
		
		return new ValidationResult(true, "Valid for evaluation");
	}
}
