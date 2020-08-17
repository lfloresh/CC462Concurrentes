
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException; 

public class Server {
    private static ArrayList<ClientHandler> clientList;
    private static Tarea tarea;
    private static Resultado resultado;
    private static ArrayList<String> listaPalabras;
    private static boolean encontrado;
    
    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        clientList = new ArrayList<ClientHandler>();
        listaPalabras = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        ServerSocket server = null;
        encontrado = false;
        //inicializador de la lista de arrays

              
        try {
            server = new ServerSocket(32000);
            server.setReuseAddress(true);
            
            System.out.println("Esperando clientes ...");
            //Escuchador de clientes
            ListenClients listen = new ListenClients(server, clientList);
            new Thread(listen).start();
            process_archive("src/main/java/listapalabras1.txt");

            //Interactuador
            
            String interactuador;
            System.out.println("Interactuando ...");
            
            while(true){
                System.out.println("Escribe algo:");
                interactuador = sc.nextLine(); 
                System.out.println("Ha escrito: " + interactuador);
                //enviar tareas
                
                 for(String palabra : listaPalabras){  
                     
                     tarea = new Tarea(6, palabra);
                     sendToAll(tarea);
                     do{
                        //verifica cada segundo si ha encontrado el resultado y esta confirmado por todos
                        Thread.sleep(1000);
                        
                     }while(encontrado == false && confirmar_resultado());
                     //reinicializar
                      encontrado = false;
                        for(ClientHandler client : clientList){
                            client.confirmado = false;
                          
                        }
                 }

            }
            
        } catch (IOException e) {
            //e.printStackTrace();
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
    
    public static void process_archive(String nameFile)    {
        
        try {
       File archivo = new File(nameFile);
       Scanner lector = new Scanner(archivo); 
        while (lector.hasNextLine()) {
        String data = lector.nextLine();
        listaPalabras.add(data);
      }
        lector.close();
        }catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
       
    }
    
    public static boolean confirmar_resultado(){
        for(ClientHandler client : clientList){
            if(client.confirmado == false){
                return false;
            }
        }
        return true;
    }
    
  
    public static void sendToAll(Object message){
        for(ClientHandler client : clientList){
            client.write(message);
        }
    }
    
    private static class ClientHandler implements Runnable {
        
        Socket clientSocket;
        int id;
        boolean confirmado = false;
        ObjectOutputStream outw;
        ObjectInputStream inw;
        
        public ClientHandler(Socket clientSocket, int id) throws IOException{
            this.clientSocket = clientSocket;
            this.id = id;        
            inw = new ObjectInputStream(clientSocket.getInputStream());
            outw = new ObjectOutputStream(clientSocket.getOutputStream());
        }
        

        @Override
        public void run() {
            Object recibido;
            try {                         
                    
                while(true){
                    //System.out.println("esperando mensaje:" ); 
                    recibido = inw.readObject();    
                    procesar_recibido(recibido);
                }
                
                
            } catch (IOException e) {
             

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
                  
        } 
        
        public void procesar_recibido(Object recibido){
            
            if(recibido.getClass() ==  String.class){
                String recibir_confirmado = (String)recibido;
                if(recibir_confirmado.contains("confirmado")){
                    confirmado = true;
                }
              System.out.println("Message: " + recibido);              
            }
            
            else if(recibido instanceof Resultado){
                resultado = (Resultado)recibido;
                resultado.mostrar();
                encontrado = true;
                sendToAll(resultado);
                //mato el proceso tarea
                //sha_tarea.continuar = false;                
                //evaluar resultado               
            }
            

        
        }
        
        
        public void write(Object message) {
            try{
                outw.writeObject(message);
            }
            catch(IOException e){ e.printStackTrace(); }
    }

        
    }
    
    private static class ListenClients implements Runnable {
        
          //lista de handlers
        ArrayList<ClientHandler> clientList;
       
        private int counter = 0;
        private final ServerSocket server;
        
        public ListenClients(ServerSocket server,  ArrayList<ClientHandler> clientList ) {
            //this.clientSocket = socket;
            this.server = server;
            this.clientList = clientList;
            
        }
 
        @Override
        public void run() {
            
           try {
                 while (true) {
                Socket client;
                     
                client = server.accept();
                                  
                System.out.println("Nuevo cliente conectado "+ counter + "  "+ client.getInetAddress().getHostAddress());
                clientList.add(new ClientHandler(client, counter));
               
                
                // The background thread will handle each client separately
                new Thread(clientList.get(clientList.size() - 1)).start();
                
                //enviar id
                clientList.get(clientList.size() - 1).write("id "+clientList.get(clientList.size() - 1).id);
                
                counter++;
            
             }
                } catch (IOException ex) {
                      
                     }               
  
        }
           
   }
}
