package traylifx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.swing.JOptionPane;

public class ConectorHTTPS {
	
    public static String get(String url, String apikey) throws IOException {
		try {
			URL u = new URL(url);
	    	HttpURLConnection uc = (HttpURLConnection) u.openConnection();
	    	uc.setRequestProperty("Authorization", "Bearer " + apikey);
	    	
	    	InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
	        BufferedReader bf = new BufferedReader(inputStreamReader);
	        String data = "";
	        while (bf.ready()) {
	        	data += bf.readLine();
	        }
	    	return data;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
    }
    
    public static String post(String url, String apikey) {
		try {
			URL u = new URL(url);
			HttpURLConnection uc = (HttpURLConnection) u.openConnection();
	    	uc.setRequestProperty("Authorization", "Bearer " + apikey);
	    	
	    	uc.setRequestMethod("POST");
	        uc.setRequestProperty("Authorization", "Bearer " + apikey);
	        
	        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
	        BufferedReader bf = new BufferedReader(inputStreamReader);
	        
	        String data = "";
	        while (bf.ready()) {
	        	data += bf.readLine();
	        }
	        return data;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    
    
}
    
