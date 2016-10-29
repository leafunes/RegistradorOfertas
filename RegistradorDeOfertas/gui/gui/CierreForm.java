package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.joda.time.DateTime;

import proc.CurrentSolutions;
import proc.OfertaData;
import proc.Solver;

import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class CierreForm extends JDialog{
	
	private List<Solver> solvers = Solver.getAllSolvers();
	private List<File> files ;
	private List<JRadioButton> filesButtons;
	private CurrentSolutions current = CurrentSolutions.getCurrent();
	private ButtonGroup selectablefiles;
	private Viewer<JRadioButton> fileViewer;
	private Viewer<OfertaMiniField> viewer;
	private JComboBox<String> comboBox;
	
	private List<OfertaData> listData;
	private List<OfertaData> obligatorios;
	private List<OfertaData> solucion;
	
	private DateTime date;
	
	public CierreForm(Component parent, List<OfertaData> listData, List<OfertaData> obligatorios, DateTime date) {
		getContentPane().setLayout(null);
		setSize(new Dimension(745, 420));
		setLocationRelativeTo(parent);
		
		this.date = date;
		this.listData = listData;
		this.obligatorios = obligatorios;
		this.files = new ArrayList<>();
		this.filesButtons = new ArrayList<>();
		
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
		
		btnGenerar.setBounds(630, 336, 89, 23);
		flechita.setBounds(192, 137, 45, 23);
		btnExportarComoPdf.setBounds(37, 336, 125, 23);
		
		btnGenerar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int index = comboBox.getSelectedIndex();
				Solver selectedSolver = solvers.get(index);
				
				solucion = selectedSolver.resolver(listData);
				
				current.saveToFile(date,solucion);
				
				addOfertasViewer();
				initRadioButtons();
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
