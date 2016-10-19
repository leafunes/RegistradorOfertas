package gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AgregarOferta extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OfertaData data = new OfertaData();
	private JTextField nombreField;
	
	public AgregarOferta(Component parent){
		getContentPane().setLayout(null);
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		super.setSize(new Dimension(300, 300));
		super.setLocationRelativeTo(parent);
		
		
		nombreField = new JTextField();
		nombreField.setBounds(125, 11, 149, 20);
		getContentPane().add(nombreField);
		nombreField.setColumns(10);
		
		JButton btnOk = new JButton("Ok!");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				data.setNombre( nombreField.getText());
				dispose();
				
			}
		});
		btnOk.setBounds(94, 227, 89, 23);
		getContentPane().add(btnOk);
		
		
		
		
	}
}
