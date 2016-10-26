package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import proc.OfertaData;

public class OfertasViewer extends JSpinner{

	private static final long serialVersionUID = 1L;
	
	private final JPanel columnpanel = new JPanel();
	
	public OfertasViewer(int x, int y, int width, int height) {
		JScrollPane scrollPane = new JScrollPane();
		JPanel borderlaoutpanel = new JPanel();
		
        scrollPane.setBounds(x, y, width, height);
        
        scrollPane.setColumnHeaderView(borderlaoutpanel);
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));

        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);
	}
	
	public void addOferta(OfertaData oferta, Dimension dimensionOfOferta){
		
		OfertaField newOffer = new OfertaField(this, oferta);
		newOffer.setPreferredSize(dimensionOfOferta);
		columnpanel.add(newOffer);
        newOffer.setLayout(null);
		
	}
	
	public void removeAllOfertas(){
		
		columnpanel.removeAll();
		
	}

}
