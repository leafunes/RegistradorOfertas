package proc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SolverGoloso extends Solver{
	
	Comparator<OfertaData> comparador;

	SolverGoloso(Comparator<OfertaData> comparador, String criterio){
		this.comparador = comparador;
		this.nombre = "Algoritmo goloso " + criterio;
	}

	@Override
	public List<OfertaData> resolver(List<OfertaData> dataList) {
		
		List<OfertaData> ret = new ArrayList<>();
		
		//Clonar
		List<OfertaData> dataClon = new ArrayList<>(dataList);
		
		Collections.sort(dataClon, comparador);
		
		for (OfertaData ofertaData : dataClon) {
			System.out.print(ofertaData.duracion.getStandardMinutes() + " "  + " ");
			System.out.println(ofertaData.getPrecio()/ofertaData.duracion.getStandardMinutes());
			if(!ofertaData.contieneIntervaloDe(ret))
				ret.add(ofertaData);
		}
		
		return ret;
	}

}
