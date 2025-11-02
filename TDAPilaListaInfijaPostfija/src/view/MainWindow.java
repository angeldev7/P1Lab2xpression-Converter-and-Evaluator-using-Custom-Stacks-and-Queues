package view;

import javax.swing.*;
import java.awt.*;
import controller.NotationConverterEvaluator;
import model.EvaluationResult;
import model.ValidationResult;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTextField txtInfix;
	private JTextField txtPostfix;
	private JTextField txtPrefix;
	private JTextArea txtSteps;
	private JLabel lblResult;
	private JButton btnConvert;
	private JButton btnConvertPrefix;
	private JButton btnEvaluate;
	private JButton btnClear;
	
	public MainWindow() {
		initializeComponents();
		configureEvents();
	}
	
	private void initializeComponents() {
		setTitle("Expression Converter - Infix/Postfix/Prefix");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));
		
		// TOP PANEL - Title
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(41, 128, 185));
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		JLabel lblTitle = new JLabel("EXPRESSION CONVERTER");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitle.setForeground(Color.WHITE);
		titlePanel.add(lblTitle);
		add(titlePanel, BorderLayout.NORTH);
		
		// PANEL CENTRAL - Todo el contenido
		JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		panelPrincipal.setBackground(Color.WHITE);
		
		// PANEL SUPERIOR CENTRAL - Campos de texto
		JPanel panelCampos = new JPanel();
		panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
		panelCampos.setBackground(Color.WHITE);
		
		// Infix Field
		JLabel lblInfix = new JLabel("Infix Expression:");
		lblInfix.setFont(new Font("Arial", Font.BOLD, 14));
		txtInfix = new JTextField();
		txtInfix.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtInfix.setPreferredSize(new Dimension(0, 35));
		txtInfix.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		
		// Postfix Field
		JLabel lblPostfix = new JLabel("Postfix Expression:");
		lblPostfix.setFont(new Font("Arial", Font.BOLD, 14));
		txtPostfix = new JTextField();
		txtPostfix.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtPostfix.setPreferredSize(new Dimension(0, 35));
		txtPostfix.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		txtPostfix.setEditable(false);
		txtPostfix.setBackground(new Color(230, 247, 255));
		
		// Prefix Field
		JLabel lblPrefix = new JLabel("Prefix Expression (CHALLENGE):");
		lblPrefix.setFont(new Font("Arial", Font.BOLD, 14));
		txtPrefix = new JTextField();
		txtPrefix.setFont(new Font("Monospaced", Font.PLAIN, 16));
		txtPrefix.setPreferredSize(new Dimension(0, 35));
		txtPrefix.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		txtPrefix.setEditable(false);
		txtPrefix.setBackground(new Color(255, 243, 224));
		
		panelCampos.add(lblInfix);
		panelCampos.add(Box.createVerticalStrut(5));
		panelCampos.add(txtInfix);
		panelCampos.add(Box.createVerticalStrut(15));
		panelCampos.add(lblPostfix);
		panelCampos.add(Box.createVerticalStrut(5));
		panelCampos.add(txtPostfix);
		panelCampos.add(Box.createVerticalStrut(15));
		panelCampos.add(lblPrefix);
		panelCampos.add(Box.createVerticalStrut(5));
		panelCampos.add(txtPrefix);
		
		// PANEL BOTONES
		JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 0));
		panelBotones.setBackground(Color.WHITE);
		panelBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
		
		btnConvert = new JButton("Convert to Postfix");
		btnConvert.setFont(new Font("Arial", Font.BOLD, 13));
		btnConvert.setOpaque(true);
		btnConvert.setContentAreaFilled(true);
		btnConvert.setBorderPainted(true);
		btnConvert.setBackground(new Color(46, 125, 50));
		btnConvert.setForeground(Color.WHITE);
		btnConvert.setFocusPainted(false);
		
		btnConvertPrefix = new JButton("Convert to Prefix");
		btnConvertPrefix.setFont(new Font("Arial", Font.BOLD, 13));
		btnConvertPrefix.setOpaque(true);
		btnConvertPrefix.setContentAreaFilled(true);
		btnConvertPrefix.setBorderPainted(true);
		btnConvertPrefix.setBackground(new Color(142, 68, 173));
		btnConvertPrefix.setForeground(Color.WHITE);
		btnConvertPrefix.setFocusPainted(false);
		
		btnEvaluate = new JButton("Evaluate");
		btnEvaluate.setFont(new Font("Arial", Font.BOLD, 13));
		btnEvaluate.setOpaque(true);
		btnEvaluate.setContentAreaFilled(true);
		btnEvaluate.setBorderPainted(true);
		btnEvaluate.setBackground(new Color(21, 101, 192));
		btnEvaluate.setForeground(Color.WHITE);
		btnEvaluate.setFocusPainted(false);
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Arial", Font.BOLD, 13));
		btnClear.setOpaque(true);
		btnClear.setContentAreaFilled(true);
		btnClear.setBorderPainted(true);
		btnClear.setBackground(new Color(198, 40, 40));
		btnClear.setForeground(Color.WHITE);
		btnClear.setFocusPainted(false);
		
		panelBotones.add(btnConvert);
		panelBotones.add(btnConvertPrefix);
		panelBotones.add(btnEvaluate);
		panelBotones.add(btnClear);
		
		// Agregar campos y botones al panel superior
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
		panelSuperior.setBackground(Color.WHITE);
		panelSuperior.add(panelCampos);
		panelSuperior.add(Box.createVerticalStrut(20));
		panelSuperior.add(panelBotones);
		
		// PANEL INFERIOR - Area de proceso MUY GRANDE
		JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
		panelInferior.setBackground(Color.WHITE);
		
		JLabel lblSteps = new JLabel("STEP BY STEP PROCESS:");
		lblSteps.setFont(new Font("Arial", Font.BOLD, 16));
		
		txtSteps = new JTextArea(18, 60);
		txtSteps.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtSteps.setEditable(false);
		txtSteps.setLineWrap(true);
		txtSteps.setWrapStyleWord(true);
		txtSteps.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JScrollPane scrollSteps = new JScrollPane(txtSteps);
		scrollSteps.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		panelInferior.add(lblSteps, BorderLayout.NORTH);
		panelInferior.add(scrollSteps, BorderLayout.CENTER);
		
		// Panel Resultado
		JPanel panelResultado = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelResultado.setBackground(new Color(232, 245, 233));
		panelResultado.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(46, 125, 50), 3),
			BorderFactory.createEmptyBorder(15, 20, 15, 20)
		));
		
		JLabel lblTextResult = new JLabel("RESULT: ");
		lblTextResult.setFont(new Font("Arial", Font.BOLD, 20));
		lblResult = new JLabel("---");
		lblResult.setFont(new Font("Arial", Font.BOLD, 36));
		lblResult.setForeground(new Color(27, 94, 32));
		
		panelResultado.add(lblTextResult);
		panelResultado.add(lblResult);
		
		panelInferior.add(panelResultado, BorderLayout.SOUTH);
		
		// Agregar todo al panel principal
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelPrincipal.add(panelInferior, BorderLayout.CENTER);
		
		add(panelPrincipal, BorderLayout.CENTER);
		
		// PANEL INFERIOR - Info
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(240, 240, 240));
		panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel lblInfo = new JLabel("<html><center><b>Examples:</b> (2+3)*4, 5+6*2, A*(B+C)/D | <b>Operators:</b> + - * / ^</center></html>");
		lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
		panelInfo.add(lblInfo);
		add(panelInfo, BorderLayout.SOUTH);
	}
	
	private void configureEvents() {
		// Event: Convert to Postfix
		btnConvert.addActionListener(e -> {
			try {
				String infix = txtInfix.getText().trim();
				
				// Validate and convert using controller
				ValidationResult result = NotationConverterEvaluator.validateAndConvertToPostfix(infix);
				
				if (!result.isValid()) {
					JOptionPane.showMessageDialog(this, 
						result.getMessage(), 
						"Invalid Expression", 
						JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Get the converted expression from message
				String postfix = result.getMessage();
				txtPostfix.setText(postfix);
				
				// Get detailed step-by-step conversion process
				java.util.ArrayList<String> steps = NotationConverterEvaluator.convertToPostfixWithSteps(infix);
				StringBuilder sb = new StringBuilder();
				for (String step : steps) {
					sb.append(step).append("\n");
				}
				txtSteps.setText(sb.toString());
				txtSteps.setCaretPosition(0);
				
				lblResult.setText("---");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, 
					"Unexpected error during conversion:\n" + ex.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			}
		});
		
		// Event: Convert to Prefix (CHALLENGE)
		btnConvertPrefix.addActionListener(e -> {
			try {
				String infix = txtInfix.getText().trim();
				
				// Validate and convert using controller
				ValidationResult result = NotationConverterEvaluator.validateAndConvertToPrefix(infix);
				
				if (!result.isValid()) {
					JOptionPane.showMessageDialog(this, 
						result.getMessage(), 
						"Invalid Expression", 
						JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Get the converted expression from message
				String prefix = result.getMessage();
				txtPrefix.setText(prefix);
				
				// Get detailed step-by-step conversion process for PREFIX
				java.util.ArrayList<String> steps = NotationConverterEvaluator.convertToPrefixWithSteps(infix);
				StringBuilder sb = new StringBuilder();
				for (String step : steps) {
					sb.append(step).append("\n");
				}
				txtSteps.setText(sb.toString());
				txtSteps.setCaretPosition(0);
				
				lblResult.setText("---");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, 
					"Unexpected error during conversion:\n" + ex.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
			}
		});
		
		// Event: Evaluate Step by Step
		btnEvaluate.addActionListener(e -> {
			try {
				String postfix = txtPostfix.getText().trim();
				
				// Validate using controller
				ValidationResult validation = NotationConverterEvaluator.validateForEvaluation(postfix);
				
				if (!validation.isValid()) {
					JOptionPane.showMessageDialog(this,
						validation.getMessage(),
						"Cannot Evaluate",
						JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				// Call controller to evaluate
				EvaluationResult result = NotationConverterEvaluator.evaluatePostfixStepByStep(postfix);
				
				// Show steps
				StringBuilder sb = new StringBuilder();
				sb.append("=====================================\n");
				sb.append("STEP BY STEP EVALUATION\n");
				sb.append("=====================================\n\n");
				
				for (String step : result.getSteps()) {
					sb.append(step).append("\n");
				}
				
				txtSteps.setText(sb.toString());
				txtSteps.setCaretPosition(0);
				
				// Show result
				lblResult.setText(String.format("%.2f", result.getResult()));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this,
					"Unexpected error during evaluation:\n" + ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
				lblResult.setText("ERROR");
			}
		});
		
		// Event: Clear all
		btnClear.addActionListener(e -> {
			txtInfix.setText("");
			txtPostfix.setText("");
			txtPrefix.setText("");
			txtSteps.setText("");
			lblResult.setText("---");
			txtInfix.requestFocus();
		});
	}
}
