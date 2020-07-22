import sys
import socket as sk

def capturar_opcion():
    while True:
        try:
            opcion = input("Consumir(1) o Enviar(2): ")
            x = int(opcion)
            
        except ValueError:
            print("El ingreso no es valido")
        else:
            if opcion == str(1):
                enviar = "consumidor consumir "
                break
            if opcion == str(2):
                enviar = "consumidor envia "
                break
            else:
                print("El ingreso no es valido")
    return enviar


host = "127.0.0.1"
port = 32000



#identificador del consumidor
while True:
    try:
        identificador = input("Ingrese un numero identificador: ")
        x = int(identificador)
    except ValueError or identificador >= 100:
        print("El ingreso no es valido")
    else:
        break

sCliente =  sk.socket()
sCliente.connect((host, port))
print("Conectado")

salida = "consumidor inicio " + identificador
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
    enviar = capturar_opcion() + identificador

    if "consumidor consumir" in enviar:
        enviable = enviar.encode("UTF8")
        env = sCliente.send(enviable)   
        elemento = sCliente.recv(512)
        elemento = elemento.decode("UTF8")
        print("Texto recibido: ", elemento)

    if "consumidor envia" in enviar:
        enviable = enviar.encode("UTF8")
        env = sCliente.send(enviable)
        #pedir texto a enviar
        texto = input("Ingrese el texto a enviar: ")
        texto = texto.encode("UTF8")
        conf = sCliente.send(texto)

          
sCliente.close()
print("Terminado")
