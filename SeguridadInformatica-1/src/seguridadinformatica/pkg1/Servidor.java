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
import java.net.ServerSocket;
import java.net.Socket;
 
public class Servidor
{
 
    private static Socket socket;
 
    public static void main(String[] args)
    {
        try
        {
 
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Servidor iniciado en el puerto 25000");
 
            while(true)
            {
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String number = br.readLine();
                System.out.println("Mensaje recibido "+number);

 
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(SeguridadInformatica1.obtieneDatos().toString()+"\n");
                System.out.println("Mensaje enviado " + SeguridadInformatica1.obtieneDatos().toString()+"\n");
                bw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}