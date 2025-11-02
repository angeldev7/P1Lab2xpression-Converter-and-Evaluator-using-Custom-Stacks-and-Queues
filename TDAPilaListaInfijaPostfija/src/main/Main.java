package main;

import view.MainWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) {
		// Runs the GUI in the Swing event thread
		SwingUtilities.invokeLater(() -> {
			try {
				// Use Nimbus Look and Feel which respects custom colors better than Windows L&F
				for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (Exception e) {
				// If Nimbus is not available, use default Look and Feel
				System.out.println("Nimbus Look and Feel not available, using default");
			}
			
			// Creates and shows the main window
			MainWindow window = new MainWindow();
			window.setVisible(true);
			
			System.out.println("====================================");
			System.out.println("  APPLICATION STARTED");
			System.out.println("====================================");
			System.out.println("Project structure:");
			System.out.println("  - model: Node, Stack, Operator, Expression, EvaluationResult");
			System.out.println("  - controller: NotationConverter");
			System.out.println("  - view: MainWindow");
			System.out.println("====================================");
		});
	}
}
