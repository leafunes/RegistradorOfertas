package forms;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTextField;

import currents.CurrentEquipamento;
import datas.EquipData;
import fields.EquipamentoField;
import fields.Viewer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EquipamentoEdit extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField textField;
	
	private Viewer<EquipamentoField> viewer;
	
	private CurrentEquipamento currentEquip = CurrentEquipamento.getCurrent();
	
	
	public EquipamentoEdit(Component parent) {
		setTitle("Editar Equipamento");
		this.setSize(350, 325);
		super.setLocationRelativeTo(parent);
		getContentPane().setLayout(null);
        
		initViewer();
		actualizeEquip();
		
        JButton btnAgregar = new JButton("Agregar");
        JButton btnOk = new JButton("OK");
        textField = new JTextField();
        
        btnAgregar.setBounds(235, 227, 89, 23);
        btnOk.setBounds(124, 259, 89, 23);
        textField.setBounds(10, 228, 215, 20);
        
        
        btnAgregar.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		agregaEquipData();
        	}
        });
        
        btnOk.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		dispose();
        		
        	}
        });
        
        textField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		
        		if(e.getKeyCode() == 10 ) //Enter
        			agregaEquipData();
        	}
        });
        
        
        getContentPane().add(textField);
        getContentPane().add(btnAgregar);
        getContentPane().add(btnOk);
		
		
		
	}
	
	private void initViewer(){
        
		viewer = new Viewer<>(0, 0, 335, 220);
        getContentPane().add(viewer);
        
	}
	
	public void removeEquip(EquipData toRemove){
		currentEquip.removeEquipamento(toRemove);
		
		actualizeEquip();
		
	}
	
	private void actualizeEquip(){
		
		viewer.removeAllViewer();
		
		List<EquipData> allEquip = currentEquip.getEquipamento();
		
		allEquip.forEach(equip -> agregaEquipViewer(equip));
		
		validate();
		
	}
	
	private void agregaEquipData(){
		if(textField.getText().length() != 0){
			
    		EquipData toAdd = new EquipData(textField.getText());
    		agregaEquipViewer(toAdd);
    		
    		textField.setText("");
    
    		currentEquip.putEquipamento(toAdd);
    		
		}
	}
	
	private void agregaEquipViewer(EquipData toAdd){
    		
    		EquipamentoField field = new EquipamentoField(this, toAdd);
    		field.setPreferredSize(new Dimension(300, 40));
    		viewer.addToViewer(field);
    		
        	validate();
		
	}
	
}
