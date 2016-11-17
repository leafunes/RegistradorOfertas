package currents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;

import data.JsonData;
import datas.OfertaData;
import interfaces.Exportator;

public class CurrentOfertas {
	
	private Exportator<OfertaData> exportador = OfertaData.exportador();
	private JsonData jsonData = JsonData.getData();
	
	private static CurrentOfertas current;
	
	File directory = new File("Datos" + File.separatorChar + "ofertas" );

	public static CurrentOfertas getCurrent(){
		if(current == null){
			current = new CurrentOfertas();
		}
		
		return current;
	}
	
	//Solo para testing
	static CurrentOfertas getCurrent(File directory){
		CurrentOfertas toTest = new CurrentOfertas();
		
		toTest.directory = directory;
		
		return toTest;
	}
	
	private CurrentOfertas(){
		
	}
	
	public List<OfertaData> getOfertas(DateTime date){

		File file = dateToFile(date);
		
		if (!file.exists()) 
			return new ArrayList<>();
		
		List<OfertaData> ret = null;
		try {
			ret = jsonData.getArray(file, exportador, "ofertas");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	public void putOferta(OfertaData oferta, DateTime date){
		
		if(!isDateOk(date))
			throw new IllegalArgumentException("La fecha " + date + " es posterior a hoy");
		
		File file = dateToFile(date);
		
		try {
			newData(file);
			jsonData.putObjectInArray(file, exportador, oferta, "ofertas");
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	
		
	}
	
	public void removeOferta(OfertaData oferta, DateTime date) {
		
		File file = dateToFile(date);
		
		if(file.exists()){
			
			try {
				jsonData.removeObjectInArray(file, exportador, oferta, "ofertas");
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void cerrarDia(DateTime date){
		
			
		File file = dateToFile(date);
		
		try {
			newData(file);
			jsonData.putField(file, "cerrado", true);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
			
		
	}
	
	private void newData(File file) throws IOException, ParseException, FileNotFoundException {
		if(!file.exists()){
			jsonData.newFile(file);
			jsonData.putArray(file, exportador, new ArrayList<>(), "ofertas");
			jsonData.putField(file, "cerrado", false);
		}
	}
	
	public boolean isCerrado(DateTime date){
		
		if(isDateOk(date)){
			
			File file = dateToFile(date);
			
			if(!file.exists())
				return false;
			
			try {
				return jsonData.getField(file, "cerrado", Boolean.class);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		return true;
	}
	
	public boolean isDateOk(DateTime date){
		
		if(date.isAfterNow())
			return true;
		
		return false;
	}
	
	private File dateToFile(DateTime date){
		
		File file = new File( directory.getAbsolutePath()+ File.separatorChar + date.toString("yyyy_MM_dd")+ ".json");
		
		return file;
		
	}


	

}
