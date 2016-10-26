package gui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class EquipamentoField extends JPanel{

	
	public EquipamentoField(String nombre) {
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBackground(new Color(102, 204, 255));
		setForeground(new Color(102, 204, 204));
		setLayout(null);
		this.setBounds(0, 0, 300, 40);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(102, 204, 255));
		btnEliminar.setBounds(201, 7, 89, 23);
		add(btnEliminar);
		
		JLabel lblNombre = new JLabel(nombre);
		lblNombre.setBounds(21, 11, 133, 14);
		add(lblNombre);
	}
}
