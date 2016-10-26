package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import proc.OfertaData;

public class OfertasViewer extends JScrollPane{

	private static final long serialVersionUID = 1L;
	
	private JPanel borderlaoutpanel;
	private final JPanel columnpanel = new JPanel();
	private List<OfertaData> ofertas;
	private List<OfertaField> ofertasFields;
	
	public OfertasViewer(int x, int y, int width, int height) {
		
        this.setBounds(x, y, width, height);
        
        borderlaoutpanel = new JPanel();
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));
        
        this.setColumnHeaderView(borderlaoutpanel);

        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);

        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        
        ofertas = new ArrayList<>();
        ofertasFields = new ArrayList<>();
        
	}
	
	public void addOferta(OfertaData oferta, Dimension dimensionOfOferta){
		
		OfertaField newOffer = new OfertaField(this, oferta);
		newOffer.setPreferredSize(dimensionOfOferta);
		columnpanel.add(newOffer);
        newOffer.setLayout(null);
 
        ofertas.add(oferta);
        ofertasFields.add(newOffer);
        
	}

	public void removeOferta(OfertaData oferta){
		
		int index = ofertas.indexOf(oferta);
		
		if(index != -1){
			
			ofertas.remove(index);
			OfertaField toRemove = ofertasFields.get(index);
			
			columnpanel.remove(toRemove);
			ofertasFields.remove(index);
			
		}
		
		validate();
		
	}
	
	public List<OfertaData> getOfertas(){
		return this.ofertas;
	}
	
	public void removeAllOfertas(){
		
		ofertas.clear();
		ofertasFields.clear();
		columnpanel.removeAll();
		
	}

}
