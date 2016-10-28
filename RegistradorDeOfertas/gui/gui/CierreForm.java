package gui;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.joda.time.DateTime;

import proc.OfertaData;

public class CierreForm extends JDialog{
	
	public CierreForm(Component parent) {
		getContentPane().setLayout(null);
		setSize(new Dimension(745, 420));
		setLocationRelativeTo(parent);
		
		Viewer<OfertaMiniField> viewer = new Viewer<>(0, 0, 650, 300);
		viewer.setBounds(247, 11, 472, 300);
		getContentPane().add(viewer);
		
		OfertaData data = new OfertaData();
		data.setNombre("lea");
		data.setApellido("funes");
		data.setInicio(12);
		data.setFin(15);
		
		for (int i = 0; i < 10; i++) {
			
			OfertaMiniField mini = new OfertaMiniField(viewer, null, data);
			viewer.addToViewer(mini);
			
		}
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 172, 300);
		getContentPane().add(panel);
		
	}
}
