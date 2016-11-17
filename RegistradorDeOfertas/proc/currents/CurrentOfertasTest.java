package currents;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import datas.EquipData;
import datas.OfertaData;

public class CurrentOfertasTest {
	
	CurrentOfertas current = CurrentOfertas.getCurrent(new File("Tests"));
	DateTime now = DateTime.now();
	DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

	private LocalTime strToLocalTime(String str){
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
	
	private OfertaData parseData(String str){
		
		OfertaData ret = new OfertaData();
		
		str = str.replaceAll("\\s+", "");
		System.out.println(str);
		
		Pattern pattern = Pattern.compile("\\d");//El equipamento que esta entre parentesis
		Matcher matcher = pattern.matcher("123456");//TODO
		
		String[] data = str.split(",");
		
		for (String string : data) {
			System.out.println(string);
		}
		
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
		System.out.println(matcher.groupCount());
		String[] equip = matcher.group(0).split(",");
		
		for(String equipData : equip){
			System.out.println(equipData);
			EquipData toAdd = new EquipData(equipData);
			ret.agregaEquip(toAdd);
		}
		
		return ret;
	}
	
	/***
	 * 
	 * @param str
	 * 
	 * Uso: parseData("nombre, apellido, DNI, Email, tel, fecha, inicio, fin, monto, (equip1, equip2, ...) y nombre2, apellido2, ...")
	 * 
	 * @return
	 */
	
	private List<OfertaData> parseList(String str){
		List<OfertaData> ret = new ArrayList<>();
		String[] rawData = str.split("y");
		
		for (String data : rawData) {
			ret.add(parseData(data));
		}
		
		return ret;
		
	}
	
	private List<OfertaData> getlistOfData(){
		List<OfertaData> ret = new ArrayList<>();
		
		parseList("leandro, funes, 40426773, f.l@gmail.com, 1169616563, 12/04/2016, 01:00, 05:00, 800, (guitarra, bajo)"
				+ "y belen, devito, 12345678, belu@ungs.com, 98754689, 12/04/2016, 08:00, 07:00, 150, (guitarra, microfono)"
				+ "y javier, marenco, 56734567, jmarenco@gmail.com, 12346578, 12/04/2016, 13:00, 22:00, 9000, (guitarra, bajo, platillos)");
		
		return ret;
	}
	
	//**************************TESTS***************************************//
	
	@Test
	public void putOfertaTest(){
		List<OfertaData> list = getlistOfData();
		
		for (OfertaData ofertaData : list) {
			current.putOferta(ofertaData, now);
		}
		
		List<OfertaData> listOfFile = current.getOfertas(now);
		
		assertEquals(list.size(), listOfFile.size());
		
	}

}
