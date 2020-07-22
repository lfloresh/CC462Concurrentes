
//-lws2_32
#include <iostream>
#include <winsock2.h>
#include <string> 

using namespace std;

class Client{
public:
    WSADATA WSAData;
    SOCKET server;
    SOCKADDR_IN addr;
    char buffer[512];

    Client()
    {
        cout<<"Conectando al servidor..."<<endl<<endl;
        WSAStartup(MAKEWORD(2,0), &WSAData);
        server = socket(AF_INET, SOCK_STREAM, 0);
        addr.sin_addr.s_addr = inet_addr("127.0.0.1");
        addr.sin_family = AF_INET;
        addr.sin_port = htons(32000);
        connect(server, (SOCKADDR *)&addr, sizeof(addr));
        cout << "Conectado al Servidor!" << endl;
    }    
    
   string capturar_opcion(){
        int id;
        int opcion = 0;
        string enviar;
        do{
            printf("Consumir(1) o Enviar(2): ");
            scanf("%d", &opcion);
            while(getchar() !='\n') {
                continue;
            }
            if (opcion == 1){
                enviar = "productor consumir ";
                break;
            } else if (opcion == 2){
                enviar = "productor envia ";
                break;
            } else {

                printf("El ingreso no es valido\n");
            }
        } while (!opcion || opcion != 1 || opcion != 2);
        return enviar;

    }

    void Enviar()
    {   
        int id;
        int return_value = 0;
        do  {
            printf("Ingrese un numero identificador:");
            return_value = scanf("%d", &id);
            while(getchar() !='\n') {
                continue;
            }
        } while (!return_value || return_value > 100);
        
        char aux[512];
        string enviar;
        string identificador = to_string(id);
        //productor y el identificador a quien va el mensaje
        string identificador_consumidor = "productor inicio " + identificador;

        strcpy(aux, identificador_consumidor.c_str());
        strncpy(buffer, aux, sizeof(buffer) - 1);
        send(server, buffer, sizeof(buffer), 0);
        memset(buffer, 0, sizeof(buffer));
        memset(aux, 0, sizeof(buffer));

        string confirmacion;
        while (true){
            recv(server, aux, sizeof(aux), 0);
            if (strcmp(aux, "fincola") != 0) {
                cout << "Recibido: " << aux << endl;
                memset(aux, 0, sizeof(aux));
                confirmacion = "recibido";
                strcpy(aux, confirmacion.c_str());
                strncpy(buffer, aux, sizeof(buffer) - 1);
                send(server, buffer, sizeof(buffer), 0);
                memset(buffer, 0, sizeof(buffer));
                memset(aux, 0, sizeof(buffer));
            } else{
                break;
            }
            
        }

        while (true){
            string opcion = capturar_opcion();
            string enviable = opcion + identificador;
            
            if(opcion.compare("productor consumir ") == 0){

                strcpy(aux, enviable.c_str());
                strncpy(buffer, aux, sizeof(buffer) - 1);
                send(server, buffer, sizeof(buffer), 0);
                memset(buffer, 0, sizeof(buffer));
                memset(aux, 0, sizeof(buffer));
                recv(server, buffer, sizeof(buffer), 0);
                cout << "Recibido: " << buffer << endl;
                memset(buffer, 0, sizeof(buffer));

            }

            if(opcion.compare("productor envia ") == 0){

                strcpy(aux, enviable.c_str());
                strncpy(buffer, aux, sizeof(buffer) - 1);
                send(server, buffer, sizeof(buffer), 0);
                memset(buffer, 0, sizeof(buffer));
                memset(aux, 0, sizeof(buffer));
                cout << "Ingrese texto a enviar: ";
                cin>> enviar;
                strcpy(aux, enviar.c_str());
                strncpy(buffer, aux, sizeof(buffer) - 1);
                send(server, buffer, sizeof(buffer), 0);
                memset(buffer, 0, sizeof(buffer));
                memset(aux, 0, sizeof(buffer));

            }



        }

       
    }


   
};



int main()
{
   Client *Cliente = new Client();

        Cliente->Enviar();
      
    

}