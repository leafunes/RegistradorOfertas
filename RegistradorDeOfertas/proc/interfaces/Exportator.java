package interfaces;

import org.json.simple.JSONObject;

public interface Exportator <T>{
	
	T clone(T other);
	
	T fromJSON(JSONObject obj);
	JSONObject toJSON(T obj);
	
}
