package solvers;

import grafo.DiGraph;
import grafo.PathSolver;
import grafo.TimeNodo;
import grafo.TimeNodo.Tipo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import datas.OfertaData;

public class SolverExacto extends Solver{
	
	private PathSolver pathSolver = PathSolver.getCurrent();
	
	private interface AgregaSi{
		public boolean agregaSi(TimeNodo n1, TimeNodo n2);
	}

	@Override
	public List<OfertaData> resolver(List<OfertaData> list) {

		List<TimeNodo> vertices = generateVertices(list);
		DiGraph<TimeNodo> grafo = generateGraph(vertices);
		
		TimeNodo nodoSource = vertices.get(0);
		TimeNodo nodoDestination = vertices.get(vertices.size() - 1);
		
		List<TimeNodo> caminoMasCorto = pathSolver.getPath(nodoSource, nodoDestination, grafo);
		
		throw new RuntimeException("No implementado"); //TODO
	}

	@Override
	public List<OfertaData> resolver(List<OfertaData> list, List<OfertaData> obligatorios) throws IllegalArgumentException {
		
		throw new RuntimeException("No implementado!!");
	}
	
	public List<TimeNodo> generateVertices(List<OfertaData> dataList){
		
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
	
	public DiGraph<TimeNodo> generateGraph(List<TimeNodo> vertices){
		
		DiGraph<TimeNodo> ret = new DiGraph<TimeNodo>(vertices);
		
		TimeNodo nodoSource = vertices.get(0);
		TimeNodo nodoDestination = vertices.get(vertices.size() - 1);
		
		for (int i = 1; i < vertices.size(); i = i + 2) {
			
			TimeNodo nodoInicio = vertices.get(i);
			TimeNodo nodoFin = vertices.get(i+1);
			
			ret.addEdge(nodoInicio, nodoFin);
			ret.addEdge(nodoSource, nodoInicio);
			ret.addEdge(nodoFin, nodoDestination);
			
			//Agrego todos las aristas desde los nodos finales de los horarios anteriores, hacia el nodoInicio
			agregarEdges(nodoInicio, ret, (n1, n2) -> n2.tipo == Tipo.FIN && 
													!n2.data.getFin().isAfter(n1.data.getInicio()));
			
			//Agrego todos las aristas desde el nodoFinal, hacia todos los finales posteriores
			agregarEdges(nodoFin, ret, (n1, n2) -> n2.tipo == Tipo.INICIO &&
												!n2.data.getInicio().isBefore(n1.data.getFin()));
			
		}
		
		return ret;
	}
	
	public void agregarEdges(TimeNodo nodo, DiGraph<TimeNodo> grafo, AgregaSi condicion){
	
		for(TimeNodo nodoGrafo : grafo.getVerticesSet())
			if(condicion.agregaSi(nodo, nodoGrafo))
				grafo.addEdge(nodoGrafo, nodo);
					
		
	}
	
	
	

}
