package proc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import data.JsonData;

public class CurrentOfertas {
	
	Exportator<OfertaData> exportador = OfertaData.exportador();
	private JsonData jsonData = JsonData.getData();
	
	private static CurrentOfertas current;

	public static CurrentOfertas getCurrent(){
		if(current == null){
			current = new CurrentOfertas();
		}
		
		return current;
	}
	
	private CurrentOfertas(){
		
	}
	
	public List<OfertaData> getOfertas(DateTime date) throws FileNotFoundException, IOException, ParseException{

		File file = dateToFile(date);
		
		if (!file.exists()) 
			return new ArrayList<>();
		
		List<OfertaData> ret = jsonData.getArray(file, exportador, "ofertas");
		
		return ret;
		
	}
	
	public void putOferta(OfertaData oferta, DateTime date) throws IOException, ParseException{
		
		if(!isDateOk(date))
			throw new IllegalArgumentException("La fecha " + date + " es posterior a hoy");
		
		File file = dateToFile(date);
		
		if(!file.exists()){
			jsonData.newFile(file);
			jsonData.putArray(file, exportador, new ArrayList<>(), "ofertas");
			jsonData.putField(file, "cerrado", false);
		}
		
		jsonData.putObjectInArray(file, exportador, oferta, "ofertas");
		
	}
	
	public void cerrarDia(DateTime date) throws IOException, ParseException{
		
		if(isDateOk(date)){
			
			File file = dateToFile(date);
			
			if(!file.exists())
				jsonData.newFile(file);
			
			jsonData.putField(file, "cerrado", true);
			
		}
		
	}
	
	public boolean isCerrado(DateTime date) throws IOException, ParseException{
		
		if(isDateOk(date)){
			
			File file = dateToFile(date);
			
			if(!file.exists())
				return false;
			
			return jsonData.getField(file, "cerrado", Boolean.class);
			
		}
		
		return true;
	}
	
	public boolean isDateOk(DateTime date){
		
		if(date.isAfterNow())
			return true;
		
		return false;
	}
	
	private File dateToFile(DateTime date){
		
		int year = date.getYear();
		int month = date.getMonthOfYear();
		int day = date.getDayOfMonth();
		
		File file = new File("Datos" + File.separatorChar + year + "_" + month + "_" + day + ".json");
		
		return file;
		
	}
	

}
