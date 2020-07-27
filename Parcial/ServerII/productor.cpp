#include <iostream>
#include <winsock2.h>
#include<string>
//-lws2_32
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
        addr.sin_addr.s_addr = inet_addr("13.59.160.76");
        addr.sin_family = AF_INET;
        addr.sin_port = htons(32000);
        connect(server, (SOCKADDR *)&addr, sizeof(addr));
        cout << "Conectado al Servidor!" << endl;
    }
    void Enviar()
    {   
        char buffer2[512];
        cout<<"Texto a enviar: ";
        cin.getline(buffer2, sizeof(buffer2));
        string enviar(buffer2);
        string total = "productor " + enviar;
        char buffertotal[512];
        strcpy(buffertotal, total.c_str());
        strncpy(buffer, buffertotal, sizeof(buffer) - 1);
        send(server, buffer, sizeof(buffer), 0);
        memset(buffer, 0, sizeof(buffer));
       
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