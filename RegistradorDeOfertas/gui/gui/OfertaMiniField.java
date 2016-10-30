package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import proc.OfertaData;

public class OfertaMiniField extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public OfertaMiniField(Viewer<OfertaMiniField> parent, OfertaData data) {
		
		//Dise�o
		setBorder(new LineBorder(new Color(0, 0, 0), 3));
		this.setBounds(0, 0, 320, 50);
		this.setBackground(new Color(204, 204, 153));
		this.setPreferredSize(new Dimension(450, 50));
		setLayout(null);
		
		//Labels de info
		JLabel lblNombreApellido = new JLabel(data.getNombre()+ " " + data.getApellido());
		JLabel lblTiempos = new JLabel("De " + data.getInicio().toString("hh:mm")+ "hs" + " a " + data.getFin().toString("hh:mm") + "hs");
		
		lblNombreApellido.setBounds(10, 19, 111, 14);
		lblTiempos.setBounds(167, 19, 165, 14);
	
		add(lblNombreApellido);
		add(lblTiempos);
		
		JButton btnDetalles = new JButton("Detalles...");
		btnDetalles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				DetallesOfertaForm detalles = new DetallesOfertaForm(parent, data);
				detalles.setVisible(true);
				
				
			}
		});
		btnDetalles.setBackground(new Color(204, 204, 102));
		btnDetalles.setBounds(342, 15, 89, 23);
		add(btnDetalles);
		
		
		
	}
}
