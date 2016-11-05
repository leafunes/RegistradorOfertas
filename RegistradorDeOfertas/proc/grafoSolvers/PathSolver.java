package grafoSolvers;

import grafo.DiGraph;
import grafo.Distanciable;

import java.util.ArrayList;
import java.util.Collections;
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
		
		List < Nodo<T> > nodos = createNodos(source, destination, graph);

		Nodo <T> destNodo = nodos.get(nodos.size() - 1);
		
		while(!estanTodosVistos(nodos)){
			
			Nodo<T> minimo = getMinimoNoVisitado(nodos);
			minimo.visitado = true;
			actualizaDistancia(minimo, nodos, graph);
			
		}
		
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
		for(Nodo<T> nodo : nodos)if(graph.containsEdge(source, nodo.vertice)){
			nodo.distanciaTent = graph.getWeigth(source, nodo.vertice);
			nodo.anterior = sourceNodo;
		}
		
		return nodos;
	}
	
	<T extends Distanciable<T>> boolean estanTodosVistos(List<Nodo<T>> nodos){
		
		for (Nodo<T> nodo : nodos)
			if(!nodo.visitado)
				return false;
		
		return true;
		
	}
	
	<T extends Distanciable<T>> Nodo<T> getMinimoNoVisitado(List<Nodo<T>> nodos){
		
		Nodo<T> min = null;
		
		for (Nodo<T> nodo : nodos)
			if(!nodo.visitado && (min == null || nodo.distanciaTent < min.distanciaTent))
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
