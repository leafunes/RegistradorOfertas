package grafoSolvers;

import static org.junit.Assert.*;

import org.junit.Test;

public class CaminoTest {
	
	DistPoint a = new DistPoint(0, 0);
	DistPoint b = new DistPoint(0, 1);
	DistPoint c = new DistPoint(1, 2);
	DistPoint d = new DistPoint(2, 4);
	DistPoint e = new DistPoint(2, 1);
	DistPoint f = new DistPoint(4, 2);
	DistPoint g = new DistPoint(3, 3);
	DistPoint h = new DistPoint(5, 4);

	@Test
	public void constructorConCaminoTest() {
		Camino<DistPoint> caminoOriginal = new Camino<>(a,e);
		caminoOriginal.agregaVertice(f);
		
		Camino<DistPoint> caminoClon = new Camino<>(caminoOriginal);
		
		assertEquals(caminoOriginal, caminoClon);
	}
	
	@Test
	public void testGetPeso() {
		Camino<DistPoint> camino_ae = new Camino<>(a,e);
		Camino<DistPoint> camino_ah = new Camino<>(a,h);
		
		assertEquals(2.23, camino_ae.getPeso(), 0.01);
		assertEquals(6.4, camino_ah.getPeso(), 0.01);
		
	}

	@Test
	public void testAgregaVertice() {
		Camino<DistPoint> camino_ae = new Camino<>(a,e);
		assertEquals(2.23, camino_ae.getPeso(), 0.01);
		
		camino_ae.agregaVertice(f);
		
		assertEquals(4.47, camino_ae.getPeso(), 0.01);
	}
	
	@Test
	public void testAgregaVerticeEnVacio() {
		Camino<DistPoint> camino = new Camino<>();
		assertEquals(0, camino.getPeso(), 0.01);
		
		camino.agregaVertice(f);
		assertEquals(0, camino.getPeso(), 0.01);
		
		camino.agregaVertice(e);
		
		assertEquals(2.23, camino.getPeso(), 0.01);
	}
	
	@Test
	public void agregaCaminoTest() {
		Camino<DistPoint> camino_ae = new Camino<>(a,e);
		Camino<DistPoint> camino_ef = new Camino<>(e,f);
		assertEquals(2.23, camino_ef.getPeso(), 0.01);
		
		camino_ae.agregaCamino(camino_ef);
		
		assertEquals(4.47, camino_ae.getPeso(), 0.01);
		assertEquals(4.47, camino_ae.getPeso(), 0.01);
	}

}
