package proc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import data.JsonData;

public class CurrentEquipamento {
	
	private Exportator<EquipData> exportador = EquipData.exportador();
	
	private JsonData jsonData = JsonData.getData();
	private File file = new File("Datos" + File.separatorChar + "equipamento" +
									File.separatorChar +
									"data.json"); 
	
	private static CurrentEquipamento current;

	public static CurrentEquipamento getCurrent(){
		if(current == null){
			current = new CurrentEquipamento();
		}
		
		return current;
	}
	
	private CurrentEquipamento(){
		
	}
	
	public List<EquipData> getEquipamento(){
		
		if(!file.exists()){
			createFile(file);
		}
		
		List<EquipData> ret = new ArrayList<>();
		
		try {
			ret = jsonData.getArray(file, exportador, "equipamento");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return ret;
		
		
	}
	
	public void putEquipamento(EquipData data){
		
		if(!file.exists()){
			createFile(file);
		}
		
		try {
			jsonData.putObjectInArray(file, exportador, data, "equipamento");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public void removeEquipamento(EquipData data){
		
		if(file.exists()){
			
			try {
				jsonData.removeObjectInArray(file, exportador, data, "equipamento");
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	private void createFile(File file){
		
		try {
			jsonData.newFile(file);
			jsonData.putArray(file, exportador, new ArrayList<>(), "equipamento");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
	}

}
