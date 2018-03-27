package traylifx;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GestorIconos {
	
	private final Image imgLamparita = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bulb.gif"));
	private String apikey;
	private ArrayList<TrayIcon> listaLamparas;
	private GestorOpciones go;
	
	public GestorIconos(GestorOpciones go) {
		if (SystemTray.isSupported()) {
			this.go = go;
	        this.apikey = go.obtenerApikey();
	        ArrayList<Lampara> a = null;
			try {
				a = ConectorLifx.obtenerLamparas(this.apikey);
		        this.listaLamparas = new ArrayList<TrayIcon>();

		        for (Lampara l : a) {
		        	if (!this.go.contiene(l)) {
		        		agregarLampara(l);
		        	}
		        }

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Couldn't connect to LIFX server");
				//go.borrar();
			}
	         
		} else {

			JOptionPane.showMessageDialog(null, "SystemTray not supported in this OS");
			
		}

	 }

	
	public void agregarLampara(Lampara l) {

		ArrayList<TrayIcon> lL = this.listaLamparas;
		
        PopupMenu popup = new PopupMenu();

		TrayIcon lampara = new TrayIcon(this.imgLamparita, l.obtenerLabel(), popup);

		try {
			SystemTray.getSystemTray().add(lampara);
			
			ActionListener alIcono = new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	           	 	ConectorLifx.alternarLampara(l.obtenerLabel(), apikey);
	            }
	        };
	        ActionListener alOcultar = new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		SystemTray.getSystemTray().remove(lampara);
	        		go.agregarOculta(l);
	        	} 
	        };
	        ActionListener alCerrar = new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
		       		for (TrayIcon i : lL) {
		       			SystemTray.getSystemTray().remove(i);
		       		}
		       	} 
	        };
	        ActionListener alReset = new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
		       		go.reset();
		       		JOptionPane.showMessageDialog(null, "Configuration reseted.\n Press OK to close");
					System.exit(0);
		       	} 
	        };
	        
	        MenuItem miOcultar = new MenuItem("Hide");
	        MenuItem miCerrar = new MenuItem("Close");
	        MenuItem miReset = new MenuItem("Exit and Reset");
	        miOcultar.addActionListener(alOcultar);
	        miCerrar.addActionListener(alCerrar);
	        miReset.addActionListener(alReset);
	        popup.add(miReset);
	        popup.add(miCerrar);
	        popup.add(miOcultar);
			
			lampara.addActionListener(alIcono);
			
			this.listaLamparas.add(lampara);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		GestorOpciones go = new GestorOpciones(System.getProperty("user.home") + "/traylifx.txt");
		@SuppressWarnings("unused")
		GestorIconos g = new GestorIconos(go);

	}
	
}
