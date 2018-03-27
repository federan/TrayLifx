package traylifx;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

public class ConectorLifx {
	
	private static String URL_BASE = "https://api.lifx.com/v1/lights/";

	public static ArrayList<Lampara> obtenerLamparas(String apikey) throws IOException{
		String data = ConectorHTTPS.get("https://api.lifx.com/v1/lights/all", apikey);
		data = "{\"lista\": "+data+"}";
		JSONObject l = new JSONObject(data);
		
		ArrayList<Lampara> lamparas = new ArrayList<Lampara>();

		try {
			for (int i = 0; i<l.getJSONArray("lista").length(); i++) {
				lamparas.add(new Lampara(l.getJSONArray("lista").getJSONObject(i)));
			}
		} catch (JSONException e) {
			JOptionPane.showMessageDialog(null, "Cannot get information from LIFX Server");
		}
		
		return lamparas;
	}
	
	public static String alternarLampara(String label, String apikey) {
		String resultado = ConectorHTTPS.post(URL_BASE+"label:"+label+"/toggle", apikey);
		return resultado;
	}
	
}
