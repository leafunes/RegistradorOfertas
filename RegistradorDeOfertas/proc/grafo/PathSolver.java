package grafo;

import java.util.List;
import java.util.PriorityQueue;

public class PathSolver{
	
	private static PathSolver current;
	
	public static PathSolver getCurrent(){
		if(current == null)
			current = new PathSolver();
		
		return current;
	}
	
	
	private PathSolver(){
		
	}

	public <T extends Distanciable<T>> List<T> getPath(T source, T destination, DiGraph<T> graph){
		
		if(!graph.getVerticesSet().contains(source) && !graph.getVerticesSet().contains(destination))
			throw new IllegalArgumentException("Los vertices no pertenecen al grafo");
		
		double[] distancias = new double[graph.vertices];
		boolean[] visitados = new boolean[graph.vertices];
		
		List<T> vertices = graph.refList;
		
		int sourceIndex = vertices.indexOf(source);
		int destIndex = vertices.indexOf(destination);
		
		for (int i = 0; i < graph.getVertices(); i++) {
			
			if(graph.containsEdge(source, graph.getVertex(i)) == false)
				distancias[i] = Double.POSITIVE_INFINITY;
			
			else
				distancias[i] = graph.getWeigth(source, graph.getVertex(i));
			
		}
		
		distancias[sourceIndex] = 0;
		visitados[sourceIndex] = true;
		
		while(!visitedAll(visitados)){
			T vertice = minimoSinVisitar(visitados, distancias, vertices);
			
			int verticeIndex = vertices.indexOf(vertice);
			visitados[verticeIndex] = true;
			for (T t : graph.getNehiVertex(vertice)) {
				
				int tIndex = vertices.indexOf(t);
				if(distancias[tIndex] > distancias[verticeIndex] + graph.getWeigth(vertice, t))
					distancias[tIndex] = distancias[verticeIndex] + graph.getWeigth(vertice, t);
				
			}
		}
		
		
	}
	
	private boolean visitedAll(boolean[] visitados){
		for (boolean visitado : visitados) {
			if(!visitado) return false;
		}
		
		return true;
	}
	
	private <T extends Distanciable<T>> T minimoSinVisitar(boolean[] visitados, double[] distancias, List<T> refs){
		double min = Double.MIN_VALUE;
		T vertexMin = null;
		
		for (int i = 0; i < distancias.length; i++) {
			double dist = distancias[i];
			boolean visitado = visitados[i];
			
			if(dist < min && !visitado){
				min = dist;
				vertexMin = refs.get(i);
			}
			
		}
		
		//TODO
	}
	
}
