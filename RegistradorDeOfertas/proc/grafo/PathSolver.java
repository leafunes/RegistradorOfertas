package grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class PathSolver{
	
	private class Nodo <T extends Distanciable<T>>{
		
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
		
		List < Nodo<T> > nodos = createNodos(source, destination, graph);
		
		
		while(!estanTodosVistos(nodos)){
			
			Nodo<T> minimo = getMinimoNoVisitado(nodos);
			minimo.visitado = true;
			actualizaDistancia(minimo, nodos, graph);
			
		}
		
		Nodo <T> destNodo = new Nodo<T>(destination);
		List<T> secuencia = new ArrayList<T>();
		
		generateSecuencia(destNodo,secuencia);
		
		return secuencia;
		
	}
	
	<T extends Distanciable<T>> List <Nodo<T>> createNodos(T source, T destination, DiGraph<T> graph){
		
		List< Nodo<T> > nodos = new ArrayList<>();
		
		//Icicializo el primer nodo(source)
		Nodo <T> sourceNodo = new Nodo<T>(source);
		sourceNodo.anterior = null;
		sourceNodo.visitado = true;
		sourceNodo.distanciaTent = 0;
		nodos.add(sourceNodo);
		
		//Creo todos los nodos que no sean ni el source ni el destination
		for(T vertice : graph.getVerticesSet()){
			if(!vertice.equals(source) && !vertice.equals(destination)){
				Nodo<T> toAdd = new Nodo<T>(vertice);
				nodos.add(toAdd);
			}
		}
		
		//Creo el ultio nodo (destination)
		Nodo <T> destNodo = new Nodo<T>(destination);
		nodos.add(destNodo);
		
		//Actualizo las distancias al primer nodo
		for(Nodo<T> nodo : nodos)if(graph.containsEdge(source, nodo.vertice))
			nodo.distanciaTent = graph.getWeigth(source, nodo.vertice);
		
		return nodos;
	}
	
	<T extends Distanciable<T>> boolean estanTodosVistos(List<Nodo<T>> nodos){
		
		for (Nodo<T> nodo : nodos)
			if(!nodo.visitado)
				return false;
		
		return true;
		
	}
	
	<T extends Distanciable<T>> Nodo<T> getMinimoNoVisitado(List<Nodo<T>> nodos){
		
		Nodo<T> min = nodos.get(0);
		
		for (Nodo<T> nodo : nodos)
			if(!nodo.visitado && min.distanciaTent < nodo.distanciaTent)
				min = nodo;
			
		return min;
	}
	
	<T extends Distanciable<T>> void actualizaDistancia(Nodo<T> nodo, List<Nodo<T>> nodosList, DiGraph<T> grafo){
		
		for(Nodo<T> vecino : nodosList)
			if(grafo.containsEdge(nodo.vertice, vecino.vertice))
				if(vecino.distanciaTent > nodo.distanciaTent + grafo.getWeigth(nodo.vertice, vecino.vertice)){
					vecino.distanciaTent = nodo.distanciaTent + grafo.getWeigth(nodo.vertice, vecino.vertice);
					vecino.anterior = nodo;
				}
		
	}
	
	<T extends Distanciable<T>> void generateSecuencia(Nodo<T> dest, List<T> secuencia){
		if(dest == null)
			Collections.reverse(secuencia);
		
		else{
			secuencia.add(dest.vertice);
			generateSecuencia(dest.anterior, secuencia);
		}
	}
	
}