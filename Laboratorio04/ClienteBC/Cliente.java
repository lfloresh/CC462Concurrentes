import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Cliente {
    
        private static Tarea tarea;
        private static Resultado resultado;
        private static SHAone shaOne;
        private static ObjectOutputStream outw = null;
        private static ObjectInputStream inw = null;
        private static boolean continuar;
        private static SHA sha_tarea;
        private static int id;  
        
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        
        Socket socket = null;
        
        SHAone shaOne = new SHAone();
  
        continuar = true;
        socket = new Socket("localhost", 32000);
        //write to socket using ObjectOutputStream
        outw = new ObjectOutputStream(socket.getOutputStream());
        inw = new ObjectInputStream(socket.getInputStream());
            
            
            Listen listen = new Listen(socket);
            new Thread(listen).start();
            //read the server response message
            //frenar thread
            
            
        
    }
    
    
    
    private static class Listen implements Runnable{
        Socket socket;
        Listen(Socket socket) throws IOException{
         
        this.socket = socket;
        }
        @Override
        public void run() {
             try { 
             
                while(true){
                    //System.out.println("esperando mensaje:" ); 
                    Object message = inw.readObject();
                    procesar_recibido(message);                        

                }            
            
             } catch (IOException e) {
             

            } catch (ClassNotFoundException ex) {
               
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void procesar_recibido(Object recibido) throws IOException, InterruptedException{
           // Tarea tarea = new Tarea(0, "");
            //recibir identificador
            if(recibido instanceof String){
                //System.out.println("aca");
                String procesado = (String) recibido;
                if(procesado.contains("id")){
                    id = Integer.parseInt(procesado.substring(3, procesado.length()));
                }
              System.out.println("Message: " + id);   
              outw.writeObject("recibio el mensaje en el cliente");
              outw.flush();           
            }
            
            else if(recibido instanceof Tarea){
                
                tarea = (Tarea)recibido;
                System.out.println("Recibido tarea:" + tarea.mensaje + tarea.nzeros );
                sha_tarea = new SHA(tarea, resultado, continuar, shaOne);
                Thread calculo = new Thread(sha_tarea);
                calculo.start();
                //calculo.join();
                
            }else if(recibido instanceof Resultado){
                //mato el proceso tarea
                sha_tarea.continuar = false;
               // System.out.println("ha parado");
                resultado = (Resultado)recibido;
                resultado.mostrar();
                
                if(corroborar(resultado)){
                   outw.writeObject("confirmado por "+ id);
                   outw.flush();
                }
                //evaluo resultado
                

            }
        
    }
    
      public void write(Object message) {
            try{
                outw.writeObject(message);
            }
            catch(IOException e){ e.printStackTrace(); }
    }
      
      public boolean corroborar(Object resultado1){
          resultado = (Resultado)resultado1;
          shaOne = new SHAone();
          String z = resultado.palabra + String.valueOf(resultado.key);
          byte[] dataBuffer = (z).getBytes();
          String thedigest = shaOne.Encript(dataBuffer);
          int sumz = 0;
            for (int j = 0; j < tarea.nzeros; j++) {
                if (thedigest.charAt(j) == '0') {
                    sumz = sumz + 1;
                }
            }
           if(sumz == resultado.nzeros){
           return true;
           
           }else{
           return false;
           } 
           
      }
    
    
    
    }
    
    
    
    private static class SHA implements Runnable{
       Tarea tarea;
       Resultado resultado;
       boolean continuar;
       SHAone shaOne;
       int sumz = 0;
       
       public SHA(Tarea tarea, Resultado resultado, boolean continuar, SHAone shaOne){
           this.tarea = tarea;
           this.resultado = resultado;
           this.continuar = continuar;
           this.shaOne = shaOne;
       }

        @Override
        public void run() {
             long TInicio, TFin, tiempo;
             TInicio = System.currentTimeMillis();
             shaOne = new SHAone();
             
             while(continuar){
                int key = (int) (Math.random() * 10000000);
                String z = tarea.mensaje + String.valueOf(key);
                
                
                byte[] dataBuffer = (z).getBytes();
                String thedigest = shaOne.Encript(dataBuffer);
                
                //String thedigest = shaOne.sha1encrypt(z);
                sumz = 0;
                for (int j = 0; j < tarea.nzeros; j++) {
                    if (thedigest.charAt(j) == '0') {
                        sumz = sumz + 1;
                    }
                }
                if (sumz == tarea.nzeros) { 
                    try {
                        TFin = System.currentTimeMillis();
                        tiempo = TFin - TInicio;
                        //llenado de resultado
                        resultado = new Resultado(key,tiempo,id,tarea.nzeros,tarea.mensaje, thedigest);
                        System.out.println("encontrado");
                        resultado.mostrar();
                        outw.writeObject(resultado);
                        outw.flush();
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
             
             }
             
            
            //ejecutar tarea
        }
    }
    
}