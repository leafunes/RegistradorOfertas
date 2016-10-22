package proc;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class OfertaData {
	
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
	public LocalTime getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = new LocalTime(fin.getTime());//Solo la hora
	}
	
	

}
