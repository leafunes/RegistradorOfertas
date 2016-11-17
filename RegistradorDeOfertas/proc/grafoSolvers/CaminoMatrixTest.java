package grafoSolvers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import grafo.DiGraph;

public class CaminoMatrixTest {
	
	DistPoint a = new DistPoint(0, 0);
	DistPoint b = new DistPoint(0, 1);
	DistPoint c = new DistPoint(1, 2);
	DistPoint d = new DistPoint(2, 4);
	DistPoint e = new DistPoint(2, 1);
	DistPoint f = new DistPoint(4, 2);
	DistPoint g = new DistPoint(3, 3);
	DistPoint h = new DistPoint(5, 4);
	
	private DiGraph<DistPoint> getGrafo(){
		
		List<DistPoint> puntos = new ArrayList<>();
		
		puntos.add(a);
		puntos.add(b);
		puntos.add(c);
		puntos.add(d);
		puntos.add(e);
		puntos.add(f);
		puntos.add(g);
		puntos.add(h);
		
		DiGraph<DistPoint> grafo = new DiGraph<DistPoint>(puntos);
		
		grafo.addEdge(a, b);
		grafo.addEdge(a, e);
		grafo.addEdge(b, e);
		grafo.addEdge(b, c);
		grafo.addEdge(c, d);
		grafo.addEdge(c, g);
		grafo.addEdge(d, h);
		grafo.addEdge(e, f);
		grafo.addEdge(g, h);
		grafo.addEdge(g, e);
		grafo.addEdge(g, h);
		grafo.addEdge(f, h);
		
		return grafo;
		
		
	}
	

	@Test
	public void constructorTest() {
		CaminoMatrix<DistPoint> matrix = new CaminoMatrix<>(getGrafo());
		
		Camino<DistPoint> camino_ab = matrix.getCamino(a, b);
		Camino<DistPoint> camino_ba = matrix.getCamino(b, a);
		Camino<DistPoint> camino_ae = matrix.getCamino(a, e);
		Camino<DistPoint> camino_ea = matrix.getCamino(e, a);
		
		assertNull(camino_ba);
		assertNull(camino_ea);
		
		assertNotNull(camino_ae);
		assertNotNull(camino_ab);
	}
	
	@Test
	public void putCaminoTest(){
		CaminoMatrix<DistPoint> matrix = new CaminoMatrix<>(getGrafo());
		
		assertNotNull(matrix.getCamino(a, b));
		assertNull(matrix.getCamino(b, a));
		
		Camino<DistPoint> toAdd = new Camino<DistPoint>(b, a);
		matrix.putCamino(b, a, toAdd);
		
		assertNotNull(matrix.getCamino(a, b));
		assertNotNull(matrix.getCamino(b, a));
		
	}
	
	@Test
	public void getCaminoWeightTest(){
		
		CaminoMatrix<DistPoint> matrix = new CaminoMatrix<>(getGrafo());
		
		
		assertEquals(2.23, matrix.getCaminoWeight(c, d), 0.01);
		assertEquals(3.00, matrix.getCaminoWeight(d, h), 0.01);
		
	}
	
	@Test
	public void actualizeCaminoTest(){

		CaminoMatrix<DistPoint> matrix = new CaminoMatrix<>(getGrafo());
		
		assertNull(matrix.getCamino(a, f));
		
		matrix.actualizaCamino(a, e, f);
		
		assertNotNull(matrix.getCamino(a, f));
		assertEquals(4.47, matrix.getCaminoWeight(a, f), 0.01);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void actualizeCaminoNoExistenteTest(){

		CaminoMatrix<DistPoint> matrix = new CaminoMatrix<>(getGrafo());
		
		matrix.actualizaCamino(a, f, d);//El camino a -> f no existe
		
	}

}
