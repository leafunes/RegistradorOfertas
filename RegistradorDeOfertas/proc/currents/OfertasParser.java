package currents;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import datas.EquipData;
import datas.OfertaData;

class OfertasParser {
	
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
	
	public OfertasParser(){
		
	}
	
	LocalTime strToLocalTime(String str){
		if(!str.matches("\\d\\d:\\d\\d"))
			throw new IllegalArgumentException("La cadena " + str + "no tiene el formato requerido");
		
		String[] time = str.split(":");
		
		return new LocalTime(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
	}
	
	/***
	 * 
	 * @param str
	 * 
	 * Uso: parseData("nombre, apellido, DNI, Email, tel, fecha, inicio, fin, monto, (equip1, equip2, ...)")
	 * 
	 * @return
	 */
	
	OfertaData parseData(String str){
		
		OfertaData ret = new OfertaData();
		
		str = str.replaceAll("\\s+", "");
		
		Pattern pattern = Pattern.compile("\\((.*?)\\)");//El equipamento que esta entre parentesis
		Matcher matcher = pattern.matcher(str);//TODO
		matcher.find();
		
		String[] data = str.split(",");
		
		ret.setNombre(data[0]);
		ret.setApellido(data[1]);
		ret.setDNI(Long.parseLong(data[2]));
		ret.setEmail(data[3]);
		ret.setTelefono(Long.parseLong(data[4]));
		ret.setFecha(DateTime.parse(data[5], formatter));
		ret.setInicio(strToLocalTime(data[6]));
		ret.setFin(strToLocalTime(data[7]));
		ret.setPrecio(Double.parseDouble(data[8]));
		
		ret.createInterval();
		
		String equipamento = matcher.group(0).replaceAll("\\(|\\)", "");
		
		String[] equipArray = equipamento.split(",");
		
		for(String equipData : equipArray){
			EquipData toAdd = new EquipData(equipData);
			ret.agregaEquip(toAdd);
		}
		
		return ret;
	}
	
	/***
	 * 
	 * @param str
	 * 
	 * Uso: parseList("nombre, apellido, DNI, Email, tel, fecha, inicio, fin, monto, (equip1, equip2, ...) y nombre2, apellido2, ...")
	 * 
	 * @return
	 */
	
	List<OfertaData> parseList(String str){
		List<OfertaData> ret = new ArrayList<>();
		String[] rawData = str.split("y");
		
		for (String data : rawData) {
			ret.add(parseData(data));
		}
		
		return ret;
		
	}

}
