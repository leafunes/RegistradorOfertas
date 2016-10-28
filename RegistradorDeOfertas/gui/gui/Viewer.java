package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import proc.OfertaData;

import javax.swing.ScrollPaneConstants;

public class Viewer<T extends Component> extends JScrollPane{

	private static final long serialVersionUID = 1L;
	
	private JPanel borderlaoutpanel;
	private final JPanel columnpanel = new JPanel();
	
	public Viewer(int x, int y, int width, int height) {
		
        this.setBounds(x, y, width, height);
        
        borderlaoutpanel = new JPanel();
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));
        
        this.setViewportView(borderlaoutpanel);

        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);

        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        
	}
	
	public void addToViewer(T toAdd){
		columnpanel.add(toAdd);
 
        columnpanel.revalidate();
        borderlaoutpanel.revalidate();
        validate();
	}
	
	public void removeFromViewer(T toRemove){
		columnpanel.remove(toRemove);
		validate();
	}
	
	public void removeAllViewer(){
		columnpanel.removeAll();
		validate();
	}

}
