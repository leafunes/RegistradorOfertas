package solvers;

import static org.junit.Assert.*;
import grafo.DiGraph;
import grafo.Edge;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.junit.Test;

import solvers.TimeNodo.Tipo;
import datas.OfertaData;

public class SolverExactoTest {

	SolverExacto solver = new SolverExacto();
	
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
	
	@Test
	public void isLastHourTest(){

		List<OfertaData> ofertas = createData("de 01:00 a 05:00 con 800, "
											+ "de 06:00 a 07:00 con 799,"
											+ "de 09:00 a 11:00 con 300");
		
		List<TimeNodo> nodos = solver.generateVertices(ofertas);
		TimeNodo ultimaHora = nodos.get(5);
		TimeNodo noUltimaHora = nodos.get(4);
		
		assertTrue(solver.isLastHour(nodos, ultimaHora));
		assertFalse(solver.isLastHour(nodos, noUltimaHora));
		
	}
	
	@Test
	public void isFirstHourTest(){
		List<OfertaData> ofertas = createData("de 01:00 a 05:00 con 800, "
											+ "de 06:00 a 07:00 con 799,"
											+ "de 09:00 a 11:00 con 300");

		List<TimeNodo> nodos = solver.generateVertices(ofertas);
		TimeNodo primeraHora = nodos.get(1);
		TimeNodo noPrimeraHora = nodos.get(4);

		assertTrue(solver.isFirstHour(nodos, primeraHora));
		assertFalse(solver.isFirstHour(nodos, noPrimeraHora));
		
	}
	
	@Test
	public void generateVerticesTest() {
		List<OfertaData> ofertas = createData("de 01:00 a 05:00 con 800, "
											+ "de 06:00 a 07:00 con 799,"
											+ "de 09:00 a 11:00 con 300");
		
		TimeNodo source = new TimeNodo(Tipo.SOURCE, null);
		TimeNodo dest = new TimeNodo(Tipo.DESTINATION, null);
		
		TimeNodo inicio2 = new TimeNodo(Tipo.INICIO, ofertas.get(1));
		TimeNodo fin2 = new TimeNodo(Tipo.FIN, ofertas.get(1));
		
		List<TimeNodo> nodos = solver.generateVertices(ofertas);
		
		assertEquals(8, nodos.size());
		assertEquals(source, nodos.get(0));
		assertEquals(dest, nodos.get(7));
		

		assertEquals(inicio2, nodos.get(3));
		assertEquals(fin2, nodos.get(4));
		
		
	}
	
	@Test
	public void generateGraphTest(){
		List<OfertaData> ofertas = createData("de 01:00 a 05:00 con 800, "
				+ "de 06:00 a 07:00 con 799,"
				+ "de 09:00 a 11:00 con 300");
		
		List<TimeNodo> vertices = solver.generateVertices(ofertas);
		
		DiGraph<TimeNodo> grafoExpected = new DiGraph<TimeNodo>(vertices);
		
		grafoExpected.addEdge(vertices.get(0), vertices.get(1)); //s -> 01:00
		
		grafoExpected.addEdge(vertices.get(1), vertices.get(2)); //01:00 -> 05:00
		grafoExpected.addEdge(vertices.get(2), vertices.get(3)); //05:00 -> 06:00
		grafoExpected.addEdge(vertices.get(2), vertices.get(5)); //05:00 -> 09:00

		grafoExpected.addEdge(vertices.get(3), vertices.get(4)); //06:00 -> 07:00
		grafoExpected.addEdge(vertices.get(4), vertices.get(5)); //07:00 -> 09:00

		grafoExpected.addEdge(vertices.get(5), vertices.get(6)); //09:00 -> 11:00
		grafoExpected.addEdge(vertices.get(6), vertices.get(7)); //11:00 -> s
		
		DiGraph<TimeNodo> grafo = solver.generateGraph(vertices);
		
		assertEquals(grafoExpected.getEdges(), grafo.getEdges());
		
		for (Edge<TimeNodo> edge : grafoExpected.getEdgesList()) {
			assertTrue(grafo.getEdgesList().contains(edge));
		}
		
	}
	
	@Test
	public void agregaEdgesTest(){
		
		List<OfertaData> ofertas = createData("de 01:00 a 05:00 con 800, "
				+ "de 06:00 a 07:00 con 799,"
				+ "de 09:00 a 11:00 con 300");
		
		List<TimeNodo> vertices = solver.generateVertices(ofertas);
		
		TimeNodo hora6 = vertices.get(3);
		TimeNodo hora7 = vertices.get(4);
		
		DiGraph<TimeNodo> grafoExpected = new DiGraph<TimeNodo>(vertices);
		
		grafoExpected.addEdge(vertices.get(2), vertices.get(3));// 05:00 -> 06:00
		grafoExpected.addEdge(vertices.get(4), vertices.get(5));// 07:00 -> 09:00
		
		DiGraph<TimeNodo> grafo = new DiGraph<TimeNodo>(vertices);
		
		solver.agregaEdges(hora6, hora7, grafo);
		
		assertEquals(grafoExpected.getEdges(), grafo.getEdges());
		
		assertEquals(grafoExpected.getEdgesList(), grafo.getEdgesList());
		
	}
	
	@Test
	public void resolverTest(){
		
		List<OfertaData> ofertas = createData("de 01:00 a 05:00 con 800,"
											+ "de 06:00 a 07:00 con 799,"
											+"de 01:00 a 02:00 con 0,"
											+ "de 09:00 a 11:00 con 300,"
											+ "de 06:30 a 09:00 con 200,"
											+ "de 01:00 a 03:00 con 999999");
		
		List<OfertaData> obligatorios = createData("de 01:00 a 02:00 con 0");
		
		List<OfertaData> expected = createData("de 01:00 a 05:00 con 800,"
											+ "de 06:00 a 07:00 con 799,"
											+"de 01:00 a 02:00 con 0,"
											+ "de 09:00 a 11:00 con 300,"
											+ "de 06:30 a 09:00 con 200,"
											+ "de 01:00 a 03:00 con 999999");//TODO
		
		List<OfertaData> solucion = solver.resolver(ofertas, obligatorios);
		
		throw new RuntimeException("TODO");
		
	}

}
