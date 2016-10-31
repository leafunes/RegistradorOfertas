package solvers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.junit.Test;

import datas.OfertaData;

public class SolverGolosoTest {
	
	private Solver porDuracion = new SolverGoloso((d1,d2) ->  d1.getDuracion().compareTo(d2.getDuracion()), "por tiempo");
	private Solver porPrecio = new SolverGoloso((d1,d2) ->  (int)(d2.getPrecio() - d1.getPrecio()), "por precio");
	private Solver porCociente = new SolverGoloso((d1,d2) ->  (int)( d1.getPrecio()/d1.getDuracion().getStandardMinutes() - d2.getPrecio()/d2.getDuracion().getStandardMinutes()), "por precio/tiempo");
	
	
	
	private LocalTime strToLocalTime(String str){
		if(!str.matches("\\d\\d:\\d\\d"))
			throw new IllegalArgumentException("La cadena " + str + "no tiene el formato requerido");
		
		String[] time = str.split(":");
		
		return new LocalTime(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
	}
	
	private List<OfertaData> createData(String str){
		
		List<OfertaData> ret = new ArrayList<>();
		str = str.replaceAll("\\s+|de", "");
		String[] ofertas = str.split("(,|a|con)");
		
		for (int i = 0; i < ofertas.length; i = i+3) {
			
			OfertaData data = new OfertaData();
			data.setInicio(strToLocalTime(ofertas[i]));
			data.setFin(strToLocalTime(ofertas[i+1]));
			data.setPrecio(Double.parseDouble(ofertas[i+2]));
			data.createInterval();
			
			ret.add(data);
			
		}
		
		return ret;
	}
	
	private void testSolver(String data, String expected,Solver solver){
		
		List<OfertaData> dataList = createData(data);

		List<OfertaData> expectedList = createData(expected);

		List<OfertaData> resultado = porPrecio.resolver(dataList);

		assertEquals(expectedList.size(), resultado.size());

		assertTrue(expectedList.containsAll(resultado));
		assertTrue(resultado.containsAll(expectedList));
		
	}
	
	@Test
	public void SolverVerifica() {
		List<OfertaData> data = createData("de 01:00 a 05:00 con 500, "
										+ "de 05:00 a 07:00 con 500");
		
		//no deberia tirar ninguna excepcion
		porDuracion.verifica(data);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void SolverVerificaException() {
		List<OfertaData> data = createData("de 01:00 a 05:00 con 500, "
										+ "de 04:59 a 07:00 con 500");
		
		//no deberia tirar ninguna excepcion
		porDuracion.verifica(data);
		
	}
	
	@Test
	public void SolverPorPrecioTest(){
		
		testSolver("de 01:00 a 05:00 con 800, "
					+ "de 06:00 a 07:00 con 799,"
					+ "de 09:00 a 11:00 con 300,"
					+ "de 06:30 a 09:00 con 200,"
					+ "de 01:00 a 03:00 con 999999", 
										
					"de 01:00 a 03:00 con 999999," 
					+ "de 06:00 a 07:00 con 799,"
					+ "de 09:00 a 11:00 con 300,", 
					porPrecio);
	}
	
	@Test
	public void SolverPorDuracionTest(){
		
		testSolver("de 01:00 a 05:00 con 800, "
					+ "de 06:00 a 07:00 con 799,"
					+ "de 09:00 a 11:00 con 300,"
					+ "de 06:30 a 09:00 con 200,"
					+ "de 01:00 a 03:00 con 999999", 
										
					"de 06:00 a 07:00 con 799," 
					+ "de 09:00 a 11:00 con 300,"
					+ "de 01:00 a 03:00 con 999999", 
					porDuracion);
	}
	
	@Test
	public void SolverPorCocienteTest(){
		
		testSolver("de 01:00 a 05:00 con 800, "
					+ "de 06:00 a 07:00 con 799,"
					+ "de 09:00 a 11:00 con 300,"
					+ "de 06:30 a 09:00 con 200,"
					+ "de 01:00 a 03:00 con 999999", 
										
					"de 01:00 a 03:00 con 999999," 
					+ "de 06:00 a 07:00 con 799,"
					+ "de 09:00 a 11:00 con 300,", 
					porCociente);
	}
	
}