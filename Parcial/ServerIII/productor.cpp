
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
    void Enviar()
    {   
        int id;
        int return_value = 0;
        while (!return_value) {
            printf("A que consumidor enviar?:");
            return_value = scanf("%d", &id);
            while(getchar() !='\n') {
                continue;
            }
        }
        
        char aux[512];
        string enviar;
        string identificador = to_string(id);
        //productor y el identificador a quien va el mensaje
        string identificador_consumidor = "productor " + identificador;
        strcpy(aux, identificador_consumidor.c_str());
        strncpy(buffer, aux, sizeof(buffer) - 1);
        send(server, buffer, sizeof(buffer), 0);
        memset(buffer, 0, sizeof(buffer));
        memset(aux, 0, sizeof(buffer));

        cout<<"Texto a enviar: ";
        cin>>enviar;
        strcpy(aux, enviar.c_str());
        strncpy(buffer, aux, sizeof(buffer) - 1);
        send(server, buffer, sizeof(buffer), 0);
        memset(buffer, 0, sizeof(buffer));
        memset(aux, 0, sizeof(buffer));
       
    }

   
};

int main()
{
    Client *Cliente = new Client();
    while(true)
    {
        Cliente->Enviar();
    
    }
}