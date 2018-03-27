package traylifx;

import org.json.JSONObject;

public class Lampara {
	private String label;
	
	public Lampara(JSONObject datos) {
		this.label = datos.getString("label");
	}
	
	public Lampara(String nombre) {
		this.label = nombre;
	}
	
	public String obtenerLabel() {
		return this.label;
	}
}
