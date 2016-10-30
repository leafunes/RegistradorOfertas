package gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFormattedTextField;

import proc.CurrentEquipamento;
import proc.EquipData;
import proc.OfertaData;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.github.lgooddatepicker.components.TimePicker;

public class OfertaForm extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OfertaData data;
	private JTextField nombreField;
	private JTextField apellidoField;
	private JFormattedTextField emailField;
	private JFormattedTextField dniField;
	private JFormattedTextField telField;
	private JFormattedTextField mntoField;
	
	private DateTime date;
	
	private List<EquipData> equipList;
	private List<JCheckBox> checksList;
	private TimePicker pickerFin;
	private TimePicker pickerInicio;
	
	public OfertaForm(Component parent, DateTime date){
		
		this.date = date;
		equipList = CurrentEquipamento.getCurrent().getEquipamento();
		checksList = new ArrayList<JCheckBox>();
		
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		super.setSize(new Dimension(500, 300));
		super.setLocationRelativeTo(parent);
		getContentPane().setLayout(null);
		
		initFields();
		initButtons();
		initLabels();
		initEquipViewer();
		
	}
	
	private void initFields(){
	
		nombreField = new JTextField();
		apellidoField = new JTextField();
		emailField = new JFormattedTextField( new RegexFormatter("\\b([\\w\\.]+)@([\\w\\.]+)\\.(\\w+)\\b"));//Email Regex
		telField = new JFormattedTextField(new RegexFormatter("\\d{8,}"));//Telefono Regex. 8 o mas?
		mntoField = new JFormattedTextField(new RegexFormatter("\\d*(\\.|,)?\\d{2}"));//Cualquier cantidad de digitos
		dniField = new JFormattedTextField(new RegexFormatter("\\d{8}"));
		pickerInicio = new TimePicker();
		pickerFin = new TimePicker();
		
		nombreField.setBounds(84, 11, 149, 20);
		apellidoField.setBounds(318, 11, 149, 20);
		dniField.setBounds(84, 42, 149, 20);
		emailField.setBounds(318, 42, 149, 20);
		telField.setBounds(84, 73, 149, 20);
		mntoField.setBounds(318, 73, 149, 20);
		pickerInicio.setBounds(350, 104, 76, 23);
		pickerFin.setBounds(350, 138, 76, 23);
		
		getContentPane().add(nombreField);
		getContentPane().add(apellidoField);
		getContentPane().add(dniField);
		getContentPane().add(emailField);
		getContentPane().add(telField);
		getContentPane().add(mntoField);
		getContentPane().add(pickerInicio);
		getContentPane().add(pickerFin);
	}
	
	private void initButtons(){

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
		
	}
	
	private void initEquipViewer(){
		
		Viewer<JCheckBox> viewer = new Viewer<>(84, 103, 149, 80);
		getContentPane().add(viewer);
		
		for(EquipData equip : equipList){
			
			JCheckBox button = new JCheckBox(equip.getNombre());
			checksList.add(button);
			viewer.addToViewer(button);
			
		}
	}
	
	private void initLabels(){
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
		//no hace falta verificar si la fecha esta cerrada, ya que
		//si estaria cerrada, el boton para agregar ofertas esta deshabilitado
		if(date.isBeforeNow()){
			JOptionPane.showMessageDialog(this, "Debe ser una fecha posterior a hoy",  "Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean isHourOk(){
		
		if(pickerInicio.getTime() == null || pickerFin.getTime() == null){
			JOptionPane.showMessageDialog(this, "Debe ingresar la hora de inicio y fin", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!pickerInicio.getTime().isBefore(pickerFin.getTime())){
			
			JOptionPane.showMessageDialog(this, "La hora de inicio es posterior a la hora de fin", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void fillData(){
		
		data = new OfertaData();
		
		data.setNombre( nombreField.getText());
		data.setApellido(apellidoField.getText());
		data.setEmail(emailField.getText());
		data.setPrecio( Double.valueOf(mntoField.getText()) );
		data.setTelefono( Long.valueOf(telField.getText()));
		data.setDNI(Long.valueOf(dniField.getText()));
		data.setFecha(date);
		
		
		data.setInicio(LocalTime.parse(pickerInicio.getTime().toString()));
		data.setFin(LocalTime.parse(pickerFin.getTime().toString()));
		
		for(int i = 0; i < checksList.size(); i++){
			
			if(checksList.get(i).isSelected())
				data.agregaEquip(equipList.get(i));
			
		}
		
		data.createInterval();
		
	}
}
