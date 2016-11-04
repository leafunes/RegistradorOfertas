package grafoSolvers;

import grafo.Distanciable;

class Nodo <T extends Distanciable<T>>{
	
	boolean visitado;
	T vertice;
	Nodo<T> anterior;
	double distanciaTent;
	
	public Nodo(T vertice){
		this.vertice = vertice;
		visitado = false;
		distanciaTent = Double.POSITIVE_INFINITY;
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Nodo<T> other = (Nodo<T>) obj;//TODO
		
		return vertice.equals(other.vertice);
	}
	
}
