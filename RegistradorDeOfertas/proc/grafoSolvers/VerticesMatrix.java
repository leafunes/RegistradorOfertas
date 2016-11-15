package grafoSolvers;

import java.util.HashMap;

import grafo.DiGraph;
import grafo.Distanciable;

class VerticesMatrix<T extends Distanciable<T>> {
	
	HashMap< T, HashMap <T, Camino<T>> > matrix;
	
	VerticesMatrix(DiGraph<T> grafo){
		
		matrix = new HashMap<T, HashMap<T,Camino<T>>>();
		
		for(T v1 : grafo.getVerticesSet()){

			HashMap<T, Camino<T>> filaMatriz = new HashMap<T, Camino<T>>();
			
			for(T v2 : grafo.getVerticesSet()){
				Camino<T> camino = null;//TODO
				
				if(grafo.containsEdge(v1, v2)){
					camino = new Camino<>(v1,v2);
				}
				
				filaMatriz.put(v2, camino);
				
			}
			
			matrix.put(v1, filaMatriz);
		}
		
	}
	
	void actualizaCamino(T v1, T v2, T v3){
		Camino<T> camino1 = matrix.get(v1).get(v2);
		Camino<T> camino2 = matrix.get(v2).get(v3);
		
		if(camino1 == null || camino2 == null)
			throw new IllegalArgumentException("El camino a sumar no existe");
		
		camino1.agregaCamino(camino2);
		
	}
	
	Camino<T> getCamino(T v1, T v2){
		return matrix.get(v1).get(v2);
	}
	
	double getCaminoWeight(T v1, T v2){
		
		Camino<T> camino = matrix.get(v1).get(v2);
		
		if(camino == null)
			return Double.POSITIVE_INFINITY;
		
		return camino.getPeso();
	}

}
