package currents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.DateTimeParserBucket;
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
		
		String [] split = fileName.split("-|@|_|\\.");
		
		return split[5] + " del " + split[4] + " del " + split[3] + " a las "+ split[6] + ":" + split[7]+ ":"+split[8];
		
	}
	
	private File dateToFile(DateTime date){
		return new File(directory.getAbsolutePath() + File.separatorChar + date.toString("yyyy_MM_dd-") + DateTime.now().toString("yyyy_MM_dd@hh_mm_ss") + ".json");
	}
	
	private String generateName(DateTime date){
		
		return date.toString("yyyy_MM_dd");
		
	}
	
}
