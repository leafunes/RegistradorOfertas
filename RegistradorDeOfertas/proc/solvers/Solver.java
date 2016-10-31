package solvers;

import java.util.ArrayList;
import java.util.List;

import datas.OfertaData;

public abstract class Solver {
	
	protected String nombre = "";
	private static List<Solver> solvers;
	
	public static List<Solver> getAllSolvers(){
		
		if(solvers == null){
			solvers = new ArrayList<>();
			
			solvers.add(new SolverGoloso((d1,d2) ->  (int)(d2.getPrecio() - d1.getPrecio()), "por precio"));
			solvers.add(new SolverGoloso((d1,d2) ->  d1.getDuracion().compareTo(d2.getDuracion()), "por tiempo"));
			solvers.add(new SolverGoloso((d1,d2) ->  (int)( d1.getPrecio()/d1.getDuracion().getStandardMinutes() - d2.getPrecio()/d2.getDuracion().getStandardMinutes()), "por precio/tiempo"));
		}
		
		return solvers;
		
	}
	
	protected Solver(){};
	
	public String getName(){
		return nombre;
	}
	
	public abstract List<OfertaData> resolver(List <OfertaData> list);
	
	public abstract List<OfertaData> resolver(List <OfertaData> list, List <OfertaData> obligatorios) throws IllegalArgumentException;
	
	protected void verifica(List<OfertaData> obligatorios) throws IllegalArgumentException{
		
		for (OfertaData ofertaData : obligatorios) {
			if(ofertaData.esValido(obligatorios))
				throw new IllegalArgumentException("La lista de ofertas obligatorias tiene ofertas que se superponen");
		}
		
	}
	
}
