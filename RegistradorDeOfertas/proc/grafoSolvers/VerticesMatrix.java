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
				Camino<T> camino = null;
				
				if(grafo.containsEdge(v1, v2))camino = new Camino<>(v1,v2);
				
				filaMatriz.put(v2, camino);
				
			}
			
			matrix.put(v1, filaMatriz);
		}
		
	}
	
	void actualizaCamino(T i, T k, T j){
		//Actualiza el camino de i a j, como un camino de i a k, y luego de k a j
		
		Camino<T> caminoik = matrix.get(i).get(k);
		Camino<T> caminokj = matrix.get(k).get(j);
		
		if(caminoik == null && caminokj == null)
			throw new IllegalArgumentException("El camino a sumar no existe");
		
		Camino<T> caminoikj = new Camino<>(caminoik);
		caminoikj.agregaCamino(caminokj);
		
		putCamino(i, j, caminoikj);
		
	}
	
	void putCamino(T i, T j, Camino<T> camino){
		matrix.get(i).put((j), camino);
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
