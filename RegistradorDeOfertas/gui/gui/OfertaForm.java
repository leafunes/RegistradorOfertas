package gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFormattedTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class OfertaForm extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OfertaData data = new OfertaData();
	private JTextField nombreField;
	private JTextField textField;
	private JTextField textField_1;
	
	public OfertaForm(Component parent){
		getContentPane().setLayout(null);
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		super.setSize(new Dimension(300, 360));
		super.setLocationRelativeTo(parent);
		
		
		nombreField = new JTextField();
		nombreField.setToolTipText("Nombre");
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
		btnOk.setBounds(32, 279, 89, 23);
		getContentPane().add(btnOk);
		
		textField = new JTextField();
		textField.setToolTipText("Apellido");
		textField.setBounds(125, 43, 149, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(125, 74, 149, 20);
		getContentPane().add(formattedTextField);
		
		textField_1 = new JTextField();
		textField_1.setBounds(125, 105, 149, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(125, 136, 149, 20);
		getContentPane().add(formattedTextField_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(125, 238, 149, 20);
		getContentPane().add(dateChooser);
		
		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setBounds(125, 167, 149, 20);
		getContentPane().add(formattedTextField_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(125, 198, 149, 20);
		getContentPane().add(comboBox);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 14, 46, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(10, 46, 46, 14);
		getContentPane().add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(10, 77, 46, 14);
		getContentPane().add(lblDni);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 108, 46, 14);
		getContentPane().add(lblEmail);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(10, 139, 46, 14);
		getContentPane().add(lblTelefono);
		
		JLabel lblMonto = new JLabel("Monto: ");
		lblMonto.setBounds(10, 170, 46, 14);
		getContentPane().add(lblMonto);
		
		JLabel lblEquipamento = new JLabel("Equipamento: ");
		lblEquipamento.setBounds(10, 201, 78, 14);
		getContentPane().add(lblEquipamento);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 238, 46, 14);
		getContentPane().add(lblFecha);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(151, 279, 89, 23);
		getContentPane().add(btnCancelar);
		
		
		
		
	}
}
