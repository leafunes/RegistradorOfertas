package proc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
	
	public List<OfertaData> getOfertas(Date date) throws FileNotFoundException, IOException, ParseException{
		

		File file = dateToFile(date);
		
		if (!file.exists()) return new ArrayList<>();
		
		List<OfertaData> ret = jsonData.getArray(file, exportador, "ofertas");
		
		return ret;
		
	}
	
	public void putOferta(OfertaData oferta, Date date) throws IOException, ParseException{
		List<OfertaData> ofertas = getOfertas(date);
		
		ofertas.add(oferta);
		
		File file = dateToFile(date);
		
		JSONObject obj = new JSONObject();

		JSONArray list = new JSONArray();
		
		for(OfertaData ofertaData : ofertas){
			list.add(exportador.toJSON(ofertaData));
		}

		obj.put("ofertas", list);

		FileWriter finalFile = new FileWriter(file);
		finalFile.write(obj.toJSONString());
		finalFile.flush();
		finalFile.close();
		
		
	}
	
	private File dateToFile(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		File file = new File("Datos" + File.separatorChar + year + "_" + month + "_" + day + ".json");
		
		return file;
		
	}
	

}
