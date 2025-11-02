package model;

// Stack node for simple linked list
public class StackNode {
	private Object element;
	private StackNode next;
	
	public StackNode(Object x) {
		element = x;
		next = null;
	}
	
	// Getters and Setters
	public Object getElement() {
		return element;
	}
	
	public void setElement(Object element) {
		this.element = element;
	}
	
	public StackNode getNext() {
		return next;
	}
	
	public void setNext(StackNode next) {
		this.next = next;
	}
}
