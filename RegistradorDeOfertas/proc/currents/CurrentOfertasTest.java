package currents;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import datas.EquipData;
import datas.OfertaData;

public class CurrentOfertasTest {
	
	CurrentEquipamento current = CurrentEquipamento.getCurrent(new File("Tests" + 
																	File.separatorChar 
																	+ "ofertasTest.json"));
	@Before
	public void clearData(){
		
		File file = new File("Tests" + 
					File.separatorChar 
					+ "ofertasTest.json");
		file.delete();
		
		
	}
	
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
		
		Pattern pattern = Pattern.compile("\\((.*?)\\)");
		Matcher matcher = pattern.matcher(str);
		
		String[] data = str.split(",");
		
		ret.setNombre(data[0]);
		ret.setApellido(data[1]);
		ret.setDNI(Long.parseLong(data[2]));
		ret.setEmail(data[3]);
		ret.setTelefono(Long.parseLong(data[4]));
		ret.setFecha(DateTime.parse(data[5]));
		ret.setInicio(strToLocalTime(data[6]));
		ret.setFin(strToLocalTime(data[7]));
		ret.setPrecio(Double.parseDouble(data[8]));
		
		String[] equip = matcher.group(0).split(",");
		
		for(String equipData : equip){
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
		
		parseData("leandro, funes, 40426773, f.l@gmail.com, 1169616563, 12/04/2016, 01:00, 05:00, 800, (guitarra, bajo)");
		
		return ret;
	}
	
	@Test
	public void test(){
		getlistOfData();
	}
	
	@Test
	public void getEquipamentoTest() {
		List<EquipData> equip = current.getEquipamento();
		
		assertEquals(0, equip.size());
		
		current.putEquipamento(new EquipData("hola"));
		
		equip = current.getEquipamento();
		
		assertEquals(1, equip.size());
		
	}
	
	//TODO
	
	/*@Test
	public void putEquipamentoTest() {
		
		List<OfertaData> list = getlistOfData();
		
		for (OfertaData equipData : list) {
			current.putEquipamento(equipData);
		}
		
		List<EquipData> dataLoaded = current.getEquipamento();
		
		assertEquals(4, dataLoaded.size());
		
		assertTrue(list.containsAll(dataLoaded));
		assertTrue(dataLoaded.containsAll(list));
		
	}
	
	@Test
	public void removeEquipamentoTest(){
		
		List<EquipData> list = getlistOfData();
		
		for (EquipData equipData : list) {
			current.putEquipamento(equipData);
		}
		
		EquipData toRemove = list.get(0);
		EquipData notToRemove = list.get(1);
		
		current.removeEquipamento(toRemove);
		
		List<EquipData> dataLoaded = current.getEquipamento();
		
		assertEquals(3, dataLoaded.size());
		assertFalse(dataLoaded.contains(toRemove));
		assertTrue(dataLoaded.contains(notToRemove));
		
	}*/

}
