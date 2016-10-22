package data;

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

import proc.Exportable;
import proc.OfertaData;

public class CurrentOfertas {
	

	Exportable<OfertaData> exportador = OfertaData.exportador();
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
		
		ArrayList<OfertaData> ret = new ArrayList<>();
		File file = dateToFile(date);
		
		if (!file.exists()) return ret;
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
		JSONArray ofertas = (JSONArray) jsonObject.get("ofertas");
		
		if (ofertas == null) throw new IOException("El archivo " + file.getAbsolutePath() + " no tiene el formato esperado");
		
		for (Object object : ofertas) {
			
			JSONObject coord = (JSONObject)object;
			ret.add(exportador.fromJSON(coord));
			
		}
		
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
