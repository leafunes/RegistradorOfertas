package grafoSolvers;

import grafo.Distanciable;

import java.awt.Point;


public class DistPoint extends Point implements Distanciable<DistPoint>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public DistPoint (int x, int y) {
		super(x,y);
	}
	

	@Override
	public double distanceTo(DistPoint other) {
		
		return this.distance(other);
	}


	@Override
	public int compareTo(DistPoint o) {
		throw new RuntimeException("No implementado");
	}
}
