package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

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
	//TODO hacer otro extends

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox chckbxAgregar;
	private JButton btnEliminar;
	private JButton btnDetalles;
	private JLabel lblTiempos;
	private JLabel lblNombreApellido;
	private Viewer<OfertaField> viewer;
	private OfertaData data;
	private OfertaField thisRef;

	/**
	 * Create the panel.
	 */
	public OfertaField(Viewer<OfertaField> viewer, OfertaData data) {
		
		//Dise�o
		setBorder(new LineBorder(new Color(0, 0, 0), 3));
		this.setBounds(0, 0, 650, 50);
		this.setBackground(new Color(204, 204, 153));
		this.setPreferredSize(new Dimension(630, 50));
		setLayout(null);
		this.viewer = viewer;
		this.data = data;
		thisRef = this;
		
		initButton();
		initLabels();
		initChechButton();
		
		
		
		btnDetalles.setBackground(new Color(204, 204, 102));
		btnDetalles.setBounds(357, 15, 89, 23);
		add(btnDetalles);
		
	}
	
	protected void initButton(){
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				viewer.removeFromViewer(thisRef);
				
				CurrentOfertas.getCurrent().removeOferta(data, data.getFecha());
				
			}
		});
		btnEliminar.setBackground(new Color(204, 204, 102));
		btnEliminar.setBounds(452, 15, 89, 23);
		add(btnEliminar);
		
		btnDetalles = new JButton("Detalles...");
		btnDetalles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				DetallesOfertaForm detalles = new DetallesOfertaForm(viewer, data);
				detalles.setVisible(true);
				
				
			}
		});
		
	}
	
	public boolean isObligatorio(){
		return chckbxAgregar.isSelected();
	}
	
	public OfertaData getData(){
		return data;
	}
	
	protected void initLabels(){
		//Labels de info
		lblNombreApellido = new JLabel(data.getNombre()+ " " + data.getApellido());
		lblTiempos = new JLabel("De " + data.getInicio().toString("hh:mm")+ "hs" + " a " + data.getFin().toString("hh:mm") + "hs");
		
		lblNombreApellido.setBounds(10, 19, 111, 14);
		lblTiempos.setBounds(182, 19, 165, 14);
		
		add(lblNombreApellido);
		add(lblTiempos);
	}
	
	protected void initChechButton(){

		chckbxAgregar = new JCheckBox("Agregar");
		chckbxAgregar.setBackground(new Color(204, 204, 153));
		chckbxAgregar.setBounds(547, 15, 80, 23);
		add(chckbxAgregar);
					
	}
}
