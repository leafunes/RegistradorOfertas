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
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		try {
			ofertas = currentOfertas.getOfertas(new Date());
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		dateChooser = new JDateChooser(new Date());
		dateChooser.setBounds(10, 93, 104, 23);
		
		dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				borrarOfertas();
				try {
					ofertas = currentOfertas.getOfertas(dateChooser.getDate());
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mostrarOfertas();
				
			}
		});
		
		frame.getContentPane().add(dateChooser);
		
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(124, 11, 650, 539);
        frame.getContentPane().add(scrollPane);
        
        borderlaoutpanel = new JPanel();
        scrollPane.setViewportView(borderlaoutpanel);
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));

        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);
        
		JButton btnNueva = new JButton("Nueva");
		btnNueva.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 
				OfertaForm agregador = new OfertaForm(frame, dateChooser.getDate());
				agregador.setVisible(true);
				agregaOferta(agregador.data);
			}
		});
		btnNueva.setBounds(10, 43, 104, 23);
		frame.getContentPane().add(btnNueva);
		
		JButton btnEquipamento = new JButton("Equip.");
		btnEquipamento.setBounds(10, 395, 104, 23);
		frame.getContentPane().add(btnEquipamento);
		
		JButton btnCerrar = new JButton("Cerrar dia");
		btnCerrar.setBounds(10, 510, 104, 23);
		frame.getContentPane().add(btnCerrar);
		
		JButton btnVerCierre = new JButton("Ver Cierre");
		btnVerCierre.setBounds(10, 476, 104, 23);
		frame.getContentPane().add(btnVerCierre);
		
		
		
		
	}
	
	private void agregaOferta(OfertaData ofertaData){
		
			OfertaField newOffer = new OfertaField(frame, ofertaData);
			newOffer.setPreferredSize(new Dimension(630,50));
			columnpanel.add(newOffer);
            newOffer.setLayout(null);
            frame.validate();
            
            try {
				currentOfertas.putOferta(ofertaData, dateChooser.getDate());
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
