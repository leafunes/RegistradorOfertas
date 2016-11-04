package grafoSolvers;

import static org.junit.Assert.*;
import grafo.DiGraph;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PathsolverTest {
	
	private PathSolver solver = PathSolver.getCurrent();

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
	public void createNodosTest(){

		DiGraph<DistPoint> grafo = getGrafo();
		
		List <Nodo<DistPoint>> nodos = solver.createNodos(a, h, grafo);
		
		Nodo<DistPoint> source = new Nodo<DistPoint>(a);
		Nodo<DistPoint> destination = new Nodo<DistPoint>(h);
		Nodo<DistPoint> primer = nodos.get(0);
		Nodo<DistPoint> ultimo = nodos.get( nodos.size() - 1 );
		
		
		assertTrue(primer.equals(source));
		assertTrue(ultimo.equals(destination));
		
		assertTrue(primer.visitado);
		assertEquals(primer.distanciaTent, 0, 0);
		
		for (Nodo<DistPoint> nodo : nodos) 
			if(!(nodo.equals(primer) || nodo.equals(ultimo)))
				assertFalse(nodo.visitado);
		
		
	}
	
	
	@Test
	public void getMinimoNoVisitadoTest() {
		DiGraph<DistPoint> grafo = getGrafo();
		
		List <Nodo<DistPoint>> nodos = solver.createNodos(a, h, grafo);
		
		Nodo<DistPoint> nodoMasCercano = new Nodo<DistPoint>(b);
		Nodo<DistPoint> nodoRecibido = solver.getMinimoNoVisitado(nodos);
		
		assertTrue(nodoRecibido.equals(nodoMasCercano));
		
		
	}
	
	@Test
	public void actualizaDistanciaTest(){

		DiGraph<DistPoint> grafo = getGrafo();
		
		List <Nodo<DistPoint>> nodos = solver.createNodos(a, h, grafo);
		
		Nodo<DistPoint> nodoCercano = solver.getMinimoNoVisitado(nodos);
		
		nodoCercano.visitado = true;
		
		solver.actualizaDistancia(nodoCercano, nodos, grafo);
		
		assertEquals(2.41, nodos.get(2).distanciaTent, 0.01); //nodo c
		assertEquals(2.23, nodos.get(4).distanciaTent, 0.01); //nodo e
		
	}

}
