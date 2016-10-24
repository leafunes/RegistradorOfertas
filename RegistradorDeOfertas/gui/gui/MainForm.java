package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
	private final JPanel columnpanel = new JPanel();
	private JPanel borderlaoutpanel;
	private List<OfertaData> ofertas;
	private CurrentOfertas currentOfertas= CurrentOfertas.getCurrent();
	private JDateChooser dateChooser;
	
	private JButton btnNueva;
	private JButton btnEquipamento;
	private JButton btnCerrar;
	private JButton btnVerCierre;
	
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
		
		initOfertas();
		mostrarOfertas();
		
		actualizeButtons();
		
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	}

	private void initOfertas() {
		try {
			ofertas = currentOfertas.getOfertas(DateTime.now());
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void initButtons(){
		
		btnNueva = new JButton("Nueva");
		btnEquipamento = new JButton("Equip.");
		btnCerrar = new JButton("Cerrar dia");
		btnVerCierre = new JButton("Ver Cierre");
		
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
		
		
		btnNueva.setBounds(10, 43, 104, 23);
		btnEquipamento.setBounds(10, 395, 104, 23);
		btnCerrar.setBounds(10, 510, 104, 23);
		btnVerCierre.setBounds(10, 476, 104, 23);
		
		frame.getContentPane().add(btnEquipamento);
		frame.getContentPane().add(btnCerrar);
		frame.getContentPane().add(btnNueva);
		frame.getContentPane().add(btnVerCierre);
		
		
	}
	
	private void initDateChooser(){
		dateChooser = new JDateChooser(new Date());
		
		dateSelected = new DateTime(dateChooser.getDate());
		
		dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				borrarOfertas();
				
				dateSelected = new DateTime(dateChooser.getDate());

				actualizeButtons();
				
				actualizeOfertas();
				
				mostrarOfertas();
				
			}
		});
		

		dateChooser.setBounds(10, 93, 104, 23);
		frame.getContentPane().add(dateChooser);
		
	}
	
	private void initOfertasViewer(){
		

		JScrollPane scrollPane = new JScrollPane();
		borderlaoutpanel = new JPanel();
		
        scrollPane.setBounds(124, 11, 650, 539);
        frame.getContentPane().add(scrollPane);
        
        
        scrollPane.setViewportView(borderlaoutpanel);
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));

        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);
		
	}
	
	private void actualizeButtons(){
		boolean cerrado = false;
		try {
			cerrado = currentOfertas.isCerrado(dateSelected);
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println(cerrado);
		
		btnNueva.setEnabled(!cerrado);
		btnCerrar.setEnabled(!cerrado);
		btnVerCierre.setEnabled(cerrado);
	}
	
	private void actualizeOfertas(){
		
		try {
			ofertas = currentOfertas.getOfertas(dateSelected);
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isDateOk(){
		
		if(currentOfertas.isDateOk(dateSelected))
			return true;
		
		return false;
		
	}
	
	private void agregaOferta(OfertaData ofertaData){
		
			OfertaField newOffer = new OfertaField(frame, ofertaData);
			newOffer.setPreferredSize(new Dimension(630,50));
			columnpanel.add(newOffer);
            newOffer.setLayout(null);
            frame.validate();
            
            try {
				currentOfertas.putOferta(ofertaData, dateSelected);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	private void mostrarOfertas(){
		
		for (OfertaData ofertaData : ofertas) {
			
			OfertaField newOffer = new OfertaField(frame, ofertaData);
			newOffer.setPreferredSize(new Dimension(630,50));
			columnpanel.add(newOffer);
	        newOffer.setLayout(null);
		}
		
		frame.validate();
		
	}
	
	private void borrarOfertas(){
		ofertas.clear();
		
		columnpanel.removeAll();
		
	}
}
