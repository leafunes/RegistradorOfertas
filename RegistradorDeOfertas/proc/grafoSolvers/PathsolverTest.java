package grafoSolvers;

import static org.junit.Assert.*;
import grafo.DiGraph;

import java.util.ArrayList;
import java.util.Arrays;
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
	public void getPathTest(){
		
		DiGraph<DistPoint> grafo = getGrafo();
		DistPoint source = a;
		DistPoint dest = h;
		
		Camino<DistPoint> path = solver.getPath(source, dest, grafo);
		
		Camino<DistPoint> expected = new Camino<>();
		
		expected.agregaVertice(a);
		expected.agregaVertice(e);
		expected.agregaVertice(f);
		expected.agregaVertice(h);
		
		assertEquals(expected.longitud, path.longitud);
		assertEquals(expected, path);
		
	}

}
