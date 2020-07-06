
public class Escenario {
     char matriz[][] = new char[40][130];
     Jugador jugadores[] = new Jugador[3];
     BalaThread balasthread[] = new BalaThread[3];
     Escenario() {
         inicializar(matriz);
           
        }
     
    //pinta escenario
    public void inicializar(char [][] matriz){
          for (int x=0; x < matriz.length; x++) {

        for (int y=0; y < matriz[x].length; y++) {
            if (x < 38 & x > 2 & y > 2 & y < 10){
              matriz[x][y] = ' ';
            }
            
            else if (x < 20 & x > 15 & y > 9 & y < 31){
                matriz[x][y] = ' ';
            }
            else if (x < 7 & x > 2 & y > 9 & y < 31){
                matriz[x][y] = ' ';
            }
            else if (x < 38 & x > 33 & y > 9 & y < 31){
                matriz[x][y] = ' ';
            }

            else if (x < 38 & x > 2 & y > 30 & y < 38){
                matriz[x][y] = ' ';
            }
            
            else if (x < 20 & x > 15 & y > 37 & y < 59){
                matriz[x][y] = ' ';
            }
            else if (x < 7 & x > 2 & y > 37 & y < 59){
                matriz[x][y] = ' ';
            }
            else if (x < 38 & x > 33 & y > 37 & y < 59){
                matriz[x][y] = ' ';
            }
            
            else if (x < 38 & x > 2 & y > 58 & y < 66){
                matriz[x][y] = ' ';
            }

            else if (x < 20 & x > 15 & y > 65 & y < 87){
                matriz[x][y] = ' ';
            }
            else if (x < 7 & x > 2 & y > 65 & y < 87){
                matriz[x][y] = ' ';
            }
            else if (x < 38 & x > 33 & y > 65 & y < 87){
                matriz[x][y] = ' ';
            }
            
            else if (x < 38 & x > 2 & y > 86 & y < 94){
                matriz[x][y] = ' ';
            }
            
            else if (x < 20 & x > 15 & y > 93 & y < 115){
                matriz[x][y] = ' ';
            }
            else if (x < 7 & x > 2 & y > 93 & y < 115){
                matriz[x][y] = ' ';
            }
            else if (x < 38 & x > 33 & y > 93 & y < 115){
                matriz[x][y] = ' ';
            }
            
            else if (x < 38 & x > 2 & y > 114 & y < 122){
                matriz[x][y] = ' ';
            }
            
            
            else {
                matriz[x][y] = '*';
            }
        }
    }
}
    
    public void mostrar(){
       for (int x=0; x < matriz.length; x++) {
        System.out.print("|");
        for (int y=0; y < matriz[x].length; y++) {
          System.out.print (matriz[x][y]);
          if (y!=matriz[x].length-1);
        }
        System.out.println("|");
      
}
        
    }
    //verificar colision
    public boolean esta_ocupado(int x, int y){
        
        if(matriz[x][y] == '*'){
            return true;
        }
        else {
        return false;
        }
        
    }
    
    public void insertar_jugador(int x, int y, int id, char simbol){
        jugadores[id] = new Jugador(x, y, simbol);
        matriz[x][y] = simbol;
    }
    
    
    public void pintar_pos_jugador(int id){
        matriz[jugadores[id].x][jugadores[id].y] = jugadores[id].simbol;    
    }
    
    public void limpiar_campo(int x, int y){
        matriz[x][y] = ' ';
    }
    
    
    public void pintar_pos_bala(int id){
        matriz[balasthread[id].x][balasthread[id].y] = 'o';    
    }

        //verificar paso de objeto escenario
    public void insertar_bala(int x, int y, int id, Escenario obj){
            balasthread[id] = new BalaThread(x, y, id, obj);

    }


    

}

