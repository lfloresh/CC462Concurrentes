import sys
import socket as sk

host = "13.59.160.76"
port = 32000

#identificador del consumidor
while True:
    try:
        identificador = input("Ingrese un numero identificador: ")
        x = int(identificador)
    except ValueError:
        print("El ingreso no es valido")
    else:
        break

sCliente =  sk.socket()
sCliente.connect((host, port))
print("Conectado")

salida = "consumidor " + identificador
salida = salida.encode("UTF8")
#enviar

lene = sCliente.send(salida)


while True:

    #espera recibir
    ins = sCliente.recv(512)
    insd = ins.decode("UTF8")
    if insd.strip() != "fincola":
        print("Texto recibido: ", insd)
        #enviar confirmacion
        confirmacion = "recibido"
        confirmacion = confirmacion.encode("UTF8")
        conf = sCliente.send(confirmacion) 
    else: 
        break

while True:
    inp = input("Enter para recibir elemento: ")
    enviar = "consumir " + identificador
    enviar = enviar.encode("UTF8")
    env = sCliente.send(enviar)
    elemento = sCliente.recv(512)
    elemento = elemento.decode("UTF8")
    print("Texto recibido: ", elemento)
          

sCliente.close()
print("Terminado")