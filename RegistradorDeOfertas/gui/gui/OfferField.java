package gui;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class OfferField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public OfferField(OfertaData data) {
		//Dise�o
		setBorder(new LineBorder(new Color(0, 0, 0), 3));
		this.setBounds(0, 0, 650, 50);
		this.setBackground(new Color(204, 204, 153));
		setLayout(null);
		
		//Labels de info
		JLabel lblNombreApellido = new JLabel(data.nombre+ " " + data.apellido);
		JLabel lblTiempos = new JLabel("De " + data.inicio.getHourOfDay()+ "hs" + " a " + data.fin.getHourOfDay() + "hs");
		
		lblNombreApellido.setBounds(10, 19, 111, 14);
		lblTiempos.setBounds(182, 19, 165, 14);
	
		add(lblNombreApellido);
		add(lblTiempos);
		
		//Acciones
		JCheckBox chckbxAgregar = new JCheckBox("Agregar");
		chckbxAgregar.setBackground(new Color(204, 204, 153));
		chckbxAgregar.setBounds(547, 15, 97, 23);
		add(chckbxAgregar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(new Color(204, 204, 102));
		btnEliminar.setBounds(452, 15, 89, 23);
		add(btnEliminar);
		
		JButton btnDetalles = new JButton("Detalles...");
		btnDetalles.setBackground(new Color(204, 204, 102));
		btnDetalles.setBounds(357, 15, 89, 23);
		add(btnDetalles);
		
		
		

	}
}
