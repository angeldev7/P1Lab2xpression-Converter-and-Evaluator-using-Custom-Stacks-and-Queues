package model;

// Model class to represent a mathematical expression
public class Expression {
	private String content;
	private NotationType type;
	
	public enum NotationType {
		INFIX,
		POSTFIX,
		PREFIX
	}
	
	public Expression(String content, NotationType type) {
		this.content = content;
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public NotationType getType() {
		return type;
	}
	
	public void setType(NotationType type) {
		this.type = type;
	}
	
	// Validates if the expression only contains digits (for evaluation)
	public boolean containsOnlyDigits() {
		for (char c : content.toCharArray()) {
			if (Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}
	
	// Reverses the expression (useful for prefix conversion)
	public String reverse() {
		StringBuilder reversed = new StringBuilder(content);
		return reversed.reverse().toString();
	}
	
	// Reverses with parenthesis swap
	public String reverseWithParenthesis() {
		StringBuilder result = new StringBuilder();
		for (int i = content.length() - 1; i >= 0; i--) {
			char c = content.charAt(i);
			if (c == '(') {
				result.append(')');
			} else if (c == ')') {
				result.append('(');
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}
	
	@Override
	public String toString() {
		return content + " [" + type + "]";
	}
}
