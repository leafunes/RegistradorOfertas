package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import proc.Exportator;
//Test
public class JsonData {
	
	private JSONParser parser ;
	
	private static JsonData jsonData;
	
	public static JsonData getData(){
		if(jsonData == null)
			jsonData = new JsonData();
		
		return jsonData;
	}
	
	private JsonData(){
		
		parser = new JSONParser();
		
	}
	
	public <T> void putObjectInArray(File file, Exportator<T> exportator, T toAdd, String array) throws IOException, ParseException{
		
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
		JSONArray jsonArray = (JSONArray) jsonObject.get(array);
		
		jsonArray.add(exportator.toJSON(toAdd));
		
		jsonObject.put(array, jsonArray);

		writeFile(file, jsonObject.toJSONString());
		
	}
	
	public <T> void putArray(File file, Exportator<T> exportator, List<T> listToAdd ,String name) throws IOException, ParseException{
		
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
		JSONArray jsonArray = new JSONArray();
		
		
		for(T ofertaData : listToAdd){
			jsonArray.add(exportator.toJSON(ofertaData));
		}

		jsonObject.put(name, listToAdd);

		writeFile(file, jsonObject.toJSONString());
		
	}
	
	public <T> List<T> getArray(File file, Exportator<T> exportator, String array) throws FileNotFoundException, IOException, ParseException{
		
		ArrayList <T> ret = new ArrayList<>();
		
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
		JSONArray jsonArray = (JSONArray) jsonObject.get(array);
		
		if(jsonArray == null)
			throw new IOException("El archivo" + file.getPath() + " no contiene el campo " + array);
		
		for (Object object : jsonArray) {
			
			T toAdd = getObject((JSONObject) object, exportator);
			
			ret.add(toAdd);
			
		}
		
		return ret;
	}
	
	public <T> void putField(File file, String field, T value) throws FileNotFoundException, IOException, ParseException{
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
		jsonObject.put(field, value);
		
		writeFile(file, jsonObject.toJSONString());
	}
	
	public <T> T getField(File file, String field, Class<T> clase) throws FileNotFoundException, IOException, ParseException{
		
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));
		return clase.cast(jsonObject.get(field));
			
	}
	
	public void newFile(File file) throws IOException{
		
		JSONObject jsonObject = new JSONObject();

		writeFile(file, jsonObject.toJSONString());
		
	}
	
	private void writeFile(File file, String toWrite) throws IOException{
		FileWriter finalFile = new FileWriter(file);
		finalFile.write(toWrite);
		finalFile.flush();
		finalFile.close();
	}
	
	private <T> T getObject (JSONObject obj, Exportator<T> exportator){
		
		return exportator.fromJSON(obj);
		
	}

}
