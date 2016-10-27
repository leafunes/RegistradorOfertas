package gui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import proc.CurrentEquipamento;
import proc.EquipData;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EquipamentoField extends JPanel{

	
	public EquipamentoField(EquipamentoEdit parent,EquipData data) {
		
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setBackground(new Color(102, 204, 255));
		setForeground(new Color(102, 204, 204));
		setLayout(null);
		this.setBounds(0, 0, 300, 40);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("asd");
				parent.removeEquip(data);
				
			}
		});
		btnEliminar.setBackground(new Color(102, 204, 255));
		btnEliminar.setBounds(201, 7, 89, 23);
		add(btnEliminar);
		
		JLabel lblNombre = new JLabel(data.getNombre());
		lblNombre.setBounds(21, 11, 133, 14);
		add(lblNombre);
	}
}
