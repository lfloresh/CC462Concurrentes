
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    
    
    public static void main(String[] args) {
        Queue<String> cola=new LinkedList();
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
        private final Queue<String> cola;
        public ClientHandler(Socket socket,Queue<String> cola ) {
            this.clientSocket = socket;
            this.cola = cola;
        }
 
        @Override
        public void run() {
            String recibido = "", enviado = "";
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
                   procesar_recibido(recibido, cola, outw);

                   recibido = "";

                   cbuf = new char[512];
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
        
    public void procesar_recibido(String recibido, Queue<String> cola, OutputStreamWriter outw) throws IOException{
        if (recibido.contains("productor")){
            String capturar = recibido.substring(10, recibido.length());
            cola.offer(capturar);

        } else if(recibido.contains("consumidor")){
            //String capturar = recibido;
            //System.out.println(capturar);
            if(cola.peek()!=null){
                outw.write(cola.poll().toCharArray());
                outw.flush();
                //System.out.println(cola.peek());//Muestra el nuevo Frente
            } else{
                outw.write("No hay mas mensajes en la cola!");
                outw.flush();
            }
            
        
        }
        
    }
    }
}