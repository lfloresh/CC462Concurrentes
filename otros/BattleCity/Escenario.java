

import java.util.Scanner;
import java.util.concurrent.locks.Lock;


public class Servidor50 {
    //int ids = 0;
    char ids[] = new char[3];
    char [] simbols = {'1', '2', '3'};
    TCPServer50 mTcpServer;
    Scanner sc;
    Escenario escena = new Escenario();
    private Lock cierreRed;

    public static void main(String[] args) {
        Servidor50 objser = new Servidor50();
        objser.iniciar();
    }

    void iniciar() {
        new Thread(
                new Runnable() {
           @Override
            public void run() {
              
                mTcpServer = new TCPServer50(
                        new TCPServer50.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                    
                       synchronized(this){
                            ServidorRecibe(message);
                    }
                    }
                }
                );
                mTcpServer.run();
                
            }
        }
        ).start();
        //-----------------
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor bandera 01");
        //ServidorEnvia(String.valueOf(mTcpServer nrcli-1));
        
        escena.mostrar();
        //Escenario escena = new Escenario();
            
            
        //\0 es el vacio de char
        
       
        System.out.println("Servidor bandera 02");

    }
    int contarcliente = 0;
    int rptacli[] = new int[20];
    int sumclient = 0;
    int posx, posy;
    void ServidorRecibe(String llego) {
        System.out.println("SERVIDOR40 El mensaje:" + llego);
        
        
        if (llego.length() == 16) {
                //recibe:   id x y xa ya
                // 00 00 000 00 000
               int id2 = Integer.parseInt(llego.substring(0, 2));

               
               escena.limpiar_campo(Integer.parseInt(llego.substring(10, 12)), 
                       Integer.parseInt(llego.substring(13, 16)));
               escena.jugadores[id2].x = Integer.parseInt(llego.substring(3, 5));
               escena.jugadores[id2].y = Integer.parseInt(llego.substring(6, 9));
               escena.pintar_pos_jugador(id2);
               escena.mostrar();
               //enviar a los demas jugadores
               
           
                for (int j = 0; j<3;j++){
                   if(j != Integer.parseInt(llego.substring(1, 2)) & ids[j] !='\0'){
                       
                       ServidorEnvia(llego, j);
                   }
               }
            
            } else if(llego.contains("id") & llego.length() == 11){
                //id i posx posy
                //id 0 00 000
                escena.insertar_jugador(Integer.parseInt(llego.substring(5,7)), 
                        Integer.parseInt(llego.substring(8,11)), 
                        Integer.parseInt(llego.substring(3,4)), simbols[Integer.parseInt(llego.substring(3, 4))]);
                escena.mostrar();
                //conjunto de ids
                ids[Integer.parseInt(llego.substring(3,4))] = llego.substring(3,4).charAt(0);
                
                //enviar a los demas
                 for (int i = 0; i<3;i++){
                   if(i != Integer.parseInt(llego.substring(3, 4)) & ids[i] !='\0'){
                       ServidorEnvia(llego, i);
                   }
               }
                //enviar las posiciones al id  de los actuales hilos disponibles
                //excepto el mismo.
                String enviar;
                for (int j = 0; j<3;j++){
                   if(j != Integer.parseInt(llego.substring(3, 4)) & ids[j] !='\0'){
                       enviar = format(j,escena.jugadores[j].x,escena.jugadores[j].y);
                       ServidorEnvia(enviar, Integer.parseInt(llego.substring(3, 4)));
                   }
               }
                
            }
        }
   

    void ServidorEnvia(String envia, int id) {//El servidor tiene texto de envio
        if (envia != null) {
            System.out.println("Soy Server y envio" + envia);
            //cambiado
                    mTcpServer.sendMessageTCPServer(envia, id + 1);
                
            } else {
                System.out.println("NO TIENE ENVIO!!!");
            }
        
        
        }
    
        String format(int id, int x, int y){
        String tercer_parametro, segundo_parametro, string_envio;
        if (Integer.toString(y).length() == 2){
                tercer_parametro = "0"+String.valueOf(y);
            }
        else if (Integer.toString(y).length() == 1){
            tercer_parametro = "00"+String.valueOf(y);
        }
        else{
            tercer_parametro = String.valueOf(y);
        }
        
        if (Integer.toString(x).length() == 1){
                segundo_parametro = "0"+String.valueOf(x);
            }
        else{
            segundo_parametro = String.valueOf(x);
        }
        
        
        //"id 0 00 000"
           string_envio = "id "+String.valueOf(id) +" "+segundo_parametro+" " + tercer_parametro; 
           return string_envio;
    }
    }
