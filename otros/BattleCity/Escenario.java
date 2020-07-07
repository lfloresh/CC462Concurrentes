import java.util.Random;
public class Escenario {
   char matriz[][] = new char[40][100];
    Jugador jugadores[] = new Jugador[30];
    char simbols[] = {'A','B','C','D','E','F','G','H','I'};
    char simbols_bala[] = {'1','2','3','4','5','6','7','8','9'};
    Escenario() {
        inicializar();
          
       }
 public void inicializar(){
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
 public void llenar_jugadores(){
  //rellenar matriz
    int m;
    int n;
    int i = 0;
    while(i < jugadores.length & jugadores[i] != null){
        m = jugadores[i].x;
        n = jugadores[i].y;
        matriz[m][n] = jugadores[i].simbol;
    i++;
    }
 
 }
 
 public void llenar_bombas(){
    int m;
    int n;
    int i = 0;
    int j = 0;
    while(i < jugadores.length & jugadores[i] != null){
       
        while (j < jugadores[i].bala.length & jugadores[i].bala[j] != null){
                    m = jugadores[i].bala[j].x;
                    n = jugadores[i].bala[j].y;
                    matriz[m][n] = jugadores[i].bala_simbol;
            j++;
        }
      j=0;
    i++;
    }
 
 }
 public void refrescar_pantalla(){
     inicializar();
     llenar_bombas();
     llenar_jugadores();
     mostrar();
     
 }
 
   public void mostrar(){
      for (int x=0; x < matriz.length; x++) {
       for (int y=0; y < matriz[x].length; y++) {
           
           
         System.out.print (matriz[x][y]);
         
       }
       System.out.println("");
     
}

       
   }
   //verificar colision
   public boolean esta_ocupado(int x, int y){
       
       if(matriz[x][y] != '*'){
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
       //matriz[x][y] = simbols[id];
   }
   
   
   
   private int obtener_random_en_rango(int min, int max) {

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

   public boolean esta_muerto(int id ){
      
        int i =0;
        int m;
        int j = 0;
        int n;

    while(i < jugadores.length && jugadores[i] != null){
        //excluir al propio jugador (permite choque con propias balas)
        // V V

        if (i != id && jugadores[i].bala_simbol != ' ' ){
            
        while (j < jugadores[i].bala.length && jugadores[i].bala[j] != null){
                    m = jugadores[i].bala[j].x;
                    n = jugadores[i].bala[j].y;
                    //revisar logica
                        if (m == jugadores[id].x && n == jugadores[id].y){
                            return true;
                        }
                    j++;
                }
        }
            j=0;
            i++;
           
        //return false;
    }
        return false;
   }
   
  public void matar(int id){
      jugadores[id].simbol= ' ';
      jugadores[id].bala_simbol = ' ';
  }
}

