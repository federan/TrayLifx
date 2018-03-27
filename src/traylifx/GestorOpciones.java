package traylifx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GestorOpciones {
	
	private ArrayList<String> opciones = new ArrayList<String>();
	private File direccion;
	
	public GestorOpciones(String dir) {
		leerOpciones(dir);
		this.direccion = new File(dir);
		this.escribirOpciones();
	}
	
	
	public void leerOpciones(String dir) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(dir));
			while (reader.ready()) {
				this.opciones.add(reader.readLine());
			}
			reader.close();
		} catch (FileNotFoundException e) {
			String res = JOptionPane.showInputDialog("Please, enter your API key");
			
			if (res == null) {
				System.exit(0);
			}
			
			while (res.length() < 64) {
				JOptionPane.showMessageDialog(null, "The api key was invalid");
				res = JOptionPane.showInputDialog("Please, enter your API key");
			}
			
			this.opciones.add(res);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void agregarOculta(Lampara l) {
		this.opciones.add(l.obtenerLabel());
		this.escribirOpciones();
	}
	
	public void escribirOpciones() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(this.direccion);
			for (String s : this.opciones) {
				pw.println(s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
	public String obtenerApikey(){
		return this.opciones.get(0);
	}
	
	public boolean contiene(Lampara l) {
		return this.opciones.contains(l.obtenerLabel());
	}
	
	public void borrar() {
		this.direccion.delete();
	}
	
	public void reset() {
		String aux = this.obtenerApikey();
		this.opciones = new ArrayList<String>();
		this.opciones.add(aux);
		this.escribirOpciones();
	}
	
	
}
