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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vertex1 == null) ? 0 : vertex1.hashCode());
		result = prime * result + ((vertex2 == null) ? 0 : vertex2.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
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
