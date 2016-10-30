package forms;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JButton;

import org.joda.time.DateTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import com.toedter.calendar.JDateChooser;

import currents.CurrentOfertas;
import datas.OfertaData;
import fields.OfertaField;
import fields.OfertaForm;
import fields.Viewer;

public class MainForm {

	private JFrame frame;
	private CurrentOfertas currentOfertas= CurrentOfertas.getCurrent();
	private JDateChooser dateChooser;
	
	private JButton btnNueva;
	private JButton btnEquipamento;
	private JButton btnCerrar;
	private JButton btnGeneraCierre;
	
	private Viewer<OfertaField> viewer;
	
	private DateTime selectedDate;
	private DateTime currentDate = DateTime.now();
	
	private List<OfertaData> ofertasList;

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
		
		initTimer();
		
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
		btnGeneraCierre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(btnGeneraCierre.isEnabled()){

					List<OfertaData> obligatorios = getObligatorios();
					actualizeOfertas();
					CierreForm cierre = new CierreForm(frame, ofertasList, obligatorios, selectedDate);
					cierre.setVisible(true);
					
				}
				
			}
		});
		
		btnEquipamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				EquipamentoEdit editEquip = new EquipamentoEdit(frame);
				
				editEquip.setVisible(true);
				
			}
		});
		
		btnNueva.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(isDateOk() && btnNueva.isEnabled()){
					OfertaForm agregador = new OfertaForm(frame, selectedDate);
					agregador.setVisible(true);
					if(agregador.data != null)agregaOferta(agregador.data);
				}
			}
		});
		
		btnCerrar.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(btnCerrar.isEnabled()){
					currentOfertas.cerrarDia(selectedDate);
					
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
		
		selectedDate = new DateTime(dateChooser.getDate());
		
		dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				selectedDate = new DateTime(dateChooser.getDate());

				actualizeButtons();
				
				actualizeOfertas();
				
			}
		});
		
		dateChooser.setBounds(10, 93, 119, 23);
		frame.getContentPane().add(dateChooser);
		
	}
	
	private void initOfertasViewer(){
		
		viewer = new Viewer<>(139, 11, 650, 539);

        frame.getContentPane().add(viewer);
		
	}
	
	private void initTimer(){
		ActionListener chechkDate = new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent evt) {
				if(currentDate.getDayOfYear() != DateTime.now().getDayOfYear()){
					
					currentOfertas.cerrarDia(currentDate);
					actualizeButtons();
					dateChooser.setDate(new Date());
					
					JOptionPane.showMessageDialog(frame, "Se ha cerrado el dia " +  currentDate.toString("yyyy-MM-dd"), "Info", JOptionPane.INFORMATION_MESSAGE);
					
					currentDate = DateTime.now();
					
				}
				
            }
        };
        Timer timer = new Timer(1000 ,chechkDate);//Cada 1 segundo
        timer.setRepeats(true);
        timer.start();
	}
	
	private List<OfertaData> getObligatorios(){
		
		List<OfertaData> ret = new ArrayList<>();
		
		for (OfertaField ofertaField : viewer.getItems()) {
			if(ofertaField.isObligatorio())
				ret.add(ofertaField.getData());
		}
		
		return ret;
	}
	
	private void actualizeButtons(){
		boolean cerrado = false;
		cerrado = currentOfertas.isCerrado(selectedDate);
		
		btnNueva.setEnabled(!cerrado);
		btnCerrar.setEnabled(!cerrado);
		btnGeneraCierre.setEnabled(cerrado);
	}
	
	private void actualizeOfertas(){
		
		viewer.removeAllViewer();
		ofertasList = currentOfertas.getOfertas(selectedDate);
		
		ofertasList.forEach(oferta -> viewer.addToViewer(getOfertaFromData(oferta)));
		
	}
	
	private void agregaOferta(OfertaData ofertaData){
		OfertaField newOffer = getOfertaFromData(ofertaData);
        
        viewer.addToViewer(newOffer);
        
		currentOfertas.putOferta(ofertaData, selectedDate);
	}
	
	private OfertaField getOfertaFromData(OfertaData ofertaData){
		
		OfertaField newOffer = new OfertaField(viewer ,ofertaData);
		
        return newOffer;
		
	}
	
	private boolean isDateOk(){
		
		if(currentOfertas.isDateOk(selectedDate))
			return true;
		
		return false;
		
	}
}
