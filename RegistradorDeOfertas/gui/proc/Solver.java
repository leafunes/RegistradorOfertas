package proc;

import java.util.ArrayList;
import java.util.List;

public abstract class Solver {
	
	protected String nombre = "";
	private static List<Solver> solvers;
	
	public static List<Solver> getAllSolvers(){
		
		if(solvers == null){
			solvers = new ArrayList<>();
			
			solvers.add(new SolverGoloso((d1,d2) ->  (int)(d2.getPrecio() - d1.getPrecio()), "por precio"));
			solvers.add(new SolverGoloso((d1,d2) ->  d1.duracion.compareTo(d2.duracion), "por tiempo"));
			solvers.add(new SolverGoloso((d1,d2) ->  (int)( d1.getPrecio()/d1.duracion.getStandardMinutes() - d2.getPrecio()/d2.duracion.getStandardMinutes()), "por precio/tiempo"));
		}
		
		return solvers;
		
	}
	
	protected Solver(){};
	
	public abstract List<OfertaData> resolver(List <OfertaData> list);
	

}
