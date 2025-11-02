package model;

import java.util.ArrayList;

// Model class to store evaluation result with steps
public class EvaluationResult {
	private double result;
	private ArrayList<String> steps;
	
	public EvaluationResult() {
		this.steps = new ArrayList<>();
	}
	
	public EvaluationResult(double result, ArrayList<String> steps) {
		this.result = result;
		this.steps = steps;
	}
	
	public double getResult() {
		return result;
	}
	
	public void setResult(double result) {
		this.result = result;
	}
	
	public ArrayList<String> getSteps() {
		return steps;
	}
	
	public void setSteps(ArrayList<String> steps) {
		this.steps = steps;
	}
	
	// Compatibility methods for Spanish interface
	public double getResultado() {
		return result;
	}
	
	public ArrayList<String> getPasos() {
		return steps;
	}
	
	public void addStep(String step) {
		this.steps.add(step);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Result: ").append(result).append("\n");
		sb.append("Steps:\n");
		for (String step : steps) {
			sb.append("  - ").append(step).append("\n");
		}
		return sb.toString();
	}
}
