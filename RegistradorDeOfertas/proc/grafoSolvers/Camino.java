package grafoSolvers;

import grafo.Distanciable;

import java.util.ArrayList;
import java.util.List;

class Camino <T extends Distanciable<T>>{
	
	ArrayList<T> camino;
	int longitud;
	double peso;
	
	Camino(T inicio, T fin){
		camino = new ArrayList<T>();
		camino.add(inicio);
		camino.add(fin);
		
		
		peso = inicio.distanceTo(fin);
		longitud = camino.size() - 1;
	}
	
	void agregaCamino(Camino<T> otro){
		
		if( ! camino.get(longitud).equals(otro.camino.get(0))){
			throw new IllegalArgumentException("Los caminos no coinciden en sus extremos");
		}
		
		this.camino.remove(longitud);
		this.camino.addAll(otro.camino);
		
		peso = peso + otro.peso;
		longitud = camino.size() - 1;
		
	}
	
	List<T> getList(){
		return this.camino;
	}
	
	double getPeso(){
		return peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((camino == null) ? 0 : camino.hashCode());
		result = prime * result + longitud;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Camino<T> other = (Camino<T>) obj;
		if (camino == null) {
			if (other.camino != null)
				return false;
		} else if (!camino.equals(other.camino))
			return false;
		if (longitud != other.longitud)
			return false;
		return true;
	}
	
}