package gui;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class CierreForm extends JDialog{
	
	public CierreForm() {
		getContentPane().setLayout(null);
		
		Viewer<OfertaMiniField> viewer = new Viewer<>(0, 0, 650, 300);
		viewer.setBounds(247, 11, 450, 300);
		getContentPane().add(viewer);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 172, 300);
		getContentPane().add(panel);
		
	}
}
