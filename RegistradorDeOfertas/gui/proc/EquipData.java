package proc;

import org.json.simple.JSONObject;

public class EquipData {
	
	public static class Exportador implements Exportator<EquipData>{

		@Override
		public EquipData clone(EquipData other) {
			throw new RuntimeException("No implementado");
		}

		@Override
		public EquipData fromJSON(JSONObject obj) {
			
			String nombre = (String) obj.get("nombre");
			
			EquipData ret = new EquipData(nombre);
			
			return ret;
		}

		@Override
		public JSONObject toJSON(EquipData obj) {
			JSONObject ret = new JSONObject();
			
			ret.put("nombre", obj.getNombre());
			
			return ret;
		}
		
	}
	
	private String nombre;
	
	public EquipData(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipData other = (EquipData) obj;
		
		
		if ((nombre == null && other.nombre != null) || !nombre.equals(other.nombre)) 
			return false;
			
		return true;
	}
	
	
	
	

}
