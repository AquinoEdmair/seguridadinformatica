/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridadinformatica.pkg1;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.filechooser.FileSystemView;
import org.json.simple.JSONObject;

/**
 *
 * @author Edmair
 */
public class SeguridadInformatica1 {

    public static JSONObject obtieneDatos(){
        
        JSONObject obj = new JSONObject();
            
        obj.put("direccion_mac", obtieneMac());
        obj.put("unidades_disco", obtieneDiscos().toString());
        obj.put("fecha_hora", obtieneFecha());
        obj.put("nombre_equipo", obtieneNombrePC());
        obj.put("sistema_operativo", System.getProperty("os.name"));
        obj.put("procesos", obtieneProcesos());
        
        return  obj;
        
    }

    private static String obtieneMac() {
        InetAddress ip;
	try {
		ip = InetAddress.getLocalHost();

		NetworkInterface network = NetworkInterface.getByInetAddress(ip);

		byte[] mac = network.getHardwareAddress();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
		}
		
                return sb.toString();

	} catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
	}
        
        return "";
    }

    private static ArrayList obtieneDiscos() {
        ArrayList discos = new ArrayList();
        File[] paths;
        FileSystemView fsv = FileSystemView.getFileSystemView();

        paths = File.listRoots();

        for(File path:paths)
        {
            discos.add(path + " " + fsv.getSystemTypeDescription(path));
        }
        
        return discos;
    }

    private static String obtieneFecha() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static String obtieneNombrePC() {
        String hostname = "Unknown";
        
        try{
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }catch (UnknownHostException ex){
            System.out.println("Hostname can not be resolved");
        }
        
        return hostname;
    }

    private static String obtieneProcesos() {        
        String procesos = "";
        
        try {
            Process p = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                procesos+=line+"\n";
            }
	} catch (Exception ex) {
            System.err.println(ex.getMessage());
	}

        return procesos;
    }
}
