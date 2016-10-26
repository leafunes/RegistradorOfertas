package gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import org.joda.time.DateTime;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JFormattedTextField;

import com.toedter.calendar.JDateChooser;

import proc.OfertaData;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OfertaForm extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OfertaData data = new OfertaData();
	private JTextField nombreField;
	private JTextField apellidoField;
	private JFormattedTextField emailField;
	private JFormattedTextField dniField;
	private JFormattedTextField telField;
	private JFormattedTextField mntoField;
	private JSpinner tiempoFin;
	private JSpinner tiempoInicio;
	
	private DateTime date;
	
	public OfertaForm(Component parent, DateTime date){
		
		this.date = date;
		getContentPane().setLayout(null);
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		super.setSize(new Dimension(500, 300));
		super.setLocationRelativeTo(parent);
		
		JButton btnOk = new JButton("Ok!");
		JButton btnCancelar = new JButton("Cancelar");
		btnOk.setBounds(84, 220, 89, 23);
		btnCancelar.setBounds(318, 220, 89, 23);
		getContentPane().add(btnOk);
		getContentPane().add(btnCancelar);
		
		//Acciones de botones
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				
				if(isDataValid()){
				
					fillData();
					dispose();
				}
				
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
			}
		});
		
		//Fields
		
		nombreField = new JTextField();
		apellidoField = new JTextField();
		emailField = new JFormattedTextField( new RegexFormatter("\\b([\\w\\.]+)@([\\w\\.]+)\\.(\\w+)\\b"));//Email Regex
		telField = new JFormattedTextField(new RegexFormatter("\\d{8,}"));//Telefono Regex. 8 o mas?
		mntoField = new JFormattedTextField(new RegexFormatter("\\d*(\\.|,)?\\d{2}"));//Cualquier cantidad de digitos
		dniField = new JFormattedTextField(new RegexFormatter("\\d{8}"));
		
		
		tiempoInicio = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor de_tiempoInicio = new JSpinner.DateEditor(tiempoInicio, "HH");//Formato de hora
		tiempoFin = new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor de_tiempoFin = new JSpinner.DateEditor(tiempoFin, "HH");//Formato de hora
		
		
		nombreField.setBounds(84, 11, 149, 20);
		apellidoField.setBounds(318, 11, 149, 20);
		dniField.setBounds(84, 42, 149, 20);
		emailField.setBounds(318, 42, 149, 20);
		telField.setBounds(84, 73, 149, 20);
		mntoField.setBounds(318, 73, 149, 20);
		tiempoInicio.setBounds(359, 104, 46, 20);
		tiempoFin.setBounds(359, 135, 46, 20);
		
		tiempoInicio.setEditor(de_tiempoInicio);
		tiempoInicio.setValue(new Date());
		tiempoFin.setEditor(de_tiempoFin);
		tiempoFin.setValue(new Date());
		
		getContentPane().add(nombreField);
		getContentPane().add(apellidoField);
		getContentPane().add(dniField);
		getContentPane().add(emailField);
		getContentPane().add(telField);
		getContentPane().add(mntoField);
		getContentPane().add(tiempoInicio);
		getContentPane().add(tiempoFin);
		
		//Labels
		JLabel lblNombre = new JLabel("Nombre:");
		JLabel lblApellido = new JLabel("Apellido:");
		JLabel lblDni = new JLabel("DNI:");
		JLabel lblEmail = new JLabel("Email:");
		JLabel lblTelefono = new JLabel("Telefono:");
		JLabel lblMonto = new JLabel("Monto: ");
		JLabel lblEquipamento = new JLabel("Equip.:");
		JLabel lblInicio = new JLabel("Inicio:");
		JLabel lblFin = new JLabel("Fin:");
		
		
		
		lblNombre.setBounds(10, 14, 64, 14);
		lblApellido.setBounds(262, 14, 59, 14);
		lblDni.setBounds(10, 45, 46, 14);
		lblEmail.setBounds(262, 45, 46, 14);
		lblTelefono.setBounds(10, 76, 59, 14);
		lblMonto.setBounds(262, 76, 46, 14);
		lblEquipamento.setBounds(10, 101, 82, 14);
		lblInicio.setBounds(262, 107, 46, 14);
		lblFin.setBounds(262, 138, 46, 14);
		
		getContentPane().add(lblNombre);
		getContentPane().add(lblApellido);
		getContentPane().add(lblDni);
		getContentPane().add(lblEmail);
		getContentPane().add(lblTelefono);
		getContentPane().add(lblMonto);
		getContentPane().add(lblEquipamento);
		getContentPane().add(lblInicio);
		getContentPane().add(lblFin);
		
		//TODO: equipamento
		JPanel panel = new JPanel();
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(84, 103, 149, 80);
		getContentPane().add(scroll);
		
		
		scroll.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 1));

	}
	
	private boolean isDataValid(){
		
		return isFieldOk() && isDateOk() && isHourOk();

	}
	
	
	private boolean isFieldOk() {

		if(nombreField.getText().equals("") ||
			apellidoField.getText().equals("") ||
			emailField.getText().equals("") ||
			mntoField.getText().equals("") ||
			telField.getText().equals("") ||
			dniField.getText().equals("") 
		){
			
			JOptionPane.showMessageDialog(this, "Hay campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean isDateOk(){
		//TODO: verificar si la fecha no esta cerrada
		if(date.isBeforeNow()){
			JOptionPane.showMessageDialog(this, "Debe ser una fecha posterior a hoy",  "Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean isHourOk(){
		Date inicioDate = (Date)tiempoInicio.getValue();
		Date finDate = (Date)tiempoFin.getValue();
		
		if(inicioDate.compareTo(finDate) >= 0){
			
			JOptionPane.showMessageDialog(this, "La hora de inicio es posterior a la hora de fin", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void fillData(){
		
		data.setNombre( nombreField.getText());
		data.setApellido(apellidoField.getText());
		data.setEmail(emailField.getText());
		data.setPrecio( Double.valueOf(mntoField.getText()) );
		data.setTelefono( Long.valueOf(telField.getText()));
		data.setDNI(Long.valueOf(dniField.getText()));
		data.setFecha(date);
		
		
		data.setInicio((Date)tiempoInicio.getValue());
		data.setFin((Date)tiempoFin.getValue());
	}
}
