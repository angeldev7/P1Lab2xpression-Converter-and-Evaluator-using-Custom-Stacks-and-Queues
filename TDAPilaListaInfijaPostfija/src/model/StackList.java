package model;

// Stack implemented with simple linked list using StackNode
public class StackList {
	private StackNode top;
	
	// Constructor
	public StackList() {
		top = null;
	}
	
	// Checks if the stack is empty
	public boolean isEmpty() {
		return (top == null);
	}
	
	// Removes and returns element from top
	public Object pop() throws Exception {
		if (isEmpty()) {
			throw new Exception("Stack is empty, cannot extract");
		}
		Object element = top.getElement();
		top = top.getNext();
		return element;
	}
	
	// Views the top element without removing it
	public Object topElement() throws Exception {
		if (isEmpty()) {
			throw new Exception("Stack is empty");
		}
		return top.getElement();
	}
	
	// Alias for topElement (peek)
	public Object peek() throws Exception {
		if (isEmpty()) {
			throw new Exception("Stack is empty");
		}
		return top.getElement();
	}
	
	// Inserts element at the top
	public void push(Object x) {
		StackNode newNode = new StackNode(x);
		newNode.setNext(top);
		top = newNode;
	}
	
	// Completely empties the stack
	public void clear() {
		while (top != null) {
			top = top.getNext();
		}
	}
	
	// Displays all elements of the stack
	public void display() {
		StackNode aux = top;
		while (aux != null) {
			System.out.println(aux.getElement());
			aux = aux.getNext();
		}
	}
	
	// Returns stack contents as String (for step-by-step display)
	public String getContents() {
		if (isEmpty()) {
			return "[empty]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		StackNode aux = top;
		while (aux != null) {
			sb.append(aux.getElement());
			if (aux.getNext() != null) {
				sb.append(", ");
			}
			aux = aux.getNext();
		}
		sb.append("] ← top");
		return sb.toString();
	}
}
