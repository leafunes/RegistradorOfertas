package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EquipamentoEdit extends JDialog{
	private JTextField textField;

	private JPanel columnpanel;
	
	public EquipamentoEdit(Component parent) {
		setTitle("Editar Equipamento");
		this.setSize(350, 325);
		super.setLocationRelativeTo(parent);
		getContentPane().setLayout(null);
        
		initViewer();
		
        JButton btnAgregar = new JButton("Agregar");
        JButton btnOk = new JButton("OK");
        textField = new JTextField();
        
        btnAgregar.setBounds(235, 227, 89, 23);
        btnOk.setBounds(124, 259, 89, 23);
        textField.setBounds(10, 228, 215, 20);
        
        
        btnAgregar.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		agregaEquip();
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
        			agregaEquip();
        	}
        });
        
        
        getContentPane().add(textField);
        getContentPane().add(btnAgregar);
        getContentPane().add(btnOk);
		
		
		
	}
	
	private void initViewer(){
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, 335, 220);
		
        JPanel borderlaoutpanel = new JPanel();
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));
        
        columnpanel = new JPanel();
        
        scroll.setViewportView(borderlaoutpanel);

        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);

        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        
        getContentPane().add(scroll);
        
	}
	
	private void agregaEquip(){
		if(textField.getText().length() != 0){
    		
    		EquipamentoField field = new EquipamentoField(textField.getText());
    		field.setPreferredSize(new Dimension(300, 40));
    		
    		columnpanel.add(field);
    		
    		textField.setText("");
    		
        	validate();
		}
	}
}
