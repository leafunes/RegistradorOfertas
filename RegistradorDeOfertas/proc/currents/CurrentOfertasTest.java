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

	OfertasParser parser = new OfertasParser();
	
	private List<OfertaData> getlistOfData(){
		
		return parser.parseList("leandro, funes, 40426773, f.l@gmail.com, 1169616563, 12/04/2016, 01:00, 05:00, 800, (guitarra, bajo)"
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
		
		OfertaData oferta = parser.parseData("leandro, funes, 40426773, f.l@gmail.com, 1169616563, 12/04/2016, 01:00, 05:00, 800, (guitarra, bajo)");
		
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
