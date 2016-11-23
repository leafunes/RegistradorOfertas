package grafoSolvers;

import grafo.DiGraph;
import grafo.Distanciable;

public class PathSolver{
	
	private static PathSolver current;
	
	public static PathSolver getCurrent(){
		if(current == null)
			current = new PathSolver();
		
		return current;
	}
	
	
	private PathSolver(){
		
	}

	public <T extends Distanciable<T>> Camino<T> getPath(T source, T destination, DiGraph<T> graph){
		
		if(!graph.getVerticesSet().contains(source) && !graph.getVerticesSet().contains(destination))
			throw new IllegalArgumentException("Los vertices no pertenecen al grafo");
		
		CaminoMatrix<T> matriz = new CaminoMatrix<T>(graph);
		
		for(T k : graph.getVerticesSet()){
			for(T i : graph.getVerticesSet()){
				for(T j : graph.getVerticesSet()){
					
					if(matriz.getCaminoWeight(i, j) > matriz.getCaminoWeight(i, k) + matriz.getCaminoWeight(k, j))
						matriz.actualizaCamino(i, k, j);
					
				}
			}
		}
		
		return matriz.getCamino(source, destination);
		
	}
	
	
}
