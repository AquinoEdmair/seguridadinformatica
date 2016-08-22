/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridadinformatica.pkg1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
public class Cliente
{
 
    private static Socket socket;
 
    public static void main(String args[])
    {
        try
        {
            String host = "localhost";
            int port = 25000;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
 
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
  
            String sendMessage = "Mensaje de prueba \n";
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Mensaje enviado : "+sendMessage);
 
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);
            
            System.out.println("Dirección MAC: " + json.get("direccion_mac"));
            System.out.println("Unidades de disco: " + json.get("unidades_disco"));
            System.out.println("Nombre Equipo: " + json.get("nombre_equipo"));
            System.out.println("Sistema Operativo: " + json.get("sistema_operativo"));
            System.out.println("Procesos: " + json.get("procesos"));

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
    
