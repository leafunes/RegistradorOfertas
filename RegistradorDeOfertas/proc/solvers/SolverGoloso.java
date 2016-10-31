package solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import datas.OfertaData;

public class SolverGoloso extends Solver{
	
	Comparator<OfertaData> comparador;

	SolverGoloso(Comparator<OfertaData> comparador, String criterio){
		this.comparador = comparador;
		this.nombre = "Algoritmo goloso " + criterio;
	}

	@Override
	public List<OfertaData> resolver(List<OfertaData> dataList) {
		
		return resolver(dataList, new ArrayList<>());
	}
	
	@Override
	public List<OfertaData> resolver(List<OfertaData> dataList, List<OfertaData> obligatorios) {
		
		verifica(obligatorios);
		
		List<OfertaData> ret = new ArrayList<>(obligatorios);
		List<OfertaData> dataClon = new ArrayList<>(dataList);
		
		Collections.sort(dataClon, comparador);
		
		for (OfertaData ofertaData : dataClon) {
			if(!ofertaData.superponeCon(ret))
				ret.add(ofertaData);
		}
		
		return ret;
	}
	

}
