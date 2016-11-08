package solvers;

import org.joda.time.LocalTime;

import datas.OfertaData;
import grafo.Distanciable;

class TimeNodo implements Distanciable<TimeNodo>{

	public static enum Tipo {INICIO, FIN, SOURCE, DESTINATION};
	
	public final Tipo tipo;
	public final OfertaData data;
	LocalTime hora;
	
	public TimeNodo(Tipo t, OfertaData data){
		tipo = t;
		if(tipo == Tipo.SOURCE || tipo == Tipo.DESTINATION){
			this.data = null;
		}
		
		else{
			if(tipo == Tipo.INICIO)
				hora = data.getInicio();
			else if(tipo == Tipo.FIN)
				hora = data.getFin();
			
			this.data = data;
		}
		
		
	}
	
	public boolean isFin(){
		return tipo == Tipo.FIN;
	}
	
	public boolean isInicio(){
		return tipo == Tipo.INICIO;
	}
	
	public boolean isSource(){
		return tipo == Tipo.SOURCE;
	}
	
	public boolean isDestination(){
		return tipo == Tipo.DESTINATION;
	}
	
	public boolean isAfter(TimeNodo other){
		return hora.isAfter(other.hora);
	}
	
	public boolean isBefore(TimeNodo other){
		return hora.isBefore(other.hora);
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

	@Override
	public String toString() {
		return "TimeNodo [tipo=" + tipo + ", data=" + data + ", toString()="+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

}
