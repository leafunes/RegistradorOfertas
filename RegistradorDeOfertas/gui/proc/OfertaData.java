package proc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.json.simple.JSONObject;

public class OfertaData{
	
	//Clase contenedora de datos, carente de inteligencia
	
	String nombre;
	String apellido;
	String email;
	long DNI;
	long telefono;
	Date fecha;
	LocalTime inicio;
	LocalTime fin;
	double precio;
	
	private static Exportador exportador;
	
	private static class Exportador implements Exportator<OfertaData>{

		private SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
	
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
			try {
				ret.setFecha(parser.parse(fechaString));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int inicio = (int)(long)obj.get("inicio"); //Cosas de la libreria
			int fin = (int)(long)obj.get("fin");
			
			ret.setInicio(inicio);
			ret.setFin(fin);
			ret.setPrecio((double)obj.get("precio"));
			
			return ret;
		}

		@Override
		public JSONObject toJSON(OfertaData data) {
			JSONObject ret = new JSONObject();
			
			
			
			String dateParsed = parser.format(data.getFecha());
			
			ret.put("nombre", data.nombre);
			ret.put("apellido", data.apellido);
			ret.put("email", data.email);
			ret.put("DNI", data.DNI);
			ret.put("telefono", data.telefono);
			ret.put("fecha", dateParsed);
			ret.put("inicio", data.inicio.getHourOfDay());
			ret.put("fin", data.fin.getHourOfDay());
			ret.put("precio", data.precio);
			
			return ret;
		
		}
	}
	
	
	public static Exportator<OfertaData> exportador(){
		if(exportador == null){
			exportador = new Exportador();
		}
		
		return exportador;
	}
	
	
	
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
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
	public void setInicio(Date inicio) {
		this.inicio = new LocalTime(inicio.getTime());//Solo la hora
	}
	public void setInicio(int inicio) {
		this.inicio = new LocalTime(inicio, 0, 0);//Solo la hora
	}
	public LocalTime getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = new LocalTime(fin.getTime());//Solo la hora
	}
	public void setFin(int fin) {
		this.fin = new LocalTime(fin, 0, 0);//Solo la hora
	}
	
	

}
