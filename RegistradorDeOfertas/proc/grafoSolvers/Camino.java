package grafoSolvers;

import grafo.Distanciable;

import java.util.ArrayList;
import java.util.List;

public class Camino <T extends Distanciable<T>>{
	
	ArrayList<T> camino;
	int longitud;
	double peso;
	
	public Camino(){
		camino = new ArrayList<T>();
		
		peso = 0;
		longitud = 0;
	}
	
	public Camino(T inicio, T fin){
		camino = new ArrayList<T>();
		camino.add(inicio);
		camino.add(fin);
		
		
		peso = inicio.distanceTo(fin);
		longitud = camino.size() - 1;
	}
	
	public Camino(Camino<T> c){
		camino = new ArrayList<T>(c.camino);
		
		peso = c.peso;
		longitud = c.longitud;
	}
	
	public void agregaCamino(Camino<T> otro){
		
		if(otro == null)
			return;
		
		if(camino.isEmpty())
			camino = new ArrayList<T>(otro.camino);
		
		else{
			
			if( ! camino.get(longitud).equals(otro.camino.get(0)))
				throw new IllegalArgumentException("Los caminos no coinciden en sus extremos " + camino.get(longitud) + ", " + otro.camino.get(0));
			
			camino.remove(longitud);
			camino.addAll(otro.camino);
		
		}
		
		peso = peso + otro.peso;
		longitud = camino.size() - 1;
	}
	
	public void agregaVertice(T v){

		if(!camino.isEmpty())peso += camino.get(longitud).distanceTo(v);
		camino.add(v);
		longitud = camino.size() - 1;
		
	}
	
	public List<T> getList(){
		return new ArrayList<>(this.camino);
	}
	
	public double getPeso(){
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

	@SuppressWarnings("unchecked")
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