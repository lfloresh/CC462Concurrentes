import sys
import socket as sk

host = "13.59.160.76"
port = 32000

sCliente =  sk.socket()
sCliente.connect((host, port))
print("Conectado")

while True: 
    inp = input("Texto para enviar: ")
    salida = "productor "+inp
    salida = salida.encode("UTF8")
    lene = sCliente.send(salida)
    if inp == "exit":
        break

sCliente.close()
print("Terminado")