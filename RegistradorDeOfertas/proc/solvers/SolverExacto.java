package solvers;

import grafo.DiGraph;
import grafoSolvers.PathSolver;
import solvers.TimeNodo.Tipo;

import java.util.ArrayList;
import java.util.List;

import datas.OfertaData;

public class SolverExacto extends Solver{
	
	private PathSolver pathSolver = PathSolver.getCurrent();
	
	SolverExacto() {

		nombre = "Algoritmo Exacto Polinomial";
	}

	@Override
	public List<OfertaData> resolver(List<OfertaData> list) {
		
		List<OfertaData> ret = new ArrayList<>();
		
		List<TimeNodo> vertices = generateVertices(list);
		DiGraph<TimeNodo> grafo = generateGraph(vertices);
		
		TimeNodo nodoSource = vertices.get(0);
		TimeNodo nodoDestination = vertices.get(vertices.size() - 1);
		
		List<TimeNodo> caminoMasCorto = pathSolver.getPath(nodoSource, nodoDestination, grafo);
		
		for (TimeNodo timeNodo : caminoMasCorto) {
			if(!ret.contains(timeNodo.data) && timeNodo.data != null)
				ret.add(timeNodo.data);
		}
		
		
		return ret;
	}

	@Override
	public List<OfertaData> resolver(List<OfertaData> list, List<OfertaData> obligatorios) throws IllegalArgumentException {
		
		List<OfertaData> ret = new ArrayList<>(obligatorios);
		List<OfertaData> dataClon = new ArrayList<>(list);
		
		dataClon.removeIf(oferta -> oferta.superponeCon(obligatorios));
		
		ret.addAll(resolver(dataClon));
		
		return ret;
	}
	
	List<TimeNodo> generateVertices(List<OfertaData> dataList){
		
		List<TimeNodo> ret = new ArrayList<>();

		TimeNodo nodoSource = new TimeNodo(Tipo.SOURCE, null);
		ret.add(nodoSource);
		
		for (OfertaData data : dataList) {
			
			TimeNodo nodoInicio = new TimeNodo(Tipo.INICIO, data);
			TimeNodo nodoFin = new TimeNodo(Tipo.FIN, data);
			
			ret.add(nodoInicio);
			ret.add(nodoFin);
			
		}
		
		TimeNodo nodoDestination = new TimeNodo(Tipo.DESTINATION, null);
		ret.add(nodoDestination);
		
		return ret;
		
	}
	
	DiGraph<TimeNodo> generateGraph(List<TimeNodo> vertices){
		
		DiGraph<TimeNodo> grafo = new DiGraph<TimeNodo>(vertices);
		
		TimeNodo nodoSource = vertices.get(0);
		TimeNodo nodoDestination = vertices.get(vertices.size() - 1);
		
		for (int i = 1; i < vertices.size() - 1; i = i + 2) {
			
			TimeNodo nodoInicio = vertices.get(i);
			TimeNodo nodoFin = vertices.get(i+1);
			
			grafo.addEdge(nodoInicio, nodoFin);
			
			grafo.addEdge(nodoSource, nodoInicio);
			
			grafo.addEdge(nodoFin, nodoDestination);
	
			agregaEdges(nodoInicio, nodoFin, grafo);
		
		}
		
		
		return grafo;
	}
	
	void agregaEdges(TimeNodo nodoInicio, TimeNodo nodoFin, DiGraph<TimeNodo> grafo){
		
		for(TimeNodo other : grafo.getVerticesSet()){
			//Agrego todos las aristas desde los nodos finales de los horarios anteriores, hacia el nodoInicio
			
			if(other.isFin() && !other.isAfter(nodoInicio))
				grafo.addEdge(other, nodoInicio); //De cualquierNodo -> nodoInicio
			
			//Agrego todos las aristas desde el nodoFin hacia los nodos incio de los horarios posteriores
			
			if(other.isInicio() && !other.isBefore(nodoFin))
				grafo.addEdge(nodoFin, other); //De nodoFin -> cualquierNodo
			
		}
	}
	
}
