package gui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;

import proc.OfertaData;

import javax.swing.JButton;

public class DetallesOfertaForm extends JDialog{
	
	public DetallesOfertaForm(Component parent, OfertaData data) {
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
		btnOk.setBounds(168, 170, 89, 23);
		getContentPane().add(btnOk);
		
		JLabel lblNombre_1 = new JLabel(data.getNombre());
		lblNombre_1.setBounds(87, 11, 132, 14);
		getContentPane().add(lblNombre_1);
		
		JLabel lblDni_1 = new JLabel(data.getDNI() + "");
		lblDni_1.setBounds(87, 36, 132, 14);
		getContentPane().add(lblDni_1);
		
		JLabel lblTel_1 = new JLabel(data.getTelefono() + "");
		lblTel_1.setBounds(87, 61, 132, 14);
		getContentPane().add(lblTel_1);
		
		JLabel lblApellido_1 = new JLabel(data.getApellido());
		lblApellido_1.setBounds(299, 11, 125, 14);
		getContentPane().add(lblApellido_1);
		
		JLabel lblEmail_1 = new JLabel(data.getEmail());
		lblEmail_1.setBounds(299, 36, 125, 14);
		getContentPane().add(lblEmail_1);
		
		JLabel lblMonto_1 = new JLabel(data.getPrecio() + "$");
		lblMonto_1.setBounds(299, 61, 125, 14);
		getContentPane().add(lblMonto_1);
		
		JLabel lblInicio_1 = new JLabel(data.getInicio() + "hs");
		lblInicio_1.setBounds(299, 92, 125, 14);
		getContentPane().add(lblInicio_1);
		
		JLabel lblFin_1 = new JLabel(data.getFin() + "hs");
		lblFin_1.setBounds(299, 117, 125, 14);
		getContentPane().add(lblFin_1);
		
	}

}
