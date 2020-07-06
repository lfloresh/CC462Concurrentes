
import java.util.Scanner;


class Cliente50 {
    int id;
    char ids[] = new char[3];
    public int sum[] = new int[40];
    TCPClient50 mTcpClient;
    Scanner sc;
    Escenario escena = new Escenario();

    public static void main(String[] args) {
        Cliente50 objcli = new Cliente50();
        objcli.iniciar();
    }

    void iniciar() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                mTcpClient = new TCPClient50("192.168.1.131", new TCPClient50.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        ClienteRecibe(message);
                    }
                });
                mTcpClient.run();
            }
        }).start();
        // ---------------------------

        String salir = "n";
        sc = new Scanner(System.in);
        // System.out.println("Cliente bandera 01");

        String envio = "00 00 000 00 000";


        int xa;
        int ya;

        // id x y xa ya
        while (true) {
            char input = sc.next().charAt(0);
            // actualizar escena
            if (input == 'w') {

                if (!escena.esta_ocupado(escena.jugadores[id].x - 2, escena.jugadores[id].y)) {
                    xa = escena.jugadores[id].x;
                    ya = escena.jugadores[id].y;
                    escena.limpiar_campo(xa, ya);
                    escena.jugadores[id].up();
                    envio = format(id, escena.jugadores[id].x, escena.jugadores[id].y, xa, ya);
                    ClienteEnvia(envio);
                    escena.actualizar_pos_jugador(id);

                    // id x y



                }

            } else if (input == 's') {

                if (!escena.esta_ocupado(escena.jugadores[id].x + 2, escena.jugadores[id].y)) {
                    xa = escena.jugadores[id].x;
                    ya = escena.jugadores[id].y;
                    escena.limpiar_campo(xa, ya);
                    escena.jugadores[id].down();
                    envio = format(id, escena.jugadores[id].x, escena.jugadores[id].y, xa, ya);
                    ClienteEnvia(envio);
                    escena.actualizar_pos_jugador(id);

                }

            } else if (input == 'a') {

                if (!escena.esta_ocupado(escena.jugadores[id].x, escena.jugadores[id].y - 2)) {
                    xa = escena.jugadores[id].x;
                    ya = escena.jugadores[id].y;
                    escena.limpiar_campo(xa, ya);
                    escena.jugadores[id].left();
                    envio = format(id, escena.jugadores[id].x, escena.jugadores[id].y, xa, ya);
                    ClienteEnvia(envio);
                    escena.actualizar_pos_jugador(id);

                }

            } else if (input == 'd') {

                if (!escena.esta_ocupado(escena.jugadores[id].x, escena.jugadores[id].y + 2)) {
                    xa = escena.jugadores[id].x;
                    ya = escena.jugadores[id].y;
                    escena.limpiar_campo(xa, ya);
                    escena.jugadores[id].right();
                    envio = format(id, escena.jugadores[id].x, escena.jugadores[id].y, xa, ya);
                    ClienteEnvia(envio);
                    escena.actualizar_pos_jugador(id);

                }

            }
            escena.mostrar();

        }
        // System.out.println("Cliente bandera 02");

    }

    void ClienteRecibe(String llego) {
        System.out.println("CLINTE50 El mensaje::" + llego);

        // crea el propio elemento
        if (llego.contains("id") & llego.length() == 4) {

            id = Integer.parseInt(llego.substring(3, 4));

            if (id == 0) {
                ClienteEnvia(llego + " 05 " + "005");
                escena.insertar_jugador(5, 5, id);
            } else if (id == 1) {
                ClienteEnvia(llego + " 05 " + "040");
                escena.insertar_jugador(5, 40, id);
            } else if (id == 2) {
                ClienteEnvia(llego + " 05 " + "080");
                escena.insertar_jugador(5, 80, id);
            }
            // conjunto de ids
            ids[Integer.parseInt(llego.substring(3, 4))] = llego.substring(3, 4).charAt(0);
            escena.mostrar();

        }

        // cuando se envian mas elementos

        else if (llego.length() == 11) {

            if (Integer.parseInt(llego.substring(3, 4)) != id) {
                escena.insertar_jugador(Integer.parseInt(llego.substring(5, 7)),
                        Integer.parseInt(llego.substring(8, 11)),
                        Integer.parseInt(llego.substring(3, 4)));;

                // conjunto de ids
                ids[Integer.parseInt(llego.substring(3, 4))] = llego.substring(3, 4).charAt(0);
                escena.mostrar();
            }

        } else if (llego.length() == 16) {

            int id2 = Integer.parseInt(llego.substring(0, 2));
            int x = Integer.parseInt(llego.substring(3, 5));
            int y = Integer.parseInt(llego.substring(6, 9));
            int xa = Integer.parseInt(llego.substring(10, 12));
            int ya = Integer.parseInt(llego.substring(13, 16));
            escena.limpiar_campo(xa, ya);
            escena.pintar_campo(x, y);
            escena.mostrar();

        }

    }



    void ClienteEnvia(String envia) {
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }


    String format(int id, int x, int y, int xa, int ya) {
        String tercer_parametro, segundo_parametro, string_envio;
        String cuarto_parametro, quinto_parametro;
        if (Integer.toString(y).length() == 2) {
            tercer_parametro = "0" + String.valueOf(y);
        } else if (Integer.toString(y).length() == 1) {
            tercer_parametro = "00" + String.valueOf(y);
        } else {
            tercer_parametro = String.valueOf(y);
        }

        if (Integer.toString(x).length() == 1) {
            segundo_parametro = "0" + String.valueOf(x);
        } else {
            segundo_parametro = String.valueOf(x);
        }

        if (Integer.toString(ya).length() == 2) {
            quinto_parametro = "0" + String.valueOf(ya);
        } else if (Integer.toString(ya).length() == 1) {
            quinto_parametro = "00" + String.valueOf(ya);
        } else {
            quinto_parametro = String.valueOf(ya);
        }

        if (Integer.toString(xa).length() == 1) {
            cuarto_parametro = "0" + String.valueOf(xa);
        } else {
            cuarto_parametro = String.valueOf(xa);
        }

        // "00 00 000 00 000"
        string_envio = String.valueOf("0" + id) + " " + segundo_parametro + " " + tercer_parametro
                + " " + cuarto_parametro + " " + quinto_parametro;
        return string_envio;
    }

    void procesar(int a, int b) {

        ClienteEnvia("rpta ");
    }



}
