package fields;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Viewer<T extends Component> extends JScrollPane{

	private static final long serialVersionUID = 1L;
	
	private JPanel borderlaoutpanel;
	private final JPanel columnpanel = new JPanel();
	private List<T> componentList;
	
	public Viewer(int x, int y, int width, int height) {
		
        this.setBounds(x, y, width, height);
        
        this.componentList = new ArrayList<>();
        
        borderlaoutpanel = new JPanel();
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));
        
        this.setViewportView(borderlaoutpanel);

        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);

        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        
	}
	
	public void addToViewer(T toAdd){
		columnpanel.add(toAdd);
		componentList.add(toAdd);
		
        columnpanel.revalidate();
        borderlaoutpanel.revalidate();
        validate();
	}
	
	public void removeFromViewer(T toRemove){
		columnpanel.remove(toRemove);
		componentList.remove(toRemove);
		
		validate();
	}
	
	public void removeAllViewer(){
		columnpanel.removeAll();
		componentList.clear();
		validate();
	}
	
	public List<T> getItems(){
		return componentList;
	}

}
