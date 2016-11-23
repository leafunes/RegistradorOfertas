package forms;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;

import org.joda.time.DateTime;

import com.itextpdf.text.DocumentException;

import currents.CurrentSolutions;
import data.PdfMaker;
import datas.OfertaData;
import fields.OfertaMiniField;
import fields.Viewer;
import solvers.Solver;

import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;

public class CierreForm extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Solver> solvers = Solver.getAllSolvers();
	private List<File> files ;
	private List<JRadioButton> filesButtons;
	private CurrentSolutions current = CurrentSolutions.getCurrent();
	private PdfMaker pdfMaker = PdfMaker.getMaker();
	private ButtonGroup selectablefiles;
	private Viewer<JRadioButton> fileViewer;
	private Viewer<OfertaMiniField> viewer;
	private JComboBox<String> comboBox;
	
	private List<OfertaData> listData;
	private List<OfertaData> obligatorios;
	private List<OfertaData> solucion;
	
	private DateTime date;
	
	private CierreForm thisRef;
	
	public CierreForm(Component parent, List<OfertaData> listData, List<OfertaData> obligatorios, DateTime date) {
		setTitle("Soluciones");
		getContentPane().setLayout(null);
		setSize(new Dimension(745, 420));
		setLocationRelativeTo(parent);
		
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		this.date = date;
		this.listData = listData;
		this.obligatorios = obligatorios;
		this.files = new ArrayList<>();
		this.filesButtons = new ArrayList<>();
		
		thisRef = this;
		
		selectablefiles = new ButtonGroup();
		
		initButtons();
		initCombobox();
		initViewers();
		initRadioButtons();
		
		
	}
	
	private void initViewers(){
		
		viewer = new Viewer<>(247, 11, 472, 300);
		fileViewer = new Viewer<>(10, 11, 172, 300);
		
		getContentPane().add(viewer);
		getContentPane().add(fileViewer);
		
	}
	
	private void initButtons(){
		JButton btnGenerar = new JButton("Generar");
		JButton flechita = new JButton("->");
		JButton btnExportarComoPdf = new JButton("Exportar como PDF");
		btnExportarComoPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				
				try {
					if(!(solucion == null || solucion.isEmpty())){
						pdfMaker.create(solucion, date);
						JOptionPane.showMessageDialog(thisRef, "Exportado como PDF!", "Info", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (FileNotFoundException | DocumentException e) {
					JOptionPane.showMessageDialog(thisRef, "Ha ocurrido un error", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		btnGenerar.setBounds(630, 336, 89, 23);
		flechita.setBounds(192, 137, 45, 23);
		btnExportarComoPdf.setBounds(37, 336, 125, 23);
		
		btnGenerar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int index = comboBox.getSelectedIndex();
				Solver selectedSolver = solvers.get(index);
				
				try{
					solucion = selectedSolver.resolver(listData, obligatorios);
					current.saveToFile(date,solucion);

					addOfertasViewer();
					initRadioButtons();
					
				}catch(IllegalArgumentException e){
					JOptionPane.showMessageDialog(null, "Hay ofertas marcadas que se superponen", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
			}
		});
		
		
		flechita.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				solucion = current.getOfertasOf(getSelectedFile());
				addOfertasViewer();
			}
		});
		
		getContentPane().add(btnGenerar);
		getContentPane().add(flechita);
		getContentPane().add(btnExportarComoPdf);
		
	}
	
	private void initCombobox(){
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(247, 337, 348, 20);
		getContentPane().add(comboBox);
		
		for (Solver solver : solvers) {
			comboBox.addItem(solver.getName());
		}
		
	}
	
	private void initRadioButtons(){
		
		files.clear();
		filesButtons.clear();
		fileViewer.removeAllViewer();
		
		for(File file : current.getAllFilesOf(date)){
			files.add(file);
			
			JRadioButton button = new JRadioButton(current.getNiceName(file));
			button.setSelected(true);
			selectablefiles.add(button);
			filesButtons.add(button);

			fileViewer.addToViewer(button);
		}
		
	}
	
	private void addOfertasViewer(){

		viewer.removeAllViewer();
		
		for(OfertaData oferta : solucion){
			
			OfertaMiniField field = new OfertaMiniField(viewer, oferta);
			viewer.addToViewer(field);
			
		}
		
	}
	
	private File getSelectedFile(){
		
		for(int i = 0; i<filesButtons.size(); i++){
			if(filesButtons.get(i).isSelected())
				return files.get(i);
		}

		return null;
	}
	
	
}
