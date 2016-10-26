package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import proc.CurrentOfertas;
import proc.OfertaData;

import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;

public class MainForm {

	private JFrame frame;
	private CurrentOfertas currentOfertas= CurrentOfertas.getCurrent();
	private JDateChooser dateChooser;
	
	private JButton btnNueva;
	private JButton btnEquipamento;
	private JButton btnCerrar;
	private JButton btnGeneraCierre;
	
	private OfertasViewer viewer;
	
	private DateTime dateSelected;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		initButtons();
		initDateChooser();
		initOfertasViewer();
		
		actualizeOfertas();
		actualizeButtons();
		
		frame.setBounds(100, 100, 815, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	}
	
	private void initButtons(){
		
		btnNueva = new JButton("Nueva");
		btnEquipamento = new JButton("Equipamento");
		btnCerrar = new JButton("Cerrar dia");
		btnGeneraCierre = new JButton("Generar Cierre");
		
		btnNueva.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(isDateOk() && btnNueva.isEnabled()){
					OfertaForm agregador = new OfertaForm(frame, dateChooser.getDate());
					agregador.setVisible(true);
					agregaOferta(agregador.data);
				}
			}
		});
		
		btnCerrar.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(btnCerrar.isEnabled()){
					currentOfertas.cerrarDia(dateSelected);
					
					actualizeButtons();
				}
			}
			
		});
		
		btnNueva.setBounds(10, 43, 119, 23);
		btnEquipamento.setBounds(10, 395, 119, 23);
		btnCerrar.setBounds(10, 510, 119, 23);
		btnGeneraCierre.setBounds(10, 476, 119, 23);
		
		frame.getContentPane().add(btnEquipamento);
		frame.getContentPane().add(btnCerrar);
		frame.getContentPane().add(btnNueva);
		frame.getContentPane().add(btnGeneraCierre);
		
	}
	
	private void initDateChooser(){
		dateChooser = new JDateChooser(new Date());
		
		dateSelected = new DateTime(dateChooser.getDate());
		
		dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				dateSelected = new DateTime(dateChooser.getDate());

				actualizeButtons();
				
				actualizeOfertas();
				
			}
		});
		
		dateChooser.setBounds(10, 93, 119, 23);
		frame.getContentPane().add(dateChooser);
		
	}
	
	private void initOfertasViewer(){
		
		viewer = new OfertasViewer(139, 11, 650, 539);

        frame.getContentPane().add(viewer);
		
	}
	
	private void actualizeButtons(){
		boolean cerrado = false;
		cerrado = currentOfertas.isCerrado(dateSelected);
		
		btnNueva.setEnabled(!cerrado);
		btnCerrar.setEnabled(!cerrado);
		btnGeneraCierre.setEnabled(cerrado);
	}
	
	private void actualizeOfertas(){
		
		viewer.removeAllOfertas();
		
		List<OfertaData> ofertas = currentOfertas.getOfertas(dateSelected);
		
		for (OfertaData ofertaData : ofertas) {
			viewer.addOferta(ofertaData, new Dimension(630, 50));
			
		}
		
		frame.validate();
	}
	
	private void agregaOferta(OfertaData ofertaData){
		
		viewer.addOferta(ofertaData, new Dimension(630,50));
        frame.validate();
        
		currentOfertas.putOferta(ofertaData, dateSelected);

	}
	
	private boolean isDateOk(){
		
		if(currentOfertas.isDateOk(dateSelected))
			return true;
		
		return false;
		
	}
}
