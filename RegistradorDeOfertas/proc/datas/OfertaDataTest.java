package datas;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class OfertaDataTest {
	

	OfertaData data1 = new OfertaData();
	OfertaData data2 = new OfertaData();
	OfertaData data3 = new OfertaData();
	OfertaData data4 = new OfertaData();
	OfertaData data5 = new OfertaData();
	
	
	@Before
	public void beforeTest(){
		data1.setInicio(new LocalTime("01:00"));
		data1.setFin(new LocalTime("02:00"));
		data1.createInterval();
		

		data2.setInicio(new LocalTime("03:00"));
		data2.setFin(new LocalTime("05:00"));
		data2.createInterval();
		

		data3.setInicio(new LocalTime("05:00"));
		data3.setFin(new LocalTime("07:00"));
		data3.createInterval();
		
		data4.setInicio(new LocalTime("05:00"));
		data4.setFin(new LocalTime("06:00"));
		data4.createInterval();
		
		data5.setInicio(new LocalTime("11:00"));
		data5.setFin(new LocalTime("13:00"));
		data5.createInterval();
		
	}
	
	private List<OfertaData> getListOf(OfertaData[] array){
		List<OfertaData> ret = new ArrayList<>();
		
		for (OfertaData ofertaData : array) ret.add(ofertaData);
		
		return ret;
	}
	
	@Test
	public void superponeConTest() {
		
		List<OfertaData> list = getListOf(new OfertaData[]{data1,data2,data3});
		
		assertTrue(data4.superponeCon(list));
		assertFalse(data5.superponeCon(list));
		
	}
	
	@Test
	public void esValido(){
		
		List<OfertaData> list = getListOf(new OfertaData[]{data1,data2,data3,data4,data5});
		
		assertTrue(data4.esValido(list));
		assertFalse(data5.esValido(list));
		
	}

}
