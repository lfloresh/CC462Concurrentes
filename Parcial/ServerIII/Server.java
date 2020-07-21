/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MenTaLisT
 */
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    
    
    public static void main(String[] args) {
        
        ServerSocket server = null;
        //inicializador de la lista de arrays
        List<Queue<String>> cola= new ArrayList<Queue<String>>(100);
        for (int i = 0; i < 100; ++i) {
                cola.add(new LinkedList<String>());
        }
        try {
            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            // The main thread is just accepting new connections
            System.out.println("Esperando clientes ...");
            while (true) {
                Socket client = server.accept();
                
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());

                ClientHandler clientSock = new ClientHandler(client, cola);
 
                // The background thread will handle each client separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
    private static class ClientHandler implements Runnable {
        
        private final Socket clientSocket;
        private final List<Queue<String>> cola;

        
        public ClientHandler(Socket socket,List<Queue<String>> cola ) {
            this.clientSocket = socket;
            this.cola = cola;
        }
 
        @Override
        public void run() {
            String recibido = "";
            try {
                OutputStreamWriter outw = new OutputStreamWriter(clientSocket.getOutputStream(), "UTF8");
                InputStreamReader inw = new InputStreamReader(clientSocket.getInputStream(), "UTF8");
                char[] cbuf = new char[512];
                while (true) {
                   
                   inw.read(cbuf);
                   for (char c : cbuf) {
                       recibido += c;
                       if (c == 00) {
                           break;
                       }
                   }
                  
                   procesar_recibido(recibido, cola, outw, inw);

                   recibido = "";

                   cbuf = new char[512];
        }
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
        
    public void procesar_recibido(String recibido, List<Queue<String>> cola, OutputStreamWriter outw, InputStreamReader inw) throws IOException{
        
        char[] cbuf2 = new char[512];
        //productor id
        String recibido2 = "";
        if (recibido.contains("productor ")){
            
            String identificador = recibido.substring(10, recibido.length());
            //capturar id
            int id = Integer.parseInt(identificador.trim());
            inw.read(cbuf2);
            for (char c : cbuf2) {
                recibido2 += c;
                if (c == 00) {
                        break;
                }
            }
            
            cola.get(id).offer(recibido2);
            
            recibido2 = "";


        } else if(recibido.contains("consumidor")){
            
            String identificador_consumidor = recibido.substring(11, recibido.length());
            int id_capturado = Integer.parseInt(identificador_consumidor.trim());

            while (cola.get(id_capturado).peek() != null){
                outw.write(cola.get(id_capturado).poll().toCharArray());
                outw.flush();
                //espera recibir confirmacion
                inw.read(cbuf2);     
               }
            outw.write("fincola".toCharArray());
            outw.flush();
          } else if(recibido.contains("consumir ")){
              
              String capturar_id = recibido.substring(9, recibido.length());
        
              int id_capturado2 = Integer.parseInt(capturar_id.trim());
              if (cola.get(id_capturado2).peek() != null){
                  outw.write(cola.get(id_capturado2).poll());
                  outw.flush();

              }else {
                  outw.write("No hay mas elementos en la cola!");
                  outw.flush();
                }
                  
          }
  

        }
        
    }
   }

