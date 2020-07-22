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
        //inicializador de la lista de lista de colas

        List<List<Queue<String>>> lista_cola = new ArrayList<>(100);
        for (int j = 0; j < 100; ++j) {
                lista_cola.add(new ArrayList<Queue<String>>(2));
        }
        
        for (int j = 0; j < 100; ++j) {
            for (int i = 0; i < 2; ++i)
                lista_cola.get(j).add(new LinkedList<String>());             
        }
        /* prueba
       lista_cola.get(1).get(1).offer("Letra");
       System.out.println(lista_cola.get(1).get(1).poll());
         */      
        try {
            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            // The main thread is just accepting new connections
            System.out.println("Esperando clientes ...");
            while (true) {
                Socket client = server.accept();
                
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());

                ClientHandler clientSock = new ClientHandler(client, lista_cola);
 
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
        private final List<List<Queue<String>>> lista_cola;

        
        public ClientHandler(Socket socket,List<List<Queue<String>>> lista_cola ) {
            this.clientSocket = socket;
            this.lista_cola = lista_cola;
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
                  
                   procesar_recibido(recibido, lista_cola, outw, inw);

                   recibido = "";

                   cbuf = new char[512];
        }
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
        
    public void procesar_recibido(String recibido, List<List<Queue<String>>> lista_cola, OutputStreamWriter outw, InputStreamReader inw) throws IOException{
        
        char[] cbuf2 = new char[512];
        //productor id
        String recibido2 = "";
        if (recibido.contains("productor envia")){
            
            //recibe el id de la cola de comunicacion
            String identificador = recibido.substring(16, recibido.length());
            int id = Integer.parseInt(identificador.trim());
            //recibe el texto
            inw.read(cbuf2);
            for (char c : cbuf2) {
                recibido2 += c;
                if (c == 00) {
                        break;
                }
            }
            //lo envia a la cola 0 del par id
            lista_cola.get(id).get(0).offer(recibido2);
            
            recibido2 = "";


        } else if(recibido.contains("consumidor envia")){
            //recibe el id de la cola de comunicacion
            String identificador_consumidor = recibido.substring(17, recibido.length());
            int id = Integer.parseInt(identificador_consumidor.trim());
            inw.read(cbuf2);
            for (char c : cbuf2) {
                recibido2 += c;
                if (c == 00) {
                        break;
                }
            }
            //lo envia a la cola 1 del par id
            lista_cola.get(id).get(1).offer(recibido2);
            recibido2 = "";
         
          } else if(recibido.contains("consumidor consumir")){
              
              String capturar_id = recibido.substring(20, recibido.length());
              int id = Integer.parseInt(capturar_id.trim());
              //consume de la cola de envio del productor
              if (lista_cola.get(id).get(0).peek() != null){
                  outw.write(lista_cola.get(id).get(0).poll());
                  outw.flush();
              }else {
                  outw.write("No hay mas elementos en la cola!");
                  outw.flush();
                }
                  
          } else if (recibido.contains("productor consumir ")){
              
              String capturar_id = recibido.substring(19, recibido.length());
              int id = Integer.parseInt(capturar_id.trim());
              //consume de la cola de envio del consumidor
              if (lista_cola.get(id).get(1).peek() != null){
                  outw.write(lista_cola.get(id).get(1).poll());
                  outw.flush();

              }else {
                  outw.write("No hay mas elementos en la cola!");
                  outw.flush();
                }
          
          } else if (recibido.contains("productor inicio ")){
              //contiene el id
              String identificador_consumidor = recibido.substring(17, recibido.length());
              int id = Integer.parseInt(identificador_consumidor.trim());
              //System.out.println(id);
              while (lista_cola.get(id).get(1).peek() != null){
                  outw.write(lista_cola.get(id).get(1).poll().toCharArray());
                  outw.flush();
                  //espera recibir confirmacion
                  inw.read(cbuf2);     
                 }
                outw.write("fincola".toCharArray());
                outw.flush();

          } else if (recibido.contains("consumidor inicio ")){
              
              String identificador_consumidor = recibido.substring(18, recibido.length());
              int id = Integer.parseInt(identificador_consumidor.trim());
                           
              while (lista_cola.get(id).get(0).peek() != null){
                  outw.write(lista_cola.get(id).get(0).poll().toCharArray());
                  outw.flush();
                  //espera recibir confirmacion
                  inw.read(cbuf2);     
                 }
              outw.write("fincola".toCharArray());
              outw.flush();

          }
  

        }
        
    }
   }

