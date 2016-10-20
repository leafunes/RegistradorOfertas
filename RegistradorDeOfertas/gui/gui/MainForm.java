package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;

public class MainForm {

	private JFrame frame;
	private final JPanel columnpanel = new JPanel();
	private JPanel borderlaoutpanel;

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
				 
				OfertaForm agregador = new OfertaForm(frame);
				agregador.setVisible(true);
				agregaOferta(agregador.data);
				frame.validate();
			}
		});
		btnNueva.setBounds(10, 43, 89, 23);
		frame.getContentPane().add(btnNueva);
		
		JButton btnFecha = new JButton("Fecha");
		btnFecha.setBounds(10, 77, 89, 23);
		frame.getContentPane().add(btnFecha);
		
		
		
		JButton btnEquipamento = new JButton("Equip.");
		btnEquipamento.setBounds(10, 452, 89, 23);
		frame.getContentPane().add(btnEquipamento);
		
		JButton btnOk = new JButton("OK!");
		btnOk.setBounds(10, 510, 89, 23);
		frame.getContentPane().add(btnOk);
		
		
		
	}
	
	private void agregaOferta(OfertaData ofertaData){
		
			OfferField newOffer = new OfferField(ofertaData);
			newOffer.setPreferredSize(new Dimension(630,50));
			columnpanel.add(newOffer);
            newOffer.setLayout(null);

	}
}
