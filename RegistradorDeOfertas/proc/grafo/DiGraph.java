package grafo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiGraph <V extends Distanciable<V>>{

	HashMap<V, HashSet<V> > table;
	ArrayList<V> refList;
	
	int edges;
	int vertices;
	
	public DiGraph(Collection<? extends V> vertices){
		
		table = new HashMap<>();
		refList = new ArrayList<V>();
		refList.addAll(vertices);
		
		for (V v : vertices) {
			
			HashSet<V> neighbors = new HashSet<>();
			table.put(v, neighbors);
			
		}
		
		this.vertices = vertices.size();
	}
	
	public boolean containsEdge(V vertex1, V vertex2){
		
		checkContainsBounds(vertex1, vertex2);
		
		return table.get(vertex1).contains(vertex2);
	}

	public void addEdge(V vertex1, V vertex2) throws IllegalArgumentException{
		
		checkAddBounds(vertex1, vertex2);
		
		if(!containsEdge(vertex1, vertex2))
			edges++;
	
		table.get(vertex1).add(vertex2);
		//table.get(vertex2).add(vertex1);
	}
	
	public void addEdge(Edge<V> e) throws IllegalArgumentException{
		if(e == null) throw new IllegalArgumentException("El edge es nulo");
		addEdge(e.vertex1, e.vertex2);
	}
	
	public void removeEdge(V vertex1, V vertex2){
		
		checkAddBounds(vertex1, vertex2);
		
		if(containsEdge(vertex1, vertex2))
			edges--;
		
		table.get(vertex1).remove(vertex2);
		//table.get(vertex2).remove(vertex1);
			
	}
	
	public void removeEdge(Edge<V> e){

		if(e == null) throw new IllegalArgumentException("El edge es nulo");
		removeEdge(e.vertex1, e.vertex2);
		
	}
	
	public Edge<V> getLongerEdge() {
		
		if(edges == 0) return null;
		
		Edge<V> ret = new Edge<V>(null, null ,Double.MIN_VALUE);
		
		for (V edgeKey : table.keySet()) {
			for(V edgeNehi : getNehiVertex(edgeKey)){
				
				if( ret.weight.compareTo( edgeKey.distanceTo(edgeNehi)) == -1){
					ret = new Edge<V>(edgeKey, edgeNehi);
				}
				
			}
		}
		
		return ret;
	}
	
	public Double getWeigth(V e1, V e2){
		
		if(containsEdge(e1, e2)){
			
			return e1.distanceTo(e2);
		}
		
		return null;
	}
	
	public Set<V> getNehiVertex(V i2) {
		
		if(i2 == null || !table.containsKey(i2))
			throw new IllegalArgumentException("No existe el vertice");
		
		return table.get(i2);
		
		
	}
	
	public List< Edge<V> > getNehiEdges(V v1, V v2){
		
		if(v1 == null || v2 == null)
			throw new IllegalArgumentException("Arista nula");
		
		if(!containsEdge(v1, v2))
			throw new IllegalArgumentException("no existe la arista, " +
											v1.toString() + " -- " + v2.toString());
		
		List < Edge <V> > ret = new ArrayList<>();
		
		for(V vertex : table.get(v1)){
			if(!vertex.equals(v2))ret.add( new Edge<V>(v1, vertex));
		}
		
		return ret;
		
	}
	
	public List< Edge<V> > getNehiEdges(Edge<V> edge){
		
		if(edge == null)
			throw new IllegalArgumentException("Arista nula");
		
		if(!containsEdge(edge.vertex1, edge.vertex2))
			throw new IllegalArgumentException("no existe la arista, " +
											edge.toString());
		
		return getNehiEdges(edge.vertex1, edge.vertex2);
		
	}
	
	public Set<V> getVerticesSet(){
		return table.keySet();
	}
	
	public V getVertex(int i) {
		
		if(i > refList.size() || i < 0)
			throw new IllegalArgumentException("Arista no encontrado " + i);
		
		
		return refList.get(i);
	}
	
	public List< Edge<V> > getEdgesList(){
		
		List< Edge<V> >ret = new ArrayList<>();
		
		for(V vertex1 : table.keySet()){
			for(V vertex2: table.get(vertex1)){
				Edge<V> toAdd = new Edge<V>(vertex1, vertex2);
				
				if(!ret.contains(toAdd))ret.add(toAdd);
			}
		}
		
		
		return ret;
		
	}
	
	public DiGraph<V> clone(){
		
		DiGraph<V> ret = new DiGraph<>(refList);
		
		for (V vertex1 : table.keySet()) {
			for (V vertex2 : table.get(vertex1)) {
				
				ret.addEdge(vertex1, vertex2);
				
			}
		}
		
		return ret;
	}
	
	private void checkContainsBounds(V v1, V v2){
		if (v1 == null || v2 == null)
			throw new IllegalArgumentException("Vertice nulo");

		if (!table.containsKey(v1) || !table.containsKey(v2))
			throw new IllegalArgumentException("Vertices no existentes " + v1.toString() + ", " + v2.toString());

	}
	
	
	private void checkAddBounds(V v1, V v2){
		if (v1 == null || v2 == null)
			throw new IllegalArgumentException("Vertice nulo");

		if (!table.containsKey(v1) || !table.containsKey(v2))
			throw new IllegalArgumentException("Vertices no existentes " + v1.toString() + ", " + v2.toString());

		if (v1.equals(v2))
			throw new IllegalArgumentException("No se pueden agregar loops: " + v1.toString());
	}
	
	public int getEdges(){
		return edges;
	}
	
	public int getVertices(){
		return vertices;
	}
	
	@Override
	public String toString(){
		//TODO
		return table.toString();
	}
	
	@Override
	public boolean equals(Object other){
		
		if(other == null)return false;
		
		if(other == this) return true;
		
		if(other instanceof DiGraph<?>){
			
			DiGraph<?> otherMap = (DiGraph<?>) other;
			
			return this.table.equals(otherMap.table);
		}
		return false;
		
	}
	
}
