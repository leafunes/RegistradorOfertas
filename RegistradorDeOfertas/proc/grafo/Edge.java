package grafo;

public class Edge <V extends Distanciable<V>>{
	public V vertex1;
	public V vertex2;		
	public Double weight;
	
	public Edge(V i, V j, Double weight){
		vertex1 = i;
		vertex2 = j;
		this.weight = weight;
	}
	
	public Edge(V i, V j){
		vertex1 = i;
		vertex2 = j;
		this.weight = i.distanceTo(j);
	}
	
	@Override
	public String toString(){
		
		return vertex1.toString() + "---" + vertex2.toString();
		
	}

	@Override
	public boolean equals(Object other)
	{
		
		if(other == null)return false;
		if(other == this)return true;
		
		if (this.getClass() == other.getClass()){
			Edge<?>edge = (Edge<?>)other;
			
	        if ( edge.vertex1.equals(vertex1) && edge.vertex2.equals(vertex2) ||
	        		edge.vertex1.equals(vertex2) && edge.vertex2.equals(vertex1)){
	            return true;
	        }
	    }
		
		return false;
	}
}
