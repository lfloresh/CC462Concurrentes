import sys
import socket as sk

host = "13.59.160.76"
port = 32000

sCliente =  sk.socket()
sCliente.connect((host, port))
print("Conectado")

salida = "consumidor"
salida = salida.encode("UTF8")
#enviar
lene = sCliente.send(salida)
contador = 0
while True:

    #espera recibir
    ins = sCliente.recv(512)
    insd = ins.decode("UTF8")
    if insd.strip() != "fincola":
        print("Texto recibido: ", insd)
        #enviar confimacion
        confirmacion = "recibido"
        confirmacion = confirmacion.encode("UTF8")
        conf = sCliente.send(confirmacion)
        contador += 1
    else: 
        break

while True:
    inp = input("Enter para recibir elemento: ")
    enviar = "consumir " + str(contador)
    enviar = enviar.encode("UTF8")
    env = sCliente.send(enviar)
    elemento = sCliente.recv(512)
    elemento = elemento.decode("UTF8")
    print("Texto recibido: ", elemento)
    if elemento.strip() != "No hay mas elementos en la cola!":
        contador += 1

sCliente.close()
print("Terminado")