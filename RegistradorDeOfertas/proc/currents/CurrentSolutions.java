package currents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import data.JsonData;
import datas.OfertaData;
import interfaces.Exportator;

public class CurrentSolutions {

	Exportator<OfertaData> exportador = OfertaData.exportador();
	private JsonData jsonData = JsonData.getData();
	
	private static CurrentSolutions current;
	
	File directory = new File("Datos" + File.separatorChar + "soluciones");

	public static CurrentSolutions getCurrent(){
		if(current == null){
			current = new CurrentSolutions();
		}
		
		return current;
	}
	
	//Solo para testing
	static CurrentSolutions getCurrent(File dir){
		CurrentSolutions ret = new CurrentSolutions();
		
		ret.directory = dir;
		
		return ret;

	}
	
	private CurrentSolutions(){
	}
	
	public List<File> getAllFilesOf(DateTime date){
		
		List<File> ret = new ArrayList<>();
		
		String dateName = generateName(date);
		
		if(!directory.exists())
			directory.mkdirs();
		
		for(File file : directory.listFiles())if(file.getName().startsWith(dateName))
			ret.add(file);
			
		
		return ret;
	}
	
	public void saveToFile(DateTime date, List<OfertaData> solution){
		
		if(!solution.isEmpty()){
			File file = dateToFile(date);
			
			try {
				
				if(!file.exists())
					jsonData.newFile(file);
				
				jsonData.putArray(file, exportador, solution, "solucion");
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<OfertaData> getOfertasOf(File file){
		try {
			return jsonData.getArray(file, exportador, "solucion");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getNiceName(File file){
		
		String fileName = file.getName();
		
		String [] split = fileName.split("-|_|\\.");
		long milis = Long.parseLong(split[3]);
		DateTime date = new DateTime(milis);
		
		return date.toString("yyyy") + " del " + date.toString("MM") + " del " 
				+ date.toString("dd") + " a las " + date.toString("HH") + ":"
				+ date.toString("mm") + ":" + date.toString("ss");
		
	}
	
	private File dateToFile(DateTime date){
		return new File(directory.getAbsolutePath() + File.separatorChar + date.toString("yyyy_MM_dd-") + DateTime.now().getMillis() + ".json");
	}
	
	private String generateName(DateTime date){
		
		return date.toString("yyyy_MM_dd");
		
	}
	
}
