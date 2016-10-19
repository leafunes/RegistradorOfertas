package gui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;

public class OfferField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public OfferField(String nombre) {
		setBorder(new LineBorder(new Color(0, 0, 0), 3));
		
		this.setBounds(0, 0, 650, 50);
		setLayout(null);
		
		JLabel lblNombre = new JLabel(nombre);
		lblNombre.setBounds(10, 11, 189, 14);
		add(lblNombre);
		
		this.setBackground(new Color(204, 204, 153));
		
		JCheckBox chckbxAgregar = new JCheckBox("Agregar");
		chckbxAgregar.setBackground(new Color(204, 204, 153));
		chckbxAgregar.setBounds(524, 11, 97, 23);
		add(chckbxAgregar);
		
		

	}
}
