
public class Escenario {
    char matriz[][] = new char[40][100];
    Jugador jugadores[] = new Jugador[3];
    
    Escenario() {
        inicializar(matriz);
          
       }
   public void inicializar(char [][] matriz){
    for (int x = 0; x < matriz.length; x++) {
        for (int y = 0; y < matriz[x].length; y++) {
            if (x < 38 & x > 2 & y > 2 & y < 10) {
                matriz[x][y] = ' ';
            } else if (x < 20 & x > 15 & y > 9 & y < 24) {
                matriz[x][y] = ' ';
            } else if (x < 7 & x > 2 & y > 9 & y < 24) {
                matriz[x][y] = ' ';
            } else if (x < 38 & x > 33 & y > 9 & y < 24) {
                matriz[x][y] = ' ';
            } else if (x < 38 & x > 2 & y > 23 & y < 31) {
                matriz[x][y] = ' ';
            } else if (x < 20 & x > 15 & y > 30 & y < 45) {
                matriz[x][y] = ' ';
            } else if (x < 7 & x > 2 & y > 30 & y < 45) {
                matriz[x][y] = ' ';
            } else if (x < 38 & x > 33 & y > 30 & y < 45) {
                matriz[x][y] = ' ';
            } else if (x < 38 & x > 2 & y > 44 & y < 52) {
                matriz[x][y] = ' ';
            } else if (x < 20 & x > 15 & y > 51 & y < 66) {
                matriz[x][y] = ' ';
            } else if (x < 7 & x > 2 & y > 51 & y < 66) {
                matriz[x][y] = ' ';
            } else if (x < 38 & x > 33 & y > 51 & y < 66) {
                matriz[x][y] = ' ';
            } else if (x < 38 & x > 2 & y > 65 & y < 73) {
                matriz[x][y] = ' ';
            } else if (x < 20 & x > 15 & y > 72 & y < 87) {
                matriz[x][y] = ' ';
            } else if (x < 7 & x > 2 & y > 72 & y < 87) {
                matriz[x][y] = ' ';
            } else if (x < 38 & x > 33 & y > 72 & y < 87) {
                matriz[x][y] = ' ';
            } else if (x < 38 & x > 2 & y > 86 & y < 94) {
                matriz[x][y] = ' ';
            } else {
                matriz[x][y] = '*';
            }
        }
    }
}
   
   public void mostrar(){
      for (int x=0; x < matriz.length; x++) {
       for (int y=0; y < matriz[x].length; y++) {
         System.out.print (matriz[x][y]);
         if (y!=matriz[x].length-1);
       }
       System.out.println("");
     
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
   
   public void insertar_jugador(int x, int y, int id){
       jugadores[id] = new Jugador(x, y);
       matriz[x][y] = 'A';
   }
   
   
   public void pintar_campo(int x, int y){
       matriz[x][y] = 'A';
   }
   
   public void actualizar_pos_jugador(int id){
       matriz[jugadores[id].x][jugadores[id].y] = 'A';
               
   }
   
   public void limpiar_campo(int x, int y){
       matriz[x][y] = ' ';
   }
}
