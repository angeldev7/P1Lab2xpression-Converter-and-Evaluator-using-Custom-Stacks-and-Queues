package controller;

import model.StackList;
import model.Operator;
import model.Expression;
import model.Expression.NotationType;
import model.EvaluationResult;
import model.Validator;
import model.ValidationResult;
import java.util.ArrayList;

// Controller that handles expression conversion and evaluation
// Following laboratory guide requirements
public class NotationConverterEvaluator {
	
	// Validates and converts infix to postfix (with error handling)
	public static ValidationResult validateAndConvertToPostfix(String infixExpression) {
		ValidationResult validation = Validator.validateInfixExpression(infixExpression);
		if (!validation.isValid()) {
			return validation;
		}
		return new ValidationResult(true, convertToPostfix(infixExpression));
	}
	
	// Validates and converts infix to prefix (with error handling)
	public static ValidationResult validateAndConvertToPrefix(String infixExpression) {
		ValidationResult validation = Validator.validateInfixExpression(infixExpression);
		if (!validation.isValid()) {
			return validation;
		}
		return new ValidationResult(true, convertToPrefix(infixExpression));
	}
	
	// Validates before evaluation
	public static ValidationResult validateForEvaluation(String postfixExpression) {
		return Validator.validateForEvaluation(postfixExpression);
	}
	
	// Converts infix expression to postfix using StackList
	public static String convertToPostfix(String infixExpression) {
		StringBuilder postfix = new StringBuilder();
		StackList stack = new StackList();
		
		// Traverses each character of the infix expression
		for (int i = 0; i < infixExpression.length(); i++) {
			char symbol = infixExpression.charAt(i);
			
			if (symbol == ' ') {
				continue; // Ignores spaces
			}
			
			// If it's a letter or number, read the complete number/variable
			if (Character.isLetterOrDigit(symbol)) {
				// Read complete number or variable
				while (i < infixExpression.length() && Character.isLetterOrDigit(infixExpression.charAt(i))) {
					postfix.append(infixExpression.charAt(i));
					i++;
				}
				i--; // Step back one position
				postfix.append(' '); // Add space as separator
			}
			// If it's '(' push to stack
			else if (symbol == '(') {
				stack.push(symbol);
			}
			// If it's ')' pop operators until finding '('
			else if (symbol == ')') {
				try {
					while (!stack.isEmpty() && (char)stack.peek() != '(') {
						postfix.append((char)stack.peek()).append(' ');
						stack.pop();
					}
					if (!stack.isEmpty()) {
						stack.pop(); // Removes the '('
					}
				} catch (Exception e) {}
			}
			// If it's operator pop those with higher or equal priority
			else if (isOperator(symbol)) {
				try {
					while (!stack.isEmpty() && 
					       (char)stack.peek() != '(' &&
					       priority((char)stack.peek()) >= priority(symbol)) {
						postfix.append((char)stack.peek()).append(' ');
						stack.pop();
					}
					stack.push(symbol);
				} catch (Exception e) {}
			}
		}
		
		// Empties the stack at the end
		try {
			while (!stack.isEmpty()) {
				postfix.append((char)stack.peek()).append(' ');
				stack.pop();
			}
		} catch (Exception e) {}
		
		return postfix.toString().trim();
	}
	
	// CHALLENGE: Converts infix expression to prefix using Expression class
	public static String convertToPrefix(String infixExpression) {
		// Creates Expression object
		Expression expression = new Expression(infixExpression, NotationType.INFIX);
		
		// Reverses with parenthesis swap
		String reversedInfix = expression.reverseWithParenthesis();
		
		// Converts to postfix
		String postfix = convertToPostfix(reversedInfix);
		
		// Reverses the result to get prefix
		Expression result = new Expression(postfix, NotationType.POSTFIX);
		return result.reverse();
	}
	
	// Converts to prefix WITH STEP-BY-STEP details for lab report
	public static java.util.ArrayList<String> convertToPrefixWithSteps(String infixExpression) {
		java.util.ArrayList<String> steps = new java.util.ArrayList<>();
		
		steps.add("═══════════════════════════════════════════════════");
		steps.add("INFIX TO PREFIX CONVERSION - STEP BY STEP (CHALLENGE)");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		steps.add("Original Infix Expression: " + infixExpression);
		steps.add("");
		steps.add("Algorithm:");
		steps.add("1. Reverse the infix expression (swap parentheses)");
		steps.add("2. Convert reversed expression to postfix");
		steps.add("3. Reverse the postfix result to get prefix");
		steps.add("");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("STEP 1: REVERSE INFIX (swap parentheses)");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		
		// Step 1: Creates Expression object and reverse
		Expression expression = new Expression(infixExpression, NotationType.INFIX);
		String reversedInfix = expression.reverseWithParenthesis();
		
		steps.add("Original:       " + infixExpression);
		steps.add("Reversed:       " + reversedInfix);
		steps.add("(Note: '(' becomes ')', and vice versa)");
		steps.add("");
		
		steps.add("═══════════════════════════════════════════════════");
		steps.add("STEP 2: CONVERT REVERSED INFIX TO POSTFIX");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		
		// Step 2: Convert to postfix with detailed steps
		java.util.ArrayList<String> postfixSteps = convertToPostfixWithStepsInternal(reversedInfix);
		
		// Add postfix conversion steps (skip the header)
		for (int i = 8; i < postfixSteps.size(); i++) {
			steps.add(postfixSteps.get(i));
		}
		
		String postfix = convertToPostfix(reversedInfix);
		
		steps.add("");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("STEP 3: REVERSE POSTFIX TO GET PREFIX");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		
		// Step 3: Reverse the postfix
		Expression result = new Expression(postfix, NotationType.POSTFIX);
		String prefix = result.reverse();
		
		steps.add("Postfix result:  " + postfix);
		steps.add("Reversed:        " + prefix);
		steps.add("");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("✓ PREFIX CONVERSION COMPLETE!");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		steps.add("Final Prefix Expression: " + prefix);
		steps.add("");
		
		return steps;
	}
	
	// Internal helper for postfix conversion steps (reusable)
	private static java.util.ArrayList<String> convertToPostfixWithStepsInternal(String infixExpression) {
		java.util.ArrayList<String> steps = new java.util.ArrayList<>();
		StringBuilder postfix = new StringBuilder();
		StackList stack = new StackList();
		
		steps.add("═══════════════════════════════════════════════════");
		steps.add("INFIX TO POSTFIX CONVERSION - STEP BY STEP");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		steps.add("Infix Expression: " + infixExpression);
		steps.add("");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		
		int stepNum = 1;
		
		// Traverses each character of the infix expression
		for (int i = 0; i < infixExpression.length(); i++) {
			char symbol = infixExpression.charAt(i);
			
			if (symbol == ' ') {
				continue; // Ignores spaces
			}
			
			// If it's a letter or number, read the complete number/variable
			if (Character.isLetterOrDigit(symbol)) {
				StringBuilder operand = new StringBuilder();
				steps.add("Step " + stepNum++ + ": Reading operand starting with '" + symbol + "'");
				
				// Read complete number or variable
				while (i < infixExpression.length() && Character.isLetterOrDigit(infixExpression.charAt(i))) {
					operand.append(infixExpression.charAt(i));
					i++;
				}
				i--; // Step back one position
				
				postfix.append(operand).append(' ');
				steps.add("   → Complete operand: '" + operand + "'");
				steps.add("   → Add '" + operand + "' directly to output");
				steps.add("   → Postfix so far: " + postfix.toString().trim());
				steps.add("   → Stack: " + getStackContent(stack));
			}
			// If it's '(' push to stack
			else if (symbol == '(') {
				steps.add("Step " + stepNum++ + ": Reading symbol '" + symbol + "'");
				stack.push(symbol);
				steps.add("   → Left parenthesis '(' detected");
				steps.add("   → Push '(' to stack");
				steps.add("   → Postfix so far: " + postfix.toString().trim());
				steps.add("   → Stack: " + getStackContent(stack));
			}
			// If it's ')' pop operators until finding '('
			else if (symbol == ')') {
				steps.add("Step " + stepNum++ + ": Reading symbol '" + symbol + "'");
				steps.add("   → Right parenthesis ')' detected");
				steps.add("   → Pop all operators until '('");
				try {
					while (!stack.isEmpty() && (char)stack.peek() != '(') {
						char op = (char)stack.peek();
						postfix.append(op).append(' ');
						stack.pop();
						steps.add("   → Pop '" + op + "' and add to output");
					}
					if (!stack.isEmpty()) {
						stack.pop(); // Removes the '('
						steps.add("   → Remove '(' from stack");
					}
				} catch (Exception e) {}
				steps.add("   → Postfix so far: " + postfix.toString().trim());
				steps.add("   → Stack: " + getStackContent(stack));
			}
			// If it's operator pop those with higher or equal priority
			else if (isOperator(symbol)) {
				steps.add("Step " + stepNum++ + ": Reading symbol '" + symbol + "'");
				steps.add("   → Operator '" + symbol + "' detected (priority: " + priority(symbol) + ")");
				try {
					while (!stack.isEmpty() && 
					       (char)stack.peek() != '(' &&
					       priority((char)stack.peek()) >= priority(symbol)) {
						char op = (char)stack.peek();
						steps.add("   → Stack top '" + op + "' has higher/equal priority");
						postfix.append(op).append(' ');
						stack.pop();
						steps.add("   → Pop '" + op + "' and add to output");
					}
					stack.push(symbol);
					steps.add("   → Push '" + symbol + "' to stack");
				} catch (Exception e) {}
				steps.add("   → Postfix so far: " + postfix.toString().trim());
				steps.add("   → Stack: " + getStackContent(stack));
			}
			
			steps.add("");
		}
		
		// Empties the stack at the end
		steps.add("═══════════════════════════════════════════════════");
		steps.add("FINAL STEP: Empty the stack");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		try {
			while (!stack.isEmpty()) {
				char op = (char)stack.peek();
				postfix.append(op).append(' ');
				stack.pop();
				steps.add("   → Pop '" + op + "' and add to output");
				steps.add("   → Postfix so far: " + postfix.toString().trim());
			}
		} catch (Exception e) {}
		
		steps.add("");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("✓ CONVERSION COMPLETE!");
		steps.add("═══════════════════════════════════════════════════");
		steps.add("");
		steps.add("Final Postfix Expression: " + postfix.toString().trim());
		steps.add("");
		
		return steps;
	}
	
	// Evaluates postfix expression step by step and returns EvaluationResult with steps
	public static EvaluationResult evaluatePostfixStepByStep(String postfix) {
		ArrayList<String> steps = new ArrayList<>();
		StackList stack = new StackList();
		
		steps.add("Postfix expression: " + postfix);
		steps.add("Starting evaluation...\n");
		
		// Split by spaces to handle multi-digit numbers
		String[] tokens = postfix.trim().split("\\s+");
		
		for (String token : tokens) {
			if (token.isEmpty()) {
				continue;
			}
			
			// Try to parse as a number
			try {
				double value = Double.parseDouble(token);
				stack.push(Double.valueOf(value));
				steps.add("Step " + (steps.size()-1) + ": Push " + value + " to stack");
			} catch (NumberFormatException e) {
				// It's an operator
				if (token.length() == 1 && isOperator(token.charAt(0))) {
					char symbol = token.charAt(0);
					try {
						if (stack.isEmpty()) {
							steps.add("ERROR: Stack is empty, invalid expression");
							return new EvaluationResult(0, steps);
						}
						
						Object obj2 = stack.peek();
						stack.pop();
						double operand2 = ((Double)obj2).doubleValue();
						
						if (stack.isEmpty()) {
							steps.add("ERROR: Missing operands");
							return new EvaluationResult(0, steps);
						}
						
						Object obj1 = stack.peek();
						stack.pop();
						double operand1 = ((Double)obj1).doubleValue();
						
						double result = operate(operand1, operand2, symbol);
						stack.push(Double.valueOf(result));
						
						steps.add("Step " + (steps.size()-1) + ": Operate " + operand1 + " " + symbol + " " + operand2 + " = " + result);
						
					} catch (Exception ex) {
						steps.add("ERROR: " + ex.getMessage());
						return new EvaluationResult(0, steps);
					}
				}
			}
		}
		
		// Gets final result
		try {
			if (!stack.isEmpty()) {
				Object objResult = stack.peek();
				double finalResult = ((Double)objResult).doubleValue();
				steps.add("\nFinal result: " + finalResult);
				return new EvaluationResult(finalResult, steps);
			} else {
				steps.add("ERROR: Stack is empty at the end");
				return new EvaluationResult(0, steps);
			}
		} catch (Exception e) {
			steps.add("ERROR: " + e.getMessage());
			return new EvaluationResult(0, steps);
		}
	}
	
	// Checks if it's an operator using Operator class
	public static boolean isOperator(char c) {
		return Operator.isOperator(c);
	}
	
	// Returns operator priority in stack using Operator class
	public static int stackPriority(char operator) {
		Operator op = new Operator(operator);
		return op.getStackPriority();
	}
	
	// Returns operator priority in input (infix) using Operator class
	public static int infixPriority(char operator) {
		Operator op = new Operator(operator);
		return op.getInfixPriority();
	}
	
	// Auxiliary method for compatibility (uses infixPriority)
	private static int priority(char operator) {
		return infixPriority(operator);
	}
	
	// Performs arithmetic operation using Operator class
	public static double operate(double a, double b, char operator) {
		Operator op = new Operator(operator);
		return op.operate(a, b);
	}
	
	// Converts to postfix WITH STEP-BY-STEP details for lab report
	// Converts to postfix WITH STEP-BY-STEP details for lab report (public wrapper)
	public static java.util.ArrayList<String> convertToPostfixWithSteps(String infixExpression) {
		return convertToPostfixWithStepsInternal(infixExpression);
	}
	
	// Helper method to display stack contents
	private static String getStackContent(StackList stack) {
		return stack.getContents();
	}
}
