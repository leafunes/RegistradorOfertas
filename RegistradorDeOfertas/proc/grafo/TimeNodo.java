package grafo;

import datas.OfertaData;

public class TimeNodo implements Distanciable<TimeNodo>{

	public static enum Tipo {INICIO, FIN, SOURCE, DESTINATION};
	
	public final Tipo tipo;
	public final OfertaData data;
	
	public TimeNodo(Tipo t, OfertaData data){
		tipo = t;
		if(tipo == Tipo.SOURCE || tipo == Tipo.DESTINATION){
			this.data = null;
		}
		
		else{
			this.data = data;
		}
	}
	
	@Override
	public int compareTo(TimeNodo arg0) {
		throw new RuntimeException("No impementado");
	}

	@Override
	public double distanceTo(TimeNodo other) {
		
		if(this.tipo == Tipo.INICIO && other.tipo == Tipo.FIN)
			if(this.data.equals(other.data))
				return -data.getPrecio();
		
		return 0;
		
	}

}
