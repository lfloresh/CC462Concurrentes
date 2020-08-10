
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    
    
    public static void main(String[] args) {
        LinkedList<String> cola=new LinkedList<String>();
        ServerSocket server = null;
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
        private final LinkedList<String> cola;
        public ClientHandler(Socket socket,LinkedList<String> cola ) {
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
        
    public void procesar_recibido(String recibido, LinkedList<String> cola, OutputStreamWriter outw, InputStreamReader inw) throws IOException{
       
        char[] cbuf2 = new char[512];
        if (recibido.contains("productor")){
            String capturar = recibido.substring(10, recibido.length());
            cola.add(capturar);
            for (String s: cola){
            System.out.println(s);
            }
            
        //cuando se conecta por primera vez
        } else if(recibido.contains("consumidor")){

            for (String s: cola){
                outw.write(s.toCharArray());
                outw.flush();
                //espera recibir confirmacion
                inw.read(cbuf2);     
               }
            outw.write("fincola".toCharArray());
            outw.flush();
          } else if(recibido.contains("consumir ")){
              //System.out.println(recibido);
              String capturar = recibido.substring(9, recibido.length());
        
              int elemento = Integer.parseInt(capturar.trim());
              if (elemento < cola.size()){
                  outw.write(cola.get(elemento));
                  outw.flush();

              }else {
                  outw.write("No hay mas elementos en la cola!");
                  outw.flush();
                }
          //consumir 5          
          }
            //fin de elementos enviados de la cola
            

        }
        
    }
   }

