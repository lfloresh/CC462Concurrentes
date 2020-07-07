import java.util.Random;
public class Escenario {
   char matriz[][] = new char[40][100];
    Jugador jugadores[] = new Jugador[30];
    char simbols[] = {'A','B','C','D','E','F','G','H','I'};
    char simbols_bala[] = {'1','2','3','4','5','6','7','8','9'};
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
         
       }
       System.out.println("");
     
}
      
      //PINTAR JUGADORES
      
      
      //PINTAR BOMBAS
       
   }
   //verificar colision
   public boolean esta_ocupado(int x, int y){
       
       if(matriz[x][y] == ' '){
           return false;
       }
       else {
       return true;
       }
       
   }
   
   public void insertar_jugador(int id){
       //generacion random de jugador
       //se llama por primera vez
       int x;
       int y;
       do{
           x = obtener_random_en_rango(0,39);
           y = obtener_random_en_rango(0,99);
       
       }while (matriz[x][y] == '*');
       jugadores[id] = new Jugador(x, y, simbols[id], simbols_bala[id]);
       matriz[x][y] = simbols[id];
   }
   
      public void insertar_jugador(int x, int y, int id){
       
       jugadores[id] = new Jugador(x, y, simbols[id], simbols_bala[id]);
       matriz[x][y] = simbols[id];
   }
   
   

   
   public void pintar_pos_jugador(int id){
       matriz[jugadores[id].x][jugadores[id].y] = jugadores[id].simbol;
               
   }
   
   public void limpiar_campo(int x, int y){
       matriz[x][y] = ' ';
   }
   
   
   private int obtener_random_en_rango(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

   public boolean esta_muerto(int id){
       int x = jugadores[id].x;
        int y = jugadores[id].y;
        int i;
        //caracter en pos en la que el jugador se ha movido
        char pos = matriz[x][y];
        
        //hay un simbolo que es diferente del mio
        char simbol_jugador = jugadores[id].simbol;
        for (i = 0; i<=8 ;i++){
            //lo que hay en esta posicion con los simbolos
            if( pos == simbols_bala[i] & pos != simbol_jugador ){
                return true;
            }
        
        }
        return false;
        
   }
}

