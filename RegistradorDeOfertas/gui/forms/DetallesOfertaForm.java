package forms;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import datas.EquipData;
import datas.OfertaData;
import fields.Viewer;

public class DetallesOfertaForm extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OfertaData data;
	
	public DetallesOfertaForm(Component parent, OfertaData data) {
		setTitle("Detalles");
		getContentPane().setLayout(null);
		super.setSize(new Dimension(450, 260));
		super.setLocationRelativeTo(parent);
		
		this.data = data;
		
		initLabels();
		initButton();
		initFields();
		initEquipViewer();
		
	}
	
	private void initLabels(){
		JLabel lblNombre = new JLabel("Nombre: ");
		JLabel lblApellido = new JLabel("Apellido: ");
		JLabel lblDni = new JLabel("DNI: ");
		JLabel lblEmail = new JLabel("Email:");
		JLabel lblInicio = new JLabel("Inicio: ");
		JLabel lblFin = new JLabel("Fin: ");
		JLabel lblEquipamento = new JLabel("Equip.:\r\n");
		JLabel lblTel = new JLabel("Tel.:");
		JLabel lblMonto = new JLabel("Monto:");
		
		lblNombre.setBounds(10, 11, 58, 14);
		lblApellido.setBounds(229, 11, 60, 14);
		lblDni.setBounds(10, 36, 46, 14);
		lblEmail.setBounds(229, 36, 46, 14);
		lblInicio.setBounds(229, 92, 46, 14);
		lblFin.setBounds(229, 117, 46, 14);
		lblEquipamento.setBounds(10, 92, 46, 14);
		lblTel.setBounds(10, 61, 46, 14);
		lblMonto.setBounds(229, 61, 46, 14);
		
		getContentPane().add(lblNombre);
		getContentPane().add(lblApellido);
		getContentPane().add(lblDni);
		getContentPane().add(lblEmail);
		getContentPane().add(lblInicio);
		getContentPane().add(lblFin);
		getContentPane().add(lblEquipamento);
		getContentPane().add(lblTel);
		getContentPane().add(lblMonto);
	}
	
	private void initButton(){
		JButton btnOk = new JButton("Ok!");

		btnOk.setBounds(165, 185, 89, 23);

		getContentPane().add(btnOk);
		
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
	}
	
	private void initFields(){
		JTextField lblNombre = new JTextField(data.getNombre());
		JTextField lblDni = new JTextField(data.getDNI() + "");
		JTextField lblTel = new JTextField(data.getTelefono() + "");
		JTextField lblApellido = new JTextField(data.getApellido());
		JTextField lblEmail = new JTextField(data.getEmail());
		JTextField lblMonto = new JTextField(data.getPrecio() + "$");
		JTextField lblInicio = new JTextField(data.getInicio().toString("hh:mm") + "hs");
		JTextField lblFin = new JTextField(data.getFin().toString("hh:mm")  + "hs");
		
		lblNombre.setEditable(false);
		lblDni.setEditable(false);
		lblTel.setEditable(false);
		lblApellido.setEditable(false);
		lblEmail.setEditable(false);
		lblMonto.setEditable(false);
		lblInicio.setEditable(false);
		lblFin.setEditable(false);
		
		lblNombre.setBounds(87, 8, 132, 20);
		lblDni.setBounds(87, 36, 132, 20);
		lblTel.setBounds(87, 61, 132, 20);
		lblApellido.setBounds(299, 11, 125, 20);
		lblEmail.setBounds(299, 36, 125, 20);
		lblMonto.setBounds(299, 61, 125, 20);
		lblInicio.setBounds(299, 92, 125, 20);
		lblFin.setBounds(299, 117, 125, 20);
		
		getContentPane().add(lblNombre);
		getContentPane().add(lblDni);
		getContentPane().add(lblTel);
		getContentPane().add(lblApellido);
		getContentPane().add(lblEmail);
		getContentPane().add(lblMonto);
		getContentPane().add(lblInicio);
		getContentPane().add(lblFin);
	}
	
	private void initEquipViewer(){

		Viewer<JTextField> viewer = new Viewer<JTextField>(83, 92, 132, 80);
		getContentPane().add(viewer);
		
		for(EquipData equip: data.getEquip()){
			
			JTextField equipTxt = new JTextField(equip.getNombre());
			equipTxt.setSize(new Dimension(130, 5));
			equipTxt.setEditable(false);
			viewer.addToViewer(equipTxt);
			
		}
	}
}
