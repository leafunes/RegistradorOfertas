package gui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import proc.EquipData;
import proc.OfertaData;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class DetallesOfertaForm extends JDialog{
	
	public DetallesOfertaForm(Component parent, OfertaData data) {
		setTitle("Detalles");
		getContentPane().setLayout(null);
		super.setSize(new Dimension(450, 260));
		super.setLocationRelativeTo(parent);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 11, 58, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(229, 11, 60, 14);
		getContentPane().add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI: ");
		lblDni.setBounds(10, 36, 46, 14);
		getContentPane().add(lblDni);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(229, 36, 46, 14);
		getContentPane().add(lblEmail);
		
		JLabel lblInicio = new JLabel("Inicio: ");
		lblInicio.setBounds(229, 92, 46, 14);
		getContentPane().add(lblInicio);
		
		JLabel lblFin = new JLabel("Fin: ");
		lblFin.setBounds(229, 117, 46, 14);
		getContentPane().add(lblFin);
		
		JLabel lblEquipamento = new JLabel("Equip.:\r\n");
		lblEquipamento.setBounds(10, 92, 46, 14);
		getContentPane().add(lblEquipamento);
		
		JLabel lblTel = new JLabel("Tel.:");
		lblTel.setBounds(10, 61, 46, 14);
		getContentPane().add(lblTel);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(229, 61, 46, 14);
		getContentPane().add(lblMonto);
		
		JButton btnOk = new JButton("Ok!");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btnOk.setBounds(165, 185, 89, 23);
		getContentPane().add(btnOk);
		
		JTextField lblNombre_1 = new JTextField(data.getNombre());
		lblNombre_1.setEditable(false);
		lblNombre_1.setBounds(87, 8, 132, 20);
		getContentPane().add(lblNombre_1);
		
		JTextField lblDni_1 = new JTextField(data.getDNI() + "");
		lblDni_1.setEditable(false);
		lblDni_1.setBounds(87, 36, 132, 20);
		getContentPane().add(lblDni_1);
		
		JTextField lblTel_1 = new JTextField(data.getTelefono() + "");
		lblTel_1.setEditable(false);
		lblTel_1.setBounds(87, 61, 132, 20);
		getContentPane().add(lblTel_1);
		
		JTextField lblApellido_1 = new JTextField(data.getApellido());
		lblApellido_1.setEditable(false);
		lblApellido_1.setBounds(299, 11, 125, 20);
		getContentPane().add(lblApellido_1);
		
		JTextField lblEmail_1 = new JTextField(data.getEmail());
		lblEmail_1.setEditable(false);
		lblEmail_1.setBounds(299, 36, 125, 20);
		getContentPane().add(lblEmail_1);
		
		JTextField lblMonto_1 = new JTextField(data.getPrecio() + "$");
		lblMonto_1.setEditable(false);
		lblMonto_1.setBounds(299, 61, 125, 20);
		getContentPane().add(lblMonto_1);
		
		JTextField lblInicio_1 = new JTextField(data.getInicio().toString("hh:mm") + "hs");
		lblInicio_1.setEditable(false);
		lblInicio_1.setBounds(299, 92, 125, 20);
		getContentPane().add(lblInicio_1);
		
		JTextField lblFin_1 = new JTextField(data.getFin().toString("hh:mm")  + "hs");
		lblFin_1.setEditable(false);
		lblFin_1.setBounds(299, 117, 125, 20);
		getContentPane().add(lblFin_1);
		
		
		//TODO: equip
		JPanel equipPanel = new JPanel();
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(83, 92, 132, 80);
		getContentPane().add(scroll);
		
		scroll.setViewportView(equipPanel);
		equipPanel.setLayout(new GridLayout(0, 1, 0, 1));
		
		for(EquipData equip: data.getEquip()){
			
			JTextField equipTxt = new JTextField(equip.getNombre());
			equipTxt.setSize(new Dimension(130, 5));
			equipPanel.add(equipTxt);
			
		}
		
	}
}
