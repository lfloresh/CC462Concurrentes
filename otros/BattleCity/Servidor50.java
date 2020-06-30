

import java.util.Scanner;
import java.util.concurrent.locks.Lock;


public class Servidor50 {
    //int ids = 0;
    char ids[] = new char[3];
    
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
            System.out.println("imprimiendo prueba");
            
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
               int id = Integer.parseInt(llego.substring(0, 2));
               int x = Integer.parseInt(llego.substring(3, 5));
               int y = Integer.parseInt(llego.substring(6, 9));
               int xa = Integer.parseInt(llego.substring(10, 12));
               int ya = Integer.parseInt(llego.substring(13, 16));
               escena.limpiar_campo(xa, ya);
               escena.pintar_campo(x, y);
               escena.mostrar();
               
               //enviar a los demas excepto al que ha enviado el msj
               
               for (int i = 0; i<3;i++){
                   if(i != Integer.parseInt(llego.substring(0, 2)) & ids[i] !='\0'){
                       ServidorEnvia(llego, i);
                   }
               }
            
            } else if(llego.contains("id")){
                //id i posx posy
                //id 0 00 000
                escena.insertar_jugador(Integer.parseInt(llego.substring(5,7)), 
                        Integer.parseInt(llego.substring(8,11)), 
                        Integer.parseInt(llego.substring(3,4)));
                escena.mostrar();
                //conjunto de ids
                ids[Integer.parseInt(llego.substring(3,4))] = llego.substring(3,4).charAt(0);
                //enviar a los demas
                for (int i = 0; i<3;i++){
                   if(i != Integer.parseInt(llego.substring(3, 4)) & ids[i] !='\0'){
                       ServidorEnvia(llego, i);
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
    }
    

    

