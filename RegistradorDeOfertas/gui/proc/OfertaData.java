package proc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.joda.time.ReadableInstant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class OfertaData{
	
	//Clase contenedora de datos, carente de inteligencia
	
	String nombre;
	String apellido;
	String email;
	long DNI;
	long telefono;
	DateTime fecha;
	LocalTime inicio;
	LocalTime fin;
	Interval intervalo;
	Duration duracion;
	double precio;//TODO BigDecimal
	
	List<EquipData> equipamento = new ArrayList<>();
	
	//Exportador
	private static Exportator<OfertaData> exportador;
	
	private static Exportator<EquipData> equipExportador = EquipData.exportador();
	
	private static class Exportador implements Exportator<OfertaData>{

		@Override
		public OfertaData clone(OfertaData other) {
			throw new RuntimeException("No implementado");
		}

		@Override
		public OfertaData fromJSON(JSONObject obj) {
			OfertaData ret = new OfertaData();
			
			String fechaString = (String)obj.get("fecha");
			
			ret.setNombre((String)obj.get("nombre"));
			ret.setApellido((String)obj.get("apellido"));
			ret.setEmail((String)obj.get("email"));
			ret.setDNI((long)obj.get("DNI"));
			ret.setTelefono((long)obj.get("telefono"));
			ret.setFecha(DateTime.parse(fechaString));
			
			String inicio = (String)obj.get("inicio");
			String fin = (String)obj.get("fin");
			
			ret.setInicio(LocalTime.parse(inicio));
			ret.setFin(LocalTime.parse(fin));
			ret.setPrecio((double)obj.get("precio"));
			
			JSONArray equipJson = (JSONArray)obj.get("equipamento");
			
			equipJson.forEach(json -> ret.agregaEquip(equipExportador.fromJSON( (JSONObject)json )) );
			
			ret.createInterval();
			
			return ret;
		}

		@Override
		public JSONObject toJSON(OfertaData data) {
			JSONObject ret = new JSONObject();
			
			ret.put("nombre", data.nombre);
			ret.put("apellido", data.apellido);
			ret.put("email", data.email);
			ret.put("DNI", data.DNI);
			ret.put("telefono", data.telefono);
			ret.put("fecha", data.getFecha().toString("yyyy-MM-dd"));
			ret.put("inicio", data.inicio.toString("hh:mm"));
			ret.put("fin", data.fin.toString("hh:mm"));
			ret.put("precio", data.precio);
			
			JSONArray equipamentoJson = new JSONArray();
			
			data.getEquip().forEach(equip -> equipamentoJson.add(equipExportador.toJSON(equip)) );
			
			ret.put("equipamento", equipamentoJson);
			
			return ret;
		
		}
	}
	
	
	public static Exportator<OfertaData> exportador(){
		if(exportador == null){
			exportador = new Exportador();
		}
		
		return exportador;
	}
	
	//Clase
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getDNI() {
		return DNI;
	}
	public void setDNI(long dNI) {
		DNI = dNI;
	}
	public long getTelefono() {
		return telefono;
	}
	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}
	public DateTime getFecha() {
		return fecha;
	}
	public void setFecha(DateTime fecha) {
		this.fecha = fecha;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public LocalTime getInicio() {
		return inicio;
	}
	public void setInicio(LocalTime inicio) {
		this.inicio = inicio;//Solo la hora
	}
	public void setInicio(int inicio) {
		this.inicio = new LocalTime(inicio, 0, 0);//Solo la hora
	}
	public LocalTime getFin() {
		return fin;
	}
	public void setFin(LocalTime fin) {
		this.fin = fin;//Solo la hora
	}
	public void setFin(int fin) {
		this.fin = new LocalTime(fin, 0, 0);//Solo la hora
	}
	
	public void agregaEquip(EquipData toAdd){
		this.equipamento.add(toAdd);
	}
	
	public List<EquipData> getEquip(){
		return this.equipamento;
	}
	
	public void createInterval(){
		
		//TODO: codigo defensivo
		DateTime inicio = this.inicio.toDateTime(fecha);
		DateTime fin = this.fin.toDateTime(fecha);
		
		this.intervalo = new Interval(inicio, fin);
		this.duracion = intervalo.toDuration();
		
	}
	
	private boolean containsEquip(EquipData equipData) {
		return this.equipamento.contains(equipData);
	}
	
	public boolean superponeCon(List<OfertaData> list){
		
		if(list.isEmpty())
			return false;
		
		for (OfertaData ofertaData : list) {
			if(intervalo.overlaps(ofertaData.intervalo))
				return true;
		}
		return false;
		
	}
	
	@Override
	public String toString(){
		return DNI + ", " + inicio + " a " + fin + ": " + precio;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfertaData other = (OfertaData) obj;
		
		
		if (DNI != other.DNI) return false;
		if ((nombre == null && other.nombre != null) || !nombre.equals(other.nombre)) return false;
		if ((apellido == null && other.apellido != null) || !apellido.equals(other.apellido)) return false;
		if ((email == null && other.email != null) || !email.equals(other.email)) return false;
		
		
		if ((fecha == null && other.fecha != null) || !fecha.equals(other.fecha)) return false;
		if ((fin == null && other.fin != null)||!fin.equals(other.fin)) return false;
		if ((inicio == null && other.inicio != null) || !inicio.equals(other.inicio)) return false;
		
		if (Double.doubleToLongBits(precio) != Double.doubleToLongBits(other.precio)) return false;
		if (telefono != other.telefono)return false;
		
		for (EquipData equipData : equipamento) {
			if(!other.containsEquip(equipData))
				return false;
		}
		
		
		return true;
	}





	
	
	
	

}
