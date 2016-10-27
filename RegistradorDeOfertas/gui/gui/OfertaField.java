package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import proc.CurrentOfertas;
import proc.OfertaData;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OfertaField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public OfertaField(OfertasViewer parent, OfertaData data) {
		//Diseño
		setBorder(new LineBorder(new Color(0, 0, 0), 3));
		this.setBounds(0, 0, 650, 50);
		this.setBackground(new Color(204, 204, 153));
		setLayout(null);
		
		//Labels de info
		JLabel lblNombreApellido = new JLabel(data.getNombre()+ " " + data.getApellido());
		JLabel lblTiempos = new JLabel("De " + data.getInicio().toString("hh:mm")+ "hs" + " a " + data.getFin().toString("hh:mm") + "hs");
		
		lblNombreApellido.setBounds(10, 19, 111, 14);
		lblTiempos.setBounds(182, 19, 165, 14);
	
		add(lblNombreApellido);
		add(lblTiempos);
		
		//Acciones
		JCheckBox chckbxAgregar = new JCheckBox("Agregar");
		chckbxAgregar.setBackground(new Color(204, 204, 153));
		chckbxAgregar.setBounds(547, 15, 80, 23);
		add(chckbxAgregar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.removeOferta(data);
				
				CurrentOfertas.getCurrent().removeOferta(data, data.getFecha());
				
			}
		});
		btnEliminar.setBackground(new Color(204, 204, 102));
		btnEliminar.setBounds(452, 15, 89, 23);
		add(btnEliminar);
		
		JButton btnDetalles = new JButton("Detalles...");
		btnDetalles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				DetallesOfertaForm detalles = new DetallesOfertaForm(parent, data);
				detalles.setVisible(true);
				
				
			}
		});
		btnDetalles.setBackground(new Color(204, 204, 102));
		btnDetalles.setBounds(357, 15, 89, 23);
		add(btnDetalles);
		
		
		

	}
}
