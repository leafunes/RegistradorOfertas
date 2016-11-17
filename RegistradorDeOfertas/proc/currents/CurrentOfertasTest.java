package currents;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import datas.EquipData;
import datas.OfertaData;

public class CurrentOfertasTest {
	
	CurrentOfertas current = CurrentOfertas.getCurrent(new File("Tests"));
	DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
	
	DateTime tomorrow = DateTime.now().plusDays(1);
	DateTime notNow = DateTime.parse("25/04/1997", formatter);

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
	
	private List<OfertaData> parseList(String str){
		List<OfertaData> ret = new ArrayList<>();
		String[] rawData = str.split("y");
		
		for (String data : rawData) {
			ret.add(parseData(data));
		}
		
		return ret;
		
	}
	
	private List<OfertaData> getlistOfData(){
		
		return parseList("leandro, funes, 40426773, f.l@gmail.com, 1169616563, 12/04/2016, 01:00, 05:00, 800, (guitarra, bajo)"
				+ "y belen, devito, 40345678, belu@ungs.com, 98754689, 12/04/2016, 08:00, 09:00, 150, (guitarra, microfono)"
				+ "y javier, marenco, 56734567, jmarenco@gmail.com, 12346578, 12/04/2016, 13:00, 22:00, 9000, (guitarra, bajo, platillos)");
		
	}
	
	//**************************TESTS***************************************//
	
	@Before
	public void clearData(){
		
		File tomorrowFile = new File("Tests" + File.separator + tomorrow.toString("yyyy_MM_dd")+".json");
		File testFile = new File("Tests" + File.separator + "testData.data");
		
		testFile.delete();
		tomorrowFile.delete();
		
	}
	
	@Test
	public void putOfertaTest(){

		List<OfertaData> list = getlistOfData();
		
		for (OfertaData ofertaData : list) current.putOferta(ofertaData, tomorrow);
		
		List<OfertaData> listOfFile = current.getOfertas(tomorrow);
		
		assertEquals(list.size(), listOfFile.size());
		
		assertTrue(list.containsAll(listOfFile));
		assertTrue(listOfFile.containsAll(list));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void putOfertaFechaAnterior(){
		
		OfertaData oferta = parseData("leandro, funes, 40426773, f.l@gmail.com, 1169616563, 12/04/2016, 01:00, 05:00, 800, (guitarra, bajo)");
		
		current.putOferta(oferta, notNow);
		
	}
	
	@Test
	public void removeOfertaTest(){
		
		List<OfertaData> ofertaList = getlistOfData();
		for(OfertaData oferta : ofertaList) current.putOferta(oferta, tomorrow);
		
		List<OfertaData> ofertasCurrent = current.getOfertas(tomorrow);
		
		assertEquals(ofertaList.size(), ofertasCurrent.size());
		assertTrue(ofertaList.containsAll(ofertasCurrent));
		assertTrue(ofertasCurrent.containsAll(ofertaList));
		
		OfertaData ofertaRemove = current.getOfertas(tomorrow).get(0);
		
		current.removeOferta(ofertaRemove, tomorrow);
		
		ofertasCurrent = current.getOfertas(tomorrow); //Actualizo la lista de ofertas
		ofertaList.remove(ofertaRemove);
		
		assertEquals(ofertaList.size(), ofertasCurrent.size());
		assertTrue (ofertaList.containsAll(ofertasCurrent));
		assertTrue(ofertasCurrent.containsAll(ofertaList));
		
	}
	
	@Test
	public void cerrarDiaTest(){
		
		assertFalse(current.isCerrado(tomorrow));
		
		current.cerrarDia(tomorrow);
		
		assertTrue(current.isCerrado(tomorrow));
		
	}
	
	@Test
	public void newDataTest(){
		File newData = new File("Tests" + File.separator + "testData.data");
		
		assertFalse(newData.exists());
		String expectedContent = "{\"ofertas\":[],\"cerrado\":false}";
		String content = null;
		
		try {
			current.newData(newData);
			

			Scanner scan = new Scanner(newData);
			content = scan.useDelimiter("\\Z").next();
			scan.close();
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		assertEquals(expectedContent, content);
		
	}
	
	@Test
	public void isDateOkTest(){
		
		assertFalse(current.isDateOk(DateTime.now()));
		assertFalse(current.isDateOk(notNow));
		
		assertTrue(current.isDateOk(tomorrow));
		
	}

}
